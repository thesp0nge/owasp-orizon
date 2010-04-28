/**
 * This file is part of Owasp Orizon.
 * Owasp Orizon is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Owasp Orizon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.owasp.orizon.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

import org.owasp.orizon.core.Constants;
import org.owasp.orizon.exceptions.OrizonIOException;

/**
 * This class is intended to provide some common facilities in managing files
 * @author thesp0nge
 * @since 1.30
 */
public class IOUtils {
	/**
	 * Directory is valid if it exists, does not represent a file, and can be
	 * read.
	 * @throws OrizonIOException 
	 */
	public static void isAGoodDirectory (String aDirectory) throws OrizonIOException {
	 isAGoodDirectory(new File(aDirectory));
	}
	/**
	 * Directory is valid if it exists, does not represent a file, and can be
	 * read.
	 * @throws OrizonIOException 
	 */
	public static void isAGoodDirectory (File aDirectory) 
	throws OrizonIOException {
		if (aDirectory == null) 
			throw new OrizonIOException(Constants.O_E_MSG_NULL_DIR, Constants.O_E_NULL_PARAM);
		
		if (!aDirectory.exists()) 
			throw new OrizonIOException(Constants.O_E_MSG_DIR_NOT_FOUND + aDirectory, Constants.O_E_DIR_NOT_FOUND);
		
		if (!aDirectory.isDirectory()) 
			throw new OrizonIOException(Constants.O_E_MSG_NOT_A_DIR + aDirectory, Constants.O_E_NOT_A_DIR);
		
		if (!aDirectory.canRead())
			throw new OrizonIOException(Constants.O_E_MSG_CAN_READ_DIR + aDirectory , Constants.O_E_PERM);
	}
	public static boolean isARealFile(File f) throws OrizonIOException {
		if (f.exists() && f.isFile())
			return true;
		if (! f.exists())
			throw new OrizonIOException(Constants.O_E_MSG_FILE_NOT_FOUND + f.getName(), Constants.O_E_FILE_NOT_FOUND);
		if (! f.isFile())
			throw new OrizonIOException(Constants.O_E_MSG_NOT_A_FILE + f.getName(), Constants.O_E_NOT_A_FILE);
		return false;
	}
	
	public static int countEmptyLinesInAFile(File f) throws OrizonIOException {
		int n = 0;
		LineNumberReader io;
		String line;
		try {
			io = new LineNumberReader(new FileReader(f));
		
			while ((line = io.readLine()) != null) {
				if ("".equals(line))
					n++;
			}
			io.close();
		} catch (FileNotFoundException e) {
			throw new OrizonIOException(e.getMessage(), 0);
		}
		catch (IOException e) {
			throw new OrizonIOException(e.getMessage(), 0);
		}
		return n;
	}
	
	public static int countLinesInAFile(File f) throws OrizonIOException {
		int n = 0;
		LineNumberReader io;
		try {
			io = new LineNumberReader(new FileReader(f));
			while ((io.readLine()) != null) {
				n++;
			}
			io.close();
		} catch (FileNotFoundException e) {
			throw new OrizonIOException(e.getMessage(), 0);
		}
		catch (IOException e) {
			throw new OrizonIOException(e.getMessage(), 0);
		}
		return n;
	}
}
