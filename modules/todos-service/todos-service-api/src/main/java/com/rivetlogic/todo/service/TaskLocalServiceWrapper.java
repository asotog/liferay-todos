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

package com.rivetlogic.todo.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TaskLocalService}.
 *
 * @author Christopher Jimenez, Emmanuel Abarca
 * @see TaskLocalService
 * @generated
 */
@ProviderType
public class TaskLocalServiceWrapper implements TaskLocalService,
	ServiceWrapper<TaskLocalService> {
	public TaskLocalServiceWrapper(TaskLocalService taskLocalService) {
		_taskLocalService = taskLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _taskLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _taskLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _taskLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _taskLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _taskLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the task to the database. Also notifies the appropriate model listeners.
	*
	* @param task the task
	* @return the task that was added
	*/
	@Override
	public com.rivetlogic.todo.model.Task addTask(
		com.rivetlogic.todo.model.Task task) {
		return _taskLocalService.addTask(task);
	}

	@Override
	public com.rivetlogic.todo.model.Task createTask(
		com.rivetlogic.todo.model.Task task)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _taskLocalService.createTask(task);
	}

	/**
	* Creates a new task with the primary key. Does not add the task to the database.
	*
	* @param taskId the primary key for the new task
	* @return the new task
	*/
	@Override
	public com.rivetlogic.todo.model.Task createTask(long taskId) {
		return _taskLocalService.createTask(taskId);
	}

	/**
	* Deletes the task from the database. Also notifies the appropriate model listeners.
	*
	* @param task the task
	* @return the task that was removed
	*/
	@Override
	public com.rivetlogic.todo.model.Task deleteTask(
		com.rivetlogic.todo.model.Task task) {
		return _taskLocalService.deleteTask(task);
	}

	/**
	* Deletes the task with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param taskId the primary key of the task
	* @return the task that was removed
	* @throws PortalException if a task with the primary key could not be found
	*/
	@Override
	public com.rivetlogic.todo.model.Task deleteTask(long taskId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _taskLocalService.deleteTask(taskId);
	}

	@Override
	public com.rivetlogic.todo.model.Task fetchTask(long taskId) {
		return _taskLocalService.fetchTask(taskId);
	}

	/**
	* Returns the task with the primary key.
	*
	* @param taskId the primary key of the task
	* @return the task
	* @throws PortalException if a task with the primary key could not be found
	*/
	@Override
	public com.rivetlogic.todo.model.Task getTask(long taskId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _taskLocalService.getTask(taskId);
	}

	/**
	* Updates the task in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param task the task
	* @return the task that was updated
	*/
	@Override
	public com.rivetlogic.todo.model.Task updateTask(
		com.rivetlogic.todo.model.Task task) {
		return _taskLocalService.updateTask(task);
	}

	/**
	* Returns the number of tasks.
	*
	* @return the number of tasks
	*/
	@Override
	public int getTasksCount() {
		return _taskLocalService.getTasksCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _taskLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _taskLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rivetlogic.todo.model.impl.TaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _taskLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rivetlogic.todo.model.impl.TaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _taskLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.rivetlogic.todo.model.Task> getTaskByUserId(
		java.lang.Long userId) {
		return _taskLocalService.getTaskByUserId(userId);
	}

	/**
	* Returns a range of all the tasks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.rivetlogic.todo.model.impl.TaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tasks
	* @param end the upper bound of the range of tasks (not inclusive)
	* @return the range of tasks
	*/
	@Override
	public java.util.List<com.rivetlogic.todo.model.Task> getTasks(int start,
		int end) {
		return _taskLocalService.getTasks(start, end);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _taskLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _taskLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public TaskLocalService getWrappedService() {
		return _taskLocalService;
	}

	@Override
	public void setWrappedService(TaskLocalService taskLocalService) {
		_taskLocalService = taskLocalService;
	}

	private TaskLocalService _taskLocalService;
}