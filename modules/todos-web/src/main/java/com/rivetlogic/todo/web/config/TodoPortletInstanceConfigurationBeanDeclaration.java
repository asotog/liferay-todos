package com.rivetlogic.todo.web.config;

import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;

public class TodoPortletInstanceConfigurationBeanDeclaration implements ConfigurationBeanDeclaration {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return TodoPortletInstanceConfiguration.class;
	}

}
