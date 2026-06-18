package riccardogulin.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Cane")
public class Dog extends Animal {
	private double maxSpeed;

	protected Dog() {
	}

	public Dog(String name, int age, double maxSpeed) {
		super(name, age);
		this.maxSpeed = maxSpeed;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	@Override
	public String toString() {
		return "Dog{" +
				"maxSpeed=" + maxSpeed +
				"} " + super.toString();
	}
}
