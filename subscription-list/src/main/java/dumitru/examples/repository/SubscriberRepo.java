package dumitru.examples.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import dumitru.examples.domain.Subscriber;
import dumitru.examples.mapper.SubscriberRowMapper;

@Repository
public class SubscriberRepo {
	
	@Autowired             // SB - > Automatically resolve DI
	JdbcTemplate jdbc;
	
	public List<Subscriber> getSubscribers() {
		
		return jdbc.query("SELECT * FROM public.subscribers;", new SubscriberRowMapper());
	}
	
	public void save(Subscriber subscriber) {
		jdbc.update("INSERT INTO public.subscribers(\n"
				+ "name, email)\n"
				+ "VALUES ('"+subscriber.getName()+"', '"+subscriber.getEmail()+"')");
	}
	public void updateName(String email,String newName) {
		jdbc.update("UPDATE public.subscribers\n"
				+ "	SET name='"+newName+"'\n"
				+ "	WHERE email='"+email+"';");
	}
	public void updateEmail(int id,String newEmail) {
		jdbc.update("UPDATE public.subscribers\n"
				+ "	SET email= '"+newEmail+"'\n"
				+ "	WHERE id='"+id+"'");
	}
	public void removeById(int id) {
		jdbc.update("DELETE FROM public.subscribers\n"
				+ "	WHERE id="+id+";");
	}
}
