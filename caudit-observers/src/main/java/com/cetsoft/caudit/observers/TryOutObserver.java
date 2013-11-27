package com.cetsoft.caudit.observers;

import com.cetsoft.caudit.Audits;
import com.cetsoft.caudit.observable.AuditEvent;
import com.cetsoft.caudit.observer.AuditObserver;

public class TryOutObserver implements AuditObserver{
	
	private String apple,orange;

	public TryOutObserver(String apple, String orange) {
		this.apple = apple;
		this.orange = orange;
	}


	public void onAuditEvent(AuditEvent auditEvent) {
		System.err.println(auditEvent);
	}
	
	public static void main(String [] args){
		Audits.monitorNumberOfThreads();
	}

}
