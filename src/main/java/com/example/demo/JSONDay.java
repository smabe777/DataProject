package com.example.demo;

public class JSONDay {
	private String message;
	private boolean is_qap;
	private String theStartDate;
	private String theEndDate;
	private boolean is_standby;
	private boolean is_workday;
	private boolean is_holiday;
	private boolean is_birthday;
	private boolean is_work_at_home;
	private String personId;
	private String eventId;
	private String firstname;
	private String lastname;
	private String eventTitle;

	public void print() {
		System.out.printf("JSONDAY :: id=%s, title=%s, start=%s, end=%s, eventId=%s\n", personId, eventTitle,
				theStartDate, theEndDate, eventId);
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getTheStartDate() {
		return theStartDate;
	}

	public void setTheStartDate(String thedate) {
		this.theStartDate = thedate;
	}

	public String getTheEndDate() {
		return theEndDate;
	}

	public void setTheEndDate(String thedate) {
		this.theEndDate = thedate;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonid(String personId) {
		this.personId = personId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isIs_standby() {
		return is_standby;
	}

	public void setIs_standby(boolean is_standby) {
		this.is_standby = is_standby;
	}

	public boolean isIs_workday() {
		return is_workday;
	}

	public void setIs_workday(boolean is_workday) {
		this.is_workday = is_workday;
	}

	public boolean isIs_holiday() {
		return is_holiday;
	}

	public void setIs_holiday(boolean is_holiday) {
		this.is_holiday = is_holiday;
	}

	public boolean isIs_birthday() {
		return is_birthday;
	}

	public void setIs_birthday(boolean is_birthday) {
		this.is_birthday = is_birthday;
	}

	public boolean isIs_work_at_home() {
		return is_work_at_home;
	}

	public void setIs_work_at_home(boolean is_work_at_home) {
		this.is_work_at_home = is_work_at_home;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isIs_qap() {
		return is_qap;
	}

	public void setIs_qap(boolean is_qap) {
		this.is_qap = is_qap;
	}
	/*
	 * public String getSecret() { return secret; } public void setSecret(String
	 * secret) { this.secret = secret; } private String secret;
	 */
}
