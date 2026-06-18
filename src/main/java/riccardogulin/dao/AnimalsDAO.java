package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import riccardogulin.entities.Animal;
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

		if (found == null) throw new NotFoundException(animalId);

		return found;
	}
}
