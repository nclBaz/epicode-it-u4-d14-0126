package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.AnimalsDAO;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;
import riccardogulin.exceptions.NotFoundException;

public class Application {
	private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("u4d14pu");

	public static void main(String[] args) {
		EntityManager em = entityManagerFactory.createEntityManager();

		AnimalsDAO ad = new AnimalsDAO(em);

		Dog rex = new Dog("Ringhio", 10, 4.2);
		Cat felix = new Cat("Tom", 2, 3);
//
//		ad.save(rex);
//		ad.save(felix);

		try {
//			Animal animal = ad.findById("12163654-9559-4dae-b62b-1883b6071f40");
//			System.out.println(animal);

			Dog rexFromDB = ad.findDogById("e22ead4f-8829-4912-a474-bf66264a127f");
			System.out.println(rexFromDB);

		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}

//		ad.getAllDogs().forEach(System.out::println);

//		ad.getAllAnimalsYoungerThan(3).forEach(System.out::println);

//		ad.getAnimalsNameStartsWith("r").forEach(System.out::println);

//		ad.findByNameAndUpdateName("Rex", "Rintintin");

//		ad.findByNameAndDelete("Ringhio");

		ad.findByOwnersName("Aldo").forEach(System.out::println);
	}
}
