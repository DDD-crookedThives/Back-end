package crookedThives.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account implements Serializable {

	private static final long serialVersionUID = -7550768643647399132L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="userid")
	private String userId;
	
	@Column(name="username", nullable= false)
	private String username;
	
	@Column(name="password", nullable= false)
	private String password;
	
	@Column(name="password2")
	private String password2;
	
	@Column(name="email", nullable= false)
	private String email;
	
	@Column(name="photo")
	private String photo;
	
	@Column(name="height")
	private Double height;
	
	@Column(name="weight")
	private Double weight;
	
	@Column(name="gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;

}

