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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link Task}.
 * </p>
 *
 * @author Christopher Jimenez, Emmanuel Abarca
 * @see Task
 * @generated
 */
@ProviderType
public class TaskWrapper implements Task, ModelWrapper<Task> {
	public TaskWrapper(Task task) {
		_task = task;
	}

	@Override
	public Class<?> getModelClass() {
		return Task.class;
	}

	@Override
	public String getModelClassName() {
		return Task.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("taskId", getTaskId());
		attributes.put("userId", getUserId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("date", getDate());
		attributes.put("completed", getCompleted());
		attributes.put("calendarBookingId", getCalendarBookingId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long taskId = (Long)attributes.get("taskId");

		if (taskId != null) {
			setTaskId(taskId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Date date = (Date)attributes.get("date");

		if (date != null) {
			setDate(date);
		}

		Boolean completed = (Boolean)attributes.get("completed");

		if (completed != null) {
			setCompleted(completed);
		}

		Long calendarBookingId = (Long)attributes.get("calendarBookingId");

		if (calendarBookingId != null) {
			setCalendarBookingId(calendarBookingId);
		}
	}

	@Override
	public Task toEscapedModel() {
		return new TaskWrapper(_task.toEscapedModel());
	}

	@Override
	public Task toUnescapedModel() {
		return new TaskWrapper(_task.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _task.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _task.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _task.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _task.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<Task> toCacheModel() {
		return _task.toCacheModel();
	}

	@Override
	public int compareTo(Task task) {
		return _task.compareTo(task);
	}

	@Override
	public int hashCode() {
		return _task.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _task.getPrimaryKeyObj();
	}

	/**
	* Returns the completed of this task.
	*
	* @return the completed of this task
	*/
	@Override
	public java.lang.Boolean getCompleted() {
		return _task.getCompleted();
	}

	@Override
	public java.lang.Object clone() {
		return new TaskWrapper((Task)_task.clone());
	}

	/**
	* Returns the description of this task.
	*
	* @return the description of this task
	*/
	@Override
	public java.lang.String getDescription() {
		return _task.getDescription();
	}

	/**
	* Returns the name of this task.
	*
	* @return the name of this task
	*/
	@Override
	public java.lang.String getName() {
		return _task.getName();
	}

	/**
	* Returns the user uuid of this task.
	*
	* @return the user uuid of this task
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _task.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _task.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _task.toXmlString();
	}

	/**
	* Returns the date of this task.
	*
	* @return the date of this task
	*/
	@Override
	public Date getDate() {
		return _task.getDate();
	}

	/**
	* Returns the calendar booking ID of this task.
	*
	* @return the calendar booking ID of this task
	*/
	@Override
	public long getCalendarBookingId() {
		return _task.getCalendarBookingId();
	}

	/**
	* Returns the primary key of this task.
	*
	* @return the primary key of this task
	*/
	@Override
	public long getPrimaryKey() {
		return _task.getPrimaryKey();
	}

	/**
	* Returns the task ID of this task.
	*
	* @return the task ID of this task
	*/
	@Override
	public long getTaskId() {
		return _task.getTaskId();
	}

	/**
	* Returns the user ID of this task.
	*
	* @return the user ID of this task
	*/
	@Override
	public long getUserId() {
		return _task.getUserId();
	}

	@Override
	public void persist() {
		_task.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_task.setCachedModel(cachedModel);
	}

	/**
	* Sets the calendar booking ID of this task.
	*
	* @param calendarBookingId the calendar booking ID of this task
	*/
	@Override
	public void setCalendarBookingId(long calendarBookingId) {
		_task.setCalendarBookingId(calendarBookingId);
	}

	/**
	* Sets the completed of this task.
	*
	* @param completed the completed of this task
	*/
	@Override
	public void setCompleted(java.lang.Boolean completed) {
		_task.setCompleted(completed);
	}

	/**
	* Sets the date of this task.
	*
	* @param date the date of this task
	*/
	@Override
	public void setDate(Date date) {
		_task.setDate(date);
	}

	/**
	* Sets the description of this task.
	*
	* @param description the description of this task
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_task.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_task.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_task.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_task.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the name of this task.
	*
	* @param name the name of this task
	*/
	@Override
	public void setName(java.lang.String name) {
		_task.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_task.setNew(n);
	}

	/**
	* Sets the primary key of this task.
	*
	* @param primaryKey the primary key of this task
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_task.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_task.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the task ID of this task.
	*
	* @param taskId the task ID of this task
	*/
	@Override
	public void setTaskId(long taskId) {
		_task.setTaskId(taskId);
	}

	/**
	* Sets the user ID of this task.
	*
	* @param userId the user ID of this task
	*/
	@Override
	public void setUserId(long userId) {
		_task.setUserId(userId);
	}

	/**
	* Sets the user uuid of this task.
	*
	* @param userUuid the user uuid of this task
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_task.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TaskWrapper)) {
			return false;
		}

		TaskWrapper taskWrapper = (TaskWrapper)obj;

		if (Objects.equals(_task, taskWrapper._task)) {
			return true;
		}

		return false;
	}

	@Override
	public Task getWrappedModel() {
		return _task;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _task.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _task.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_task.resetOriginalValues();
	}

	private final Task _task;
}