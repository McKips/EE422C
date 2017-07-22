package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Ismael Marquez>
 * <im6549>
 * <71675>
 * <Kiptoo Tonui>
 * <kt23347>
 * <71675>
 * Slip days used: <0>
 * Summer 2017
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;    // scanner connected to keyboard input, or input file
    private static String inputFile;    // input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;    // if test specified, holds all console output
    private static String myPackage;    // package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;    // if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     *
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     *             and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */

        /**
         * Next section of the main method sets up the world and performs actions
         * dependent on the input from console.
         *
         * First get the newline and split it into an array.
         * Then see if it is a valid command, if not, throw an exception.
         * Check which command was inputted and perform the appropriate actions.
         *
         */

        boolean run = true;

        while (run) {
            String[] input = kb.nextLine().split(" ");
            try {
                /* Check to see if input is an accepted command, throw exception otherwise */

                if (!input[0].matches("quit|show|step|seed|make|stats"))
                    throw new Exception();
                String command = input[0];
                /* Terminate the program */
                if (command.equals("quit")) {
                    run = false;
                }

                /* Display the current world with current critters */

                else if (command.equals("show")) {
                    if(input.length != 1)
                        throw new IllegalArgumentException();
                    Critter.displayWorld();
                }

                /* Show the stats of a specified Name, if a name is not inputted, throw an exception */

                else if (command.equals("stats")) {
                    String name = input[1];
                    Class<?> critClass = Class.forName(myPackage + "." + name);
                    List<Critter> critters = Critter.getInstances(name);
                    Method stats = critClass.getMethod("runStats",List.class);
                    stats.invoke(List.class, critters);
                }

                /* Call the worldTimeStep for a default 1 step, or multiple steps if passed in */

                else if (command.equals("step")) {
                    int steps;
                    if (input.length == 2)
                        steps = Integer.parseInt(input[1]);
                    else if(input.length == 1)
                        steps = 1;
                    else
                        throw new IllegalArgumentException();
                    for (; steps > 0; steps--)
                        Critter.worldTimeStep();
                }

                /* Set seed in the critter class */

                else if (command.equals("seed")) {
                    int n;
                    if (input.length < 2 || input.length > 2) {
                        throw new IllegalArgumentException();
                    } else
                        n = Integer.parseInt(input[1]);
                    Critter.setSeed(n);
                }

                /* Create more critters of a specified class. If no name is given, throw an exception.
                   Create the amount of critters specified by the input, otherwise default to 1.
                */

                else if (command.equals("make")) {
                    if (input.length == 1 || input.length > 3)
                        throw new IllegalArgumentException();
                    String name = input[1];
                    int num;
                    if (input.length == 3)
                        num = Integer.parseInt(input[2]);
                    else
                        num = 1;
                    for (; num > 0; num--)
                        Critter.makeCritter(name);
                }

            /* Catch any errors that occur during processing the input. */

            } catch (NoClassDefFoundError | InvalidCritterException | ClassNotFoundException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                String error = String.join(" ", input);
                System.out.println("error processing: " + error);
            } catch (Exception e) {
                String invalid = String.join(" ", input);
                System.out.println("invalid command: " + invalid);
            }
        }
        System.out.flush();
    }
}


