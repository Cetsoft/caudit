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
