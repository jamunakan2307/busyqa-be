package com.busyqa.crm.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailClient {

	private JavaMailSender mailSender;
	private MailContentBuilder mailContentBuilder;

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Autowired
	public MailClient(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
		this.mailSender = mailSender;
		this.mailContentBuilder = mailContentBuilder;
	}

	public void prepareAndSend(String recipient, String message, String subject, String templateName) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(senderEmail);
			messageHelper.setTo(recipient);
			messageHelper.setSubject(subject);
			String content = mailContentBuilder.build(message, templateName);
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
		}
	}

	public void prepareAndSend(String recipient, String url, String trainerName) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(senderEmail);
			messageHelper.setTo(recipient);
			messageHelper.setSubject("Register to BusyQA Portal");
			String content = "Please complete your registration here !!!     " + url;
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
		}
	}

	public void prepareAndSendLateFees(String recipient, String amount, String usrName, String nowamount, String schededDate) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(senderEmail);
			messageHelper.setTo(recipient);
			messageHelper.setSubject("[Late Payment]Busyqa Payment Alert ...");
			String content1 = "Hello " + usrName + "\r\n" ;
			String content2 = "\r\n" +"You have  " + " Late  payment of " + amount + "$CAD" +  " And now new Payment including next installment and  late fees charge  is "+nowamount+ "$CAD" + " Please make the payment at earliest to avoid extra charges" ;
			String content33 = " Payment Date : " + schededDate;
			String content3  = "\r\n" + "Thank you  " + "\r\n" + "BusyQa Accounts Team" +  "\r\n";
			String content = content1 + "\r\n" + content2 + content33 + content3;
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
		}
	}

	public void prepareAndSendMissedFees(String recipient, String amount, String usrName, String schededDate) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(senderEmail);
			messageHelper.setTo(recipient);
			messageHelper.setSubject("[Missed Payment]Busyqa Payment Alert ...");
			String content1 = "Hello " + usrName + "\r\n" ;
			String content2 = "\r\n" +"You have  " + " Missed payment of " + amount + "$CAD"  + " Please make the payment at earliest to avoid extra charges" ;
			String content33 = "  Payment Date : " + schededDate;
			String content3  = "\r\n" + "Thank you  " + "\r\n" + "BusyQa Accounts Team" +  "\r\n";
			String content = content1 + "\r\n" + content2 +  content33 + content3;
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
		}
	}


	public void prepareAndSend2(String recipient, String contentTest) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(senderEmail);
			messageHelper.setTo(recipient);
			messageHelper.setSubject("BusyQA Missed Payment");
			String content = "You have missed a payment of      " + contentTest;
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
		}
	}

	public void prepareAndSend3(String recipient, String contentTest) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(senderEmail);
			messageHelper.setTo(recipient);
			messageHelper.setSubject("BusyQA Late  Payment");
			String content = "Your are Late to Pay      " + contentTest;
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
		}
	}

	public void prepareAndSendPassword(String recipient, String url) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom(senderEmail);
			messageHelper.setTo(recipient);
			messageHelper.setSubject("You have been provided Access to BusyQA Portal");
			String content = "Please reset Your Password!!!              " + url;
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			// runtime exception; compiler will not force you to handle it
		}
	}

}
