package mac.project.main;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;import java.util.ArrayList;import java.util.List;import java.util.regex.Matcher;import java.util.regex.Pattern;public class KillProcess {	private String cmd;	private Runtime rt;	private Process proc;	static boolean sip_killed = false;	static boolean kip_killed = false;	ScreenInfoPanel sip; 	KeyInfoPanel kip;	public KillProcess(ScreenInfoPanel sip) { 		this.sip = sip;	}		public KillProcess(KeyInfoPanel kip) { 		this.kip = kip; 	}		public void killProgress() {		try {//			Pattern p1;			Pattern p2;//			Matcher m1;			Matcher m2;			rt = Runtime.getRuntime();			proc = rt.exec("ps -e");			InputStreamReader isr = new InputStreamReader(proc.getInputStream());			BufferedReader br = new BufferedReader(isr);			String line = null;			if(sip != null){//				p1 = Pattern.compile("\\d+\\ ScreenSaver ");				p2 = Pattern.compile("\\d+\\ "); // get PID				String PID = null;				List<String> PID_ScreenSaver = new ArrayList<String>();				while ((line = br.readLine()) != null) {//					m1 = p1.matcher(line);//					if (m1.find()) {					if (line.contains("ScreenSaver")) {						System.out.println(line);						m2 = p2.matcher(line);						if (m2.find()){							PID = m2.group();							PID_ScreenSaver.add(m2.group());						}						System.out.println(PID);					}				}				proc.waitFor();				// Kill the "find " progress in CustomedFilterPanel				if (PID != null) {//					cmd = "kill " + PID;					cmd = "killall ScreenSaver";					Process proc1 = Runtime.getRuntime().exec(cmd);					int exitVal = proc1.waitFor();					System.out.println("KillProgress's Exit Value = " + exitVal);					sip_killed = true;				}				// Kill the "ScreenSaver" progress				for (int j = 0; j < PID_ScreenSaver.size(); j++) {					cmd = "kill " + PID_ScreenSaver.get(j);					Process proc2 = Runtime.getRuntime().exec(cmd);					int exitVal = proc2.waitFor();					System.out.println("KillProgress's Exit Value = " + exitVal);					sip_killed = true;				}			}						if(kip != null){//				p1 = Pattern.compile("\\d+\\ keydefin ");				p2 = Pattern.compile("\\d+\\ "); // get PID				String PID = null;				List<String> PID_logKext = new ArrayList<String>();				while ((line = br.readLine()) != null) {//					m1 = p1.matcher(line);//					if (m1.find()) {					if (line.contains("keydefin")) {						System.out.println(line);						m2 = p2.matcher(line);						if (m2.find()){							PID = m2.group();							PID_logKext.add(m2.group());						}						System.out.println(PID);					}				}				proc.waitFor();				// Kill the "find " progress in CustomedFilterPanel				if (PID != null) {					cmd = "kill " + PID;					Process proc1 = Runtime.getRuntime().exec(cmd);					int exitVal = proc1.waitFor();					System.out.println("KillProgress's Exit Value = " + exitVal);					kip_killed = true;				}				// Kill the "ScreenSaver" progress				for (int j = 0; j < PID_logKext.size(); j++) {					cmd = "kill " + PID_logKext.get(j);					Process proc2 = Runtime.getRuntime().exec(cmd);					int exitVal = proc2.waitFor();					System.out.println("KillProgress's Exit Value = " + exitVal);					kip_killed = true;				}			}			br.close();		} catch (IOException e) {			e.printStackTrace();		} catch (InterruptedException e) {			e.printStackTrace();		}	}}