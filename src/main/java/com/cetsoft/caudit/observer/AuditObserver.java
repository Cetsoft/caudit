/*
* Copyright (C) 2013 Cetsoft, http://www.cetsoft.com
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Library General Public
* License as published by the Free Software Foundation; either
* version 2 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Library General Public License for more details.
*
* You should have received a copy of the GNU Library General Public
* License along with this library; if not, write to the Free
* Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
* 
* Author : Yusuf Aytas
* Date   : Apr 8, 2013
*/
package com.cetsoft.caudit.observer;

import com.cetsoft.caudit.observable.AuditEvent;

/**
 * An asynchronous update interface for receiving notifications
 * about Audit information as the Audit is constructed.
 */
public interface AuditObserver {

	/**
	 * This method is called when information about an Audit
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param auditEvent the audit event
	 */
	void onAuditEvent(AuditEvent auditEvent);
}
