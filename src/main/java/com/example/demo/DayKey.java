package com.example.demo;
 
import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Id;

@Embeddable
public class DayKey implements Serializable{
	private static final long serialVersionUID = 1L;
 
	//@Id
	@Column(name = "person_id")
	//private Person person;
	private String person_id;

	//@Id 
	@Column(name = "date_yyyymmdd")
	private String date;

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String personId) {
		this.person_id = personId;
	}
	/* public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}*/

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public DayKey(){};
	
	public DayKey(String personId, String date){
		this.person_id = personId;
		this.date = date;
	}
	
	/*public DayKey(Person person, String date){
		this.person = person;
		this.date = date;
	}*/
    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof DayKey)) {
            return false;
        }
        DayKey daykey = (DayKey) o;
       /*return Objects.equals(person, daykey.getPerson()) &&
               Objects.equals(date, daykey.getDate());*/
        return Objects.equals(person_id, daykey.getPerson_id()) &&
                Objects.equals(date, daykey.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(person_id, date);
    }
}
