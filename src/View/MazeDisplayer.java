package View;
import javax.swing.*;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    static int check=0;
    // player position:
    private int playerRow ;
    private int playerCol ;
    private Solution solution;
    public static int count=0;
    // wall and player images:
    public MediaPlayer mediaPlayer;
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameSolution = new SimpleStringProperty();

    StringProperty imageFileNameSolution1 = new SimpleStringProperty();


    public MazeDisplayer(){
        widthProperty().addListener(e->draw());
        heightProperty().addListener(e->draw());
    }
    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.imageFileNameSolution.set(imageFileNameSolution);
    }

    public void setImageFileNameSolution1(String imageFileNameSolution1) {
        this.imageFileNameSolution1.set(imageFileNameSolution1);
    }


    public Solution getSolution() {
        return solution;
    }

    public String getImageFileNameSolution() {
        return imageFileNameSolution.get();
    }

    public String getImageFileNameSolution1() {
        return imageFileNameSolution1.get();
    }

    public StringProperty imageFileNameSolutionProperty() {
        return imageFileNameSolution;
    }
    public StringProperty imageFileNameSolution1Property() {
        return imageFileNameSolution1;
    }


    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerRow() {
        this.playerRow = maze.getStartPosition().getRowIndex();
    }

    public void setPlayerCol() {
        this.playerCol = maze.getStartPosition().getColumnIndex();
    }
//    private void Exit() {
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Exit");
//        alert.setHeaderText("Are you sure?");
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK){
//            viewModel.stopServers();
//            System.exit(0);
//        }

    public void Music(){
        Main.mp.play();
    }
    private void ExitYouWin(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Play again or quit");
        alert.setHeaderText("Would you like to go for another or did you have enough?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            stage.close();
            mediaPlayer.stop();

        }
        Music();
    }
    private void YouWin() {
        try {
            Stage stage = new Stage();
            stage.setTitle("You Have Won!");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Win.fxml").openStream());
            Scene scene = new Scene(root, 300, 300);
            stage.setOnCloseRequest(WindowEvent -> {
                ExitYouWin(stage);
                WindowEvent.consume();});
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        if(row== maze.getGoalPosition().getRowIndex() && col==maze.getGoalPosition().getColumnIndex())
        {
            if(!(Main.mp == null))
                Main.mp.stop();
            Media media = new Media(Main.songs.get(1).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.2);
            mediaPlayer.play();
            YouWin();
        }


        draw();
    }
    public void setSolution(Solution solution) {
        this.solution = solution;
        draw();
    }



    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public void drawMaze(Maze maze)
    {
        if(solution!=null)
            solution=null;

        this.maze = maze;
        setPlayerRow();
        setPlayerCol();
        draw();
    }


    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.getGoalPosition().getRowIndex()+1;
            int cols = maze.getGoalPosition().getColumnIndex()+1;

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
            if(solution != null)
                drawSolution(graphicsContext, cellHeight, cellWidth,rows,cols);
            drawPlayer(graphicsContext, cellHeight, cellWidth);


        }
    }

    //keren need to implement this
    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth,int rows,int cols)
    {
        graphicsContext.setFill(Color.RED);

        Image Solmage = null;
        try{
            Solmage = new Image(new FileInputStream(getImageFileNameSolution()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no solution image file");
        }
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for(int k=0;k<solutionPath.size();k++)
        {
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < cols; j++)
                {
                    if(maze.GetCellCalue(i,j).toString().equals(solutionPath.get(k).toString()))
                    {
                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.drawImage(Solmage, x, y, cellWidth, cellHeight);
                    }
                }

            }
        }




    }


    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols)
    {
        graphicsContext.setFill(Color.RED);

        Image wallImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }




        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(i== maze.getGoalPosition().getRowIndex() && j==maze.getGoalPosition().getColumnIndex())
                {
                    Image solutionIm = null;
                    try{
                        solutionIm = new Image(new FileInputStream(getImageFileNameSolution1()));
                    } catch (FileNotFoundException e) {
                        System.out.println("There is no wall image file");
                    }
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    graphicsContext.drawImage(solutionIm, x, y, cellWidth, cellHeight);
                }
                else if(maze.GetCellCalue(i,j).getValue() == 1){
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }



            }
        }
    }

    public Maze getMaze() {
        return maze;
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth)
    {


        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);



        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);

    }
}
