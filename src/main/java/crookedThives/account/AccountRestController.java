package crookedThives.account;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {
	
	@Autowired
	private AccountRepository accountRepository;
	
// http://localhost:8080/swagger-ui.html#/
/** 조회   **/	
	/** 사용자 List **/
	@GetMapping(value="/user")
	public ResponseEntity<Map<String, Object>> getUserList(){
		Map<String, Object> results = new HashMap<String, Object>();
		List<Account> userList = accountRepository.findAll();		
		if(!userList.isEmpty()) {
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			results.put("data", userList);
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		} else {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(results,HttpStatus.OK);
		}
	}
	
	/** 특정 사용자 id로 조회 **/
	@GetMapping(value="/user/{id}")
	public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id){
		Map<String, Object> results = new HashMap<String, Object>();	
		Optional<Account> user = accountRepository.findById(id);
		System.out.println(user);
		if(user.isPresent()){
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			results.put("data", user);
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		} else {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(results,HttpStatus.OK);
		}
	}
	
	/** 특정 사용자 userid로 조회 **/
	@GetMapping(value="/user/{userid}/userid")
	public ResponseEntity<Map<String, Object>> getUserByUserId(@PathVariable String userid) {
		Map<String, Object> results = new HashMap<String, Object>();
		Account user = accountRepository.findByUserId(userid);
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
	
	/** 특정 사용자 username로 조회 **/
	@GetMapping(value="/user/{username}/username")
	public ResponseEntity<Map<String, Object>> getUserByUserName(@PathVariable String username) {
		Map<String, Object> results = new HashMap<String, Object>();
		Account user = accountRepository.findByUsername(username);
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
	/** 특정 사용자 id로 삭제 **/
	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<Map<String,Object>> deleteAccountByid(@PathVariable Long id){
		Map<String, Object> results = new HashMap<String, Object>();		
		try {
			Account account = accountRepository.findById(id).get();
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		} catch(Exception e) {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(results, HttpStatus.OK);
		}
	}
	
	/** 특정 사용자 userid로 삭제 **/
	@DeleteMapping(value = "/user/{userid}/userid")
	public ResponseEntity<Map<String, Object>> deleteAccountByUserid(@PathVariable String userid){
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			Account account = accountRepository.findByUserId(userid);
			accountRepository.delete(account);
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		} catch(Exception e) {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String,Object>>(results, HttpStatus.OK);
		}
	}

/** 등록  **/
	/** user 계정 등록 **/
	@PostMapping(value="/user")
	public ResponseEntity<Map<String, Object>> createUser(@ModelAttribute("Account") Account account) {
//		Boolean duplicateAccount = accountRepository.findByEmail(account.getEmail());
//		if(duplicateAccount){
//			//동일한 이메일 존재시!!
//			System.out.println("!!!!!!!!!!!!존재한다!!!!!!!!");
//		}
		Map<String, Object> results = new HashMap<String, Object>();
		try {
			accountRepository.save(account);
			results.put("result", Boolean.TRUE);
			results.put("message", "ok");
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.CREATED);
		} catch(Exception e) {
			results.put("result", Boolean.FALSE);
			results.put("message", "fail");
			return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
		}
	}

/** 수정  **/
	/** 특정 사용자 id로부터 사진, 키, 몸무게 수정 **/
	@PutMapping(value="user/{id}")
	public ResponseEntity<Map<String, Object>> updateUserByid(@PathVariable Long id, @RequestParam(required=false) String photo, 
			@RequestParam(required=false) Double weight, @RequestParam(required=false) Double height) {
		Map<String, Object> results = new HashMap<String, Object>();
		try{
			Account account = accountRepository.findById(id).get();
			
			if(photo!=null){
				account.setPhoto(photo);
			} else {
				account.setPhoto(account.getPhoto());
			}
			if(weight!=null) {
				account.setWeight(weight);
			} else {
				account.setWeight(account.getWeight());
			}
			if(height!=null) {
				account.setHeight(height);
			} else {
				account.setHeight(account.getHeight());
			}
			
			accountRepository.save(account);
			
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
