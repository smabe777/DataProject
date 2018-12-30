package com.example.demo;

public class Event {
	private String start;
	private String end;
	private String id;
	private String title;

	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getStart() {
		return start;
	}

	public void setStart(String thedate) {
		this.start = thedate;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String thedate) {
		this.end = thedate;
	}

	public String getId() {
		return id;
	}

	public void setId(String eventId) {
		this.id = eventId;
	}
	
}
