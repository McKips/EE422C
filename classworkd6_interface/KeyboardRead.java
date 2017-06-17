import java.util.Scanner;
import java.io.*;

public class KeyboardRead {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String in = kb.next();
        System.out.print("Enter an int: ");
        int i = kb.nextInt();
        System.out.print("Enter a double: ");
        double d = kb.nextDouble();
        String rest = kb.nextLine();

        System.out.println(in + i + d);

        File infile = new File("example");
        Scanner scInfile = new Scanner(infile);

        while (scInfile.hasNextLine()){
            System.out.println(scInfile.nextLine());
        }

        //print to a file
        
        Scanner scInfile2 = new Scanner(infile);

        File outfile = new File("output_lect6");
        PrintStream pOutfile = new PrintStream(outfile);
        while (scInfile2.hasNextLine()) {
            String line = scInfile2.nextLine();
            pOutfile.println(scInfile2.nextLine());
            System.out.println(line);
        }
        pOutfile.close();
    }
}
