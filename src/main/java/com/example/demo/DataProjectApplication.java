package com.example.demo;
import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;


@SpringBootApplication
public class DataProjectApplication {


	//private static DbService dbservice;
	public static void main(String[] args) {
		//dbservice = new DbService();
		SpringApplication.run(DataProjectApplication.class, args);
	}
	
	@Bean
	@Transactional
	CommandLineRunner runner(ApplicationContext ctx){
		return args -> {

		
            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

			System.out.println("CommandLineRunner running in the class...");
			/*Person person = new Person ("123456", "Bill", "Boket");
			Day day1= new Day(new Date());
			day1.Set_birthday(true);

			
			Day day2= new Day(new Date());
			day2.Set_holiday(true);
			day2.setPerson(person);
		
			person.print();
			dbservice.save(person);
			dbservice.save(day1);;
*/
			System.out.println("CommandLineRunner after Day...");
			
		};
	}
}
