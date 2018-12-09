package crookedThives.account;

import java.util.List;

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
	public ResponseEntity<List<Account>> getUserList(){
		List<Account> userList = accountRepository.findAll();
		if(userList.isEmpty()) {
			return new ResponseEntity<List<Account>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Account>>(userList,HttpStatus.OK);
	}
	
	/** 특정 사용자 id로 조회 **/
	@GetMapping(value="/user/{id}")
	public ResponseEntity<Account> getUserById(@PathVariable Long id){
		Account user = accountRepository.findById(id).get();
		if(user==null) {
			return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Account>(user, HttpStatus.OK);
	}
	
	/** 특정 사용자 userid로 조회 **/
	@GetMapping(value="/user/{userid}/userid")
	public ResponseEntity<Account> getUserByUserId(@PathVariable String userid) {
		Account user = accountRepository.findByUserId(userid);
		if(user == null) {
			return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Account>(user, HttpStatus.OK);
	}
	
	/** 특정 사용자 username로 조회 **/
	@GetMapping(value="/user/{username}/username")
	public ResponseEntity<Account> getUserByUserName(@PathVariable String username) {
		Account user = accountRepository.findByUsername(username);
		if(user == null) {
			return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);			
		}
		return new ResponseEntity<Account>(user, HttpStatus.OK);
	}

/** 삭제 **/
	/** 특정 사용자 id로 삭제 **/
	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<Void> deleteAccountByid(@PathVariable Long id){
		Account account = accountRepository.findById(id).get();
		if(account==null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} else {
			accountRepository.delete(account);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/** 특정 사용자 userid로 삭제 **/
	@DeleteMapping(value = "/user/{userid}/userid")
	public ResponseEntity<Void> deleteAccountByUserid(@PathVariable String userid){
		Account account = accountRepository.findByUserId(userid);
		if(account==null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} else {
			accountRepository.delete(account);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

/** 등록  **/
	/** user 계정 등록 **/
	@PostMapping(value="/user")
	public ResponseEntity<Account> createUser(@ModelAttribute("Account") Account account) {
		try {
			accountRepository.save(account);
			return new ResponseEntity<Account>(HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
		}
	}

/** 수정  **/
	/** 특정 사용자 id로부터 사진, 키, 몸무게 수정 **/
	@PutMapping(value="user/{id}")
	public ResponseEntity<Account> updateUserByid(@PathVariable Long id, @RequestParam(required=false) String photo, 
			@RequestParam(required=false) Double weight, @RequestParam(required=false) Double height) {
		
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
			return new ResponseEntity<Account>(HttpStatus.CREATED);
		} catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<Account>(HttpStatus.CONFLICT);
		}
	}
	

}
