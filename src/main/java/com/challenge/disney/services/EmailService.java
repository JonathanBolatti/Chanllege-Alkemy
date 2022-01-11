
package com.challenge.disney.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonathan
 */
@Service
public class EmailService {
	
	@Autowired
	public JavaMailSender mailSender; 
	
	public void mailSender(String addressee, String subject, String container){
		SimpleMailMessage mailMessage = new SimpleMailMessage(); 
		mailMessage.setTo(addressee);
		mailMessage.setSubject(subject);
		mailMessage.setText(container);
		
		mailSender.send(mailMessage);
	}
	
}
