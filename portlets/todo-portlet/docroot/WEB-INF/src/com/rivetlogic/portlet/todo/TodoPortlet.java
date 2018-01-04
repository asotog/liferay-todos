/**
 * Copyright (C) 2005-2014 Rivet Logic Corporation.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.rivetlogic.portlet.todo;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.rivetlogic.portlet.todo.bean.TasksBean;
import com.rivetlogic.portlet.todo.beans.PreferencesBean;
import com.rivetlogic.portlet.todo.model.Task;
import com.rivetlogic.portlet.todo.model.impl.TaskImpl;
import com.rivetlogic.portlet.todo.service.TaskLocalServiceUtil;
import com.rivetlogic.portlet.todo.util.Constants;
import com.rivetlogic.portlet.todo.util.TodoUtil;
import com.rivetlogic.portlet.todo.validator.TodoValidator;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.portlet.PortletException;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author christopherjimenez
 * 
 */
public class TodoPortlet extends MVCPortlet {
    private static final Log LOG = LogFactoryUtil.getLog(TodoPortlet.class);
    
    private static final String COMMAND_KEY = "cmd";
    private static final String COMMAND_SUCCESS = "success";
    private static final String COMMAND_TOGGLE_TASK = "toggle-task";
    private static final String COMMAND_LIST_TASKS = "list-tasks";
    private static final String COMMAND_ADD_TASK = "add-task";
    private static final String COMMAND_DELETE_TASK = "delete-task";
    private static final String COMMAND_UPDATE_TASK = "update-task";
    private static final String DATE_DAY = "day";
    private static final String DATE_MONTH = "month";
    private static final String DATE_YEAR = "year";
    
    private static final int DEFAULT_INT_VALUE = 0;
    
    @Override
    public void render(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        PreferencesBean prefBean = new PreferencesBean(request);
        request.setAttribute(Constants.PREF_BEAN, prefBean);
        super.render(request, response);
    }
    /**
    
    public void savePreferences(ActionRequest request, ActionResponse response) throws ReadOnlyException, ValidatorException, IOException{
        PreferencesBean prefBean = new PreferencesBean();
        prefBean.save(request);
        SessionMessages.add(request, PortalUtil.getPortletId(request) +
                SessionMessages.KEY_SUFFIX_UPDATED_PREFERENCES);
        
        String redirect = getRedirect(request, response);

        if (Validator.isNotNull(redirect)) {
            response.sendRedirect(redirect);
        }
    }
     */
    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
        PortletException {
        HttpServletRequest httpReq = PortalUtil.getOriginalServletRequest(PortalUtil
                .getHttpServletRequest(resourceRequest));
        ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
        JSONObject jsonObject = null;
        String cmd = ParamUtil.getString(httpReq, COMMAND_KEY);
        if (COMMAND_TOGGLE_TASK.equals(cmd)) {
            jsonObject = JSONFactoryUtil.createJSONObject();
            toggleTask(httpReq, jsonObject);
        }
        if (COMMAND_LIST_TASKS.equals(cmd)) {
            listTasks(resourceResponse, themeDisplay);
        }
        if (COMMAND_ADD_TASK.equals(cmd)) {
            jsonObject = JSONFactoryUtil.createJSONObject();
            addTask(httpReq, jsonObject);
        }
        if (COMMAND_DELETE_TASK.equals(cmd)) {
            jsonObject = JSONFactoryUtil.createJSONObject();
            deleteTask(httpReq, jsonObject);
        }
        if (COMMAND_UPDATE_TASK.equals(cmd)) {
            jsonObject = JSONFactoryUtil.createJSONObject();
            updateTask(httpReq, jsonObject);
        }
        
        if (jsonObject != null) {
            TodoUtil.returnJSONObject(resourceResponse, jsonObject);
        }
    }
    
    private void toggleTask(HttpServletRequest request, JSONObject jsonObject) {
        Long taskId = ParamUtil.getLong(request, TasksBean.JSON_TASK_DATA_ID, TasksBean.UNDEFINED_ID);
        if (taskId != TasksBean.UNDEFINED_ID) {
            try {
                Task task = TaskLocalServiceUtil.getTask(taskId);
                task.setCompleted(!task.getCompleted());
                TaskLocalServiceUtil.updateTask(task);
                jsonObject.put(COMMAND_SUCCESS, true);
            } catch (Exception e) {
                jsonObject.put(COMMAND_SUCCESS, false);
                LOG.error(e);
            }
        } else {
            jsonObject.put(COMMAND_SUCCESS, false);
        }
    }
    
    private void listTasks(PortletResponse response, ThemeDisplay themeDisplay) {
        TasksBean tb = new TasksBean(themeDisplay.getUserId(), new Date());
        TodoUtil.returnJSONObject(response, tb.toJSON());
    }
    
