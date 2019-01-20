package crookedThives.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import crookedThives.config.Gender;

@RestController
public class UserRestController {
	// http://localhost:8080/swagger-ui.html#/

	@Autowired
	private UserRepository userRepository;

	/*
	 * Google oAuth에서 token, email, name 가져오면 token 값으로 기존 사용자가 있는지 없는지 확인함 있다면
	 * -> 사용자 정보 return 없다면 -> 등록 -> 클라이언트쪽에서 사용자 정보 등록 팝업 유도
	 */
	@GetMapping(value = "/login")
	public ResponseEntity<Map<String, Object>> getUserByToken(@ModelAttribute SaveUser user) {
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			User usertoken = userRepository.findByToken(user.getToken());
			if (usertoken == null || usertoken.getToken()=="" || usertoken.getToken()== null) {
				// userByToken 존재하지 않으면 => save 저장
				String now_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
				User saveUser = new User(user.getToken(), user.getEmail(), user.getName(), now_time, user.getPhoto());
				userRepository.save(saveUser);
				results.put("result", Boolean.TRUE);
				results.put("message", "create user");
				results.put("data", saveUser);
				return new ResponseEntity<Map<String, Object>>(results, HttpStatus.CREATED);
			} else {
				// userByToken 존재한다면 => 원래 있는 사용자라고 return
				results.put("result", Boolean.TRUE);
				// 이메일이나 이름이 변경된 경우,
				if (!usertoken.getEmail().equals(user.getEmail()) && !usertoken.getName().equals(user.getName())) {
					usertoken.setEmail(user.getEmail());
					usertoken.setName(user.getName());
					usertoken.setPhoto(user.getPhoto());
					userRepository.save(usertoken);
					results.put("message", "update user");
				} else {
					results.put("message", "exist user");
				}
				results.put("data", usertoken);
				return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
			}
		} catch (Exception e) {
			results.put("result", Boolean.FALSE);
			results.put("message", "error " + e.getStackTrace());
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		}

	}
	
	/** 사용자 전체 조회 **/
	@GetMapping("/user")
	public ResponseEntity<Map<String, Object>> getUser() {
		Map<String, Object> results = new HashMap<String, Object>();
		List<User> userList = userRepository.findAll();		
		if(!userList.isEmpty()) {
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			results.put("data", userList);
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		} else if(userList.isEmpty()) {
			results.put("result", Boolean.FALSE);
			results.put("message", "empty");
			return new ResponseEntity<Map<String, Object>>(results,HttpStatus.OK);
		} else {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(results,HttpStatus.OK);
		}
	}

	/** 특정 사용자 token으로 조회 **/
	@GetMapping(value="/user/token")
	public ResponseEntity<Map<String, Object>> getUserByUserId(@RequestParam("token") String token) {
		Map<String, Object> results = new HashMap<String, Object>();
		User user = userRepository.findByToken(token);
		if(user!=null){
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			results.put("data", user);
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		} else {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(results, HttpStatus.OK);
		}
	}
	
	/** 삭제 **/
	/** 특정 사용자 token으로 삭제 **/
	@DeleteMapping(value = "/user/token")
	public ResponseEntity<Map<String,Object>> deleteUserByToken(@RequestParam("token") String token){
		Map<String, Object> results = new HashMap<String, Object>();		
		try {
			User user = userRepository.findByToken(token);
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		} catch(Exception e) {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(results, HttpStatus.OK);
		}
	}
	
	/** 수정  **/
	/** 특정 사용자 token으로부터 사진, 키, 몸무게 수정 **/
	@PutMapping(value="/user/token")
	public ResponseEntity<Map<String, Object>> updateUserByToken(@RequestParam("token") String token, @RequestParam(required=false) String photo, 
			@RequestParam(required=false) Gender gender, @RequestParam(required=false) Double weight, @RequestParam(required=false) Double height) {
		Map<String, Object> results = new HashMap<String, Object>();
		try{
			User user = userRepository.findByToken(token);
			
			if(photo!=null){
				user.setPhoto(photo);
			} else {
				user.setPhoto(user.getPhoto());
			}
			if(gender!= null) {
				user.setGender(gender);
			} else {
				user.setGender(user.getGender());
			}
			if(weight!=null) {
				user.setWeight(weight);
			} else {
				user.setWeight(user.getWeight());
			}
			if(height!=null) {
				user.setHeight(height);
			} else {
				user.setHeight(user.getHeight());
			}
			
			userRepository.save(user);
			
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.CREATED);
		} catch(Exception e){
			//e.printStackTrace();
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		}
	}
	
}
