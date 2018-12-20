package com.example.demo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

//Spring Annotation to indicate that this component handles storing data to a data store.
@Repository
// Spring annotation used to simplify transaction management
@Transactional
public class DbService {
	// A persistence context handles a set of entities which hold data to be
	// persisted in some persistence store (e.g. a database). In particular, the
	// context is aware of the different states an entity can have (e.g. managed,
	// detached) in relation to both the context and the underlying persistence
	// store.
	@PersistenceContext
	// Interface used to interact with the persistence context.
	private EntityManager entityManager = JPAConfig.getEntityManager();

	public Person save(Person person) {
		try {
			if (person.getUserId() == null) {
				entityManager.getTransaction().begin();
				entityManager.persist(person);
				entityManager.getTransaction().commit();
				return person;
			} else {
				entityManager.getTransaction().begin();
				Person p = entityManager.merge(person);
				entityManager.getTransaction().commit();
				return p;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			entityManager.getTransaction().rollback();
			return person;
		}
	}

	public Day save(Day day) {
		try {
			if (day.getDayId() == null) {
				entityManager.getTransaction().begin();
				entityManager.persist(day);
				entityManager.getTransaction().commit();
				return day;
			} else {
				entityManager.getTransaction().begin();
				Day d = entityManager.merge(day);
				entityManager.getTransaction().commit();
				return d;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			entityManager.getTransaction().rollback();
			return day;
		}
	}

	public List<Day> findDaysByPerson(Person person) {

		TypedQuery<Day> query = entityManager.createQuery("select d from Day d where d.person = ?1", Day.class);
		query.setParameter(1, person);

		return query.getResultList();
	}

	public List<Day> findDayByPersonAndByDate(Person person, String date) {

		TypedQuery<Day> query = entityManager.createQuery("select d from Day d where d.person = ?1 and d.date = ?2",
				Day.class);
		query.setParameter(1, person);
		query.setParameter(2, date);
		List<Day> ret = query.getResultList();
		assert (ret.size() < 2);
		return ret;
	}

	public Person findPersonById(String id) {
		return entityManager.find(Person.class, id);
	}

	public List<Person> findAll() {
		// Creates an instance of TypedQuery for executing a Java Persistence query
		// language named query. The second parameter indicates the type of result
		TypedQuery<Person> query = entityManager.createQuery("select p from Person p", Person.class);
		return query.getResultList();
	}

	public List<Person> findByLastname(String lastname, int page, int pageSize) {

		TypedQuery<Person> query = entityManager.createQuery("select p from Person p where p.lastname = ?1",
				Person.class);

		query.setParameter(1, lastname);
		query.setFirstResult(page * pageSize);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

}
