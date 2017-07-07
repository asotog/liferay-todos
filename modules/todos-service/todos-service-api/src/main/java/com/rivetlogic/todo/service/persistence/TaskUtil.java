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

package com.rivetlogic.todo.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.rivetlogic.todo.model.Task;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the task service. This utility wraps {@link com.rivetlogic.todo.service.persistence.impl.TaskPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Christopher Jimenez, Emmanuel Abarca
 * @see TaskPersistence
 * @see com.rivetlogic.todo.service.persistence.impl.TaskPersistenceImpl
 * @generated
 */
@ProviderType
public class TaskUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(Task task) {
		getPersistence().clearCache(task);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Task> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Task> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Task> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end, OrderByComparator<Task> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static Task update(Task task) {
		return getPersistence().update(task);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static Task update(Task task, ServiceContext serviceContext) {
		return getPersistence().update(task, serviceContext);
	}

	/**
	* Returns all the tasks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching tasks
	*/
	public static List<Task> findByuserId(long userId) {
		return getPersistence().findByuserId(userId);
	}

	/**
	* Returns a range of all the tasks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of tasks
	* @param end the upper bound of the range of tasks (not inclusive)
	* @return the range of matching tasks
	*/
	public static List<Task> findByuserId(long userId, int start, int end) {
		return getPersistence().findByuserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the tasks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of tasks
	* @param end the upper bound of the range of tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching tasks
	*/
	public static List<Task> findByuserId(long userId, int start, int end,
		OrderByComparator<Task> orderByComparator) {
		return getPersistence()
				   .findByuserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the tasks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of tasks
	* @param end the upper bound of the range of tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching tasks
	*/
	public static List<Task> findByuserId(long userId, int start, int end,
		OrderByComparator<Task> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findByuserId(userId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first task in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching task
	* @throws NoSuchTaskException if a matching task could not be found
	*/
	public static Task findByuserId_First(long userId,
		OrderByComparator<Task> orderByComparator)
		throws com.rivetlogic.todo.exception.NoSuchTaskException {
		return getPersistence().findByuserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first task in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching task, or <code>null</code> if a matching task could not be found
	*/
	public static Task fetchByuserId_First(long userId,
		OrderByComparator<Task> orderByComparator) {
		return getPersistence().fetchByuserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last task in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching task
	* @throws NoSuchTaskException if a matching task could not be found
	*/
	public static Task findByuserId_Last(long userId,
		OrderByComparator<Task> orderByComparator)
		throws com.rivetlogic.todo.exception.NoSuchTaskException {
		return getPersistence().findByuserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last task in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching task, or <code>null</code> if a matching task could not be found
	*/
	public static Task fetchByuserId_Last(long userId,
		OrderByComparator<Task> orderByComparator) {
		return getPersistence().fetchByuserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the tasks before and after the current task in the ordered set where userId = &#63;.
	*
	* @param taskId the primary key of the current task
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next task
	* @throws NoSuchTaskException if a task with the primary key could not be found
	*/
	public static Task[] findByuserId_PrevAndNext(long taskId, long userId,
		OrderByComparator<Task> orderByComparator)
		throws com.rivetlogic.todo.exception.NoSuchTaskException {
		return getPersistence()
				   .findByuserId_PrevAndNext(taskId, userId, orderByComparator);
	}

	/**
	* Removes all the tasks where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public static void removeByuserId(long userId) {
		getPersistence().removeByuserId(userId);
	}

	/**
	* Returns the number of tasks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching tasks
	*/
	public static int countByuserId(long userId) {
		return getPersistence().countByuserId(userId);
	}

	/**
	* Caches the task in the entity cache if it is enabled.
	*
	* @param task the task
	*/
	public static void cacheResult(Task task) {
		getPersistence().cacheResult(task);
	}

	/**
	* Caches the tasks in the entity cache if it is enabled.
	*
	* @param tasks the tasks
	*/
	public static void cacheResult(List<Task> tasks) {
		getPersistence().cacheResult(tasks);
	}

	/**
	* Creates a new task with the primary key. Does not add the task to the database.
	*
	* @param taskId the primary key for the new task
	* @return the new task
	*/
	public static Task create(long taskId) {
		return getPersistence().create(taskId);
	}

	/**
	* Removes the task with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param taskId the primary key of the task
	* @return the task that was removed
	* @throws NoSuchTaskException if a task with the primary key could not be found
	*/
	public static Task remove(long taskId)
		throws com.rivetlogic.todo.exception.NoSuchTaskException {
		return getPersistence().remove(taskId);
	}

	public static Task updateImpl(Task task) {
		return getPersistence().updateImpl(task);
	}

	/**
	* Returns the task with the primary key or throws a {@link NoSuchTaskException} if it could not be found.
	*
	* @param taskId the primary key of the task
	* @return the task
	* @throws NoSuchTaskException if a task with the primary key could not be found
	*/
	public static Task findByPrimaryKey(long taskId)
		throws com.rivetlogic.todo.exception.NoSuchTaskException {
		return getPersistence().findByPrimaryKey(taskId);
	}

	/**
	* Returns the task with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param taskId the primary key of the task
	* @return the task, or <code>null</code> if a task with the primary key could not be found
	*/
	public static Task fetchByPrimaryKey(long taskId) {
		return getPersistence().fetchByPrimaryKey(taskId);
	}

	public static java.util.Map<java.io.Serializable, Task> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the tasks.
	*
	* @return the tasks
	*/
	public static List<Task> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the tasks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tasks
	* @param end the upper bound of the range of tasks (not inclusive)
	* @return the range of tasks
	*/
	public static List<Task> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the tasks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tasks
	* @param end the upper bound of the range of tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of tasks
	*/
	public static List<Task> findAll(int start, int end,
		OrderByComparator<Task> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the tasks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link TaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of tasks
	* @param end the upper bound of the range of tasks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of tasks
	*/
	public static List<Task> findAll(int start, int end,
		OrderByComparator<Task> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the tasks from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of tasks.
	*
	* @return the number of tasks
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static TaskPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<TaskPersistence, TaskPersistence> _serviceTracker =
		ServiceTrackerFactory.open(TaskPersistence.class);
}