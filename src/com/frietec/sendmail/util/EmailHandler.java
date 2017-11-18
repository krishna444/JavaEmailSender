package com.frietec.sendmail.util;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Email sender class <br>
 * This class is implementation of Runnable interface to make easy for
 * multi-threading<br>
 * 
 * @author Krishna Paudel
 *
 *         Jun 5, 2015
 */
public class EmailHandler implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(EmailHandler.class.toString());
	private String hostname;
	private boolean isAuthenticated;
	private String username;
	private String password;
	private String sender;
	private List<String> receiverList;
	private String subject;
	private String message;

	private Multipart multipart;
	private Message emailMessage;

	// Constructor
	public EmailHandler(String hostname, boolean isAuthenticated, String username, String password, String sender,
			List<String> receiverList, String subject, String message) {
		this.hostname = hostname;
		this.isAuthenticated = isAuthenticated;
		this.username = username;
		this.password = password;
		this.sender = sender;
		this.receiverList = receiverList;
		this.subject = subject;
		this.message = message;
		this.multipart = new MimeMultipart();
		this.createMessage();
		this.addMessageBody();
	}

	public void attachFile(String fileName) {
		this.addAttachement(fileName);
	}

	private void createMessage() {
		Session session;
		Properties properties = new Properties();

		// Den Properties wird die ServerAdresse hinzugefuegt
		properties.put("mail.smtp.host", this.hostname);

		// If authentication, uses port 587 and needs user and password
		if (this.isAuthenticated) {
			MailAuthenticator auth = new MailAuthenticator(username, password);
			properties.put("mail.smtp.auth", "true");
			session = Session.getDefaultInstance(properties, auth);
		} else { // uses port 25 and dont need username and password
			properties.put("mail.smtp.auth", "false");
			session = Session.getDefaultInstance(properties);
		}

		try {
			// Eine neue Message erzeugen
			this.emailMessage = new MimeMessage(session);

			// Hier werden die Absender- und Empfaengeradressen gesetzt
			this.emailMessage.setFrom(new InternetAddress(this.sender));

			StringBuilder recipientsAddress = new StringBuilder();
			for (String client : this.receiverList) {
				if (recipientsAddress.length() > 0) {
					recipientsAddress.append(",");
				}
				recipientsAddress.append(client);
			}
			recipientsAddress.append(",");

			this.emailMessage.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipientsAddress.toString(), false));

			// Der Betreff und Body der Message werden gesetzt
			this.emailMessage.setSubject(subject);
			this.emailMessage.setHeader("Test", "Test");
			this.emailMessage.setSentDate(new Date());

			this.emailMessage.setContent(this.multipart);
		} catch (Exception ex) {
			LOGGER.warning("Could not create message. Exception: " + ex);
		}

	}

	private void addMessageBody() {
		BodyPart bodyPart = new MimeBodyPart();
		try {
			bodyPart.setContent(message, "text/html");
			this.multipart.addBodyPart(bodyPart);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	private void addAttachement(String fileName) {

		BodyPart bodyPart = new MimeBodyPart();
		DataSource dataSource = new FileDataSource(fileName);
		try {
			bodyPart.setDataHandler(new DataHandler(dataSource));
			bodyPart.setFileName(fileName);
			this.multipart.addBodyPart(bodyPart);
		} catch (MessagingException ex) {
			LOGGER.warning("Could not perform attachment. Exception:" + ex);
		}

	}

	@Override
	public void run() {
		this.sendMail();
	}

	private void sendMail() {
		LOGGER.info("Sending email...");
		try {
			Transport.send(this.emailMessage);
		} catch (MessagingException e) {
			LOGGER.warning("Could not send email. Exception:" + e);
		}
		LOGGER.info("Sending email...SUCCESS!");
	}

}
