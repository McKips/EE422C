package assignment5;

import java.util.List;

public class MyCritter1 extends Critter.TestCritter {

	@Override
	public void doTimeStep() {
		walk(0);
	}

	@Override
	public boolean fight(String opponent) {
		return getEnergy() > 10;
	}

	@Override
	public CritterShape viewShape() {
		return null;
	}

	public String toString() {
		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}
}
