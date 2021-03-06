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
 * The listener interface for receiving auditRemoval events.
 * The class that is interested in processing a auditRemoval
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAuditRemovalListener<code> method. When
 * the auditRemoval event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AuditRemovalEvent
 */
public interface AuditRemovalListener {
	
	/**
	 * On removal audit event.
	 *
	 * @param auditEvent the audit event
	 */
	void onRemoval(AuditEvent auditEvent);
}
