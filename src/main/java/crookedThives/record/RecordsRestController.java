package crookedThives.record;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import crookedThives.dog.Dogs;
import crookedThives.dog.DogsRepository;
import crookedThives.user.User;
import crookedThives.user.UserRepository;

@RestController
public class RecordsRestController {
	
	@Autowired
	private RecordsRepository recordsRepository;
	
	@Autowired
	private DogsRepository dogsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value="/records")
	public ResponseEntity<Map<String, Object>> getRecordsList() {
		Map<String, Object> results = new HashMap<String, Object>();
		List<Records> recordsList = recordsRepository.findAll();
		if(!recordsList.isEmpty()){
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			results.put("data", recordsList);
		} else if(recordsList.isEmpty()){
			results.put("result", Boolean.FALSE);
			results.put("message", "empty");
		} else {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
		}
		return new ResponseEntity<Map<String,Object>> (results, HttpStatus.OK);
	}
	
	@GetMapping(value="/records/tokenAndDogName")
	public ResponseEntity<Map<String, Object>> getRecordsByDogid(@RequestParam("token") String token, @RequestParam("name") String dogName) {
		Map<String, Object> results = new HashMap<String, Object>();
		User user = userRepository.findByToken(token);
		Dogs dogs = dogsRepository.findByUseridAndName(user, dogName);
		List<Records> recordsList = recordsRepository.findByDogid(dogs);
		if(!recordsList.isEmpty()){
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			results.put("data", recordsList);
		} else if(recordsList.isEmpty()){
			results.put("result", Boolean.FALSE);
			results.put("message", "empty");
		} else {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
		}
		return new ResponseEntity<Map<String,Object>> (results, HttpStatus.OK);
	}
	
	@GetMapping(value="/records/token")
	public ResponseEntity<Map<String, Object>> getRecordsByUserid(@RequestParam("token") String token) {
		Map<String, Object> results = new HashMap<String, Object>();
		User user = userRepository.findByToken(token);
		List<Records> recordsList = recordsRepository.findByUserid(user);
		if(!recordsList.isEmpty()){
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			results.put("data", recordsList);
		} else if(recordsList.isEmpty()){
			results.put("result", Boolean.FALSE);
			results.put("message", "empty");
		} else {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
		}
		return new ResponseEntity<Map<String,Object>> (results, HttpStatus.OK);
	}
	
	@PostMapping(value="/records/tokenAndDogName")
	public ResponseEntity<Map<String, Object>> createRecords(@RequestParam("token") String token, @RequestParam("name") String dogName, @ModelAttribute("records") RecordsPojo recordsPojo){
		Map<String, Object> results = new HashMap<>();
		try{
			User user = userRepository.findByToken(token);
			Dogs dog = dogsRepository.findByUseridAndName(user, dogName);
//			List<Records> recordsList = recordsRepository.findByDogid(dogs);			
//			Dogs dog = dogsRepository.findById(dogid).get();
//			User user = dog.getUserid();
			String now_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
			String now_time_m = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());
			Records saveRecords = new Records(dog, user, recordsPojo.getTotal_meter(), recordsPojo.getTotal_time(), recordsPojo.getCalorie(), recordsPojo.getAchieve_meter(),
					recordsPojo.getAchieve_time(), recordsPojo.getAchieve_rate(),now_time, now_time_m );
			recordsRepository.save(saveRecords);
			results.put("result", Boolean.TRUE);
			results.put("message", "create record");
			results.put("data", saveRecords);
			return new ResponseEntity<Map<String,Object>> (results, HttpStatus.OK);
		} catch(Exception e){
			results.put("result", Boolean.FALSE);
			results.put("message", "error " + e.getStackTrace());
			return new ResponseEntity<Map<String,Object>> (results, HttpStatus.OK);
		}
	}


}
