package com.example.demo;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RestInterface {

    public static ResponseEntity<String> createResponseEntity(String message, HttpStatus statusCode){
        return new ResponseEntity<>(message, statusCode);
    }
    
    @GetMapping("/user/{id}")    
    public ResponseEntity<JSONPerson> getUserById(@PathVariable("id") String id){
    	DbService dbservice = new DbService();
    	Person p = dbservice.findPersonById(id);
    	JSONPerson jp = null;

    	if (p != null) {
    		jp = new JSONPerson();
    		jp.setUserId(p.getUserId());
        	jp.setLastname(p.getLastname());
        	jp.setFirstname(p.getFirstname());
    		return new ResponseEntity<JSONPerson>(jp, HttpStatus.OK);
    	}
    	return new ResponseEntity<JSONPerson>(jp, HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("/days/{id}")    
    public ResponseEntity<List<JSONDay> > getDaysById(@PathVariable("id") String id){
    	DbService dbservice = new DbService();
    	Person p = dbservice.findPersonById(id);
    	List<Day> ld = p.getCalendar();
    	List<JSONDay> ljd = null;
    	if (ld != null) {
    		
    		ljd= new ArrayList<JSONDay>();
    
	    	for (Day d : ld) {
	    		JSONDay jd = new JSONDay();
	    		jd.setThedate(d.getDate());
	    		if(d.Is_birthday()) jd.setIs_birthday(true);
	    		if(d.Is_holiday()) jd.setIs_holiday(true);
	    		if(d.Is_standby()) jd.setIs_standby(true);
	    		if(d.Is_work_at_home()) jd.setIs_work_at_home(true);
	    		if(d.Is_workday()) jd.setIs_workday(true);
	    		ljd.add(jd);
	    	}

    	}
    	return new ResponseEntity<List<JSONDay> >(ljd, HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/users")    
    public ResponseEntity<List<JSONPerson> > getUsers(){
    	DbService dbservice = new DbService();
    	List<Person> lp = dbservice.findAll();
    	System.out.println(lp);
    	List<JSONPerson> ljp = null;
    	if (lp != null) {
    		
    		ljp= new ArrayList<JSONPerson>();
    
	    	for (Person p : lp) {
	    		JSONPerson jp = new JSONPerson();
	    		jp.setUserId(p.getUserId());
	        	jp.setLastname(p.getLastname());
	        	jp.setFirstname(p.getFirstname());
	        	ljp.add(jp);	
	    	}

    	}
    	return new ResponseEntity<List<JSONPerson> >(ljp, HttpStatus.BAD_REQUEST);
    }

    @Transactional
    @PostMapping(value = "/NewUser", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addPerson(@RequestBody JSONPerson person) {
    	DbService dbservice = new DbService();
    	if(person.getUserId() == ""){
    		System.out.println("---------- ID is empty ------ ");
    		return createResponseEntity("Error creating person : ID is empty", HttpStatus.BAD_REQUEST);

    	}
    	Person p = dbservice.findPersonById(person.getUserId());
    	if (p != null) {
        	System.out.println("---------- Person already in database --- : ");
    		p.print();
    		 return createResponseEntity("Person already in database", HttpStatus.CREATED);

    	}
    	else {
        	System.out.println("---------- New Person ------- : ");
        	p = new Person();
    		p.setFirstname(person.getFirstname());
    		p.setLastname(person.getLastname());
    		p.setUserId(person.getUserId());
    		
    		p.print();
    		
			if (dbservice.save(p) != null) {
	    		System.out.println("Created person");
	            return createResponseEntity("Successful creation of a person", HttpStatus.CREATED);
	        }
			else {
	    		System.out.println("Could not create person ");
				return createResponseEntity("Error creating person", HttpStatus.BAD_REQUEST);
			}
    	}
    	
    }
    
    @Transactional
    @PostMapping(value = "/Calendar", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> addDay(@RequestBody JSONDay day) {

    	DbService dbservice = new DbService();
		
    	Person p = dbservice.findPersonById(day.getPersonid());
    	
    	if (p != null) {
        	System.out.println("---------- Person already in database --- : ");
    		p.print();
    	}
    	else {
        	System.out.println("---------- New Person ------- : ");
        	p = new Person();
        	
        	p.setUserId(day.getPersonid());
        	p.setFirstname(day.getFirstname());
        	p.setLastname(day.getLastname());
        	
        	p.print();

    	
	    	if (dbservice.save(p) != null) {
	    		System.out.println("Created person");
	            //createResponseEntity("Successful creation of a person", HttpStatus.CREATED);
	        }
	    	else {
	    		System.out.println("Could not create person ");
	    		return createResponseEntity("Error creating person", HttpStatus.BAD_REQUEST);
	    	}
    	}

        
    	String formatted_date = day.getThedate().replaceAll("-", "");
    	
    	List<Day> daylist = dbservice.findDayByPersonAndByDate(p, formatted_date);
    	
    	Day d ;

    	if (daylist != null && daylist.size() == 1) {
    		for (Day dt : daylist) {
    			System.out.println("Existing date = " + dt.getDate() +", id =  " + dt.getDayId());
    		}
    		d = daylist.get(0);
    	}
    	else {
        	d = new Day();	
        	d.setDate(day.getThedate().replaceAll("-", ""));
        	d.setPerson(p);
        	System.out.println("After setPerson");
    	}
    	d.Set_birthday(day.isIs_birthday());
    	d.Set_holiday(day.isIs_holiday());
    	d.Set_standby(day.isIs_standby());
    	d.Set_work_at_home(day.isIs_work_at_home());
    	d.Set_workday(day.isIs_workday());
   	
		if (dbservice.save(d) != null) {
    		System.out.println("Created day");
            return createResponseEntity("Successful creation of a day", HttpStatus.CREATED);
        }
		else {
    		System.out.println("Could not create day ");
			return createResponseEntity("Error creating day", HttpStatus.BAD_REQUEST);
		}
    	
    }
}

