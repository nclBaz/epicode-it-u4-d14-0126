package riccardogulin.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Gatto")
public class Cat extends Animal {
	private double maxJumpHeigth;

	protected Cat() {
	}

	public Cat(String name, int age, double maxJumpHeigth) {
		super(name, age);
		this.maxJumpHeigth = maxJumpHeigth;
	}

	public double getMaxJumpHeigth() {
		return maxJumpHeigth;
	}

	@Override
	public String toString() {
		return "Cat{" +
				"maxJumpHeigth=" + maxJumpHeigth +
				"} " + super.toString();
	}
}
