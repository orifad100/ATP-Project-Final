package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    private File directory = new File("resources/Music");
    private File[] files = directory.listFiles();
    public static ArrayList<File> songs = new ArrayList<File>();
    public static MediaPlayer mp;

    private Group group;
    public MyViewController view;

    Scene scene1,scene2,scene3;

    @Override
    public void start(Stage primaryStage) throws Exception{


        music();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Opening.fxml"));
        primaryStage.setTitle("Welcome!");
        scene1= new Scene(fxmlLoader.load(), 1000, 800);
        primaryStage.setScene(scene1);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public void music()
    {
        if(files!=null)
        {
            for(File file:files)
                songs.add(file);
        }
        Media media = new Media(songs.get(0).toURI().toString());
        mp = new MediaPlayer(media);
        mp.setCycleCount(MediaPlayer.INDEFINITE);
        mp.play();

    }





        public static void main(String[] args) {
        launch(args);
    }
}
