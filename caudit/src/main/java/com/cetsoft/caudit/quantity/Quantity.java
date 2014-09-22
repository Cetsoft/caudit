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
package com.cetsoft.caudit.quantity;

import com.cetsoft.caudit.common.AbstractAudit;


/**
 * The Class Quantity.
 */
public abstract class Quantity extends AbstractAudit{

	/** The unit. */
	private volatile String unit;
	
	/**
	 * Instantiates a new quantity.
	 *
	 * @param auditId the audit id
	 * @param auditName the audit name
	 */
	public Quantity(int auditId, String auditName) {
		super(auditId, auditName);
	}

	/**
	 * Gets the unit.
	 *
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit the new unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

}
