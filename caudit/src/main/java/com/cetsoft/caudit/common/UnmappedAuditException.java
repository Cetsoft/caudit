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
 * The Class UnmappedAuditException.
 */
public class UnmappedAuditException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8102366232518273815L;

	/**
	 * Instantiates a new unmapped audit exception.
	 *
	 * @param auditId the audit id
	 */
	public UnmappedAuditException(int auditId) {
		super("There is not any mapped audit with the given auditId = "+auditId);
	}

}
