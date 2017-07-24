// insert header here
package assignment6;

import java.util.*;

public class Theater {

    private TreeMap<Seat,Ticket> seatMap;

    /*
     * Represents a seat in the theater
     * A1, A2, A3, ... B1, B2, B3 ...
     */
    static class Seat implements Comparable<Seat>{
        private int rowNum;
        private int seatNum;
        private boolean reserved;

        public Seat(int rowNum, int seatNum) {
            this.rowNum = rowNum;
            this.seatNum = seatNum;
            this.reserved = false;
        }

        public int getSeatNum() {
            return seatNum;
        }

        public int getRowNum() {
            return rowNum;
        }

        @Override
        public String toString() {
            // TODO: Implement this method to return the full Seat location ex: A1
            StringBuilder seatPos = new StringBuilder();
            int row = rowNum;
            while(row >= 26){
                seatPos.append((char) ('A' + row % 26));
                row /= 26;
            }
            seatPos.append((char) ('A' + row % 26));
            seatPos.append(seatNum);
            return seatPos.toString();
        }

        @Override
        public int compareTo(Seat o) {
            if( this.rowNum < o.rowNum )
                return -1;
            else if( this.rowNum > o.rowNum )
                return 1;
            else{
                if(this.seatNum < o.seatNum)
                    return -1;
                else if (this.seatNum > o.seatNum)
                    return 1;
            }
            return 0;
        }
    }

    /*
       * Represents a ticket purchased by a client
       */
    static class Ticket {
        private String show;
        private String boxOfficeId;
        private Seat seat;
        private int client;

        public Ticket(String show, String boxOfficeId, Seat seat, int client) {
            this.show = show;
            this.boxOfficeId = boxOfficeId;
            this.seat = seat;
            this.client = client;
        }

        public Seat getSeat() {
            return seat;
        }

        public String getShow() {
            return show;
        }

        public String getBoxOfficeId() {
            return boxOfficeId;
        }

        public int getClient() {
            return client;
        }

        @Override
        public String toString() {
            // TODO: Implement this method to return a string that resembles a ticket
            return "Show: " + show + "\n" +
                    "Box Office ID: " + boxOfficeId + "\n" +
                    "Seat: " + seat + "\n" +
                    "Client: " + client + "\n";
        }
    }

    public Theater(int numRows, int seatsPerRow, String show) {
        seatMap = new TreeMap<>();
        for(int i=0; i<numRows; i++){
            for(int j=0; j<seatsPerRow; j++){
                Seat seats = new Seat(i,j);
                Ticket tickets = new Ticket(show,"",seats,-1);
                seatMap.put(seats,tickets);
            }
        }
    }

    /*
     * Calculates the best seat not yet reserved
     *
      * @return the best seat or null if theater is full
   */
    public Seat bestAvailableSeat() {
        for(Map.Entry<Seat,Ticket> bestSeat : seatMap.entrySet()){
            Seat seat = bestSeat.getKey();
            if( !seat.reserved )
                return seat;
        }
        return null;
    }

    /*
     * Prints a ticket for the client after they reserve a seat
   * Also prints the ticket to the console
     *
   * @param seat a particular seat in the theater
   * @return a ticket or null if a box office failed to reserve the seat
   */
    public Ticket printTicket(String boxOfficeId, Seat seat, int client) {
        //TODO: Implement this method
        Ticket tick = seatMap.get(seat);
        tick.boxOfficeId = boxOfficeId;
        tick.client = client;
        return null;
    }

    /*
     * Lists all tickets sold for this theater in order of purchase
     *
   * @return list of tickets sold
   */
    public List<Ticket> getTransactionLog() {
        List<Ticket> ticketList = new ArrayList<>();
        for(Map.Entry<Seat,Ticket> addTicket : seatMap.entrySet()){
            boolean reserved = addTicket.getKey().reserved;
            ticketList.add( reserved ? addTicket.getValue(): null);
        }
        return ticketList;
    }
}
