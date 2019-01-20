package crookedThives.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveUser {
	
	private String token;
	private String email;
	private String name;
	private String photo;
	
	public SaveUser(String token, String email, String name, String photo) {
		super();
		this.token = token;
		this.email = email;
		this.name = name;
		this.photo = photo;
	}
	
}
