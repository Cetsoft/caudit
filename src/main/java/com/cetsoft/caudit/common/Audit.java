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
