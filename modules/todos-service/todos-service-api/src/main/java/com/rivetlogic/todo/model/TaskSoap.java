/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.rivetlogic.todo.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.rivetlogic.todo.service.http.TaskServiceSoap}.
 *
 * @author Christopher Jimenez, Emmanuel Abarca
 * @see com.rivetlogic.todo.service.http.TaskServiceSoap
 * @generated
 */
@ProviderType
public class TaskSoap implements Serializable {
	public static TaskSoap toSoapModel(Task model) {
		TaskSoap soapModel = new TaskSoap();

		soapModel.setTaskId(model.getTaskId());
		soapModel.setUserId(model.getUserId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setDate(model.getDate());
		soapModel.setCompleted(model.getCompleted());
		soapModel.setCalendarBookingId(model.getCalendarBookingId());

		return soapModel;
	}

	public static TaskSoap[] toSoapModels(Task[] models) {
		TaskSoap[] soapModels = new TaskSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TaskSoap[][] toSoapModels(Task[][] models) {
		TaskSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TaskSoap[models.length][models[0].length];
		}
		else {
			soapModels = new TaskSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TaskSoap[] toSoapModels(List<Task> models) {
		List<TaskSoap> soapModels = new ArrayList<TaskSoap>(models.size());

		for (Task model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TaskSoap[soapModels.size()]);
	}

	public TaskSoap() {
	}

	public long getPrimaryKey() {
		return _taskId;
	}

	public void setPrimaryKey(long pk) {
		setTaskId(pk);
	}

	public long getTaskId() {
		return _taskId;
	}

	public void setTaskId(long taskId) {
		_taskId = taskId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public Boolean getCompleted() {
		return _completed;
	}

	public void setCompleted(Boolean completed) {
		_completed = completed;
	}

	public long getCalendarBookingId() {
		return _calendarBookingId;
	}

	public void setCalendarBookingId(long calendarBookingId) {
		_calendarBookingId = calendarBookingId;
	}

	private long _taskId;
	private long _userId;
	private String _name;
	private String _description;
	private Date _date;
	private Boolean _completed;
	private long _calendarBookingId;
}