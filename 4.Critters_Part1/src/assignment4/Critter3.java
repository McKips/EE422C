package assignment4;

/**
 * Created by kipki on 7/7/2017.
 */
public class Critter3 extends Critter {
    private static int dir = 0;
    private static int run = 0;
    private static int walk = 0;
    private static int nothing = 0;

    @Override
    public String toString() { return "P"; }
    @Override
    public void doTimeStep() {
        if(getEnergy() >= Params.run_energy_cost + Params.rest_energy_cost) {
            run(dir);
            dir += 1;
            run += 1;
        }
        else if (getEnergy() >= Params.rest_energy_cost + Params.walk_energy_cost){
            walk(dir);
            walk += 1;
        }
        else
            nothing += 1;
        if (getEnergy() >= 50){
            Critter3 run = new Critter3();
            int r = Critter.getRandomInt(8);

            reproduce(run,r);
        }
    }

    @Override
    public boolean fight(String opponent) {
        return false;
    }

    public static void runStats(){
       System.out.print("Run count:" + run);
       System.out.print("    Walk count:" + walk);
       System.out.print("    Nothing count:" + nothing);
       System.out.println();
    }
}
