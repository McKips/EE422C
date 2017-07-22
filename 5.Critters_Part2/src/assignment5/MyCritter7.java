package assignment5;

import assignment5.Critter.TestCritter;

public class MyCritter7 extends TestCritter {
	
	@Override
	public void doTimeStep() {
	}

	@Override
	public boolean fight(String opponent) {

		return true;
	}

	@Override
	public CritterShape viewShape() {
		return null;
	}

	@Override
	public String toString () {
		return "7";
	}
}
