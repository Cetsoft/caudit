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
* Date   : Sep 23, 2014
*/
package com.cetsoft.caudit;

import com.cetsoft.caudit.common.AuditProvider;
import com.cetsoft.caudit.common.AuditRemovalListener;
import com.cetsoft.caudit.observable.AuditEvent;
import com.cetsoft.caudit.quantity.DoubleQuantity;
import com.cetsoft.caudit.quantity.LongQuantity;
import com.cetsoft.caudit.stopwatch.CountingStopwatch;
import com.cetsoft.caudit.stopwatch.Stopwatch;

/**
 * The Class ExampleAudit.
 */
public class ExampleAudit {

	/** The Constant BASIC_STOPWATCH_ID. */
	private final static int BASIC_STOPWATCH_ID = Audits.mapAudit("main.basicStopwatch");
	
	/** The Constant COUNTING_STOPWATCH_ID. */
	private final static int COUNTING_STOPWATCH_ID = Audits.mapAudit("main.countingStopwatch");
	
	/** The Constant LONG_QUANTITY_ID. */
	private final static int LONG_QUANTITY_ID = Audits.mapAudit("main.longQuantity");
	
	/** The Constant PROVIDED_QUANTITY_ID. */
	private final static int PROVIDED_QUANTITY_ID = Audits.mapAudit("main.providedQuantity");

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws InterruptedException the interrupted exception
	 */
	public static void main(String[] args) throws InterruptedException {
		final Stopwatch stopwatch = Audits.getBasicStopwatch(BASIC_STOPWATCH_ID);
		stopwatch.setShouldReset(false);
		stopwatch.setRemovalListener(new AuditRemovalListener() {
			public void onRemoval(AuditEvent auditEvent) {
				System.out.println(auditEvent);
			}
		});
		Audits.unmapAudit(stopwatch);
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					stopwatch.start();
					try {
						Thread.sleep((long) (Math.random() * 100));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					stopwatch.stop();
				}
			}
		}).start();
		for (int i=0;i<10;i++) {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						CountingStopwatch stopwatch = Audits.getCountingStopwatch(COUNTING_STOPWATCH_ID);
						stopwatch.start();
						try {
							Thread.sleep((long) (Math.random() * 100));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						stopwatch.stop(23);
					}
				}
			}).start();
		}
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					LongQuantity quantity = Audits.getLongQuantity(LONG_QUANTITY_ID);
					quantity.increment();
				}
			}
		}).start();
		final DoubleQuantity doubleQuantity = Audits.getDoubleQuantity(PROVIDED_QUANTITY_ID);
		doubleQuantity.setProvider(new AuditProvider() {
			public void updateAudit() {
				doubleQuantity.set(Math.random());
			}
		});
		Audits.monitorUsedMemoryInMB();
		Audits.monitorUsedMemoryInKB();
		Audits.monitorNumberOfThreads();
	}
}
