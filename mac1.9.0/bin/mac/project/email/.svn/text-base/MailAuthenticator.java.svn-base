package mac.project.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(MailUser.getUserName(), MailUser.getPassWord());
	}
}