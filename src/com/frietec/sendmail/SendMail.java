package com.frietec.sendmail;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.beust.jcommander.JCommander;
import com.frietec.sendmail.util.EmailSender;

public class SendMail {
	public static final Logger LOGGER = Logger.getLogger(SendMail.class.toString());
	public static final String DEFAULT_SUBJECT = "";
	public static final String DEFAULT_BODYMESSAGE = "";
	public static final String[] DEFAULT_TOADDRESS = { "k.paudel@friedmann-elektrotechnik.de" };
	public static final String DEFAULT_FROM_ADDRESS = "admin@frietec.com";
	public static final String DEFAULT_SMTP_ADDRESS = "SMTP_DEFAULT";
	public static final Boolean IS_AUTHENTICATED = false;
	public static final String DEFAULT_USERNAME = "";
	public static final String DEFAULT_PASSWORD = "";
	public static final Integer DEFAULT_PORT = 25;

	/**
	 * Please check the format of the arguments<br>
	 * If no arguments provided, uses default possible values
	 * 
	 * @param args
	 *            Arguments
	 * 
	 *            SendMail -s Subject -m Message -f FromAddress -t ToAddress1 -t
	 *            ToAddress1 -t ToAddress2 ...<br>
	 * 
	 *            The parameters are provided in the order mentioned above. If
	 *            one parameter provided, it is subject, if two, first one is
	 *            subject and second one is message.
	 */
	public static void main(String... args) {

		MyCommander jCommander = new MyCommander();
		JCommander commander = new JCommander(jCommander, args);

		String fromAddress = jCommander.sender;
		List<String> receiverList = new ArrayList<>();
		receiverList = jCommander.receivers;
		String subject = jCommander.subject;
		String message = jCommander.message;
		List<String> attachments = jCommander.attachments;
		String SMTPHostName = jCommander.SMTP;
		boolean isAuthenticated = jCommander.isAuthenticated;
		String username = jCommander.userName;
		String passwrod = jCommander.password;
		// int port = jCommander.port;

		if (jCommander.help) {
			commander.usage();
			return;
		}

		LOGGER.info("Sending Email");

		LOGGER.info(String.format("Subject=%s, message=%s, toAddress=%s,fromAddress=%s", subject, message, receiverList,
				fromAddress));

		EmailSender emailSender = new EmailSender(SMTPHostName, isAuthenticated, username, passwrod, fromAddress,
				receiverList, subject, message);
		for (String file : attachments) {
			emailSender.attachFile(file);
		}

		emailSender.sendMail();
		return;

	}
}
