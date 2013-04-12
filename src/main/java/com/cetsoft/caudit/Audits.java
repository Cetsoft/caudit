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
package com.cetsoft.caudit;

import com.cetsoft.caudit.common.Audit;
import com.cetsoft.caudit.common.AuditFactory;
import com.cetsoft.caudit.common.AuditMapper;
import com.cetsoft.caudit.common.AuditProvider;
import com.cetsoft.caudit.common.AuditStore;
import com.cetsoft.caudit.common.AuditType;
import com.cetsoft.caudit.loader.AuditContextLoader;
import com.cetsoft.caudit.quantity.DoubleQuantity;
import com.cetsoft.caudit.quantity.LongQuantity;
import com.cetsoft.caudit.stopwatch.CountingStopwatch;
import com.cetsoft.caudit.stopwatch.Stopwatch;

/**
 * The Class Audits.
 */
public class Audits {

	/** The Constant KILOBYTE. */
	private final static long KILOBYTE = 1024;
	
	/** The Constant MEGABYTE. */
	private final static long MEGABYTE = KILOBYTE * 1024;
	
	/** The Constant GIGABYTE. */
	private final static long GIGABYTE = MEGABYTE * 1024;
	
	/** The audit store. */
	private static AuditStore auditStore = new AuditStore(new SimpleAuditFactory());
	
	/** The audit mapper. */
	private static AuditMapper mapper = new AuditMapper();

	static {
		Audits.init();
	}

	/**
	 * Inits the audit context.
	 */
	private static void init() {
		AuditContextLoader loader = new AuditContextLoader(auditStore);
		loader.load();
	}

	/**
	 * Maps the audit.
	 *
	 * @param auditName the audit name
	 * @return the auditId
	 */
	public static int mapAudit(String auditName) {
		return mapper.getAuditId(auditName);
	}
	
	/**
	 * Map audit.
	 *
	 * @param auditName the audit name
	 * @return the auditId
	 */
	public static int mapAudit(long auditName) {
		return mapper.getAuditId(auditName+"");
	}
	
	/**
	 * Unmap audit.
	 *
	 * @param audit the audit
	 */
	public static void unmapAudit(Audit audit) {
		mapper.removeAuditId(audit);
		auditStore.remove(audit);
	}
	
	/**
	 * Monitor used memory in kb.
	 */
	public static void monitorUsedMemoryInKB() {
		final int longQuantityId = Audits.mapAudit("USED_MEMORY_IN_KB");
		final LongQuantity longQuantity = Audits.getLongQuantity(longQuantityId);
		longQuantity.setProvider(new AuditProvider() {
			public void updateAudit() {
				long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / KILOBYTE;
				longQuantity.set(usedMemory);
			}
		});
	}

	/**
	 * Monitor used memory in mb.
	 */
	public static void monitorUsedMemoryInMB() {
		final int longQuantityId = Audits.mapAudit("USED_MEMORY_IN_MB");
		final LongQuantity longQuantity = Audits.getLongQuantity(longQuantityId);
		longQuantity.setProvider(new AuditProvider() {
			public void updateAudit() {
				long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / MEGABYTE;
				longQuantity.set(usedMemory);
			}
		});
	}

	/**
	 * Monitor used memory in gb.
	 */
	public static void monitorUsedMemoryInGB() {
		final int longQuantityId = Audits.mapAudit("USED_MEMORY_IN_GB");
		final LongQuantity longQuantity = Audits.getLongQuantity(longQuantityId);
		longQuantity.setProvider(new AuditProvider() {
			public void updateAudit() {
				long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / GIGABYTE;
				longQuantity.set(usedMemory);
			}
		});
	}

	/**
	 * Monitor number of threads.
	 */
	public static void monitorNumberOfThreads() {
		final int longQuantityId = Audits.mapAudit("NO_OF_THREADS");
		final LongQuantity longQuantity = Audits.getLongQuantity(longQuantityId);
		longQuantity.setProvider(new AuditProvider() {
			public void updateAudit() {
				longQuantity.set(Thread.activeCount());
			}
		});
	}

	/**
	 * Gets the basic stopwatch.
	 *
	 * @param basicStopwatchId the basic stopwatch id
	 * @return the basic stopwatch
	 */
	public static Stopwatch getBasicStopwatch(int basicStopwatchId) {
		return auditStore.get(basicStopwatchId, AuditType.BASIC_STOPWATCH);
	}

	/**
	 * Gets the counting stopwatch.
	 *
	 * @param countingStopwatchId the counting stopwatch id
	 * @return the counting stopwatch
	 */
	public static CountingStopwatch getCountingStopwatch(int countingStopwatchId) {
		return auditStore.get(countingStopwatchId, AuditType.COUNTING_STOPWATCH);
	}

	/**
	 * Gets the long quantity.
	 *
	 * @param longQuantityId the long quantity id
	 * @return the long quantity
	 */
	public static LongQuantity getLongQuantity(int longQuantityId) {
		return auditStore.get(longQuantityId, AuditType.LONG_QUANTITY);
	}

	/**
	 * Gets the double quantity.
	 *
	 * @param quantityId the quantity id
	 * @return the double quantity
	 */
	public static DoubleQuantity getDoubleQuantity(int quantityId) {
		return auditStore.get(quantityId, AuditType.DOUBLE_QUANTITY);
	}

	/**
	 * A factory for creating SimpleAudit objects.
	 */
	private static class SimpleAuditFactory implements AuditFactory {
		
		/* (non-Javadoc)
		 * @see com.cetsoft.caudit.common.AuditFactory#create(com.cetsoft.caudit.common.AuditType, int)
		 */
		public Audit create(AuditType type, int auditId) {
			String auditName = mapper.getAuditName(auditId);
			switch (type) {
			case BASIC_STOPWATCH:
				return new Stopwatch(auditId, auditName);
			case COUNTING_STOPWATCH:
				return new CountingStopwatch(auditId, auditName);
			case LONG_QUANTITY:
				return new LongQuantity(auditId, auditName);
			case DOUBLE_QUANTITY:
				return new DoubleQuantity(auditId, auditName);
			case COMPLEX_QUANTITY:
				return new DoubleQuantity(auditId, auditName);
			}
			return null;
		}
	}

}
