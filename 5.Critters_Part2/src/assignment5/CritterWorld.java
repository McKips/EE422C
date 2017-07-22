package assignment5;

/**
 * Helper class that updates and stores world information as well
 * as keep track of how many critters are at each (x,y) coordinate).
 */

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class CritterWorld {
    public static HashMap<Integer, ArrayList<Critter>> encounterMap = new HashMap<Integer,ArrayList<Critter>>(Params.world_height*Params.world_width);
    public static GraphicsContext graphics;
    private static GridPane grid;
    private static int base;
    public static int scale = 1;
    public static Canvas canvas;

	private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    public static void initialize(){
    	int base1 = (int) (primaryScreenBounds.getHeight() / Params.world_height);

    	if(base1*Params.world_width > primaryScreenBounds.getWidth()-600)
    		base = ( (int) (primaryScreenBounds.getWidth() - 600) /Params.world_width );
		else
			base = base1;
//		base = (base/10) * 10;
//		if(base < 10)
//			base = 10;
		Main.graphicStage.setMaxHeight(Params.world_height * base + 50);
		Main.graphicStage.setMaxWidth(Params.world_width * base + 50);
        canvas = new Canvas(Params.world_width * base, Params.world_height * base);
        graphics = canvas.getGraphicsContext2D();
        grid = new GridPane();
    }

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
        if(critEncounter != null)
            critEncounter.remove(crit);
    }

    /**
     * Create an empty world with borders.
     * Set up the borders of the world.
     */


    /**********************
     * Critter FX methods *
     **********************/

    public static void drawCrittersFX(Critter crit, int x, int y) {
		graphics = canvas.getGraphicsContext2D();
		grid = new GridPane();
		grid.maxHeight(Params.world_height);
		grid.maxWidth(Params.world_width);
		
		// Change thickness of outline
    	int strokeWidth = 1;
		if (scale > 1) {
			if (scale > 10)
				strokeWidth = 3;
			else
				strokeWidth = 2;
		}
		graphics.setLineWidth(strokeWidth);
		x *= base;
		y *= base;
		
    	double leftBound = x*scale+(strokeWidth+1);
		double centerX = x*scale+(base*scale/2);
		double rightBound = x*scale+(base*scale-(strokeWidth+1));
		double topBound = y*scale+(strokeWidth+1);
		double centerY = y*scale+(base*scale/2);
		double botBound = y*scale+(base*scale-(strokeWidth+1));
		
		graphics.setStroke(Color.RED);
		graphics.strokeRect(0, 0, Params.world_width * base * scale, Params.world_height * base * scale);
		// view bounding box of critters
		//graphics.strokeRect(x*scale,y*scale, base*scale, base*scale);
		clearCritFX(crit, x, y);
		
		Critter.CritterShape critShape = crit.viewShape();
    	graphics.setStroke(crit.viewOutlineColor());
    	graphics.setFill(crit.viewFillColor());
    	
    	switch(critShape) {
    	    case CIRCLE:
    	    	graphics.fillOval(
    	    			(x*scale)+(strokeWidth+1),(y*scale)+(strokeWidth+1),
    	    			rightBound-leftBound,rightBound-leftBound);
    			graphics.strokeOval(
    					(x*scale)+(strokeWidth+1),(y*scale)+(strokeWidth+1),
    					rightBound-leftBound,rightBound-leftBound);
    			break;
    	    case SQUARE:
    	    	graphics.fillPolygon(
    	    			new double[] {leftBound,rightBound,rightBound,leftBound}, 
    	    			new double[] {topBound,topBound,botBound,botBound}, 4);
    			graphics.strokePolygon(
    					new double[] {leftBound,rightBound,rightBound,leftBound}, 
    					new double[] {topBound,topBound,botBound,botBound}, 4);
    			break;
    	    case TRIANGLE:
    	    	graphics.fillPolygon(
    	    			new double[] {leftBound,centerX,rightBound}, 
    	    			new double[] {botBound,topBound,botBound}, 3);
    			graphics.strokePolygon(
    					new double[] {leftBound,centerX,rightBound}, 
    					new double[] {botBound,topBound,botBound}, 3);
    			break;
    	    case DIAMOND:
    	    	graphics.fillPolygon(
    	    			new double[] {leftBound,centerX,rightBound,centerX}, 
    	    			new double[] {centerY,topBound,centerY,botBound}, 4);
    			graphics.strokePolygon(
    					new double[] {leftBound,centerX,rightBound,centerX}, 
    					new double[] {centerY,topBound,centerY,botBound}, 4);
    			break;
    	    case STAR:
    	    	double armL = x*scale + scale*base*((double)4/10);
    			double armR = x*scale + scale*(base - base*((double)4/10));
    			double legL = x*scale + scale*base*((double)3/10);
    			double legR = x*scale + scale*(base - base*((double)3/10));
    			double armH = y*scale + scale*base*((double)3/10);
    			double legH = y*scale + (scale*base)*((double)6/10);
    	    	graphics.fillPolygon(
    					new double[] {leftBound,armL,centerX,armR,rightBound,legR,rightBound,centerX,leftBound,legL},
    					new double[] {armH,armH,topBound,armH,armH,centerY,botBound,legH,botBound,centerY}, 10);
    			
    			graphics.strokePolygon(
    					new double[] {leftBound,armL,centerX,armR,rightBound,legR,rightBound,centerX,leftBound,legL},
    					new double[] {armH,armH,topBound,armH,armH,centerY,botBound,legH,botBound,centerY}, 10);
    			break;
    	}
    }
    
    public static void printWorldFX(Stage stage){
		grid.getChildren().add(canvas);	
		stage.setScene(new Scene(grid));
		stage.show();
    }
    
    public static void clearCritFX(Critter crit, int x, int y) {
    	graphics.clearRect(x*base, y*base, base, base);
    }

}

