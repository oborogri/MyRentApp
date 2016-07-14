package controllers;

import java.util.ArrayList;

import play.Logger;

import play.*;
import play.mvc.*;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import models.*;

/**
 * Class configured only for localhost
 * 
 * @author G.Oboroceanu
 * 
 */
public class Contact extends Controller {

	/**
	 * Renders contact page
	 */
	public static void index() {
		render();
	}

	/**
	 * Renders contact error page
	 */
	public static void contacterror() {
		render();
	}
	/**
	 * Renders feedback page
	 */
	public static void feedback() {

		render();
	}

	/**
	 * 
	 * @param firstName
	 *            : the message sender's first and
	 * @param lastName
	 *            : last name.
	 * @param emailSender
	 *            : the email address of the sender of the message.
	 * @param messageTxd
	 *            : the message.
	 */
	// public static void sendMessage(String firstName, String lastName, String
	// emailSender, String messageTxd)
	public static void sendMessage(String firstName, String lastName, String email, String messageText) {

		if (Accounts.isValidEmailAddress(email)) {

			/*if (session.get("logged_in_userid") != null) {*/

				final String username = "xxxxxx@yahoo.com";
				final String password = "password";

				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp.mail.yahoo.com");
				props.put("mail.smtp.port", "587");

				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

				try {
					String forwarderAddress = username;
					String destinationAddress1 = email;
					String destinationAddress2 = username;
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(forwarderAddress));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinationAddress1));
					message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(destinationAddress2));
					message.setSubject("Message for MyRent Webmaster");
					message.setText(messageText);

					Transport.send(message);

				} catch (MessagingException e) {
					Logger.info(e.getMessage());
					feedback();// a temporary fix: an exception caught when
								// sendMessage invoked from Cloud
					throw new RuntimeException(e);
				}

				feedback();

			/*} else {
				Welcome.index();*/
			
		} else {
			contacterror();
		}
	}
}
