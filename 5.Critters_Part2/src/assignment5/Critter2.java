package assignment5;


import javafx.scene.paint.Color;

/* Ismael Marquez 7-7-2017
 * Summary:
 * This rabbit critter's goal is to reproduce as much as possible
 */
public class Critter2 extends Critter {

	@Override
	public CritterShape viewShape() {
		return CritterShape.DIAMOND;
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return Color.AQUA; }

	@Override
	public String toString() { return "R"; }
	private int dir;
	private int children = 0;
	public Critter2() {
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { return true; }

	@Override
	public void doTimeStep() {
		
		// Rabbit only moves after reproducing
		dir = Critter.getRandomInt(8);

		// Reproduces as long as it has enough energy
		if (getEnergy() >= Params.min_reproduce_energy) {
			Critter2 child = new Critter2();			
			reproduce(child, Critter.getRandomInt(8));
			for(int i = 0; i<8; i++){
				if(look(i,false) == null) {
					dir = i;
					break;
				}
			}
			walk(dir);
			children += 1;
		}
	}

	public static void runStats(java.util.List<Critter> rabbits) {
		int currentMax = 0;
		int familyMembers = 0;
		for (Object obj : rabbits) {
			Critter2 h = (Critter2) obj;
			if (h.children > currentMax) {
				currentMax = h.children;
			}
			familyMembers += h.children;
		}
		System.out.print("" + rabbits.size() + " total Critter1(Rabbits)    ");
		System.out.print("Most Children: " + currentMax + "    ");
		System.out.println("Family Members: " + familyMembers + "    ");
	}
}
