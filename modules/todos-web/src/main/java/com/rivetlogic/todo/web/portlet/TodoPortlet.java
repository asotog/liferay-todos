package com.rivetlogic.todo.web.portlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.rivetlogic.todo.keys.ToDosKeys;
import com.rivetlogic.todo.model.Task;
import com.rivetlogic.todo.service.TaskLocalServiceUtil;
import com.rivetlogic.todo.util.TasksBean;
import com.rivetlogic.todo.util.TodoUtil;
import com.rivetlogic.todo.validator.TodoValidator;

/**
 * @author emmanuelabarca
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name="+ToDosKeys.PORTLET_ID,
		"com.liferay.portlet.icon=/icon.png",
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.display-name=todos-web Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.init-param.help-template=/help.jsp",
		"javax.portlet.portlet-mode=text/html;view,help",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,power-user,user,guest",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
        "com.liferay.portlet.header-portlet-css=/css/main_responsive.css",
		"com.liferay.portlet.footer-portlet-javascript=/js/todo.js",
		"com.liferay.portlet.css-class-wrapper=todo-portlet",
		"javax.portlet.init-param.copy-request-parameters=false"
	},
	service = Portlet.class
)
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
                // If booking exists and marked as done, we remove from calendar the event, in order to prevent sending reminder when 
                // already marked as done
                if (task.getCalendarBookingId() != TasksBean.UNDEFINED_ID) {
                	if (task.getCompleted()) {
                    	// remove booking
                    	CalendarBookingLocalServiceUtil.moveCalendarBookingToTrash(task.getUserId(), task.getCalendarBookingId());
                    } else {
                    	// restore booking
                    	CalendarBookingLocalServiceUtil.restoreCalendarBookingFromTrash(task.getUserId(), task.getCalendarBookingId());
                    }
                }
                
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
        TasksBean tb = new TasksBean(themeDisplay);
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
        Task task = TaskLocalServiceUtil.createTask(0);
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
//        Calendar calendar = Calendar.getInstance(themeDisplay.getTimeZone());
//        calendar.set(year, month, day, 0, 0, 0);
//        //return calendar;
//        // convert to utc
//        Calendar utcCalendar = Calendar.getInstance();
//        utcCalendar.setTime(calendar.getTime());
//        return utcCalendar;
        return JCalendarUtil.getJCalendar(year, month, day, 0, 0, 0, 0, themeDisplay.getTimeZone());
    }
        
    private CalendarBooking addCalendarBooking(HttpServletRequest request, Task task, long calendarId, long[] reminders, String[] remindersType) throws PortalException, SystemException {

        Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		
		titleMap.put(ServiceContextFactory.getInstance(request).getLocale(), task.getName());
		descriptionMap.put(ServiceContextFactory.getInstance(request).getLocale(), task.getDescription());
		CalendarBooking cb = CalendarBookingLocalServiceUtil.addCalendarBooking(PortalUtil.getUserId(request),
        	calendarId, new long[]{}, CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT, titleMap, descriptionMap,
			StringPool.BLANK, task.getDate().getTime(),
			task.getDate().getTime(), true, "", reminders[0], remindersType[0], reminders[1], remindersType[1],
			ServiceContextFactory.getInstance(request));
		return updateCalendarBooking(request, task, cb.getCalendarBookingId(), calendarId);
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
