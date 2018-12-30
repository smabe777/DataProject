package com.example.demo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "People")
public class Person {

	protected Person() {
	}

	public Person(String id, String first, String last) {
		personId = id;
		firstname = first;
		lastname = last;
	}

	@Id
	@Column(name = "PERSON_ID")
	public String personId;

	private String firstname;

	private String lastname;

	// The 'mappedBy = "person"' attribute specifies that
	// the 'private Person person;' field in Day owns the
	// relationship (i.e. contains the foreign key for the query to
	// find all days for a person.)
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	// @OneToMany(mappedBy = "person")
	List<Day> calendar = new ArrayList<Day>();

	public void addDay(Day day) {
		calendar.add(day);
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String userId) {
		this.personId = userId;
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

	public List<Day> getCalendar() {
		return calendar;
	}

	public void setCalendar(List<Day> calendar) {
		this.calendar = calendar;
	}

	public void print() {
		System.out.println("----> " + personId + ": " + firstname + " " + lastname);
		for (Day day : calendar) {
			System.out.printf("(%s, %s)\n", day.getStartDate(), day.getEndDate());
		}
		System.out.println("<----");
	}

}
