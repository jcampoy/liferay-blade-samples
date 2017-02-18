/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liferay.blade.samples.lifecycle.loginpostaction;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Ángel Jiménez
 */
@Component(
	immediate = true, property = {"key=login.events.post"},
	service = LifecycleAction.class
)
public class LoginPostAction implements LifecycleAction {

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {
	
		_log.fatal("--- login.event.post");
		_log.fatal("Current date: " + new Date());
		_log.fatal("---");

		try {
			User user = PortalUtil.getUser(lifecycleEvent.getRequest());
			long userId = PortalUtil.getUserId(lifecycleEvent.getRequest());

			_log.fatal("User from request attribute");
			_log.fatal("Login date: " + user.getLoginDate());
			_log.fatal("Last login date: " +user.getLastLoginDate());

			_log.fatal("---");

			_log.fatal("User from Data Base");

			user = _userLocalService.getUser(userId);
			_log.fatal("Login date: " + user.getLoginDate());
			_log.fatal("Last login date: " +user.getLastLoginDate());

			_log.fatal("--- fatal!!");
		} 
		catch (Exception e) {
			_log.error("Error", e);
		}
		
	}

	Log _log = LogFactoryUtil.getLog(LoginPostAction.class);
	
	@Reference
	public void setUserLocalService(UserLocalService uls) {
		_userLocalService = uls;
	}
	
	UserLocalService _userLocalService;

}