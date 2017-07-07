package com.rivetlogic.todo.web.config;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import aQute.bnd.annotation.metatype.Meta;

@ExtendedObjectClassDefinition(
		category = "other",
		scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
		id = "com.rivetlogic.todo.web.config.TodoPortletInstanceConfiguration",
		localization = "content/Language", name = "enable-lr-calendar-integration"
)
public interface TodoPortletInstanceConfiguration {
	@Meta.AD(
			deflt = "false",
			required=false,
			name="configuration.default-open-tab.name"
	)
	public boolean enableLRCalendarIntegration();
}
