package crookedThives.record;

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

import org.hibernate.annotations.ManyToAny;

import crookedThives.dog.Dogs;
import crookedThives.user.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="records")
public class Records implements Serializable {

	private static final long serialVersionUID = 8525849287529435462L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="recordid")
	private Long recordid;
	
	@ManyToOne(targetEntity=Dogs.class, fetch = FetchType.EAGER)
	@JoinColumn(name="dogid")
	private Dogs dogid;
	
	@ManyToOne(targetEntity=User.class, fetch = FetchType.EAGER)
	@JoinColumn(name="userid")
	private User userid;
	
	@Column(name="total_meter")
	private Double total_meter;
	
	@Column(name="total_time")
	private Double total_time;
	
	@Column(name="calorie")
	private Double calorie;
	
	@Column(name="achieve_meter")
	private Double achieve_meter;
	
	@Column(name="achieve_time")
	private Double achieve_time;
	
	@Column(name="achieve_rate")
	private Double achieve_rate;
	
	@Column(name="rpt_ymd")
	private String rpt_ymd;
	
	@Column(name="rpt_ym")
	private String rpt_ym;

	
	public Records(Dogs dogid, User userid, Double total_meter, Double total_time, Double calorie, Double achieve_meter,
			Double achieve_time, Double achieve_rate, String rpt_ymd, String rpt_ym) {
		super();
		this.dogid = dogid;
		this.userid = userid;
		this.total_meter = total_meter;
		this.total_time = total_time;
		this.calorie = calorie;
		this.achieve_meter = achieve_meter;
		this.achieve_time = achieve_time;
		this.achieve_rate = achieve_rate;
		this.rpt_ymd = rpt_ymd;
		this.rpt_ym = rpt_ym;
	}


	public Records() {
		super();
	}
	
	
	

}
