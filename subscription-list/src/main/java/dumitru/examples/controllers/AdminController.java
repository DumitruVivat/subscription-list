package dumitru.examples.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dumitru.examples.repository.SubscriberRepo;


@Controller                 
public class AdminController {
	
	@Autowired
	SubscriberRepo subscriberRepo;

	@GetMapping("/admin/subscribers") 
	public String adminSubscribersIndex(Model model) {
		model.addAttribute( "subscribers",subscriberRepo.getSubscribers());	
		return "admin/subscribers";
	}
	@GetMapping("/admin/subscribers/compose") 
	public String adminSubscribersCompose(
			@RequestParam() List<Integer> subscribers
	) {
		System.out.println( subscribers );
		List<String> emails = subscriberRepo.getSubscribersEmailsById(subscribers);
		System.out.println(emails);
		return "/admin/compose";
	}
	@PostMapping("/admin/subscribers/remove") 
	public String adminSubscribersRemove(
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
