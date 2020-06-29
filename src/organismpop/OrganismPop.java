/*

Adam Botens
Assignment 4
6/29/2020

Using JavaFX for GUI. Program allows user to input data
to predict the size of a population over given days.

 */
package organismpop;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

/**
 *
 * @author Adam
 */
public class OrganismPop extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Labels that will appear on left side of GridPane
        Label initialOrg = new Label("Starting number of organisms");
        Label dailyIncrease = new Label("Percentage of increase");
        Label numberOfDays = new Label("Number of Days to predict");
        
        // Text fields for user input. Will appear on right side of GridPane
        // textformatter used to prevent non-numeric input
        TextField inputInitialOrg = new TextField();
        inputInitialOrg.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        TextField inputDailyIncrease = new TextField();
        inputDailyIncrease.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        TextField inputNumberDays = new TextField();
        inputNumberDays.setTextFormatter(new TextFormatter<>(new DoubleStringConverter()));
        
        // List view for displaying results
        ListView list = new ListView();
        
        //Submit button
        Button btn1 = new Button();
        btn1.setText("Submit");
        
        //Button on click will check for input discrepancies
        //And calculate the increase for each day using a For loop
        btn1.setOnAction((ActionEvent event) -> {
            //ensure text fields are not empty before parsing to double
            if (inputInitialOrg.getText().isEmpty()
                    || inputDailyIncrease.getText().isEmpty()
                    || inputNumberDays.getText().isEmpty()) {
                list.getItems().add("Error: One or more fields are empty");
            } else {
                double initial = Double.parseDouble(inputInitialOrg.getText());
                double multiple = Double.parseDouble(inputDailyIncrease.getText()) / 100;
                double days = Double.parseDouble(inputNumberDays.getText());
                //check for bad input
                if (initial < 2) {
                    list.getItems().add("Error: Number of Organisms must be 2 or more");
                }
                else if (multiple <= 0) {
                    list.getItems().add("Error: Percentage must be greater than 0");
                }
                else if(days < 1)
                {
                    list.getItems().add("Error: Number of days must be greater than 0");
                }
                //calculate and add to list view
                else
                {
                    double sum = initial;
                    list.getItems().add("Day 1:          " + sum);
                    //loop to display various days
                    for(int i = 0; i < days - 1; i++)
                    {
                        sum += multiple * sum;
                        list.getItems().add("Day " + (i+2) + ":          " + sum);
                    }
                }
            }
        });
        
        //button that clears all inputs and results
        Button btn2 = new Button();
        btn2.setText("Clear");
        btn2.setOnAction((ActionEvent event) -> {
            inputInitialOrg.clear();
            inputDailyIncrease.clear();
            inputNumberDays.clear();
            list.getItems().clear();
        });
        
        //hbox that holds the buttons
        HBox hbox = new HBox(btn1, btn2);
        hbox.setSpacing(50);
        
        //gridpane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(15, 15, 15, 15));
        grid.add(initialOrg, 0, 0);
        grid.add(inputInitialOrg, 1, 0);
        grid.add(dailyIncrease, 0, 1);
        grid.add(inputDailyIncrease, 1, 1);
        grid.add(numberOfDays, 0, 2);
        grid.add(inputNumberDays, 1, 2);
        grid.add(hbox, 1, 3);
        grid.add(list, 0, 4, 2, 2);

        Scene scene = new Scene(grid, 350, 500);

        primaryStage.setTitle("Organism Population Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
