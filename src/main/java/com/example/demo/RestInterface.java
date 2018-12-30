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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class RestInterface {

	public static ResponseEntity<String> createResponseEntity(String message, HttpStatus statusCode) {
		return new ResponseEntity<>(message, statusCode);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<JSONPerson> getUserById(@PathVariable("id") String id) {
		DbService dbservice = new DbService();
		Person p = dbservice.findPersonById(id);
		JSONPerson jp = null;

		if (p != null) {
			jp = new JSONPerson();
			jp.setPersonId(p.getPersonId());
			jp.setLastname(p.getLastname());
			jp.setFirstname(p.getFirstname());
			return new ResponseEntity<JSONPerson>(jp, HttpStatus.OK);
		}
		return new ResponseEntity<JSONPerson>(jp, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/days/{id}")
	public ResponseEntity<List<Event>> getDaysById(@PathVariable("id") String id,
			@RequestParam(value = "start", required = false) String startDate,
			@RequestParam(value = "end", required = false) String endDate,
			@RequestParam(value = "_", required = false) String sessionId) {
		DbService dbservice = new DbService();
		Person p = dbservice.findPersonById(id);
		List<Day> ld = p.getCalendar();
		List<Event> ljd = null;
		System.out.printf("/days/id : id = %s, start = %s, end = %s\n", id, startDate, endDate);
		System.out.printf("Calendar contains %d element(s)\n", ld.size());
		if (ld != null) {

			ljd = new ArrayList<Event>();

			for (Day d : ld) {
				// JSONDay jd = new JSONDay();
				Event jd = new Event();
				jd.setStart(d.getStartDate());
				jd.setEnd(d.getEndDate());
				jd.setId(d.getDayId().toString());
				jd.setTitle(d.getEventTitle());
				/*
				 * jd.setTheStartDate(d.getStartDate()); jd.setTheEndDate(d.getEndDate());
				 * jd.setEventTitle(d.getEventTitle()); jd.setEventId(d.getEventId()); if
				 * (d.Is_birthday()) jd.setIs_birthday(true); if (d.Is_holiday())
				 * jd.setIs_holiday(true); if (d.Is_standby()) jd.setIs_standby(true); if
				 * (d.Is_work_at_home()) jd.setIs_work_at_home(true); if (d.Is_workday())
				 * jd.setIs_workday(true);
				 */
				ljd.add(jd);
			}

		}
		return new ResponseEntity<List<Event>>(ljd, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<JSONPerson>> getUsers() {
		DbService dbservice = new DbService();
		List<Person> lp = dbservice.findAll();
		System.out.println(lp);
		List<JSONPerson> ljp = null;
		if (lp != null) {

			ljp = new ArrayList<JSONPerson>();

			for (Person p : lp) {
				JSONPerson jp = new JSONPerson();
				jp.setPersonId(p.getPersonId());
				jp.setLastname(p.getLastname());
				jp.setFirstname(p.getFirstname());
				ljp.add(jp);
			}

		}
		return new ResponseEntity<List<JSONPerson>>(ljp, HttpStatus.OK);
	}

	@Transactional
	@PostMapping(value = "/NewUser", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> addPerson(@RequestBody JSONPerson person) {
		DbService dbservice = new DbService();
		if (person.getPersonId() == "") {
			System.out.println("---------- ID is empty ------ ");
			return createResponseEntity("Error creating person : ID is empty", HttpStatus.BAD_REQUEST);

		}
		Person p = dbservice.findPersonById(person.getPersonId());
		if (p != null) {
			System.out.println("---------- Person already in database --- : ");
			p.print();
			return createResponseEntity("Person already in database", HttpStatus.CREATED);

		} else {
			System.out.println("---------- New Person ------- : ");
			p = new Person();
			p.setFirstname(person.getFirstname());
			p.setLastname(person.getLastname());
			p.setPersonId(person.getPersonId());

			p.print();

			if (dbservice.save(p) != null) {
				System.out.println("Created person");
				return createResponseEntity("Successful creation of a person", HttpStatus.CREATED);
			} else {
				System.out.println("Could not create person ");
				return createResponseEntity("Error creating person", HttpStatus.BAD_REQUEST);
			}
		}

	}

	@Transactional
	@DeleteMapping(value = "/deleteDay", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteDay(@RequestBody JSONDay day) {
		DbService dbservice = new DbService();
		Day d;
		if (day.getEventId() != null) {
			d = dbservice.findDayById(Long.parseLong(day.getEventId()));
			System.out.println("---------- Day/Event found in database --- ");
			d.print();
			dbservice.removeDay(d);
			return createResponseEntity("Successful deletion of a day", HttpStatus.OK);
		} else {
			System.out.println("---------- Day/Event not found  ---  ");
			return createResponseEntity("Error removing day", HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	@PostMapping(value = "/Calendar", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> addDay(@RequestBody JSONDay day) {

		DbService dbservice = new DbService();
		day.print();

		Day d;
		Person p;

		if (day.getEventId() != null) {
			d = dbservice.findDayById(Long.parseLong(day.getEventId()));
			System.out.println("---------- Day/Event already in database --- : ");
			d.print();
			p = d.getPerson();
		}

		else {
			// Without day_id, I need the person to look for relevant dates
			p = dbservice.findPersonById(day.getPersonId());

			if (p != null) {
				System.out.println("---------- Person already in database --- : ");
				p.print();
			} else {
				System.out.println("---------- New Person ------- : ");
				p = new Person();

				p.setPersonId(day.getPersonId());
				p.setFirstname(day.getFirstname());
				p.setLastname(day.getLastname());

				p.print();

				if (dbservice.save(p) != null) {
					System.out.println("Created person");
					// createResponseEntity("Successful creation of a person", HttpStatus.CREATED);
				} else {
					System.out.println("Could not create person ");
					return createResponseEntity("Error creating person", HttpStatus.BAD_REQUEST);
				}
			}

			List<Day> daylist;

			daylist = dbservice.findDayByPersonAndByDates(p, day.getTheStartDate(), day.getTheEndDate());
			// This retrieves the list of events for that person with that start and end
			// datetimes.
			if (daylist != null && (daylist.size() > 0)) {// RE-WORK : Do we expect multiple day_id's??
				assert (daylist.size() == 1);
				for (Day dt : daylist) {
					System.out.println("Existing date = " + dt.getStartDate() + ", " + dt.getEndDate() + ", id =  "
							+ dt.getDayId());
				}
				d = daylist.get(0);
			} else { // The person has no events with that start and end
				d = new Day();
				d.setPerson(p);
			}
		}
		/// Update
		////////////////
		d.setStartDate(day.getTheStartDate());
		d.setEndDate(day.getTheEndDate());
		d.setEventTitle(day.getEventTitle());
		d.Set_birthday(day.isIs_birthday());
		d.Set_holiday(day.isIs_holiday());
		d.Set_standby(day.isIs_standby());
		d.Set_work_at_home(day.isIs_work_at_home());
		d.Set_workday(day.isIs_workday());

		if (dbservice.save(d) != null) {
			System.out.println("Created day");
			return createResponseEntity("Successful creation of a day", HttpStatus.CREATED);
		} else {
			System.out.println("Could not create day ");
			return createResponseEntity("Error creating day", HttpStatus.BAD_REQUEST);
		}

	}
}
