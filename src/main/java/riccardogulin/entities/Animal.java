package riccardogulin.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_animale")

/*
SINGLE TABLE: è la strategia più semplice in quanto a prescindere dal numero di figli mi troverò ad avere un'unica tabella da gestire.
La strategia è comoda poichè avrò tutti gli animali nella stessa tabella, quindi per reperire tutti i dati non serve fare JOIN ma bastano
delle query più semplici (e più veloci), in questi casi potrebbe essere più performante rispetto ad altre strategie

Purtroppo però si può arrivare ad avere tabelle molto disordinate, poichè ci saranno tanti valori NULL sparsi in giro per la tabella
(questo peggiora se i figli hanno tanti attributi diversi tra di loro e se abbiamo tanti figli). Questo mi impedisce di mettere dei vincoli
NON NULL andando a peggiorare la robustezza della tabella
* */

public abstract class Animal {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;
	private int age;

	protected Animal() {
	}

	public Animal(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Animal{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
