package com.amazon.ask.helloworld.DAO;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Basemethods {

	public static String generatePassword() {

		int n = 8;
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	public static void sendMail(String email, String OTP) {
		System.out.println("in mail send method::::");
		java.util.Properties properties = new java.util.Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		javax.mail.Session mailSession = javax.mail.Session.getInstance(properties);

		try {
			MimeMessage message = new MimeMessage(mailSession);

			message.setContent("Your password " + OTP+" for email "+email, "text/html");
			message.setSubject("Sucessfully Booked ");

			InternetAddress sender = new InternetAddress("noreply.flightbooking2@gmail.com", "FLIGHT BOOKING");
			InternetAddress receiver = new InternetAddress(email);
			message.setFrom(sender);
			message.setRecipient(Message.RecipientType.TO, receiver);
			message.saveChanges();

			javax.mail.Transport transport = mailSession.getTransport("smtp");
			transport.connect("smtp.gmail.com", 587, "noreply.flightbooking2@gmail.com", "kas212000");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Msg sent :::: ");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
