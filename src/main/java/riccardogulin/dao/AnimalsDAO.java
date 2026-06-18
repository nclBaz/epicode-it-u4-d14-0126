package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardogulin.entities.Animal;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;
import riccardogulin.exceptions.NotFoundException;

import java.util.UUID;

public class AnimalsDAO {
	private final EntityManager em;

	public AnimalsDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Animal newAnimal) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		em.persist(newAnimal);

		transaction.commit();

		System.out.println("L'animale " + newAnimal + " è stato correttamente salvato!");
	}

	public Animal findById(String animalId) {
		Animal found = em.find(Animal.class, UUID.fromString(animalId));
		// SELECT * FROM animals WHERE id = :animalId <-- SINGLE TABLE
		// SELECT * FROM animals JOIN cats ON animals.id = cats.id WHERE id = :animalId <-- JOINED
		// SELECT * FROM animals UNION ALL ... WHERE id = :animalId <-- TABLE PER CLASS

		if (found == null) throw new NotFoundException(animalId);

		return found;
	}

	public Dog findDogById(String dogId) {
		Dog found = em.find(Dog.class, UUID.fromString(dogId));
		// SELECT * FROM animals WHERE id = :dogId AND tipo_animale = 'Cane' <-- SINGLE TABLE
		// SELECT * FROM animals JOIN dogs ON... WHERE id = :dogId  <-- JOINED
		// SELECT * FROM dogs where id = :dogId <-- TABLE PER CLASS (NO UNION ALL IN QUESTO CASO!)

		if (found == null) throw new NotFoundException(dogId);

		return found;
	}

	public Cat findCatById(String catId) {
		Cat found = em.find(Cat.class, UUID.fromString(catId));
		// SELECT * FROM animals WHERE id = :catId AND tipo_animale = 'Gatto' <-- SINGLE TABLE
		// SELECT * FROM animals JOIN cats ON... WHERE id = :catId  <-- JOINED
		// SELECT * FROM cats where id = :catId <-- TABLE PER CLASS (NO UNION ALL IN QUESTO CASO!)

		if (found == null) throw new NotFoundException(catId);

		return found;
	}
}
