<%--
/**
 * Copyright (C) 2005-2016 Rivet Logic Corporation.
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
 */
--%>

<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.rivetlogic.todo.web.config.TodoPortletInstanceConfiguration"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%  
TodoPortletInstanceConfiguration configuration = portletDisplay.getPortletInstanceConfiguration(
	       TodoPortletInstanceConfiguration.class);
boolean enableLRCalendarIntegration = configuration.enableLRCalendarIntegration();
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form name="fm" action="<%=configurationURL %>" method="post" cssClass="container-fluid-1280">
	<aui:input name="<%=Constants.CMD%>" type="hidden"
		value="<%=Constants.UPDATE%>" />
		
	<aui:input type="checkbox" name="preferences--enableLRCalendarIntegration--" label="enable-lr-calendar-integration" value="<%=enableLRCalendarIntegration %>" >
	</aui:input>
	<aui:button-row>
		<aui:button type="submit" value="submit"/>
	</aui:button-row>
</aui:form>
