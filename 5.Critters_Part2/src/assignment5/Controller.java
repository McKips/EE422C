package assignment5;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.stage.Screen;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;



/**
 * Developed by Ismael Marquez and Kiptoo Tonui.
 * It interacts with ControllerScene.fxml which is the graphics for the controller. It was created in SceneBuilder
 * and all the actions are defined here.It sets up, implements, and handles all controls that
 * the end user will perform different actions with.
 */
public class Controller implements Initializable {

    @FXML private Button quitWorld;
    @FXML private Button makeCritter;
    @FXML private Button runStats;
    @FXML private Button stepWorld;
    @FXML private Button runWorld;
    @FXML private Button seedNumber;
    @FXML private Button speedInc1;
    @FXML private Button speedInc5;
    @FXML private Button speedInc10;
    @FXML private Button speedInc50;
    @FXML private Button speedInc100;
    @FXML private Button speedReset;

    @FXML private ChoiceBox<String> choiceBox;
    @FXML private ChoiceBox<String> statsCritter;

    @FXML private Slider numCrittersSlider;
    @FXML private Slider multiCrittersSlider;
    @FXML private Slider numSeedSlider;
    @FXML private Slider multiSeedSlider;

    @FXML private Label numCritters;
    @FXML private Label multiCritters;
    @FXML private Label animSpeed;
    @FXML private Label crittersToMake;
    @FXML private Label labelNumberSeed;

    @FXML private TextArea textArea;

    private int totalCritters;
    private int numberSeed = 0;
    private int animateSpeed = 0;

    private boolean run = false;

    private WorldAnimate worldTimer;
    private Object singleCritter;
    private Object[] allCritters;

    private Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        worldTimer = new WorldAnimate(500) {
            private int speedAnimate = 0;
            @Override
            public void handle() {
                textArea.clear();
                speedAnimate = animateSpeed;
                for(int i=0; i<speedAnimate; i++){
                    Critter.worldTimeStep();
                }
                Critter.displayWorld(Main.graphicStage);
                if(singleCritter.toString().equals("All critters")){
                    for(Object crit : allCritters)
                        Controller.displayStats(crit);
                }
                else
                    Controller.displayStats(singleCritter);
            }
        };
        CritterWorld.initialize();
        textArea.setWrapText(true);
        Main.controllerStage.setX(primaryScreenBounds.getMinX()-4.5);
        Main.controllerStage.setY(primaryScreenBounds.getMinY());
        Critter.displayWorld(Main.graphicStage);

        Console console = new Console(textArea);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        ArrayList<String> critterSubclass = new ArrayList<>(findSubClass());

        totalCritters = (int) (numCrittersSlider.getValue() * multiCrittersSlider.getValue());
        choiceBox.getItems().removeAll(choiceBox.getItems());
        choiceBox.getItems().add("Select a critter");
        statsCritter.getItems().removeAll(statsCritter.getItems());
        statsCritter.getItems().add("All critters");

        for(String crit : critterSubclass) {
            choiceBox.getItems().add(crit);
            statsCritter.getItems().add(crit);
        }

        choiceBox.setValue("Select a critter");
        crittersToMake.setText("No critter selected");

