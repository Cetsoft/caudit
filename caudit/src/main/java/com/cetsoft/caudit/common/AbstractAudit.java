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


/**
 * The Class AbstractAudit.
 */
public abstract class AbstractAudit implements Audit{

	/** The audit id. */
	volatile int auditId;
	
	/** The should reset. */
	volatile boolean shouldReset = true;
	
	/** The is observable. */
	volatile boolean isObservable = true;
	
	/** The audit name. */
	protected volatile String auditName;
	
	/** The provider. */
	protected volatile AuditProvider provider;
	
	/** The removal listener. */
	protected AuditRemovalListener removalListener;
	
	/**
	 * Instantiates a new abstract audit.
	 *
	 * @param auditId the audit id
	 * @param auditName the audit name
	 */
	public AbstractAudit(int auditId, String auditName) {
		this.auditId = auditId;
		this.auditName = auditName;
	}
	
	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#getAuditId()
	 */
	public int getAuditId() {
		return auditId;
	}
	
	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#getAuditName()
	 */
	public String getAuditName() {
		return auditName;
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#setShouldReset(boolean)
	 */
	public void setShouldReset(boolean shouldReset) {
		this.shouldReset = shouldReset;
	}
	
	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#setProvider(com.cetsoft.caudit.common.AuditProvider)
	 */
	public void setProvider(AuditProvider provider){
		this.provider = provider;
	}
	
	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#setRemovalListener(com.cetsoft.caudit.common.AuditRemovalListener)
	 */
	public void setRemovalListener(AuditRemovalListener removalListener){
		this.removalListener = removalListener;
	}
	
	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#getRemovalListener()
	 */
	public AuditRemovalListener getRemovalListener() {
		return removalListener;
	}

	/**
	 * Should reset.
	 */
	public void shouldReset() {
		if(shouldReset){
			reset();
		}
	}
	
	/**
	 * Update.
	 */
	protected void update(){
		if(provider!=null){
			provider.updateAudit();
		}
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#isObservable()
	 */
	public boolean isObservable() {
		return isObservable;
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.common.Audit#setObservable(boolean)
	 */
	public void setObservable(boolean isObservable) {
		this.isObservable = isObservable;
	}

}
