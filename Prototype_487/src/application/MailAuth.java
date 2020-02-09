package application;

/*
 * Hyongsok Sim 
 */

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuth extends Authenticator{	    
	    PasswordAuthentication pa;
	    
	    public MailAuth() {
	        String mail_id = "aguhag@naver.com"; // SMTP Owner's ID
	        String mail_pw = "aguh@prunuspluto"; // SMTP Owner's Password
	        
	        pa = new PasswordAuthentication(mail_id, mail_pw); // Set Authentication with ID and Password
	    }
	    
	    public PasswordAuthentication getPasswordAuthentication() {
	        return pa; // getter of PasswordAuthentication
	    }

}
