package application;

/*
 * Hyongsok Sim 
 */

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	static final String user = "aguhag"; // Input sender's email ID
	
	public static void navermail(String emailA, String body) throws Exception {
	
		Properties prop = new Properties(); // Mail Properties
		prop.put("mail.transport.protocol", "smtp"); // Protocol : SMTP
		prop.put("mail.smtp.host", "smtp.naver.com"); // Host : Naver
		prop.put("mail.smtp.port", 587); // Port : 587
		prop.put("mail.smtp.auth", "true"); // Authentication : True
		//prop.put("mail.smtp.ssl.enable","true"); // this code is for Gmail
		//prop.put("mail.smtp.ssl.trust","smtp.gmail.com"); // this code is for Gmail

		Authenticator auth = new MailAuth(); // Set Authenticator with MailAuth(Contains ID and Password) which is subClass of SendEmail
		Session session = Session.getDefaultInstance(prop, auth); // Set Session with properties and authentication
		
		MimeMessage message = new MimeMessage(session); // Prepare to send an Email
		message.setFrom(new InternetAddress(user)); // From : user(The name of the sender)
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailA)); // To : emailA(EmailAddress from outer class)
		message.setSubject("Encrypto Shares"); // Subject : Encrypto Shares
		message.setText(body); // Body : body from the outer class

		try {
			Transport.send(message); // Send Email
			System.out.println("message sent successfully..."); // Print Message sent successfully
		} catch (MessagingException e) {
			e.printStackTrace(); // if not, print Exception
		}

	}
}
