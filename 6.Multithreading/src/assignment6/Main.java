package assignment6;

import java.util.List;

public class Main {
    public static void main(String[] args){
        Theater th1 = new Theater(3,3,"Ouija");
        List<Theater.Ticket> tickets = th1.getTransactionLog();
    }
}
