<%--
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
--%>

<%@page import="com.rivetlogic.todo.web.config.TodoPortletInstanceConfiguration"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.servlet.BrowserSnifferUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />


<c:if test="<%= themeDisplay.isSignedIn() %>">

    <%
    TodoPortletInstanceConfiguration configuration = portletDisplay.getPortletInstanceConfiguration(
       TodoPortletInstanceConfiguration.class);
    /* use html5 native calendar for mobile or regular calendar */
    String calendarAlloyModule = "aui-datepicker" + (BrowserSnifferUtil.isMobile(request) ? "-native" : StringPool.BLANK);
    String calendarAlloyClass = "A.DatePicker" + (BrowserSnifferUtil.isMobile(request) ? "Native" : StringPool.BLANK);
    String isMobile = (BrowserSnifferUtil.isMobile(request) ? "true" : "false");
    boolean enableLRCalendarIntegration = configuration.enableLRCalendarIntegration();
    %>
      
    <script type="text/javascript">
        AUI().ready('todo-portlet','<%=calendarAlloyModule %>', function(A) {
            var todo = new A.Todo({
                container: A.one('#<portlet:namespace/>'),
                resourceUrl: '<portlet:resourceURL />',
                calendarWidgetClass: <%=calendarAlloyClass %>,
                isMobile: <%=isMobile %>,
                portletNamespace: '<portlet:namespace/>'
            });
        });
    </script>
 
    <div id="<portlet:namespace/>" class="todo-portlet">
    	<div id="todo-calendar"></div>
		<h2 class="clearfix">
			<div class="add-reminder">
				<button class="btn-primary btn add-submit icon-plus-sign"></button>
			</div>
			<liferay-ui:message key="tasks-title" />
		</h2>

		<ul class="activities-list" id="h">
			<li><span class="subtitle"><liferay-ui:message
						key="previous-tasks-title" /><span
					class="previous-tasks-count taskscount"></span></span>
				<ul class="tasks previous">

				</ul></li>
			<li><span
				class="subtitle toggler-header toggler-header-expanded"><liferay-ui:message
						key="today-tasks-title" /><span
					class="today-tasks-count taskscount"></span></span>
				<ul class="tasks today toggler-content toggler-content-expanded">

				</ul></li>
			<li><span class="subtitle"><liferay-ui:message
						key="tomorrow-tasks-title" /><span
					class="tomorrow-tasks-count taskscount"></span></span>
				<ul class="tasks tomorrow">

				</ul></li>
			<li><span class="subtitle"><liferay-ui:message
						key="future-tasks-title" /><span
					class="future-tasks-count taskscount"></span></span>
				<ul class="tasks future">

				</ul></li>

		</ul>
		<%@include file="/add-task-form.jsp" %>
      <%@include file="/task-list-item.jsp" %>   
    </div>

</c:if>
<c:if test="<%= !themeDisplay.isSignedIn() %>">

    <%
    renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
    %>
    
</c:if>
