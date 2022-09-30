package View;

import Model.IModel;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene1Con  {


    public javafx.scene.control.Button To_scene2;
    public javafx.scene.control.Button START_NOW;

    private Stage stage;
    private MyViewController view;


    public void Starting(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        stage=(Stage)To_scene2.getScene().getWindow();
        stage.setScene(new Scene(root,1000,800));
        stage.setResizable(true);
        stage.show();
    }

    public void Scene3(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent root = (Parent)loader.load();
        stage=(Stage)START_NOW.getScene().getWindow();
        Scene scene1 = new Scene(root,1000,800);
        stage.setScene(scene1);
        stage.show();


        IModel model=new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MyViewController view = loader.getController();
        view.setViewModel(viewModel);
        viewModel.addObserver(view);

        view.setResize(scene1);

    }

}
