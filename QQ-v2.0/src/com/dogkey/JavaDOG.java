/**
 *	JavaDOG.java
 *	
 * 	Contains class JavaDOG to show as a sample
 *  to protect software with SoftDog by SafeNet China Ltd.  
 *
 * @version	1.0
 * @author 	SafeNet China Ltd.
 */
package com.dogkey;

import javax.swing.JOptionPane;

import com.QQPanel.ICQLogin;
import com.enc.Blowfish;

public class JavaDOG 
{
	/**
	 ***************************************
	 *
	 *  @Method:            main
	 *  @Description:       This method runs first.
	 *
	 ***************************************
	 */

	public static void main(String [] args)
	{
		new ICQLogin().loginICQ();
		int i;
		System.out.println( "JavaDog API Demonstration Program" );
		System.out.println( "Implemented for Java J2SDK" );
		System.out.println( "Copyright (C) SafeNet China Ltd.\n" );

		DOGRCDL sample = new DOGRCDL();

		sample.DogData = new byte[100];
		for(i=0;i<20;i++){
			sample.DogData[i]=(byte)(i+605%127);
		}

		sample.DogAddr=0;
		sample.DogBytes=20;

/*//		try {
//			sample.CallWriteDog();
//		}catch(DOGException e){
//			System.err.println( "Dog Write Error" );
//			System.err.println( "A DOGException caught." );
//			System.err.println( "Error: " + e.Error);
//			JOptionPane.showMessageDialog(null, e.getMessage(), "软件狗错误",
//					JOptionPane.ERROR_MESSAGE);
//			System.exit(0);
//		}
//		
//		String keyString = "logkext";
//		String testString = "logkext";
//		System.out.println("加密前**************\n"+"testString = "+testString);
//		Blowfish crypt = new Blowfish(keyString);
//		System.out.println("开始加密");
//		String testString1 = crypt.encryptString(testString);
//		System.out.println("加密之后**************\n"+"testString = "+testString1+" 加密后长度:"+testString1.length());
//		byte[] str = testString1.getBytes();
//		sample.DogBytes=20+testString1.length();
////		String testString2 = crypt.decryptString(new String(str));
////		System.out.println("jie密之后**************\n"+"testString = "+testString2);
//		for(i=20;i<sample.DogBytes;i++){
//			sample.DogData[i]=str[i-20];
//		}
//		try {
//			sample.CallWriteDog();
//		}catch(DOGException e){
//			System.err.println( "Dog Write Error" );
//			System.err.println( "A DOGException caught." );
//			System.err.println( "Error: " + e.Error);
//			JOptionPane.showMessageDialog(null, e.getMessage(), "软件狗错误",
//					JOptionPane.ERROR_MESSAGE);
//			System.exit(0);
//		}

		try {
			sample.CallReadDog();
		}catch(DOGException e){
			System.err.println( "Dog Read Error" );
			System.err.println( "A DOGException caught." );
			System.err.println( "Error: " + e.Error);
			JOptionPane.showMessageDialog(null, e.getMessage(), "软件狗错误",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		CheckDog cd = new CheckDog();
		cd.start();
		
		sample.DogBytes=0;

		try {
			sample.CallReadDog();
		}catch(DOGException e){
			System.err.println( "Dog Read Error" );
			System.err.println( "A DOGException caught." );
			System.err.println( "Error: " + e.Error);
			JOptionPane.showMessageDialog(null, e.getMessage(), "软件狗错误",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		try {
			sample.DogAddr=20;
			sample.DogBytes=48;
			sample.DogData = new byte[100];

			try {
				char[] topare = sample.CallReadDog();
				String keyString = "logkext";
				Blowfish crypt = new Blowfish(keyString);
				String tpde = new String(topare);
				String tpee = crypt.decryptString(tpde);
				if(tpee==null||!tpee.equals(keyString)) {
					JOptionPane.showMessageDialog(null, "请检查软件狗是否正常！", "软件狗错误",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				new ICQLogin().loginICQ();
			} catch (DOGException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "软件狗错误",
				JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			Thread.sleep(3*60*1000);//
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
}
// End of file JavaDOG.java