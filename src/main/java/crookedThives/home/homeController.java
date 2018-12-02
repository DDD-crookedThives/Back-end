package crookedThives.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {
	
	@RequestMapping(value="/")
	public String home(){
		return "hi";
	}

}
