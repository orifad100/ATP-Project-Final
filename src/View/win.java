package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class win
{
    @FXML
    private ImageView imageview;


    public void initialize() {
        Image i = new Image(new File("resources/Images/HARRY.gif").toURI().toString());
        imageview.setImage(i);


    }
}
