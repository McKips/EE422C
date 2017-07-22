package assignment4;

/**
 * Created by kipki on 7/14/2017.
 */
public class MyCritter9 extends Critter{
    @Override
    public void doTimeStep() {
       run(3);
    }

    @Override
    public boolean fight(String opponent) {
        return false;
    }
}
