package crookedThives.dog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import crookedThives.user.User;
import crookedThives.user.UserRepository;

@RestController
public class DogsRestController {
	
	@Autowired
	private DogsRepository dogsRepository;
	
	@Autowired
	private DogsService dogsService;
	
	@Autowired
	private UserRepository userRepository;
	
	//userid의 dog를 검색
	@GetMapping(value="/dogs/userid")
	public ResponseEntity<Map<String,Object>> getDogs(@RequestParam("userid") User userid) {
		Map<String, Object> results = new HashMap<String, Object>();
		List<Dogs> dogsList = dogsRepository.findByUserid(userid);
		if(!dogsList.isEmpty()){
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			results.put("data", dogsList);
		} else if(dogsList.isEmpty()){
			results.put("result", Boolean.FALSE);
			results.put("message", "empty");
		} else {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
		}
		return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
	}
	
	@PostMapping(value="/dogs/userid") 
	public ResponseEntity<Map<String, Object>> createDogs(@RequestParam("userid") Long userid, @ModelAttribute("Dogs") DogsPojo dogPojo){
		Map<String, Object> results = new HashMap<String, Object>();
		try{
			Dogs dog = new Dogs();
			User user = userRepository.findById(userid).get();
			dog.setUserid(user);
			dog.setName(dogPojo.getName());
			dog.setPhoto(dogPojo.getPhoto());
			dog.setGender(dogPojo.getGender());
			dog.setBirth(dogPojo.getBirth());
	
			dogsRepository.save(dog);
			results.put("result", Boolean.TRUE);
			results.put("data", dog);
			results.put("message", "create");
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		} catch(Exception e) {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);			
		}
	}
	
	@GetMapping(value="/dogs")
	public ResponseEntity<Map<String, Object>> dogList(){
		Map<String, Object> results = new HashMap<>();
		List<Dogs> dogsList = dogsRepository.findAll();
		if(!dogsList.isEmpty()) {
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			results.put("data", dogsList);
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		} else if(dogsList.isEmpty()) {
			results.put("result", Boolean.FALSE);
			results.put("message", "empty");
			return new ResponseEntity<Map<String, Object>>(results,HttpStatus.OK);
		} else {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(results,HttpStatus.OK);
		}
	}

}
