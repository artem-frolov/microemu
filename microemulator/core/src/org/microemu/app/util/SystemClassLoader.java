/*
 *  MicroEmulator
 *  Copyright (C) 2001-2006 Bartek Teodorczyk <barteo@barteo.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.microemu.app.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SystemClassLoader extends ClassLoader {
	
	private static List childClassLoaders = new ArrayList(); 
	
	protected SystemClassLoader() {
		this(getSystemClassLoader());
	}

	public SystemClassLoader(ClassLoader parent) {
		super(parent);
		
		if (this instanceof ProgressJarClassLoader) {
			childClassLoaders.add(this);
		}
	}

	protected URL findResource(String name) {
		URL result = null;
		
		Iterator it = childClassLoaders.iterator();
		while (it.hasNext()) {
			result = ((ProgressJarClassLoader) it.next()).findResource(name);
			if (result != null) {
				break;
			}
		}
		
		return result;
	}
	
}