package com.rivetlogic.todo.web.portlet;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.GetterUtil;
import com.rivetlogic.todo.keys.ToDosKeys;
import com.rivetlogic.todo.web.config.TodoPortletInstanceConfiguration;

@Component(
		configurationPid = "com.rivetlogic.todo.web.config.TodoPortletInstanceConfiguration",
		configurationPolicy = ConfigurationPolicy.OPTIONAL,
		immediate = true,
		property = {
				"javax.portlet.name="+ToDosKeys.PORTLET_ID
		},
		service = ConfigurationAction.class
)
public class TodoPortletConfigurationAction extends DefaultConfigurationAction {
	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
					throws Exception {

		String enableLRCalendarIntegration = GetterUtil.getString(getParameter(actionRequest, "enableLRCalendarIntegration"));
		setPreference(actionRequest, "enableLRCalendarIntegration", enableLRCalendarIntegration);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {

		httpServletRequest.setAttribute(
				TodoPortletInstanceConfiguration.class.getName(),
				_todoPortletConfiguration);

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<Object, Object> properties) {
		_todoPortletConfiguration = ConfigurableUtil.createConfigurable(
				TodoPortletInstanceConfiguration.class, properties);
	}

	private volatile TodoPortletInstanceConfiguration _todoPortletConfiguration;
}