package assignment5;

import javafx.scene.paint.Color;

/**
 * Created by kipki on 7/7/2017.
 */
public class Critter4 extends Critter{

    static int walkedToDeath = 0;
    @Override
    public CritterShape viewShape() {
        return CritterShape.SQUARE;
    }

    @Override
    public javafx.scene.paint.Color viewOutlineColor() {
        return Color.HOTPINK;
    }


        @Override
    public String toString(){ return "S"; }

    @Override
    public void doTimeStep() {
        int dir = Critter.getRandomInt(8);
       if(getEnergy() >= 1000){
           while(getEnergy() > 100){
               Critter4 rock = new Critter4();
               int n = getRandomInt(8);
               reproduce(rock,n);
           }
           if(look(dir,true) != null){
               run(dir);
           }
       }
       else if(getEnergy() < Params.walk_energy_cost){
           if(look(dir,false) == null) {
               walk(dir);
               walkedToDeath += 1;
           }
       }
       else if(getEnergy() > 2.5*Params.run_energy_cost){
           if(look(dir,true) != null){
               run(dir);
           }
       }
       else
       if(look(dir,false) == null)
           walk(dir);
    }

    @Override
    public boolean fight(String opponent) {
        if(getEnergy() > 3*Params.min_reproduce_energy){
            Critter4 rock = new Critter4();
            reproduce(rock,getRandomInt(8));
            if(rock.getEnergy() > Params.run_energy_cost){
                rock.run(5);
            }
            return false;
        }
        else{
            run(getRandomInt(8));
            return true;
        }
    }

    public static void runStats(){
        System.out.println("Walked to Death: " + walkedToDeath);
    }
}
