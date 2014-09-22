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
package com.cetsoft.caudit.observable;

import java.util.List;

import com.cetsoft.caudit.observer.AuditObserver;

/**
 * The Interface AuditObservable.
 */
public interface AuditObservable{

	/**
	 * Adds the.
	 *
	 * @param auditObserver the audit observer
	 */
	void add(AuditObserver auditObserver);
	
	/**
	 * Removes the.
	 *
	 * @param auditObserver the audit observer
	 */
	void remove(AuditObserver auditObserver);
	
	/**
	 * Notify observers.
	 *
	 * @param events the events
	 */
	void notifyObservers(List<? extends AuditEvent> events);
	
	/**
	 * Start.
	 */
	void start();
	
	/**
	 * Stop.
	 */
	void stop();
}
