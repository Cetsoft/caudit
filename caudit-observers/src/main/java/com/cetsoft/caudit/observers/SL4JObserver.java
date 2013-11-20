package com.cetsoft.caudit.observers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cetsoft.caudit.observable.AuditEvent;
import com.cetsoft.caudit.observer.AuditObserver;

public class SL4JObserver implements AuditObserver{
	
	private Logger logger = LoggerFactory.getLogger(SL4JObserver.class);
	
	public void onAuditEvent(AuditEvent auditEvent) {
		logger.info(auditEvent.toString());
	}

}
