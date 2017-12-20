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

AUI.add('todo-portlet', function (Y, NAME) {
    var LIFERAY_PHONE_MEDIA_BREAK = 768;
    var TASK_VALIDATION_RULES = {
        title: {
            rangeLength: [4, 75]
        },
        description: {
            required: true
          },
        time: {
            required: true
          }
    };
    
    var SELECT_CALENDAR = '.select-calendar';
    var CHECKBOX_CALENDAR = '.chk-calendar';
    var CHECKBOX_REMINDER = '.chk-reminder';
    var UNDEFINED_CALENDAR_ID = -1; // matches the value of TasksBean.java
    var REMINDERS_BOX = '.reminders';
    var REMINDERS_HIDDEN_CLASS = 'reminders-hidden';
    var REMINDER_BOX = '.reminder';
    var REMINDER_VALUE = '.reminder-value';
    var REMINDER_DURATION = '.reminder-duration';
    var FIRST_REMINDER_VALUE = '.first-reminder-value';
    var FIRST_REMINDER_DURATION = '.first-reminder-duration';
    var SECOND_REMINDER_VALUE = '.second-reminder-value';
    var SECOND_REMINDER_DURATION = '.second-reminder-duration';
    
    Y.Todo = Y.Base.create('todo-portlet', Y.Base, [], {

        lis: null,
        activities: null,
        deleteActivity: null,
        submit: null,
        cancel: null,
        done: null,
        undo: null,
        inputs: null,
        goingUp: null,
        last: null,
        addButton: null,
        toggler: null,
        
        initializer: function () {
            var box = this.get('container');      
            this.addButton = box.one('.add-submit');
            this.goingUp = false,
            this.lastY = 0;
            this.setComponents();
            this.setAddTask();
            this.updateTaskListUI(); 
            
        },
        
        /**
         * Updates tasks list with the data provided
         * 
         */
        updateTaskListUI: function(callback) {
            var me = this;
            var box = this.get('container');
            var dataKeys = ['previousTasks', 'todayTasks', 'tomorrowTasks', 'futureTasks'];
            var containerClasses = ['.previous', '.today', '.tomorrow', '.future'];
            var dateFieldType = (this.get('isMobile') ? "date" : "text");
            this.listTasksCall({}, function(data) {          
                for (var i = 0; i < dataKeys.length; i++) {
                    var tasks = data[dataKeys[i]];
                    var markup = '';
                    var count = "("+tasks.length+")";
                    var firstReminderSelectProperty = '';
                    var secondReminderSelectProperty = '';
                    box.one(containerClasses[i]+'-tasks-count').set('innerHTML',count);
                    for (var j = 0; j < tasks.length; j++) {
                    	tasks[j].dateFieldType = dateFieldType;
                        tasks[j].done = (tasks[j].isCompleted) ? "done" : "";
                        if (tasks[j])
                        // checkbox
                        tasks[j].checked = (tasks[j].calendarId !== UNDEFINED_CALENDAR_ID)? "checked": "";
                        // checkbox for reminders
                        tasks[j].firstReminderChecked = (parseInt(tasks[j].firstReminderValue) !== 0)? "checked": "";
                        tasks[j].secondReminderChecked = (parseInt(tasks[j].secondReminderValue) !== 0)? "checked": "";
                        // reminders
                        tasks[j].firstReminderValue = (tasks[j].firstReminderValue !== 0) ? me.calcReminderValue(tasks[j].firstReminderValue, tasks[j].firstReminderDuration) : "";
                        tasks[j].secondReminderValue = (tasks[j].secondReminderValue !== 0)? me.calcReminderValue(tasks[j].secondReminderValue, tasks[j].secondReminderDuration) : "";
                        firstReminderSelectProperty = "first" + tasks[j].firstReminderDuration;
                        tasks[j][firstReminderSelectProperty] = "selected";
                        secondReminderSelectProperty = "second" + tasks[j].secondReminderDuration;
                        tasks[j][secondReminderSelectProperty] = "selected";
                         
                        // select
                        tasks[j][tasks[j].calendarId] = "selected";
                        markup += Y.Lang.sub(box.one('#' + me.get('portletNamespace') + 'task-list-item-template').get('innerHTML'), tasks[j]);
                        // removed text that was not substituted
                        markup = markup.replace(/{\w+}/g, "");
                    }
                    box.one('.tasks' + containerClasses[i]).empty();
                    box.one('.tasks' + containerClasses[i]).append(markup);
                }
                me.bindTasksList();
                if (typeof callback == 'function') {
                    callback();
                }
            });
        },
        
        /**
         * Calculates the value that is going to be displayed to the user based on the duration type (hours, minutes, days etc...)
         * 
         */
        calcReminderValue: function(value, duration) {
            var calc = value / duration;
            return isNaN(calc) ? '' : calc;
        },

        /**
         * Creates a calendar component with the given field selector as trigger
         * 
         */
        _createCalendar: function(trigger) {
            var calendarClass = this.get('calendarWidgetClass');
            var datePicker = new calendarClass({
                mask:  '%Y-%m-%d',
                on: {
                    disabledChange: function(event) {

                    }
                    // remove validation which fails when the value is changed through javascript
                    // and not by the user, as in this case
                },
                after: {
                    selectionChange: function(event) {
                        var group = Y.one(".lfr-input-date").get("parentNode");

                            group.removeClass("error").addClass("success");

                            try {
                                group.one(".help-inline").remove(false);
                            }
                            catch(e) {

                            }
                        
                    }
                },
                calendar: {
                    minimumDate: new Date()
                },
                popover: {
                    zIndex: Liferay.zIndex.TOOLTIP
                },
                trigger: trigger
            });
            return datePicker;
        },
        
        /**
         * Binds events to current list
         * 
         */
        bindTasksList: function() {
            var me = this;
            var box = this.get('container');
            this.lis = box.all(".tasks li:not(.add-reminder):not(.done)");
            this.activities = box.all(".activities-list .activity");
            this.deleteActivity = box.all(".activity-delete");
            this.submit = box.all(".edit-submit");
            this.cancel = box.all(".edit-cancel");
            this.done = box.all(".activity-finished");
            this.undo = box.all(".activity-undo");
            this.inputs = box.all(".edit input, .edit button, .edit textarea");
            
            /* initializes calendar and select */
            box.all(".tasks li").each(function(node) {
                me._createCalendar('#' + node.one('.edit-time').get('id'));
                
                // only disable the select when the task was not added to the liferay calendar
                if (node.one(CHECKBOX_CALENDAR) && !node.one(CHECKBOX_CALENDAR).attr("checked")) {
                	node.one(SELECT_CALENDAR).setAttribute("disabled", "disabled");
                	// if select is disabled, hide the reminders container
                	node.one(REMINDERS_BOX).addClass(REMINDERS_HIDDEN_CLASS);
                }
                
                // reminder select and input status
                node.all(CHECKBOX_REMINDER).each(function (reminder) {
                	// no reminder was set
                	if (!reminder.attr('checked')) {
                		// disable the input and select elements for the reminder
                		var reminderDiv = reminder.ancestor(REMINDER_BOX);
                		reminderDiv.one(REMINDER_VALUE).setAttribute('disabled', 'disabled');
                		reminderDiv.one(REMINDER_DURATION).setAttribute('disabled', 'disabled');
                	}
                });
                
                /* check if LR calendar integration enabled from config */
                if (!node.one(CHECKBOX_CALENDAR)) {
                  return;
                }
                // Add to calendar checkbox on edit
                node.one(CHECKBOX_CALENDAR).on('change', function (event) { 
                	me.checkCalendarHandler(event, node.one(SELECT_CALENDAR));
                	node.one(REMINDERS_BOX).toggleClass(REMINDERS_HIDDEN_CLASS);
                });
                
                // Add reminders checkboxes on edit
                node.all(CHECKBOX_REMINDER).on('change', function (event) {
                	var reminderDiv = event.target.ancestor(REMINDER_BOX);
                	me.checkReminderHandler(event, reminderDiv.one(REMINDER_VALUE), reminderDiv.one(REMINDER_DURATION));
                });
            });
            /** Shows edit mode when clicking on a task **/
            this.activities.each(function (activity) {
                activity.on("click", function () {
                    var element = me.getMembers(this.get("parentNode"));
                    me.fire('activityOpened', element);
                    //element.titleInput.set("value",element.title.get("text"));
                    //element.timeInput.set("value",element.time.get("text"));
                });

            });

            /* delete task */
            this.deleteActivity.each(function (button) {
                button.on("click", function (e) {
                    e.stopPropagation();
                    
                    var message = Liferay.Language.get('todo-confirm-task-delete');
                    var actionConfirmation = confirm(message);
            		if (actionConfirmation == true){

	                    var listNode = this.get("parentNode").get("parentNode");
	                    var element = me.getMembers(listNode);
	                    var listHeader = listNode.ancestor('li').one('span.subtitle');
	                    var oldCountStr =  listHeader.one('.taskscount').get('innerHTML').replace("(","").replace(")","");
	                    var oldCount = parseInt(oldCountStr);
	                    var newCount = (oldCount != NaN? oldCount-1:0);
	                    var newCountStr = "("+newCount+")";
	                    var id = element.edit.one('.edit-task-id').get('value');
	                    var calendarBookingId = element.edit.one('.edit-calendar-booking-id').get('value');
	                    
	                    me.deleteTaskCall(
                    		{
                    			taskId: id,
                    			calendarBookingId: calendarBookingId
                    		}, function() {
	                        element.li.remove(true);
                            listHeader.one('.taskscount').set('innerHTML', newCountStr);
                            me.reloadCalendar();
	                    });
            		}
                });
            });

            /* update task submit */
            this.submit.each(function (button) {
                var cont = button.get("parentNode").get("parentNode").get("parentNode");
                var validator = new Y.FormValidator({
                    boundingBox: cont.one('form'),
                    rules: TASK_VALIDATION_RULES
                });
                button.on("click", function (e) {
                    e.preventDefault();
                    e.stopPropagation();
                    if (cont.all('.error').size() == 0) {
                        var element = me.getMembers(this.ancestor('[id^="task-"]'));
                        var id = element.edit.one('.edit-task-id').get('value');
                        var calendarId = me.getCalendarId(element.selectCalendar);
                        var calendarBookingId = element.edit.one('.edit-calendar-booking-id').get('value');
                        var title = element.edit.one('.edit-title').get('value');
                        var description = element.edit.one('.edit-description').get('value');
                        var date = element.edit.one('.lfr-input-date input').get('value');
                        var firstReminderValue = me.getReminderValue(element.edit.one(FIRST_REMINDER_VALUE));
                        var firstReminderDuration = me.getReminderValue(element.edit.one(FIRST_REMINDER_DURATION));
                        var secondReminderValue = me.getReminderValue(element.edit.one(SECOND_REMINDER_VALUE));
                        var secondReminderDuration = me.getReminderValue(element.edit.one(SECOND_REMINDER_DURATION));
                        
                        date = new Date(date);
                        
                        me.updateTaskCall(
                    		{
                        		taskId: id, 
                        		name: title, 
                        		description: description, 
                        		day: (date.getDate() + 1), 
                        		month: date.getMonth(), 
                        		year: date.getFullYear(),
                        		calendarId: calendarId, 
                        		calendarBookingId: calendarBookingId,
                        		firstReminderType: 'email',
                    			firstReminderDuration: firstReminderDuration,
                    			firstReminderValue: firstReminderValue,
                    			secondReminderType: 'email',
                    			secondReminderDuration: secondReminderDuration,
                    			secondReminderValue: secondReminderValue,
                    		}, function() {
                    			me.updateTaskListUI(function() {
                                me.openTaskGroup(id);
                                me.reloadCalendar();
                            });
                        });
                    }
                });
            });

            /* cancel the task edit form */
            this.cancel.each(function (button) {
                button.on("click", function (e) {
                    e.preventDefault();
                    e.stopPropagation();
                    var element = me.getMembers(this.ancestor('[id^="task-"]'));
                    me.closeActivity(element);
                });
            });
            
            this.on('activityOpened', function(e) {
                box.all(".tasks li").each(function() {
                    var element = me.getMembers(this);
                    me.closeActivity(element);
                });
                e.activity.addClass("hide");
                e.edit.addClass("show");
            });
            
            /** Sets the function that marks an activity as finished when clicking on the check mark **/
            this.done.each(function (button) {
                button.on("click", function (e) {
                    e.stopPropagation();
                    var element = me.getMembers(this.get("parentNode").get("parentNode"));
                    me.toggleTaskCall({taskId: element.edit.one('input[type="hidden"]').get('value')}, function() {});
                    element.li.addClass("done");
                });
            });

            this.undo.each(function (button) {
                button.on("click", function (e) {
                    e.stopPropagation();
                    var element = me.getMembers(this.get("parentNode").get("parentNode"));
                    me.toggleTaskCall({taskId: element.edit.one('input[type="hidden"]').get('value')}, function() {});
                    element.li.removeClass("done");
                });
            });

            /** Removes bubbling so that edit panel is not shown when clicking on a button **/
            this.inputs.each(function (button) {
                button.on("click", function (e) {
                    e.stopPropagation();
                });
            });
        },
        
        closeActivity: function(element) {
            element.activity.addClass("show").removeClass("hide");
            element.edit.addClass("hide").removeClass("show");
        },
        
        getCalendarId: function(select) {
          if (!select) {
              return UNDEFINED_CALENDAR_ID;
          }
        	return select.attr("disabled")? UNDEFINED_CALENDAR_ID : select.val();
        },
        
        getReminderValue: function(inputReminder) {
          if (!inputReminder) {
            return 0;
          }
        	return inputReminder.attr("disabled")? 0: inputReminder.val();
        },
        
        getReminderDuration: function(selectReminder) {
          if (!selectReminder) {
            return 0;
          }
        	return selectReminder.attr("disabled")? 0: selectReminder.val();
        },
        
        /**
         * Common ajax wrapper
         * 
         * @param Object configuration
         * { 
         *    method: '',
         *    data: {}
         * }
         * @param String url for testing purposes
         */
        executeAjax: function(configuration, callback, testResponseUrl) {
            var url = testResponseUrl ? testResponseUrl : this.get('resourceUrl');
            configuration.data.ajax_timestamp = new Date().getTime();
            Y.io.request(url, {
                method: configuration.method,
                data: configuration.data,
                dataType: 'json',
                on: {
                    success: function(e) {
                        var data = this.get('responseData');
                        callback(data);
                    }
                }
            });
        },
        
        /**
         * Add task call
         * 
         */
        addTaskCall: function(data, callback) {
            data.cmd = 'add-task';
            this.executeAjax({method: 'POST', data: data}, function(d) {
                callback(d); 
            });
        },
        
        /**
         * Delete
         * 
         */
        deleteTaskCall: function(data, callback) {
            data.cmd = 'delete-task';
            this.executeAjax({method: 'POST', data: data}, function() {
                callback(); 
            });
        },
        
        /**
         * Toggles task from undone to done and viceversa
         * 
         */
        toggleTaskCall: function(data, callback) {
            data.cmd = 'toggle-task';
            this.executeAjax({method: 'POST', data: data}, function() {
                callback(); 
            });
        },
        
        /**
         * Update
         * 
         */
        updateTaskCall: function(data, callback) {
            data.cmd = 'update-task';
            this.executeAjax({method: 'POST', data: data}, function() {
                callback(); 
            });
        },
        
        /**
         * List
         * 
         */
        listTasksCall: function(data, callback) {
            data.cmd = 'list-tasks';
            this.executeAjax({method: 'GET', data: data}, function(d) {
                callback(d); 
            });
        },
        
        /**
         * Retrieve device view port
         * 
         */
        getViewport: function() {
            var e = window, a = 'inner';
            if (!('innerWidth' in window )) {
                a = 'client';
                e = document.documentElement || document.body;
            }
            return { width : e[ a+'Width' ] , height : e[ a+'Height' ] };
        },
        
        /**
         * Configs add task popup
         */
        setAddTask: function () {
            var me = this;
            var modal = new Y.Modal({
                bodyContent: Y.Lang.sub(this.get('container').one('#' +  me.get('portletNamespace') + 'add-task-template').get('innerHTML'), {
                    dateFieldType: (this.get('isMobile') ? "date" : "text")
                }),
                centered: !(me.getViewport().width < LIFERAY_PHONE_MEDIA_BREAK),
                modal: true,
                width: 500,
                headerContent: Liferay.Language.get('add-task'),
                visible: false,
                zIndex: 1001
            }).render();

            var datePicker = this._createCalendar('.add .lfr-input-date input');
 
            var validator = new Y.FormValidator({
                boundingBox: modal.get('boundingBox').one('form'),
                rules: TASK_VALIDATION_RULES
            });

            this.addButton.on('click', function (e) {
                modal.set('width', (me.getViewport().width < LIFERAY_PHONE_MEDIA_BREAK ? (me.getViewport().width - 40) : 500));
                if (modal.get('boundingBox').one(SELECT_CALENDAR)) {
                    modal.get('boundingBox').one(SELECT_CALENDAR).setAttribute("disabled", "disabled");
                }
                modal.show();
            });

            modal.get('boundingBox').one('form').on('submit', function (e) {
                e.preventDefault();
                // e.stopPropagation();
                /* trigger validator */
                var title = modal.get('boundingBox').one('.add-title').get('value');
                var description = modal.get('boundingBox').one('.add-description').get('value');
                var date = modal.get('boundingBox').one('.lfr-input-date input').get('value');
                var calendarId = me.getCalendarId(modal.get('boundingBox').one(SELECT_CALENDAR));
                var firstReminderValue = me.getReminderValue(modal.get('boundingBox').one(FIRST_REMINDER_VALUE));
                var firstReminderDuration = me.getReminderValue(modal.get('boundingBox').one(FIRST_REMINDER_DURATION));
                var secondReminderValue = me.getReminderValue(modal.get('boundingBox').one(SECOND_REMINDER_VALUE));
                var secondReminderDuration = me.getReminderValue(modal.get('boundingBox').one(SECOND_REMINDER_DURATION));
                
                
                
                if (Y.Lang.trim(title) != '' && 
                        Y.Lang.trim(description) != '' && Y.Lang.trim(date) != '') {
                    date = new Date(date);
                    date.setHours(0,0,0,0); //starts at midnight
                    modal.get('boundingBox').one('.todo-portlet-loader').toggleClass('visible');
                    modal.get('boundingBox').one('.add-submit').setAttribute('disabled', 'true');
                    me.addTaskCall(
                		{
                			name: title, 
                			description: description, 
                			day: (date.getDate() + 1), 
                			month: date.getMonth(), 
                			year: date.getFullYear(), 
                			calendarId: calendarId,
                			firstReminderType: 'email',
                			firstReminderDuration: firstReminderDuration,
                			firstReminderValue: firstReminderValue,
                			secondReminderType: 'email',
                			secondReminderDuration: secondReminderDuration,
                			secondReminderValue: secondReminderValue,
                		},  function(data) {
	                        modal.get('boundingBox').one('.todo-portlet-loader').toggleClass('visible');
	                        modal.get('boundingBox').one('.add-submit').removeAttribute('disabled');
	                        me.updateTaskListUI(function() {
                                me.openTaskGroup(data.taskId);
                            });
                            modal.get('boundingBox').one(REMINDERS_BOX).addClass(REMINDERS_HIDDEN_CLASS);
                            modal.get('boundingBox').one('form').reset();
                            modal.hide();
                            me.reloadCalendar();
                    });
                    
                }
            });

            modal.get('boundingBox').one('.add-cancel').on('click', function (e) {
            	e.preventDefault();
                e.stopPropagation();
                modal.hide();
            	me.clearInputs(modal);
            });
            
            /* 
             *  If the modal is closed using the "x" at the corner, 
             *  the datepicker has to be manually hidden 
             */
            modal.get('boundingBox').one('.close:button').on('click', function (e) { 
            	me.clearInputs(modal);
            	var popover = datePicker.getPopover();
            	if (popover.get('visible')) {
            		popover.hide();
            	}
            });
            
            /* check if LR calendar integration configured */
            if (!modal.get('boundingBox').one('.add-to-calendar')) {
              return;
            }
            
            // Add to calendar checkbox on modal
            modal.get('boundingBox').one(CHECKBOX_CALENDAR).on('change', function (event) {
            	me.checkCalendarHandler(event, modal.get('boundingBox').one(SELECT_CALENDAR));
            	// toggling the reminders div when checking or unchecking the add to liferay calendar checkbox
            	modal.get('boundingBox').one(REMINDERS_BOX).toggleClass(REMINDERS_HIDDEN_CLASS);
            });
            
            // Add reminder checkbox on modal
            modal.get('boundingBox').all(CHECKBOX_REMINDER).on('change', function (event) {
            	// get the reminder parent div
            	var reminderDiv = event.target.ancestor(REMINDER_BOX);
            	// using the reminder div, get the input for the reminder value and the select for the reminder duration
            	me.checkReminderHandler(event, reminderDiv.one(REMINDER_VALUE), reminderDiv.one(REMINDER_DURATION));
            });
            
            // Initialy the reminders controls are disabled
            modal.get('boundingBox').all(REMINDER_VALUE).setAttribute('disabled', 'disabled');
            modal.get('boundingBox').all(REMINDER_DURATION).setAttribute('disabled', 'disabled');
        },
        
        /** Handler for the checkbox to enable or disabled the calendar select **/
        checkCalendarHandler: function (event, selectCalendar) {
        	if (selectCalendar) {
        		if (selectCalendar.attr("disabled")) {
	        		selectCalendar.removeAttribute("disabled");
	        	} else {
	        		selectCalendar.setAttribute("disabled", "disabled");
	        	}
        	}
        },
        
        checkReminderHandler: function (event, inputReminder, selectReminder) {
        	if (inputReminder && selectReminder) {
        		// toggle the disabled attribute for the input
        		if (inputReminder.attr("disabled")) {
        			inputReminder.removeAttribute("disabled");
        		} else {
        			inputReminder.setAttribute("disabled", "disabled");
        		}
        		
        		// toggle the disabled attribute for the select
        		if (selectReminder.attr("disabled")) {
        			selectReminder.removeAttribute("disabled");
        		} else {
        			selectReminder.setAttribute("disabled", "disabled");
        		}
        	}
        },
        
        openTaskGroup: function(taskId) {
            var task = this.get('container').one('#task-' + taskId);
            var list = task.get('parentNode');
            list.removeClass('toggler-content-collapsed');
            list.addClass('toggler-content-expanded');
            list.setStyle('margin', '0');
        },
        
        
        
        setComponents: function () {
            /** Toggling tasks categories **/
            this.toggler = new Y.TogglerDelegate({
                animated: true,
                closeAllOnExpand: true,
                container: '#h',
                content: '.tasks',
                expanded: false,
                header: '.subtitle',
                transition: {
                    duration: 0.2,
                    easing: 'cubic-bezier(0, 0.1, 0, 1)'
                }
            });
            
            
        },
        
        /*clear inputs and textarea when click cancel or close buttons */
        clearInputs: function (modal) {
             modal.get('boundingBox').all('.control-group').removeClass("error");
             modal.get('boundingBox').all('.control-group').removeClass("success");
             modal.get('boundingBox').all('.control-group input').set('value', '');
             modal.get('boundingBox').all('.control-group textarea').val('');
             modal.get('boundingBox').all(".help-inline").remove(false);
        },


        /** Returns the different members of a single task
    @element Must be a li containing a task **/
        getMembers: function (element) {
            var activity = element.one(".activity"),
            edit = element.one(".edit"),
            title = activity.one(".activity-title"),
            time = activity.one(".activity-time"),
            titleInput = edit.one(".edit-title"),
            timeInput = edit.one(".edit-time"),
            selectCalendar = edit.one(SELECT_CALENDAR);
            

            return {
                li: element,
                activity: activity,
                edit: edit,
                title: title,
                time: time,
                titleInput: titleInput,
                timeInput: timeInput,
                selectCalendar: selectCalendar
            };
        },

        /**
         * If there is a liferay calendar portlet in the same page as the todo portlet
         * this method makes it reload retrieving the latest events updates
         */
        reloadCalendar() {
            // using namespace directly here, seems no problem because calendar portlet is not instanceable multiple times per page
            var scheduler = window._com_liferay_calendar_web_portlet_CalendarPortlet_scheduler;
            // check if exists a calendar in this page
            if (scheduler) {
                scheduler.load();
            }
        }



    }, {
        ATTRS: {

            portletNamespace: {
                value: ''
            },
            
            container: {
                value: null
            },
            
            resourceUrl: {
                value: ''
            },
            
            calendarWidgetClass: {
                value: null
            },
            
            isMobile: {
                value: false
            }

        }
    });

}, '@VERSION@', {
    "requires": ["yui-base", "base-build", "node", "event",
 "anim",
 "dd-constrain",
 "dd-proxy",
 "dd-drop",
 "aui-datepicker",
 "aui-timepicker",
    "aui-toggler",
    "aui-modal", "aui-io-request", "json-parse", "aui-form-validator"]
});
