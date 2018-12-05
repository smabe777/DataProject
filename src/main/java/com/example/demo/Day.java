package com.example.demo;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import javax.persistence.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table (name = "Days")
//@NamedQuery(query = "select d from Day d", name = "query_find_all_days")
public class Day implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DAY_ID")
	private Long dayId;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="PERSON_ID", referencedColumnName="USER_ID")
	private Person person;
	

	@Column (name="date_yyyymmddd", unique=true)
	private String date;
	
	public Long getDayId() {
		return dayId;
	}

	public void setDayId(Long day_id) {
		this.dayId = day_id;
	}
	
	 public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		person.addDay(this);
	}

	public Day() {		System.out.println("Creating day =" + date);}


	@Column (name="is_standby")
	private boolean is_standby = false;
	
	@Column (name="is_workday")
	private boolean is_workday = false;
	
	@Column (name="is_holiday")
	private boolean is_holiday = false;
	
	@Column (name="is_birthday")
	private boolean is_birthday = false;
	
	@Column (name="is_work_at_home")
	private boolean is_work_at_home = false;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		System.out.println("Setting date =" + date);
		this.date = date;
	}

	public boolean Is_standby() {
		return is_standby;
	}

	public void Set_standby(boolean is_standby) {
		this.is_standby = is_standby;
	}

	public boolean Is_workday() {
		return is_workday;
	}

	public void Set_workday(boolean is_workday) {
		this.is_workday = is_workday;
	}

	public boolean Is_holiday() {
		return is_holiday;
	}

	public void Set_holiday(boolean is_holiday) {
		this.is_holiday = is_holiday;
	}

	public boolean Is_birthday() {
		return is_birthday;
	}

	public void Set_birthday(boolean is_birthday) {
		this.is_birthday = is_birthday;
	}

	public boolean Is_work_at_home() {
		return is_work_at_home;
	}

	public void Set_work_at_home(boolean is_work_at_home) {
		this.is_work_at_home = is_work_at_home;
	}

	public Day (Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		this.date = formatter.format(date);
	}

}
