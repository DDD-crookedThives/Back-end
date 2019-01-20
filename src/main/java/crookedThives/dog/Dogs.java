package crookedThives.dog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import crookedThives.account.Account;
import crookedThives.config.Gender;
import crookedThives.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="dogs")
public class Dogs implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dogid")
	private Long dogid;

	@ManyToOne(targetEntity=User.class, fetch = FetchType.EAGER)
	@JoinColumn(name="userid")
	private User userid;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "reg_ymd")
	private String reg_ymd;

	@Column(name = "photo")
	private String photo;

	@Column(name = "gender")
	private Gender gender;

	@Column(name = "birth")
	private String birth;

	public Dogs(Long dogid, User userid, String name, String reg_ymd, String photo, Gender gender, String birth) {
		super();
		this.dogid = dogid;
		this.userid = userid;
		this.name = name;
		this.reg_ymd = reg_ymd;
		this.photo = photo;
		this.gender = gender;
		this.birth = birth;
	}

	public Dogs() {
		super();
	}
	
	
}
