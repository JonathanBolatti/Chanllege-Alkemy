
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
	
	public void enviarMail(String body, String title, String mail){
		 SimpleMailMessage msj = new SimpleMailMessage();
        msj.setTo(mail);
        msj.setFrom("loscanuteros06@hotmail.com");
        msj.setSubject(title);
        msj.setText(body);
        
        mailSender.send(msj);
	}
}
