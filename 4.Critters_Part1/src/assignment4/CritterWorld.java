package assignment4;

/**
 * Helper class that updates and stores world information as well
 * as keep track of how many critters are at each (x,y) coordinate).
 */

import java.util.ArrayList;
import java.util.HashMap;

public class CritterWorld {
    private static String[][] world;
    public static HashMap<Integer, ArrayList<Critter>> encounterMap = new HashMap<Integer,ArrayList<Critter>>(Params.world_height*Params.world_width);
    private static int col;
    private static int row;

    public CritterWorld(){}

    /**
     * Method to store how many critters occupy the same col and row position and the critter itself.
     * @param position integer that is used as the key to get the List of critters at that location.
     * @param critter the object critter is stored to retain all pertinent information.
     */

    public static void mapEncountered(Integer position, Critter critter){
        ArrayList<Critter> critEncounter = encounterMap.get(position);
        if(critEncounter == null)
            critEncounter = new ArrayList<Critter>();
        critEncounter.add(critter);
        encounterMap.put(position,critEncounter);
    }

    /**
     * Remove the current critter from list using position as the key.
     * @param position indicates which box the critter occupies.
     * @param crit the crit that is to be removed from the list.
     */
    public static void removeMapEncounter(Integer position, Critter crit){
        ArrayList<Critter> critEncounter = encounterMap.get(position);
        critEncounter.remove(crit);
    }

    /**
     * Create an empty world with borders.
     * Set up the borders of the world.
     */

    public static void createBorders(){
        col = 2 + Params.world_width; // Account for borders on left and right side
        row = 2 + Params.world_height; // Account for borders along the top and bottom
        world = new String[row][col];
        world[0][0] = "+";
        world[0][col-1] = "+";
        world[row-1][0] = "+";
        world[row-1][col-1] = "+";

        for(int i = 1; i < col-1; i++)
            world[0][i] = "-";

        for(int i = 1; i < col-1;i++)
            world[row-1][i] = "-";

        for(int j = 1; j < row-1; j++)
            world[j][0] = "|";

        for(int j = 1; j< row-1; j++)
            world[j][col-1] = "|";

        for(int i = 1; i < row-1; i++){
            for(int j = 1; j < col-1; j++)
                world[i][j] = " ";
        }
    }

    /**
     * Method showCritters places the critter within the borders of the world.
     *
     * @param crit Critter object that is used to access the string representation of the object.
     * @param x the x coordinate of the critter.
     * @param y the y coordinate of the critter.
     */
    public static void showCritters(Critter crit, int x, int y){
        int worldX = x+1;
        int worldY = y+1;
        
/*        worldX = checkBoundsX(worldX);
        worldY = checkBoundsY(worldY);*/
        
        world[worldY][worldX] = crit.toString();
    }
    
/*    public static int checkBoundsX(int x) {
    	if(x > col-2)
    		x = x%(col-2);
    	else if(x < 1)
    		x = (col - 2) + x;
    	return x;
    }
    
    public static int checkBoundsY(int y) {
    	if(y > row-2)
    		y = y%(row-2);
    	else if(y < 1)
    		y = (row - 2) + y;
    	return y;
    }*/
    /** Print the current world to the screen. **/

    public static void printWorld(){
        for(int i = 0; i < row;i++){
            for (int j = 0; j < col; j++)
                System.out.print(world[i][j]);
            System.out.println();
        }
    }
}

