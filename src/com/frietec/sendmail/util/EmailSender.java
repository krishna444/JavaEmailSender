package com.frietec.sendmail.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class EmailSender {
	// private static final Log LOGGER = LogFactory.getLog(EmailSender.class);
	private static final Logger LOGGER = Logger.getLogger(EmailSender.class.toString());
	private EmailHandler emailHandler;

	/**
	 * Constructor to send email messages
	 */
	public EmailSender(String hostname, boolean isAuthenticated, String username, String password, String sender,
			List<String> receiverList, String subject, String message) {
		this.emailHandler = new EmailHandler(hostname, isAuthenticated, username, password, sender, receiverList,
				subject, message);
	}

	public void attachFile(String fileName) {
		this.emailHandler.attachFile(fileName);
	}

	/**
	 * Sends plain email
	 * 
	 * @param hostname
	 *            Hostname for SMTP server
	 * @param isAuthenticated
	 *            Indicated whether authentication needed or not
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 * @param sender
	 *            Sender Address
	 * @param receiverList
	 *            List of receivers
	 * @param subject
	 *            Subject
	 * @param message
	 *            Message Text to send
	 */
	public void sendMail() {
		ExecutorService pool = Executors.newSingleThreadExecutor();
		try {
			pool.execute(this.emailHandler);
		} catch (Exception ex) {
			LOGGER.warning(
					"Can not send message. Please check email server and credentials. Exception=>" + ex.toString());

		} finally {
			pool.shutdown();
		}
	}

}
