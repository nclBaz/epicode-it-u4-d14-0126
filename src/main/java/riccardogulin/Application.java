package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.AnimalsDAO;
import riccardogulin.entities.Animal;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;
import riccardogulin.exceptions.NotFoundException;

public class Application {
	private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("u4d14pu");

	public static void main(String[] args) {
		EntityManager em = entityManagerFactory.createEntityManager();

		AnimalsDAO ad = new AnimalsDAO(em);

		Dog rex = new Dog("Rex", 10, 4.2);
		Cat felix = new Cat("Felix", 2, 3);

		ad.save(rex);
		ad.save(felix);

		try {
			Animal animal = ad.findById("57e006a7-8276-41db-bf5f-480a1985c551");
			System.out.println(animal);

		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}

	}
}
