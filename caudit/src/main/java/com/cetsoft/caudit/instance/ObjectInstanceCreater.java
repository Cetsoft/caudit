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
