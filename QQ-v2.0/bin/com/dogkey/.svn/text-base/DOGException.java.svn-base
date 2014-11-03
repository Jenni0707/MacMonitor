/**
 * DOGException.java
 * 
 *This file an execption-handling file
 *
 * @version 1.0
 * @author SafeNet China Ltd.
 */
package com.dogkey;

public class DOGException extends Exception
{		
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
		public 	int		Error;

		/**
		 *	Constructs a DOG exception that contain
		 *	an error number returned by DOG device driver
		 *
		 * @param ErrorNumber   Value returned by last call to DOG device driver.
		 */	
					
		public DOGException( int ErrorNumber )
		{
				super( "Dog Operation Error " + ErrorNumber );
				Error = ErrorNumber;
		}
}
// End of file DOGException.java

