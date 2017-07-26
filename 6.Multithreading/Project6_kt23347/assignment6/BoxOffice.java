package assignment6;

public class BoxOffice implements Runnable {
    private String boxOffice;
    private int line;
    private Theater theater;

    BoxOffice(String boxOffice, int line, Theater theater) {
        this.boxOffice = boxOffice;
        this.line = line;
        this.theater = theater;
    }

    @Override
    public void run() {
        while (line > 0) {
                Theater.Seat seat = theater.bestAvailableSeat();
                if (seat == null) {
                    if (theater.soldOut()) {
                        System.out.println("Sorry, we are sold out!");
                        return;
                    }
                } else {
                    Theater.Ticket tick = theater.printTicket(boxOffice, seat, 0);
                    if (tick == null)
                        continue;
                    line -= 1;
                }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
