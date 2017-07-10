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

package com.rivetlogic.todo.service.impl;

import aQute.bnd.annotation.ProviderType;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.rivetlogic.todo.model.Task;
import com.rivetlogic.todo.service.base.TaskLocalServiceBaseImpl;

/**
 * The implementation of the task local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.rivetlogic.todo.service.TaskLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Christopher Jimenez, Emmanuel Abarca
 * @see TaskLocalServiceBaseImpl
 * @see com.rivetlogic.todo.service.TaskLocalServiceUtil
 */
@ProviderType
public class TaskLocalServiceImpl extends TaskLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.rivetlogic.todo.service.TaskLocalServiceUtil} to access the task local service.
	 */
	private static final Log LOG = LogFactoryUtil.getLog(TaskLocalServiceImpl.class);

	public Task createTask(Task task) throws SystemException {
		Task newTask = taskPersistence.create(counterLocalService.increment(Task.class.getName()));
		newTask.setCompleted(task.getCompleted());
		newTask.setDate(task.getDate());
		newTask.setDescription(task.getDescription());
		newTask.setName(task.getName());
		newTask.setUserId(task.getUserId());
		newTask.setCalendarBookingId(task.getCalendarBookingId());
		taskPersistence.update(newTask);
		return newTask;
	}

	public List<Task> getTaskByUserId(Long userId) {
		List<Task> tasks = new ArrayList<Task>();
		try {
			tasks = taskPersistence.findByuserId(userId);
		} catch (Exception e) {
			LOG.error(e);
		}
		return tasks;
	}
}