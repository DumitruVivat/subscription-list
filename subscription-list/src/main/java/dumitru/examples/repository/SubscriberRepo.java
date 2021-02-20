package dumitru.examples.repository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import dumitru.examples.domain.mapper.SubscriberRowMapper;
import dumitru.examples.domain.subscriber.Subscriber;

@Repository
public class SubscriberRepo {
	
	@Autowired   
	JdbcTemplate jdbc;
	
	public List<Subscriber> getSubscribers() {
		return jdbc.query("SELECT * FROM public.subscribers;", new SubscriberRowMapper());
	}
    public List<String> getSubscribersEmailsById(List<Integer> ids) {
    	String id_values = "";
    	for (Integer id : ids) {
			id_values += id + ",";
		}
    	id_values = id_values.substring(0,id_values.length()-1);
    	System.out.println(id_values);
		return jdbc.queryForList(
				"SELECT email FROM public.subscribers WHERE id IN ( "+id_values+" );"
				,String.class
		);
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
