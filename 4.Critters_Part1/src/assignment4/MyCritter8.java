package assignment4;

/**
 * Created by kipki on 7/14/2017.
 */
public class MyCritter8 extends Critter{
    @Override
    public void doTimeStep() {
        walk(3);
    }

    @Override
    public boolean fight(String opponent) {
        return false;
    }
}

