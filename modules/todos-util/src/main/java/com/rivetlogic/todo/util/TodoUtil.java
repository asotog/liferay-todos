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

package com.rivetlogic.todo.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author christopherjimenez
 * 
 */
public class TodoUtil {
    private static final Log LOG = LogFactoryUtil.getLog(TodoUtil.class);
    
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN);
    
    public static Calendar getCalendarWithOutTime() {
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal;
    }
    
    public static Calendar getCalendarWithOutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal;
    }
    
    public static void returnJSONObject(PortletResponse response, JSONObject jsonObj) {
        HttpServletResponse servletResponse = PortalUtil.getHttpServletResponse(response);
        PrintWriter pw;
        try {
            pw = servletResponse.getWriter();
            pw.write(jsonObj.toString());
            pw.close();
        } catch (IOException e) {
            LOG.error("Error while returning json", e);
        }
    }
    
}
