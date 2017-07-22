package assignment5;


/* Ismael Marquez 7-7-2017
 * Summary:
 * Harold lives to run and is only focused on covering as much
 * distance as possible before running himself to death. 
 * Because of this, Harold refuses to reproduce until the last
 * possible moment, preferring to save his reproductive energy
 * for another run
 */
public class Critter1 extends Critter {
	@Override
	public CritterShape viewShape() { return CritterShape.STAR; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.RED; }
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.YELLOW; }

	@Override
	public String toString() { return "H"; }
	private int dir;
	private int totalDistance = 0;
	
	public Critter1() {
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		
		// Runs if it has more than five times the required run energy
		if (getEnergy() > (5 * Params.run_energy_cost)) {
			boolean algae = false;
			for(int i=0; i<8; i++){
				if(look(i,true) == "@") {
					algae = true;
					dir = i;
					break;
				}
			}
			if(algae)
				run(dir);
			else
				run(dir);
			totalDistance += 2;
		} else {
			boolean algae = false;
			for(int i=0; i<8; i++){
				if(look(i,true) == "@") {
					algae = true;
					dir = i;
					break;
				}
			}
			if(algae)
				walk(dir);
			else
				walk(dir);
			totalDistance += 1;
		}
		
		// Reproduces only if it has just enough energy to reproduce
		if (getEnergy() == Params.min_reproduce_energy) {
			Critter1 child = new Critter1();			
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* Harold only moves diagonally */
		dir = Critter.getRandomInt(8);
		if (dir % 2 == 0) {
			dir += 1;
			assert(dir < 8);
		}
	}

	public static void runStats(java.util.List<Critter> harolds) {
		int currentMax = 0;
		int cumulativeDistance = 0;
		for (Object obj : harolds) {
			Critter1 h = (Critter1) obj;
			if (h.totalDistance > currentMax) {
				currentMax = h.totalDistance;
			}
			cumulativeDistance += h.totalDistance;
		}
		System.out.print("" + harolds.size() + " total Critter2(Harolds)    ");
		System.out.print("Furthest runner: " + currentMax + "    ");
		System.out.println("Total distance: " + cumulativeDistance + "    ");
	}
}
