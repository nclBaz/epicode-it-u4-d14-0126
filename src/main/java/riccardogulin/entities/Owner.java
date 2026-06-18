package riccardogulin.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "owners")
public class Owner {
	@Id
	@GeneratedValue
	private UUID id;

	private String name;
	private String surname;

	public Owner() {
	}

	public Owner(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public String toString() {
		return "Owner{" +
				"id=" + id +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				'}';
	}
}
