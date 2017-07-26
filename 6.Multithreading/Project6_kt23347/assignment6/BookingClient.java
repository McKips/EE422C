package assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingClient {
    private Map<String,Integer> officeMap;
    private Theater theater;

    public static void main(String[] args){
        Theater theater = new Theater(3,5,"Ouija");
        Map<String,Integer> offices = new HashMap<>();
        offices.put("BX1",3); offices.put("BX3",3); offices.put("BX2",4);
        offices.put("BX5",3); offices.put("BX4",3);

        BookingClient booking = new BookingClient(offices,theater);
        booking.simulate();

    }


    /*
     * @param office maps box office id to number of customers in line
     * @param theater the theater where the show is playing
     */
    public BookingClient(Map<String, Integer> office, Theater theater) {
        officeMap = new HashMap<>(office);
        this.theater = theater;

    }

    /*
     * Starts the box office simulation by creating (and starting) threads
     * for each box office to sell tickets for the given theater
     *
     * @return list of threads used in the simulation,
     *         should have as many threads as there are box offices
     */
    public List<Thread> simulate() {
        List<Thread> newThread = new ArrayList<>();
        for( Map.Entry<String,Integer> map : officeMap.entrySet()) {
            Runnable bx = new BoxOffice(map.getKey(), map.getValue(), theater);
            Thread th = new Thread(bx);
            newThread.add(th);
            th.start();
        }
        return newThread;
    }
}
