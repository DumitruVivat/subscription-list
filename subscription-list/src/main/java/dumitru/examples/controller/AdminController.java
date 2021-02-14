package dumitru.examples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dumitru.examples.repository.SubscriberRepo;


// functionality:
// same as SubscriberController
// SECURE!!!
// + remove subscriber
// + send mass message
// + change email 

@RestController                 
public class AdminController {
	
	@Autowired
	SubscriberRepo subscriberRepo;
	
	@PostMapping("/admin/subscribers/remove") 
	public String adminSubscribersRemove(
//			@RequestBody String data
			@RequestParam(required=true) int id
			) {
		
		subscriberRepo.removeById(id);
		return "Subscriber "+id+" removed";
		
	}
	@PostMapping("/admin/subscribers/send") 
	public String adminSubscribersSendMessage(
			@RequestParam String message
			) {
		return "Admin: "+message;
	}
	@PostMapping("/admin/subscribers/setemail") 
	public String adminSubscribersSetEmail(
			@RequestParam int id,
			@RequestParam String newEmail
			) {
		subscriberRepo.updateEmail(id, newEmail);;
		return "Email was updated";
	}

}
