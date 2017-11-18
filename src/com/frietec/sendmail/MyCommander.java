package com.frietec.sendmail;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class MyCommander {
	@Parameter
	private List<String> parameters = new ArrayList<>();

	@Parameter(names = { "--from", "-f" }, description = "Sender address")
	public String sender = SendMail.DEFAULT_FROM_ADDRESS;

	@Parameter(names = { "--to", "--receiver", "-t", "-r" }, description = "Receiver address")
	public List<String> receivers = new ArrayList<>();

	@Parameter(names = { "--subject", "-s" }, description = "Subject")
	public String subject = SendMail.DEFAULT_SUBJECT;

	@Parameter(names = { "--message", "-m" }, description = "Message")
	public String message = SendMail.DEFAULT_BODYMESSAGE;

	@Parameter(names = { "--attach", "-a" }, description = "Message")
	public List<String> attachments = new ArrayList<>();

	@Parameter(names = { "--smtp" }, description = "SMTP hostname", hidden = true)
	public String SMTP = SendMail.DEFAULT_SMTP_ADDRESS;

	@Parameter(names = { "--auth" }, description = "Is authenticated?", hidden = true)
	public boolean isAuthenticated = SendMail.IS_AUTHENTICATED;

	@Parameter(names = { "--user" }, description = "User for authentication", hidden = true)
	public String userName = SendMail.DEFAULT_USERNAME;

	@Parameter(names = { "--password" }, description = "Password for authentication", hidden = true)
	public String password = SendMail.DEFAULT_PASSWORD;

	@Parameter(names = { "--port" }, description = "Port", hidden = true)
	public int port = SendMail.DEFAULT_PORT;

	@Parameter(names = { "--help", "--usage", "-h" }, help = true, description = "Dispaly Usage Information. \n***Use inverted commas if content is more than one word.***")
	public boolean help;

}
