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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cetsoft.caudit.common.Audit;
import com.cetsoft.caudit.common.AuditStore;
import com.cetsoft.caudit.observer.AuditObserver;

/**
 * The Class AbstractAuditObservable.
 */
public abstract class AbstractAuditObservable implements AuditObservable {

	/** The audit store. */
	private AuditStore auditStore;
	
	/** The observers. */
	private List<AuditObserver> observers = Collections.synchronizedList(new ArrayList<AuditObserver>());

	/**
	 * Instantiates a new abstract audit observable.
	 *
	 * @param auditStore the audit store
	 */
	public AbstractAuditObservable(AuditStore auditStore) {
		this.auditStore = auditStore;
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.observable.AuditObservable#add(com.cetsoft.caudit.observer.AuditObserver)
	 */
	public void add(AuditObserver auditObserver) {
		observers.add(auditObserver);
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.observable.AuditObservable#remove(com.cetsoft.caudit.observer.AuditObserver)
	 */
	public void remove(AuditObserver auditObserver) {
		observers.remove(auditObserver);
	}

	/* (non-Javadoc)
	 * @see com.cetsoft.caudit.observable.AuditObservable#notifyObservers(java.util.List)
	 */
	public void notifyObservers(List<? extends AuditEvent> events) {
		for (AuditObserver observer : observers) {
			for (AuditEvent auditEvent : events) {
				observer.onAuditEvent(auditEvent);
			}
		}
	}

	/**
	 * Creates the events.
	 */
	public void createEvents() {
		List<AuditEvent> events = new ArrayList<AuditEvent>();
		for (Audit audit : auditStore.audits()) {
			if (audit.isObservable()) {
				events.add(audit.createEvent());
			}
		}
		if (events.size() > 0) {
			AuditEvent event = createNullEvent();
			events.add(event);
		}
		notifyObservers(events);
	}

	/**
	 * Creates the null event.
	 *
	 * @return the audit event
	 */
	private AuditEvent createNullEvent() {
		return new NullAuditEvent();
	}

	/**
	 * The Private Class NullAuditEvent.
	 */
	private class NullAuditEvent extends AuditEvent {
		
		/* (non-Javadoc)
		 * @see com.cetsoft.caudit.observable.AuditEvent#toString()
		 */
		@Override
		public String toString() {
			return "";
		}
	}

}
