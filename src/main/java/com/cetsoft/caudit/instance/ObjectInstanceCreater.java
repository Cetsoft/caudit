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
* Date   : Apr 11, 2013
*/
package com.cetsoft.caudit.instance;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


// TODO: Auto-generated Javadoc
/**
 * The Class ObjectInstanceCreater.
 */
public class ObjectInstanceCreater {

	/**
	 * Creates the object.
	 *
	 * @param className the class name
	 * @param constructorArgs the constructor args
	 * @return the object
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SecurityException the security exception
	 * @throws NoSuchMethodException the no such method exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object createObject(String className, Object constructorArgs[]) throws ClassNotFoundException, SecurityException, 
	NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Class objectClass = Class.forName(className);
		Class [] parameterTypes = getParameterTypes(constructorArgs);
		Constructor constructor = objectClass.getConstructor(parameterTypes);
		Object object = constructor.newInstance(constructorArgs);
		return object;
	}
	
	/**
	 * Gets the parameter types.
	 *
	 * @param constructorArgs the constructor args
	 * @return the parameter types
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getParameterTypes(Object constructorArgs[]){
		Class[] parameterTypes = new Class[constructorArgs.length];
		for (int i=0;i<constructorArgs.length;i++) {
			try{
				Integer.parseInt(constructorArgs[i].toString());
				parameterTypes[i] = int.class;
			}catch (Exception e) {
				parameterTypes[i] = String.class;
			}
		}
		return parameterTypes;
	}
	
}
