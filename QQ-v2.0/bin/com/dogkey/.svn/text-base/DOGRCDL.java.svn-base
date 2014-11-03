/**
 *	DOGRCDL.java
 *	
 * 	Contains class DOGRCDL to show methods to call base class GTDDOG's 
 *      native methods
 * 
 * @version	1.0
 * @author 	SafeNet China Ltd.
 */
package com.dogkey;

public class DOGRCDL extends GTDDOG {
	/**
	 * write a byte array to Dog memory 
	 * return 0 if success
	 */

	public int CallWriteDog()throws DOGException{
		int retCode;

		if((retCode = WriteDog()) != 0)
			throw new DOGException(retCode);
		else{
			System.out.println("Dog Write is OK!");
		}
		return retCode;
	}


	/**
	 * read Dog memory
	 * return 0 if success
	 */

	public char[] CallReadDog() throws DOGException{
		int retCode;
		char[] toPrint;
		int i;
		int serialno = 0;
                
		toPrint=new char[DogBytes];
                
		if((retCode = ReadDog()) != 0)
			throw new DOGException(retCode);
		else {
			if (DogBytes>0){ 
				System.out.println("Dog Read is OK!");
				System.out.print("Dog Read result ");
				for(i=0;i<DogBytes;i++){
					toPrint[i]=(char)DogData[i];
					System.out.print(toPrint[i]);
				}
				System.out.println("");
			}else{
				System.out.println("Get Dog Serial Number is OK!");
				serialno = (DogData[0]+256)%256 + ((DogData[1]+256)%256)*256+
							((DogData[2]+256)%256)*65536 + ((DogData[3]+256)%256)*256*65536; 
				System.out.println("Dog Serial Number is " + serialno);
			}
			
		}
		return toPrint;
	}
}
// End of file DOGRCDL.java
