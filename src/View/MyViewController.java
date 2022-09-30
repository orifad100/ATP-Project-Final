package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.stage.Window;


import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.scene.media.MediaPlayer;


public class MyViewController implements Initializable, Observer,IView
{
    public GridPane pane;
    public BorderPane Mazepane;
    private double mouseDragStartY;
    private double mouseDragStartX;
    @FXML
    VBox vbMenu;
    public volatile boolean stop;

    //public MazeGenerator generator;
    public TextField textField_mazeRows;

    int goalRow;
    int goalColumn;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public Label playerRow;
    public Label playerCol;
    private MyViewModel viewModel;
    Stage stage;
    public MediaPlayer mediaPlayer;

    private int startX;
    private int startY;
    public Pane MazeP;


    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

    FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerRow.textProperty().bind(updatePlayerRow);
        playerCol.textProperty().bind(updatePlayerCol);
        fileChooser.setInitialDirectory(new File("C:\\ProjectFiles"));


    }

    public void setViewModel(MyViewModel viewMode) {
        this.viewModel = viewMode;
        //this.viewModel.addObserver(this);
    }

    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }


    public void generateMaze(ActionEvent actionEvent)
    {
        try
        {
            int rows = Integer.valueOf(textField_mazeRows.getText());
            int cols = Integer.valueOf(textField_mazeColumns.getText());
            if(rows<=2 || cols<=2)
                return;
            viewModel.getgenerateMaze(rows, cols);
            goalRow = viewModel.getMaze().getGoalPosition().getRowIndex();
            goalColumn = viewModel.getMaze().getGoalPosition().getColumnIndex();
        }
        catch (Exception e)
        {
            System.out.println("Need to enter number bigger than 2.");
        }



    }

    public void solveMaze(ActionEvent actionEvent)
    {
        if(this.mazeDisplayer.getMaze()==null)
        {
            System.out.println("Maze has not been generated yet .");
            return;
        }
        viewModel.solveMaze();

    }

    public void openFile(ActionEvent actionEvent) {
        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("load Maze");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze txt file", "*.txt", "*.doc"),
                new FileChooser.ExtensionFilter("Maze pdf", "*.pdf"),
                new FileChooser.ExtensionFilter("Maze Image", "*jpg", "*gif"));

        try {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());//save the chosen directory

            File fileMaze1 = new File(file.getPath());
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(fileMaze1);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Maze maze =  ((Maze) ois.readObject());
                viewModel.getgenerateMaze(maze.getGoalPosition().getRowIndex(),maze.getGoalPosition().getColumnIndex());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //loading file
        } catch (Exception e) {

        }

    }

    public void keyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent);
        keyEvent.consume();

    }

    private void ExitYouWin(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alert.showAndWait();
        stage.close();
        mediaPlayer.stop();
    }


    public void setPlayerPosition(int row, int col) {
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);


    }


    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change) {
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            default -> System.out.println("Not implemented change: " + change);
        }

    }

    private void mazeSolved() {
        mazeDisplayer.setSolution(viewModel.getSolution());
    }

    private void playerMoved() {
        setPlayerPosition(viewModel.getRowChar(), viewModel.getColChar());
    }

    private void mazeGenerated() {
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    public void saveFile(ActionEvent actionEvent)
    {
        FileChooser fileChooser = new FileChooser();

        Window stage = vbMenu.getScene().getWindow();
        fileChooser.setTitle("Save Maze");
        fileChooser.setInitialFileName("MySave");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Maze file", "*.png"));

        try {
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());//save the chosen directory

            File fileMaze = new File(file.getPath());
            System.out.println(fileMaze.getAbsolutePath());
           // counter++;
            FileOutputStream fis = null;
            try {
                fis = new FileOutputStream(fileMaze);
                ObjectOutputStream ois = new ObjectOutputStream(fis);
                ois.writeObject(viewModel.getMaze());
                ois.flush();
                ois.close();
            } catch (FileNotFoundException var22) {
                var22.printStackTrace();
            } catch (IOException var23) {
                var23.printStackTrace();
            }
        } catch (Exception e) {

        }


    }

    public void NewFile(ActionEvent actionEvent) {
        generateMaze(actionEvent);
    }

    public void OpenProperties(ActionEvent actionEvent) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShowingProperties.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();


    }


    public void ExitBut(ActionEvent actionEvent) {

        stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void HelpBut(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Help.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void AboutBut(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void setResize(Scene scene) {
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            mazeDisplayer.widthProperty().bind(MazeP.widthProperty());
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            mazeDisplayer.heightProperty().bind(MazeP.heightProperty());
        });


    }
}
