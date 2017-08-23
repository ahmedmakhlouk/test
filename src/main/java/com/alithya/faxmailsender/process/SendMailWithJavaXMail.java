package com.alithya.faxmailsender.process;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class SendMailWithJavaXMail {
	private static final String FILE_TO_ATTACH = "";
	////////////////////////////
	static String YAHOO_SMTP_HOST = "smtp.mail.yahoo.com";
	// static Integer YAHOO_SMTP_SERVER_PORT = 465;
	static Integer YAHOO_SMTP_SERVER_PORT = 587;
	static String YAHOO_MAIL_ID = "";
	static String YAHOO_PASSWORD = "";
	/////////////////////////////
	static String GMAIL_SMTP_HOST = "smtp.gmail.com";
	static Integer GMAIL_SMTP_SERVER_PORT = 587;
	static String GMAIL_MAIL_ID = "";
	static String GMAIL_PASSWORD = "";
	static String GMAIL_MAIL = "";

	static String RECIEVER = "";

	/**
	 * Sending Email from given adresse
	 */
	public static void sendEmail() {
		String[] recipients = { RECIEVER };

		SimpleEmail email = new SimpleEmail();
		email.setStartTLSEnabled(true);
		email.setHostName(GMAIL_SMTP_HOST);
		email.setAuthentication(GMAIL_MAIL_ID, GMAIL_PASSWORD);
		email.setDebug(true);
		email.setSmtpPort(YAHOO_SMTP_SERVER_PORT);
		try {
			for (int i = 0; i < recipients.length; i++) {
				email.addTo(recipients[i]);
			}
			email.setFrom(GMAIL_MAIL_ID, "Me");
			email.setSubject("Test message to telus from gmailllll");
			email.setMsg("This is a simple test of commons-email");
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	/**
	 * Sending an email with attachement
	 */
	public static void sendEmailWithAttachment() {
		// Recipient's email ID needs to be mentioned.
		String to = RECIEVER; /******* NEEDED *******/

		// Sender's email ID needs to be mentioned
		String from = GMAIL_MAIL_ID;
		/******* NEEDED *******/

		final String username = GMAIL_MAIL_ID;
		/******* NEEDED *******/
		final String password = GMAIL_PASSWORD;/******* NEEDED *******/

		// Assuming you are sending email through relay.jangosmtp.net
		String host = GMAIL_SMTP_HOST; /******* NEEDED *******/

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", GMAIL_SMTP_SERVER_PORT);/******* NEEDED *******/

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject("Testing Subject");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Now set the actual message
			messageBodyPart.setText("This is message body");

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = FILE_TO_ATTACH;
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String args[]) {
		System.out.println("01");
		sendEmailWithAttachment();
		System.out.println("02");

	}
}
