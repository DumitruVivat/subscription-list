package dumitru.examples.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dumitru.examples.domain.mapper.MessageRowMapper;
import dumitru.examples.domain.message.Message;

@Repository
public class MessageRepo {
	
	@Autowired
	JdbcTemplate jdbc;

	public void saveMessage(Message message) {
		jdbc.update("INSERT INTO public.messages(\n"
				+ "	content)\n"
				+ "	VALUES ('"+ message.getContent() +"');");
	}
	public void getMessageById(int id) {
		jdbc.update("SELECT id\n"
				+ "	FROM public.messages;"
				+ "WHERE id="+id+"");
	}
	public List<Message> getMessages() {
		
		return jdbc.query("SELECT id, content\n"
				+ "	FROM public.messages;",new MessageRowMapper());
	}
}
