
/**
 *	GTDDOG.java
 *	
 * 	Contains class GTDDOG defining variables and native methods to
 * 	interface with GTD DOG device driver.
 *
 * @version	1.0
 * @author 	SafeNet China Ltd.
 */
package com.dogkey;

class GTDDOG 
{

	//The global variable DogAddr indicates the first memory
	//address(range from 0 to 99) of user area in the SoftDog
	//The sum of DogAddr and DogBytes should not exceed 100
	public int DogAddr;

	//The global variable DogBytes indicates the number of bytes
	//in reading/writing operation or convertion
	//The sum of DogAddr and DogBytes should not exceed 100
	public int DogBytes;


	//the global variable DogData refers to the pointer variable 
	//which points to the data for writing/reading or converting
	public byte[] DogData;

	public GTDDOG()		
	{
	}	


	/**
	 * Perform Write Dog Service 
	 * @param none.
	 */

	public native int    
	WriteDog();

	/**
	 * Perform Read Dog Service 
	 * @param none.
	 */

	public native int    
	ReadDog();



	/**
	 *	Static initializer 
	 */

	static 
	{
		try 
		{
			System.loadLibrary("DOGJava"); 
		}
		catch( UnsatisfiedLinkError e )
		{
			System.err.println("Can't find library DOGJava.DLL"); 
			System.exit( -1 );
		}
	}
}         
// End of file GTDDOG.java

