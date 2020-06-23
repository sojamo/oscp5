/**
 * An OSC (Open Sound Control) library for processing.
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 * 
 * @author ##author##
 * @modified ##date##
 * @version ##version##
 */

package oscP5;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class OscPlug {

	public void plug(final Object theObject, final String theMethodName, final String theAddrPattern) {
		/** TODO re-implement */
	}

	public void plug(final Object theObject, final String theMethodName, final String theAddrPattern,
			final String theTypetag) {
		/** TODO re-implement */
	}

	public Object getObject() {
		/** TODO re-implement */
		return null;
	}

	public boolean checkMethod(final OscMessage theOscMessage, final boolean isArray) {
		/** TODO re-implement */
		return false;
	}

	public Method getMethod() {
		return null;
	}

	static public String checkType(final String theName) {
		/** TODO check or re-implement */
		if (theName.equals("int")) {
			return "i";
		} else if (theName.equals("float")) {
			return "f";
		} else if (theName.equals("java.lang.String")) {
			return "s";
		} else if (theName.equals("[Ljava.lang.String;")) {
			return "S";
		}

		else if (theName.equals("char")) {
			return "c";
		} else if (theName.equals("[B")) {
			return "B";
		} else if (theName.equals("[F")) {
			return "F";
		} else if (theName.equals("[I")) {
			return "I";
		}

		else if (theName.equals("double")) {
			return "d";
		} else if (theName.equals("boolean")) {
			return "T";
		} else if (theName.equals("long")) {
			return "h";
		}
		return null;
	}
}
