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
package com.cetsoft.caudit.common;

import com.cetsoft.caudit.observable.AuditEvent;

/**
 * The Interface Audit.
 */
public interface Audit {
	
	/**
	 * Gets the audit id.
	 *
	 * @return the audit id
	 */
	int getAuditId();
	
	/**
	 * Gets the audit name.
	 *
	 * @return the audit name
	 */
	String getAuditName();
	
	/**
	 * Reset.
	 */
	void reset();
	
	/**
	 * Checks if is observable.
	 *
	 * @return true, if is observable
	 */
	boolean isObservable();
	
	/**
	 * Sets the observable.
	 *
	 * @param isObservable the new observable
	 */
	void setObservable(boolean isObservable);
	
	/**
	 * Sets the should reset.
	 *
	 * @param shouldReset the new should reset
	 */
	void setShouldReset(boolean shouldReset);
	
	/**
	 * Sets the provider.
	 *
	 * @param provider the new provider
	 */
	void setProvider(AuditProvider provider);
	
	/**
	 * Sets the removal listener.
	 *
	 * @param listener the new removal listener
	 */
	void setRemovalListener(AuditRemovalListener listener);
	
	/**
	 * Gets the removal listener.
	 *
	 * @return the removal listener
	 */
	AuditRemovalListener getRemovalListener();
	
	/**
	 * Creates the event.
	 *
	 * @return the audit event
	 */
	AuditEvent createEvent();
}
