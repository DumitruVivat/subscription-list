package dumitru.examples.tasks;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import dumitru.examples.domain.message.Message;
import dumitru.examples.domain.subscriber.Subscriber;
import dumitru.examples.repository.MessageRepo;

@Component
public class EmailScheduler {
	
	@Autowired
	MessageRepo messageRepo;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Scheduled(fixedRate = 20000,initialDelay = 0)
	public void sendEmail() {
		
		System.err.println("PREPARING TO SEND EMAIL");
		
		Map<Subscriber, Message> tuple;
		try {
			tuple = messageRepo.getNextUnsendMessage();
			
		    System.err.println(tuple);
			
		    SimpleMailMessage message = new SimpleMailMessage();
				   
			  tuple.forEach( (k,v) -> {
								
				message.setFrom("iduca077@gmail.com");
				message.setTo( k.getEmail());
				message.setSubject( "Newsletter" );
				message.setText( v.getContent());
								
				javaMailSender.send(message);
				messageRepo.setMessageAsSent(v.getId());
								
			});
		} catch (SQLException e) {
			System.err.println("NOTHING TO SEND!");
		}
	}
}
