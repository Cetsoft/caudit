package com.cetsoft.caudit.observers;

import org.apache.log4j.Logger;

import com.cetsoft.caudit.observable.AuditEvent;
import com.cetsoft.caudit.observer.AuditObserver;

public class Log4JObserver implements AuditObserver{
	
	private Logger logger = Logger.getLogger(Log4JObserver.class);

	public void onAuditEvent(AuditEvent auditEvent) {
		logger.info(auditEvent.toString());
	}

}
