package assignment4;

/**
 * Created by kipki on 7/7/2017.
 */
public class Critter4 extends Critter{
    @Override
    public String toString(){ return "S"; }

    @Override
    public void doTimeStep() {
       if(getEnergy() >= 1000){
           while(getEnergy() > 100){
               Critter4 rock = new Critter4();
               int n = getRandomInt(8);
               reproduce(rock,n);
           }
       }
       else if(getEnergy() < Params.walk_energy_cost){
           walk(getRandomInt(8));
       }
       else if(getEnergy() > 2.5*Params.run_energy_cost){
           run(getRandomInt(8));
       }
       else
           walk(getRandomInt(8));
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
        System.out.println("No stats:" + 0);
    }
}
