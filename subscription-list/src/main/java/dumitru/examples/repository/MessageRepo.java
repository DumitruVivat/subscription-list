package dumitru.examples.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import dumitru.examples.domain.mapper.MessageRowMapper;
import dumitru.examples.domain.message.Message;
import dumitru.examples.domain.subscriber.Subscriber;

@Repository
public class MessageRepo {
	
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private SubscriberRepo subscriberRepo;

	public void saveMessage(Message message) {
		
		jdbc.update("INSERT INTO public.messages(\n"
				+ "	content)\n"
				+ "	VALUES ('"+ message.getContent() +"');");
	}
	public Message getMessageById(int id) {
		
	   return jdbc.queryForObject("SELECT * FROM public.messages\n"
				+ "WHERE id="+id+";",new MessageRowMapper());
	}
	public List<Message> getMessages() {
		
		return jdbc.query("SELECT *\n"
				+ "	FROM public.messages;",new MessageRowMapper());
	}
	public Map<Subscriber,Message> getNextUnsendMessage() throws SQLException{
		
		Map<Subscriber,Message> tuple = new HashMap<>();
		
		SqlRowSet result = jdbc.queryForRowSet("SELECT subscriber_id, message_id\n"
				+ "	FROM public.subscriber_message\n"
				+ " WHERE sent = false LIMIT 1 OFFSET 0;");
		
		if(result.first()) {
			
			System.out.println( " >>>>>>> " + result.getInt("subscriber_id") + " " + result.getInt("message_id") );
			
			Message message = this.getMessageById(result.getInt("message_id"));
			Subscriber subscriber = subscriberRepo.getSubscriberById(result.getInt("subscriber_id"));
				
			tuple.put(subscriber, message);
			return tuple; 
			
		} else {
        	throw new SQLException();
		}
	}
	public void setMessageAsSent(int message_id) {
		jdbc.update("UPDATE public.subscriber_message\n"
				+ "	SET sent= true\n"
				+ " WHERE message_id='"+message_id+"';");
	}
}