        statsCritter.setValue("All critters");
        singleCritter = statsCritter.getValue();
        allCritters = statsCritter.getItems().toArray();


        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("Select a critter")) {
                crittersToMake.setText("No critter selected");
            }
            else {
                choiceBox.getItems().remove("Select a critter");
                crittersToMake.setText(totalCritters + " " + choiceBox.getValue());
            }
        });

        numSeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            numberSeed = (int) (multiSeedSlider.getValue() * newValue.doubleValue());
            seedNumber.setText("Set seed number: " + numberSeed);
            labelNumberSeed.setText("Number: " + Integer.toString(newValue.intValue()) + " "
                    + "Multiplier: " + String.format("%.2f", multiSeedSlider.getValue()));
        });

        multiSeedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            numberSeed = (int) (multiSeedSlider.getValue() * newValue.doubleValue());
            seedNumber.setText("Set seed number: " + numberSeed);
            labelNumberSeed.setText("Number: " + (int) numSeedSlider.getValue() + " "
                    + "Multiplier: " + String.format("%.2f", multiSeedSlider.getValue()));
        });

        seedNumber.setOnAction(event -> Critter.setSeed(numberSeed));

        numCrittersSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            numCritters.setText(Integer.toString(newValue.intValue()));
            Double num = (numCrittersSlider.getValue() * multiCrittersSlider.getValue());
            totalCritters = num.intValue();
            if( !choiceBox.getValue().equals("Select a critter") )
                crittersToMake.setText(Integer.toString(totalCritters) + " " + choiceBox.getValue());
        });

        multiCrittersSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            multiCritters.setText(String.format("%.2f", newValue.doubleValue()));
            Double num = (numCrittersSlider.getValue() * multiCrittersSlider.getValue());
            totalCritters = num.intValue();
            if( !choiceBox.getValue().equals("Select a critter") ) {
                crittersToMake.setText(Integer.toString(totalCritters) + " " + choiceBox.getValue());
            }
        });

        makeCritter.setOnAction(event -> {
            for(int i=0; i< totalCritters; i++){
                if(choiceBox.getValue().equals("Select a critter"))
                    break;
                try {
                    Critter.makeCritter(choiceBox.getValue());
                } catch (InvalidCritterException e) {
                    System.out.println("Critter does not exist: " + choiceBox.getValue());
                }
            }
            Critter.displayWorld(Main.graphicStage);
        });

        speedInc1.setOnAction(event -> {
            animateSpeed += 1;
            animSpeed.setText("Animation Speed: " + animateSpeed);
        });

        speedInc5.setOnAction(event -> {
            animateSpeed += 5;
            animSpeed.setText("Animation Speed: " + animateSpeed);
        });

        speedInc10.setOnAction(event -> {
            animateSpeed += 10;
            animSpeed.setText("Animation Speed: " + animateSpeed);
        });

        speedInc50.setOnAction(event -> {
            animateSpeed += 50;
            animSpeed.setText("Animation Speed: " + animateSpeed);
        });

        speedInc100.setOnAction(event -> {
            animateSpeed += 100;
            animSpeed.setText("Animation Speed: " + animateSpeed);
        });


        speedReset.setOnAction(event -> {
            animateSpeed = 0;
            animSpeed.setText("Animation Speed: " + animateSpeed);
        });

        stepWorld.setOnAction(event -> {
            textArea.clear();
            if (animateSpeed > 0) {
                for (int i = 0; i < animateSpeed; i++) {
                    Critter.worldTimeStep();
                }
                displayStats(statsCritter.getValue());
                Critter.displayWorld(Main.graphicStage);
            }
        });

        runWorld.setOnAction(event -> {
            run = !run;
            if(animateSpeed > 0 && run){
                runWorld.setText("Stop");
                singleCritter = statsCritter.getValue();
                disableAllControls();
                worldTimer.start();
            }
            else{
                runWorld.setText("Run");
                worldTimer.stop();
                enableAllControls();
            }
        });

        runStats.setOnAction(event -> {
            textArea.clear();
            if(statsCritter.getValue().equals("All critters")){
                Object[] allCritters = statsCritter.getItems().toArray();
                for(Object crit : allCritters){
                    if(!crit.toString().equals("All critters"))
                        displayStats(crit);
                }
            }
            else{
                displayStats(statsCritter.getValue());
            }
        });

        quitWorld.setOnAction(event -> System.exit(0));
    }


    private static void displayStats(Object crit){
        String myPackage = Critter.class.getPackage().toString().split(" ")[1];
        Class<?> critClass;
        try {
            critClass = Class.forName(myPackage + "." + crit.toString());
            List<Critter> critters = Critter.getInstances(crit.toString());
            Method stats = critClass.getMethod("runStats", List.class);
            stats.invoke(List.class, critters);
        }catch(Exception ignored){
        }
    }

    private static ArrayList<String> findSubClass() {
        String assignableClass = "Critter";
        Class<?> critClass = Critter.class;
        Class<?> testCritter = Critter.TestCritter.class;
        Class<?> algae = Algae.class;
        File directory = new File("src/assignment5/");
        ArrayList<String> storeClasses = new ArrayList<>();
        String[] files = directory.list();

        String myPackage = Critter.class.getPackage().toString().split(" ")[1];

        if (directory.exists() && files != null) {

            for (String file : files) {
                if (!file.endsWith(".class") && !file.endsWith(".java") || file.split("\\.")[0].equals(assignableClass))
                    continue;
                try {
                    Class<?> subCrit = Class.forName(myPackage + "." + file.split("\\.")[0]);
                    if (testCritter.isAssignableFrom(subCrit) && !subCrit.getName().equals(algae.getName()))
                        continue;
                    if (critClass.isAssignableFrom(subCrit)) {
                        storeClasses.add(file.split("\\.")[0]);
                    }
                } catch (ClassNotFoundException ignored) {
                }
            }
        }
        else {
            storeClasses.add("No critters found.");
        }
        Collections.sort(storeClasses);
        return storeClasses;
    }

    public static class Console extends OutputStream{

        private TextArea output;

        Console(TextArea textArea){
            this.output = textArea;
        }
        @Override
        public void write(int b) throws IOException {
            output.appendText(String.valueOf((char) b));
        }
    }

    static abstract class WorldAnimate extends AnimationTimer {
        private long sleepNs = 0;
        long prevTime = 0;

        WorldAnimate(long sleepMs) {
            this.sleepNs = sleepMs * 1_000_000;
        }

        @Override
        public void handle(long now) {
            if ((now - prevTime) < sleepNs) {
                return;
            }

            prevTime = now;
            handle();
        }

        public abstract void handle();
    }

    private void disableAllControls() {
        makeCritter.setDisable(true);
        speedInc1.setDisable(true);
        speedInc5.setDisable(true);
        speedInc10.setDisable(true);
        speedInc50.setDisable(true);
        speedInc100.setDisable(true);
        speedReset.setDisable(true);
        stepWorld.setDisable(true);
        statsCritter.setDisable(true);
        runStats.setDisable(true);
        seedNumber.setDisable(true);
        choiceBox.setDisable(true);
        numCrittersSlider.setDisable(true);
        multiCrittersSlider.setDisable(true);
        multiSeedSlider.setDisable(true);
        numSeedSlider.setDisable(true);
    }

    private void enableAllControls(){
        makeCritter.setDisable(false);
        speedInc1.setDisable(false);
        speedInc5.setDisable(false);
        speedInc10.setDisable(false);
        speedInc50.setDisable(false);
        speedInc100.setDisable(false);
        speedReset.setDisable(false);
        stepWorld.setDisable(false);
        statsCritter.setDisable(false);
        runStats.setDisable(false);
        seedNumber.setDisable(false);
        choiceBox.setDisable(false);
        numCrittersSlider.setDisable(false);
        multiCrittersSlider.setDisable(false);
        multiSeedSlider.setDisable(false);
        numSeedSlider.setDisable(false);
    }


}
