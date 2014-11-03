package mac.project.email;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MailMessage implements Serializable {
	// 用于保存用户邮件信息
	String Attachment;	// 邮件附件
	String Content;		// 邮件内容
	Date recievedDate;	// 接收日期
	String Address;		// 邮件地址
	String Subject;		// 邮件主题
	boolean contenttype;// 内容类型

	public void setAddress(String Address) {
		this.Address = Address;
	}

	public void setSubject(String Subject) {
		this.Subject = Subject;
	}

	public void setAttachment(String Attachment) {
		this.Attachment = Attachment;
	}

	public void setRecievedDate(Date recievedDate) {
		this.recievedDate = recievedDate;
	}

	public void setIsHtml(boolean type) {
		this.contenttype = type;
	}

	public void setContent(String Content) {
		this.Content = Content;
	}

	public String getAddress() {
		return Address;
	}

	public String getSubject() {
		return Subject;
	}

	public String getAttachment() {
		return Attachment;
	}

	public Date getRecievedDate() {
		return recievedDate;
	}

	public boolean getContenttype() {
		return contenttype;
	}

	public String getContent() {
		return Content;
	}
}
