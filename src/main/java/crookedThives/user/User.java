package crookedThives.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import crookedThives.config.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = 2580751077416502034L;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String token, String email, String name, String reg_ymd) {
		super();
		this.token = token;
		this.email = email;
		this.name = name;
		this.reg_ymd = reg_ymd;
	}
	
	

	public User(String token, String email, String name, String reg_ymd, String photo) {
		super();
		this.token = token;
		this.email = email;
		this.name = name;
		this.reg_ymd = reg_ymd;
		this.photo = photo;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="userid")
	private Long userid;
	
	@Column(name="token", nullable= false)
	private String token;
	
	@Column(name="email", nullable= false)
	private String email;
	
	@Column(name="name", nullable = false)
	private String name;
	
	@Column(name="reg_ymd")
	private String reg_ymd;
	
	@Column(name="photo")
	private String photo;
	
	@Column(name="gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name="weight")
	private Double weight;
	
	@Column(name="height")
	private Double height;

}
