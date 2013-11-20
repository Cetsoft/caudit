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
