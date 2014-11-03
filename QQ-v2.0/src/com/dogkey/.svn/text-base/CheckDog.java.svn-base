package com.dogkey;

import javax.swing.JOptionPane;

import com.enc.Blowfish;

public class CheckDog extends Thread {
	int i;
	char[] topare;
	DOGRCDL sample = new DOGRCDL();
	boolean flag = true;
	
	public CheckDog(){}
	
	public void run(){
		while(true){
			checkdog(flag);
			try {
				sleep(3*60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}//
		}
	}
	
	public void checkdog(boolean f){
		sample.DogAddr=20;
		sample.DogBytes=48;
		sample.DogData = new byte[100];

		try {
			if(!f){
				topare = sample.CallReadDog();
				String keyString = "logkext";
				Blowfish crypt = new Blowfish(keyString);
				String tpde = new String(topare);
				String tpee = crypt.decryptString(tpde);
				if(tpee==null||!tpee.equals(keyString)) {
					JOptionPane.showMessageDialog(null, "请检查软件狗是否正常！", "软件狗错误",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}else if(Random(13)==7){//true
				topare = sample.CallReadDog();
				String keyString = "logkext";
				Blowfish crypt = new Blowfish(keyString);
				String tpde = new String(topare);
				String tpee = crypt.decryptString(tpde);
				if(tpee==null||!tpee.equals(keyString)) {
					JOptionPane.showMessageDialog(null, "请检查软件狗是否正常！", "软件狗错误",
							JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		} catch (DOGException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "软件狗错误",
			JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	static int Random(int i) {
		int sum = 0;
		long ram = System.currentTimeMillis()/i;
		sum = (int) (ram%i);
		return sum;
	}
}
