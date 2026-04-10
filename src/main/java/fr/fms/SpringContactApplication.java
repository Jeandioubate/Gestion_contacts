package fr.fms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import fr.fms.entities.Contact;
import fr.fms.entities.Category;
import fr.fms.dao.ContactRepository;
import fr.fms.dao.CategoryRepository;

import java.util.List;

@SpringBootApplication
public class SpringContactApplication implements CommandLineRunner{
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringContactApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		// 🔹 1. Création des catégories
	    Category personnel = categoryRepository.findByName("Personnel");
	    if (personnel == null) {
	        personnel = categoryRepository.save(new Category("Personnel"));
	    }

	    Category professionnel = categoryRepository.findByName("Professionnel");
	    if (professionnel == null) {
	        professionnel = categoryRepository.save(new Category("Professionnel"));
	    }
	    
	    // 🔹 2. Création des contacts
	    List<Contact> contacts = List.of(
	        new Contact("DOMINIQUE", "Mathias", "mdom@gmail.com", "05.35.45.50.55", "Toulouse"),
	        new Contact("BOULANGER", "Robert", "rbou@gmail.com", "05.15.20.25.30", "Toulouse"),
	        new Contact("DIOUBATE", "Jean", "djean@gmail.com", "05.20.25.30.35", "Toulouse")
	    );
	    
	 // 🔹 3. Association catégories
	    contacts.get(0).setCategory(personnel);
	    contacts.get(1).setCategory(professionnel);
	    contacts.get(2).setCategory(personnel);
	    
	 // 🔹 4. Insertion sans doublon (plus simple)
	    for (Contact c : contacts) {
	        if (contactRepository.findByEmail(c.getEmail()).isEmpty()) {
	            contactRepository.save(c);
	        }
	    }
		
	}
	
}
		 



