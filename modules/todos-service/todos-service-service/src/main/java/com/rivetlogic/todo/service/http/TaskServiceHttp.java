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

package com.rivetlogic.todo.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import com.rivetlogic.todo.service.TaskServiceUtil;

/**
 * Provides the HTTP utility for the
 * {@link TaskServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link HttpPrincipal} parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Christopher Jimenez, Emmanuel Abarca
 * @see TaskServiceSoap
 * @see HttpPrincipal
 * @see TaskServiceUtil
 * @generated
 */
@ProviderType
public class TaskServiceHttp {
	public static com.rivetlogic.todo.model.Task createTask(
		HttpPrincipal httpPrincipal, com.rivetlogic.todo.model.Task task)
		throws com.liferay.portal.kernel.exception.SystemException {
		try {
			MethodKey methodKey = new MethodKey(TaskServiceUtil.class,
					"createTask", _createTaskParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(methodKey, task);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.rivetlogic.todo.model.Task)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.rivetlogic.todo.model.Task> getTaskByUserId(
		HttpPrincipal httpPrincipal, java.lang.Long userId) {
		try {
			MethodKey methodKey = new MethodKey(TaskServiceUtil.class,
					"getTaskByUserId", _getTaskByUserIdParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.rivetlogic.todo.model.Task>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TaskServiceHttp.class);
	private static final Class<?>[] _createTaskParameterTypes0 = new Class[] {
			com.rivetlogic.todo.model.Task.class
		};
	private static final Class<?>[] _getTaskByUserIdParameterTypes1 = new Class[] {
			java.lang.Long.class
		};
}