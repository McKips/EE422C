package assignment6;

import java.util.*;

public class Theater {

    private TreeMap<Seat, Ticket> seatMap;
    private int clients;
    private int totalSeats;

    public boolean soldOut() {
        return clients >= totalSeats;
    }

    /*
     * Represents a seat in the theater
     * A1, A2, A3, ... B1, B2, B3 ...
     */
    static class Seat implements Comparable<Seat> {
        private int rowNum;
        private int seatNum;
        private boolean reserved;
        private int numClient;

        public Seat(int rowNum, int seatNum) {
            this.rowNum = rowNum;
            this.seatNum = seatNum;
            this.reserved = false;
            this.numClient = 0;
        }

        public int getSeatNum() {
            return seatNum;
        }

        public int getRowNum() {
            return rowNum;
        }

        public int getNumClient() { return numClient; }


        @Override
        public String toString() {
            StringBuilder seatPos = new StringBuilder();
            int row = rowNum;
            while (row >= 26) {
                seatPos.append((char) ('A' + row % 26));
                row = row / 26 - 1;
            }
            seatPos.append((char) ('A' + row % 26));
            seatPos.reverse();
            seatPos.append(seatNum);
            return seatPos.toString();
        }

        @Override
        public int compareTo(Seat o) {
            if (this.rowNum < o.rowNum)
                return -1;
            else if (this.rowNum > o.rowNum)
                return 1;
            else {
                if (this.seatNum < o.seatNum)
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
            StringBuilder seq1 = new StringBuilder();
            for (int i=0; i<31; i++)
                seq1.append('-');
            seq1.append('\n');
            StringBuilder seq2 = new StringBuilder();
            seq2.append('|');
            seq2.append(" Show: " + show);
            for(int i = seq2.length(); i<30;i++)
                seq2.append(' ');
            seq2.append("|\n");
            StringBuilder seq3 = new StringBuilder();
            seq3.append('|' + " Box Office ID: " + boxOfficeId);
            for(int i = seq3.length(); i<30;i++)
                seq3.append(' ');
            seq3.append("|\n");
            StringBuilder seq4 = new StringBuilder();
            seq4.append('|' + " Seat: " + seat);
            for(int i = seq4.length(); i<30;i++)
                seq4.append(' ');
            seq4.append("|\n");
            StringBuilder seq5 = new StringBuilder();
            seq5.append('|' + " Client: " + client);
            for(int i = seq5.length(); i<30;i++)
                seq5.append(' ');
            seq5.append("|\n");
            return seq1.toString() + seq2.toString() + seq3.toString() + seq4.toString() + seq5.toString() + seq1.toString();
        }
    }

    public Theater(int numRows, int seatsPerRow, String show) {
        seatMap = new TreeMap<>();
        totalSeats = numRows*seatsPerRow;
        clients = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 1; j <= seatsPerRow; j++) {
                Seat seats = new Seat(i, j);
                Ticket tickets = new Ticket(show, "", seats, -1);
                seatMap.put(seats, tickets);
            }
        }

    }


    /*
     * Calculates the best seat not yet reserved
     *
      * @return the best seat or null if theater is full
   */
    public synchronized Seat bestAvailableSeat() {
        for (Map.Entry<Seat, Ticket> bestSeat : seatMap.entrySet()) {
            Seat seat = bestSeat.getKey();
            if (!seat.reserved) {
                return seat;
            }
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
    public synchronized Ticket printTicket(String boxOfficeId, Seat seat, int client) {
        if(seat.reserved){
            return null;
        }
        seat.reserved = true;
        clients += 1;
        client = clients;
        Ticket tick = seatMap.get(seat);
        tick.boxOfficeId = boxOfficeId;
        tick.client = client;
        System.out.println(tick);
        return tick;
    }

    /*
     * Lists all tickets sold for this theater in order of purchase
     *
   * @return list of tickets sold
   */
    public List<Ticket> getTransactionLog() {
        List<Ticket> ticketList = new ArrayList<>();
        for (Map.Entry<Seat, Ticket> addTicket : seatMap.entrySet()) {
            boolean reserved = addTicket.getKey().reserved;
            ticketList.add(reserved ? addTicket.getValue() : null);
        }
        return ticketList;
    }
}