    private void addTask(HttpServletRequest request, JSONObject jsonObject) {
        Task task = createTaskFromRequest(request);
        long[] reminders;
        String[] remindersType;
        long calendarId = ParamUtil.getLong(request, TasksBean.JSON_TASK_DATA_CALENDAR_ID, TasksBean.UNDEFINED_ID);
        if (TodoValidator.validateNewTask(task)) {
            try {
            	// add task to liferay calendar only if calendarId is valid
            	if (calendarId != TasksBean.UNDEFINED_ID) {
            		reminders = getReminders(request);
            		remindersType = getRemindersType(request);
            		CalendarBooking cb = addCalendarBooking(request, task, calendarId, reminders, remindersType);
            		task.setCalendarBookingId( cb == null? TasksBean.UNDEFINED_ID : cb.getCalendarBookingId() );
            	} else {
            		// if no calendar was selected, assing a default value to the field
            		task.setCalendarBookingId(TasksBean.UNDEFINED_ID);
            	}
            	
            	task = TaskLocalServiceUtil.createTask(task);
                jsonObject.put(COMMAND_SUCCESS, true);
                jsonObject.put(TasksBean.JSON_TASK_DATA_ID, task.getTaskId());
            } catch (Exception e) {
                jsonObject.put(COMMAND_SUCCESS, false);
                LOG.error(e);
            }
        } else {
            jsonObject.put(COMMAND_SUCCESS, false);
        }
        
    }
    
    private void deleteTask(HttpServletRequest request, JSONObject jsonObject) {
        Long taskId = ParamUtil.getLong(request, TasksBean.JSON_TASK_DATA_ID, TasksBean.UNDEFINED_ID);
        Long calendarBookingId = ParamUtil.getLong(request, TasksBean.JSON_TASK_DATA_CALENDAR_BOOKING_ID, TasksBean.UNDEFINED_ID);
        if (taskId != TasksBean.UNDEFINED_ID) {
            try {
            	// if the task was added to a liferay calendar
            	if (calendarBookingId != TasksBean.UNDEFINED_ID) {
            		// delete the task from the liferay calendar
            		deleteCalendarBooking(calendarBookingId);
            	}
                TaskLocalServiceUtil.deleteTask(taskId);
                jsonObject.put(COMMAND_SUCCESS, true);
            } catch (Exception e) {
                jsonObject.put(COMMAND_SUCCESS, false);
                LOG.error(e);
            }
        } else {
            jsonObject.put(COMMAND_SUCCESS, false);
        }
    }
    
    private void updateTask(HttpServletRequest request, JSONObject jsonObject) {
        Long taskId = ParamUtil.getLong(request, TasksBean.JSON_TASK_DATA_ID, TasksBean.UNDEFINED_ID);
        Long calendarBookingId = ParamUtil.getLong(request, TasksBean.JSON_TASK_DATA_CALENDAR_BOOKING_ID, TasksBean.UNDEFINED_ID);
        Long calendarId = ParamUtil.getLong(request, TasksBean.JSON_TASK_DATA_CALENDAR_ID, TasksBean.UNDEFINED_ID);
        try {
            Task task = TaskLocalServiceUtil.getTask(taskId);
            setCommonTaskFields(request, task);
            if (TodoValidator.validateNewTask(task)) {
            	// if the task was added to a liferay calendar
            	if (calendarId != TasksBean.UNDEFINED_ID) {
            		// update the task info in the liferay calendar
            		CalendarBooking cb = updateCalendarBooking(request, task, calendarBookingId, calendarId);
            		task.setCalendarBookingId( cb == null? TasksBean.UNDEFINED_ID : cb.getCalendarBookingId() );
            	}
            	
                task = TaskLocalServiceUtil.updateTask(task);
                jsonObject.put(COMMAND_SUCCESS, true);
                jsonObject.put(TasksBean.JSON_TASK_DATA_ID, task.getTaskId());
            } else {
                jsonObject.put(COMMAND_SUCCESS, false);
            }
        } catch (Exception e) {
            jsonObject.put(COMMAND_SUCCESS, false);
            LOG.error(e);
        }
    }
    
    private void setCommonTaskFields(HttpServletRequest request, Task task) {
        Calendar calendar = getDateFromRequest(request);
        task.setDate(calendar.getTime());
        task.setDescription(ParamUtil.getString(request, TasksBean.JSON_TASK_DATA_DESCRIPTION, StringPool.BLANK));
        task.setName(ParamUtil.getString(request, TasksBean.JSON_TASK_DATA_NAME, StringPool.BLANK));
    }
    
