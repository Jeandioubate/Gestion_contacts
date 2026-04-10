package fr.fms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import fr.fms.entities.Contact;
//import fr.fms.entities.Category;
import fr.fms.dao.ContactRepository;
//import fr.fms.dao.CategoryRepository;

//import java.util.List;

@SpringBootApplication
public class SpringContactApplication implements CommandLineRunner{
	@Autowired
	private ContactRepository contactRepository;
	
	//@Autowired
	//private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringContactApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		 System.out.println("=== Tous les contacts ===");
		 for (Contact a : contactRepository.findAll()) {
		     System.out.println(a);
		    }		
	}
	
}
		 



