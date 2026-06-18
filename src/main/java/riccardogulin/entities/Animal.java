package riccardogulin.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "animals")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// @DiscriminatorColumn(name = "tipo_animale")

/*
SINGLE TABLE: è la strategia più semplice in quanto a prescindere dal numero di figli mi troverò ad avere un'unica tabella da gestire.
La strategia è comoda poichè avrò tutti gli animali nella stessa tabella, quindi per reperire tutti i dati non serve fare JOIN ma bastano
delle query più semplici (e più veloci), in questi casi potrebbe essere più performante rispetto ad altre strategie

Purtroppo però si può arrivare ad avere tabelle molto disordinate, poichè ci saranno tanti valori NULL sparsi in giro per la tabella
(questo peggiora se i figli hanno tanti attributi diversi tra di loro e se abbiamo tanti figli). Questo mi impedisce di mettere dei vincoli
NON NULL andando a peggiorare la robustezza della tabella

JOINED: con questa strategia otteniamo una tabella per la classe padre (anche se astratta) e una per ogni figlio. Il risultato nel nostro
caso sarà di 3 tabelle (1 per Animal, 1 per Cat e 1 per Dog)

Vantaggio principale è quello di avere uno schema più pulito senza "buchi" nelle colonne, difatti qua possiamo inserire tutti i vincoli
NON NULL che vogliamo senza problemi, ottenendo tabelle più robuste

Di contro però ogni qualvolta ho bisogno dei dati degli animali, dietro le quinte avverranno dei JOIN

TABLE PER CLASS: la si potrebbe chiamare anche TABLE PER CONCRETE CLASS, poiché verranno create tabelle diverse per ogni classe CONCRETA
Nel nostro caso creerà una tabella per i dogs e una per i cats. Risolve i problemi delle altre 2 strategie, nel senso che non abbiamo
il problema dei null riscontrato nella single table, nè il problema dei join della joined.

I contro però sono particolarmente pesanti sia in termini di relazioni sia in termini di prestazioni
Quando faccio ad esempio delle query che coinvolgano tutti gli animali, il database dovrà fare del lavoro extra molto impattante per le prestazioni
soprattutto se di animali ce ne sono tanti. Dovrà prendere tutti i cani poi tutti i gatti, metterli assieme in una sorta di "tabella virtuale"
da filtrare
Anche per le relazioni è una scelta problematica. Se ci dovesse essere una terza tabella da mettere in relazione con queste 2, non ho la possibilità
di creare una FK che si biforchi sulle 2 PK delle 2 tabelle (in realtà JPA anche lo farebbe simulando questa relazione però con delle tecniche
abbastanza distruttive in termini di performance)

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
