package mac.project.main;

public class SleepTime extends Thread{
	int sleepTime = 15;
	boolean isSleep = true;       // 标记该线程是否中止

	public SleepTime() {

	}

	public SleepTime(int st) {
		this.sleepTime = st;
	}

	public void run() {
		while(isSleep){
			try {
				System.err.println("sleepTime>>" + sleepTime);
				sleep(sleepTime*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