    private Task createTaskFromRequest(HttpServletRequest request) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        Task task = new TaskImpl();
        task.setCompleted(false);
        task.setUserId(themeDisplay.getUserId());
        setCommonTaskFields(request, task);
        return task;
    }
    
    private Calendar getDateFromRequest(HttpServletRequest request) {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        
        int day = ParamUtil.getInteger(request, DATE_DAY, DEFAULT_INT_VALUE);
        int month = ParamUtil.getInteger(request, DATE_MONTH, DEFAULT_INT_VALUE);
        int year = ParamUtil.getInteger(request, DATE_YEAR, DEFAULT_INT_VALUE);
        
        Calendar jCalendar = CalendarFactoryUtil.getCalendar(themeDisplay.getTimeZone());

		jCalendar.set(Calendar.YEAR, year);
		jCalendar.set(Calendar.MONTH, month);
		jCalendar.set(Calendar.DATE, day);
		jCalendar.set(Calendar.HOUR_OF_DAY, 0);
		jCalendar.set(Calendar.MINUTE, 0);
		jCalendar.set(Calendar.SECOND, 0);
		jCalendar.set(Calendar.MILLISECOND, 0);

		return jCalendar;
        
    }

        
    private CalendarBooking addCalendarBooking(HttpServletRequest request, Task task, long calendarId, long[] reminders, String[] remindersType) throws PortalException, SystemException {

        Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		
		titleMap.put(ServiceContextFactory.getInstance(request).getLocale(), task.getName());
		descriptionMap.put(ServiceContextFactory.getInstance(request).getLocale(), task.getDescription());
		
        return CalendarBookingLocalServiceUtil.addCalendarBooking(PortalUtil.getUserId(request),
        	calendarId, new long[]{}, 0l, titleMap, descriptionMap,
			StringPool.BLANK, task.getDate().getTime(),
			task.getDate().getTime(), true, "", reminders[0], remindersType[0], reminders[1], remindersType[1],
			ServiceContextFactory.getInstance(request));
    }
    
    private CalendarBooking updateCalendarBooking(HttpServletRequest request, Task task, long calendarBookingId, long calendarId) throws PortalException, SystemException {
    	CalendarBooking cb = (calendarBookingId == TasksBean.UNDEFINED_ID)? null : CalendarBookingLocalServiceUtil.getCalendarBooking(calendarBookingId);
    	long[] reminders = getReminders(request);
		String[] remindersType = getRemindersType(request);
    	if (cb != null) {
    		cb.setStartTime(task.getDate().getTime());
    		cb.setEndTime(task.getDate().getTime());
    		cb.setTitle(task.getName());
    		cb.setDescription(task.getDescription());
    		cb.setCalendarId(calendarId);
    		cb.setFirstReminder(reminders[0]);
    		cb.setFirstReminderType(remindersType[0]);
    		cb.setSecondReminder(reminders[1]);
    		cb.setSecondReminderType(remindersType[1]);
    		CalendarBookingLocalServiceUtil.updateCalendarBooking(cb);
    	} else {
    		cb = addCalendarBooking(request, task, calendarId, reminders, remindersType);
    	}
    	    	
    	return cb;
    }
    
    private void deleteCalendarBooking(long calendarBookingId) {
    	try {
			CalendarBookingLocalServiceUtil.deleteCalendarBooking(calendarBookingId);
		} catch (Exception e) {
			LOG.error(e);
		}
    }
    
    private long[] getReminders(HttpServletRequest request) {
    	long firstReminderValue = ParamUtil.getLong(request, TasksBean.JSON_TASK_FIRST_REMINDER_VALUE, DEFAULT_INT_VALUE);
    	long firstReminderDuration = ParamUtil.getLong(request, TasksBean.JSON_TASK_FIRST_REMINDER_DURATION, DEFAULT_INT_VALUE);
    	long secondReminderValue = ParamUtil.getLong(request, TasksBean.JSON_TASK_SECOND_REMINDER_VALUE, DEFAULT_INT_VALUE);
    	long secondReminderDuration = ParamUtil.getLong(request, TasksBean.JSON_TASK_SECOND_REMINDER_DURATION, DEFAULT_INT_VALUE);
    	
    	return new long[] {
    		firstReminderValue * firstReminderDuration,
    		secondReminderValue * secondReminderDuration
    	};
    }
    
    /**
     * Gets the type for each reminder. For now, the liferay's Calendar Portlet only supports 'email'.
     * @param request HttpServletRequest to get the parameters from.
     * @return An array of String with the corresponding type of each reminder.
     */
    private String[] getRemindersType(HttpServletRequest request) {
    	String firstReminderType = ParamUtil.getString(request, TasksBean.JSON_TASK_FIRST_REMINDER_TYPE, StringPool.BACK_SLASH);
    	String secondReminderType = ParamUtil.getString(request, TasksBean.JSON_TASK_SECOND_REMINDER_TYPE, StringPool.BACK_SLASH);
    	
    	return new String[] {
    		firstReminderType,
    		secondReminderType
    	};
    }
}
