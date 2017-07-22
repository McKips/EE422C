package assignment5;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}

	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() {
		return javafx.scene.paint.Color.WHITE;
	}

	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }

	public abstract CritterShape viewShape();

	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	public static HashMap<Integer, ArrayList<Critter>> origEncounterMap;
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	private int position;
	private boolean hasMoved = false;
	private boolean fighting = false;

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	protected final String look(int direction, boolean steps) {
		this.energy -= Params.look_energy_cost;
		int positionX = this.x_coord;
		int positionY = this.y_coord;

		int loop = 1;

		if (steps) {
			loop = 2;
		}
		for (;loop > 0; loop--) {
			switch (direction) {
				// Right
				case 0:
					positionX += 1;
					break;
				// Up - Right
				case 1:
					positionX += 1;
					positionY -= 1;
					break;
				// Up
				case 2:
					positionY -= 1;
					break;
				// Up - Left
				case 3:
					positionX -= 1;
					positionY -= 1;
					break;
				// Left
				case 4:
					positionX -= 1;
					break;
				// Down - Left
				case 5:
					positionX -= 1;
					positionY += 1;
					break;
				// Down
				case 6:
					positionY -= 1;
					break;
				// Down - Right
				case 7:
					positionX += 1;
					positionY += 1;
					break;
			}
		}
		int viewPosition = ((positionY) * Params.world_width) + positionX;


		if (isOccupied(viewPosition)) {
			if (this.fighting) {
				ArrayList<Critter> crits = CritterWorld.encounterMap.get(viewPosition);
				return crits.get(0).toString();
			} else {
				ArrayList<Critter> crit = origEncounterMap.get(viewPosition);
				if( crit == null || crit.size() == 0)
					return null;

				return crit.get(0).toString();
			}
		}
		return null;
	}

	/* rest is unchanged from Project 4 */


	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
		System.out.println(rand);
	}


	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

	private int energy = 0;
	protected int getEnergy() { return energy; }

	private int x_coord;
	private int y_coord;

	/**
	 * moves critter one step in the specified direction
	 * @param direction of critter's movement
	 */
	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;

		// remove the critter if it has <= 0 energy
		if (this.energy <= 0) {
			removeCritter(this);
			return;
		}

		// return if the critter has already moved this time step
		if (this.hasMoved) {return;}

		int initialX = this.x_coord;
		int initialY = this.y_coord;

		switch (direction) {
			// Right
			case 0: this.x_coord += 1;
				break;
			// Up - Right
			case 1: this.x_coord += 1;
				this.y_coord -= 1;
				break;
			// Up
			case 2: this.y_coord -= 1;
				break;
			// Up - Left
			case 3: this.x_coord -= 1;
				this.y_coord -= 1;
				break;
			// Left
			case 4: this.x_coord -= 1;
				break;
			// Down - Left
			case 5: this.x_coord -= 1;
				this.y_coord += 1;
				break;
			// Down
			case 6: this.y_coord -= 1;
				break;
			// Down - Right
			case 7: this.x_coord += 1;
				this.y_coord += 1;
				break;
		}
		exceedsBounds(this);
		critterPosition(this);

		// return a fighting critter to its initial position if the space was occupied
		if (fighting == true && isOccupied(this.position)) {
			this.x_coord = initialX;
			this.y_coord = initialY;
			critterPosition(this);
		}
		this.hasMoved = true;
	}
	/**
	 * moves critter two steps in the specified direction
	 * @param direction of critter's movement
	 */
	protected final void run(int direction) {
		this.energy -= Params.run_energy_cost;

		// remove the critter if it has <= 0 energy
		if (this.energy <= 0) {
			removeCritter(this);
			return;
		}

		// return if the critter has already moved this time step
		if (this.hasMoved) {return;}

		int initialX = this.x_coord;
		int initialY = this.y_coord;

		switch (direction) {
			// Right
			case 0: this.x_coord += 2;
				break;
			// Up - Right
			case 1: this.x_coord += 2;
				this.y_coord -= 2;
				break;
			// Up
			case 2: this.y_coord -= 2;
				break;
			// Up - Left
			case 3: this.x_coord -= 2;
				this.y_coord -= 2;
				break;
			// Left
			case 4: this.x_coord -= 2;
				break;
			// Down - Left
			case 5: this.x_coord -= 2;
				this.y_coord += 2;
				break;
			// Down
			case 6: this.y_coord -= 2;
				break;
			// Down - Right
			case 7: this.x_coord += 2;
				this.y_coord += 2;
				break;
		}
		exceedsBounds(this);
		critterPosition(this);

		// return a fighting critter to its initial position if the space was occupied
		if (fighting == true && isOccupied(this.position)) {
			this.x_coord = initialX;
			this.y_coord = initialY;
			critterPosition(this);
		}
		this.hasMoved = true;
	}
	/**
	 * making babies, buying you a Mercedes
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.getEnergy() < Params.min_reproduce_energy){
			return;
		}
		offspring.energy = this.energy/2;
		this.energy = this.energy/2 + this.energy%2;
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.walk(direction);
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);

	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {

		Class<?> subclass = null;
		try {
			// create the new critter
			subclass = Class.forName(myPackage + "." + critter_class_name);
			Object subCritter = subclass.newInstance();
			Critter newCritter = (Critter) subCritter;

			// set initial data values of the critter
			newCritter.energy = Params.start_energy;
			newCritter.x_coord = Critter.getRandomInt(Params.world_width);
			newCritter.y_coord = Critter.getRandomInt(Params.world_height);
			critterPosition(newCritter);

			// add the critter to the population and map
			population.add(newCritter);
			CritterWorld.mapEncountered(newCritter.position,newCritter);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new InvalidCritterException(critter_class_name);
		}
	}

	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		for(Critter crit : population){
			String critName = crit.getClass().getSimpleName().split("\\.")[0];
			if(critter_class_name.equals(critName))
				result.add(crit);
		}

		return result;
	}

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}

	/* the TestCritter class allows some critters to "cheat". If you want to
     * create tests of your Critter model, you can create subclasses of this class
     * and then use the setter functions contained here.
     *
     * NOTE: you must make sure that the setter functions work with your implementation
     * of Critter. That means, if you're recording the positions of your critters
     * using some sort of external grid or some other data structure in addition
     * to the x_coord and y_coord functions, then you MUST update these setter functions
     * so that they correctly update your grid/data structure.
     */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}


		/*
         * This method getPopulation has to be modified by you if you are not using the population
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
         * This method getBabies has to be modified by you if you are not using the babies
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.  Babies should be added to the general population
         * at either the beginning OR the end of every timestep.
         */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		/*for(Critter crit : population){
			CritterWorld.removePosition(crit.x_coord,crit.y_coord);
		}*/
		population.clear();
	}

	/**
	 * Iterate through each critter in the population and call their doTimeStep function.
	 * Remove them from the currentWorld before calling their doTimeStep function.
	 * Remove them from the encounter step and update their new encounter position after time step.
	 * Subtract rest_energy_cost from their energy.
	 * Check the (x,y) coordinate to see if the new position is within the boundaries.
	 * Update the new position of the critter on the world.
	 */
	public static void worldTimeStep() {
	    origEncounterMap= new HashMap<>(CritterWorld.encounterMap);
		for(Critter crit : population){
			//CritterWorld.removePosition(crit.x_coord,crit.y_coord);
			CritterWorld.removeMapEncounter(crit.position, crit);
			if(CritterWorld.graphics != null){
				CritterWorld.clearCritFX(crit,crit.x_coord,crit.y_coord);
			}
			crit.doTimeStep();
			critterPosition(crit);
			CritterWorld.mapEncountered(crit.position,crit);
		}

        /* Remove any critter that has zero or less energy and remove from the encounterMap. */
		List<Critter> newPopulation = new ArrayList<>(population);
		for(Critter crit : population){
			if(crit.energy <= 0){
				removeCritter(crit);
				newPopulation.remove(crit);
			}
		}
		population = new ArrayList<>(newPopulation);

        /* Check for any encounters. */
		for(Critter c : population) {
			int mapSpace = c.position;
			if (CritterWorld.encounterMap.get(mapSpace) != null && CritterWorld.encounterMap.get(mapSpace).size() > 1) {
				encounterHandler(CritterWorld.encounterMap.get(mapSpace));
			}
		}

        /* Remove any critter that has zero or less energy and remove from the encounterMap. */
		newPopulation = new ArrayList<>(population);
		for(Critter crit : population){
			crit.energy -= Params.rest_energy_cost;
			crit.hasMoved = false;
			if(crit.energy <= 0){
				newPopulation.remove(crit);
				if(CritterWorld.graphics != null){
					CritterWorld.clearCritFX(crit,crit.x_coord,crit.y_coord);
				}
			}
		}
		population = new ArrayList<>(newPopulation);
		newPopulation.clear();

        /* Add the babies to current population and to the encounterMap. */
		for(Critter critBaby : babies) {
			population.add(critBaby);
			critterPosition(critBaby);
			CritterWorld.mapEncountered(critBaby.position, critBaby);
		}
		babies.clear();

        /* Add algae to the world. */
		for(int i = 0; i < Params.refresh_algae_count; i++) {
			Critter green = new Algae();
			critterPosition(green);
			green.x_coord = Critter.getRandomInt(Params.world_width);
			green.y_coord = Critter.getRandomInt(Params.world_height);
			CritterWorld.mapEncountered(green.position, green);
			green.energy = Params.start_energy;
			population.add(green);
		}
	}

	/**
	 * Display the world to the console by calling the printWorld function in the CritterWorld class.
	 */
	static void displayWorld(Object stage) {
		for(Critter critter : population){
			CritterWorld.drawCrittersFX(critter,critter.x_coord,critter.y_coord);
		}
		if (stage instanceof Stage) {
			CritterWorld.printWorldFX((Stage)stage);
		} else {
			System.out.println("Object was not a stage");
		}
	}

	/**
	 * Calculate a position integer that calculates which box of the world it occupies
	 * @param crit Critter object for (x,y) coordinates.
	 */
	private static void critterPosition(Critter crit){
		crit.position = ((crit.y_coord ) * Params.world_width) + crit.x_coord;
	}

	/**
	 * Removes a critter from the display and encounter map
	 */
	private static void removeCritter(Critter ripCrit) {
		CritterWorld.removeMapEncounter(ripCrit.position,ripCrit);
	}

	private static boolean isOccupied(int position) {
        return CritterWorld.encounterMap.get(position) != null && CritterWorld.encounterMap.get(position).size() != 0;
    }
	/**
	 * Method exceedsBounds makes sure the x and y coordinates of the critter does not
	 * exceed the dimensions of the parameters.
	 * @param crit Critter object used for to access its' x and y coordinates.
	 */

	private static void exceedsBounds(Critter crit){

		if(crit.x_coord >= Params.world_width)
			crit.x_coord = crit.x_coord % Params.world_width;
		else if(crit.x_coord < 0)
			crit.x_coord = Params.world_width + crit.x_coord;

		if(crit.y_coord >= Params.world_height)
			crit.y_coord = crit.y_coord % Params.world_height;
		else if(crit.y_coord < 0)
			crit.y_coord = Params.world_height + crit.y_coord;
	}

	/**
	 * Gets a list of critters found on the same map space, and handles
	 * the encounter by checking if a critter wants to fight or run
	 * @param encounterList List of critters on same map space
	 */
	private static void encounterHandler(List<Critter> encounterList) {
		Critter A = encounterList.get(0);
		for (int i = 1; i < encounterList.size(); i++) {
			// get next critter on the list
			Critter B = encounterList.get(i);
			int rollA = 0;
			int rollB = 0;
			int initialAPos = A.position;
			int initialBPos = B.position;
			A.fighting = true;
			B.fighting = true;

			// check if critters want to fight
			if (A.fight(B.toString())) {
				rollA = getRandomInt(A.energy);
			}
			if (B.fight(A.toString())) {
				rollB = getRandomInt(B.energy);
			}

			// update the map if a critter moved
			if (A.position != initialAPos) {
				A.fighting = false;
				CritterWorld.encounterMap.remove(initialAPos, A);
				CritterWorld.encounterMap.get(initialAPos).remove(A);
				//CritterWorld.mapEncountered(A.position, A);
			}
			if (B.position != initialBPos) {
				B.fighting = false;
				CritterWorld.encounterMap.remove(initialBPos, B);
				CritterWorld.encounterMap.get(initialAPos).remove(B);
				//CritterWorld.mapEncountered(B.position, B);
			}

			// if the critters are still alive and haven't moved, have them fight
			if (A.position == B.position) {
				if (rollA <= rollB) {
					B.energy += A.energy / 2;
					A.energy = 0;
					removeCritter(A);
					B.fighting = false;
					A = B;
				} else {
					A.energy += B.energy / 2;
					B.energy = 0;
					removeCritter(B);
					A.fighting = false;
				}
			}
		}
	}
}

