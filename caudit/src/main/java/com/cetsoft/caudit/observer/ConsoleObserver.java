/*
* Copyright (C) 2014 Cetsoft, http://www.cetsoft.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
* Author : Yusuf Aytas
* Date   : Apr 8, 2013
*/
package com.cetsoft.caudit.observer;

import com.cetsoft.caudit.observable.AuditEvent;

/**
 * An asynchronous update interface for receiving notifications
 * about Console information as the Console is constructed.
 */
public class ConsoleObserver implements AuditObserver{

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.observer.AuditObserver#onAuditEvent(com.cetsoft.caudit.observable.AuditEvent)
	 */
	public void onAuditEvent(AuditEvent auditEvent) {
		System.out.println(auditEvent.toString());
	}

}
