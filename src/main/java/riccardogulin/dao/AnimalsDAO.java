package riccardogulin.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import riccardogulin.entities.Animal;
import riccardogulin.entities.Cat;
import riccardogulin.entities.Dog;
import riccardogulin.entities.Owner;
import riccardogulin.exceptions.NotFoundException;

import java.util.List;
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


	public List<Animal> getAllAnimals() {
		// SELECT * FROM animals <-- SINGLE TABLE
		// Come sopra però con 2 JOIN in più <-- JOINED
		// SELECT * FROM dogs poi SELECT * FROM cats e unisce tutto con UNION ALL <-- TABLE PER CLASS

		TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a", Animal.class);
		List<Animal> result = query.getResultList();
		return result;
	}

	public List<Dog> getAllDogs() {
		TypedQuery<Dog> query = em.createQuery("SELECT d FROM Dog d", Dog.class);
		// SELECT * FROM animals WHERE tipo_animale = 'Cane' <-- SINGLE TABLE
		// Come sopra però con 1 JOIN su dogs <-- JOINED
		// SELECT * FROM dogs <-- TABLE PER CLASS

		List<Dog> result = query.getResultList();
		return result;
	}

	public List<Animal> getAllAnimalsYoungerThan(int age) {
		TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE a.age < :param", Animal.class);
		query.setParameter("param", age);
		return query.getResultList();
	}

	public List<Animal> getAnimalsNameStartsWith(String partialName) {
		TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE LOWER(a.name) LIKE LOWER(:param)", Animal.class);
		query.setParameter("param", partialName + "%");
		return query.getResultList();
	}


	public void findByNameAndUpdateName(String currentName, String newName) {
		// Siccome quest'operazione non è una lettura ma una SCRITTURA, deve essere tutto collocato in una transazione
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Query query = em.createQuery("UPDATE Animal a SET a.name = :newName WHERE a.name = :currentName ");
		// UPDATE animals SET name = :newName WHERE name = :currentName
		query.setParameter("newName", newName);
		query.setParameter("currentName", currentName);

		query.executeUpdate(); // <-- Questa riga esegue la query nella transazione

		transaction.commit();
	}

	public void findByNameAndDelete(String name) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		Query query = em.createQuery("DELETE FROM Animal a WHERE a.name = :name ");
		// DELETE FROM animals WHERE name = :name
		query.setParameter("name", name);

		query.executeUpdate(); // <-- Questa riga esegue la query nella transazione

		transaction.commit();
	}

	public List<Animal> findByOwnersName(String name) {
		TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE a.owner.name = :name", Animal.class);
		// Grazie a JPQL posso attraversare le relazioni tramite la dotnotation sull'attributo che si riferisce
		// all'altra entità
		query.setParameter("name", name);
		return query.getResultList();
	}

	public List<Animal> findByOwner(Owner owner) {
		TypedQuery<Animal> query = em.createQuery("SELECT a FROM Animal a WHERE a.owner = :owner", Animal.class);
		// Grazie a JPQL posso anche usare OGGETTI come parametri (in SQL non si può fare)
		query.setParameter("owner", owner);
		return query.getResultList();
	}

}
