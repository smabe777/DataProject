package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
	 /* 
      Spring Data JPA will automatically parse this method name 
      and create a query from it
   */
   List<Day> findDaysByPerson(Long person);
}
