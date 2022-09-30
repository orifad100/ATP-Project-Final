package View;

import Model.IModel;
import javafx.event.ActionEvent;
import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene2Con  {

    private Stage stage;

    public javafx.scene.control.Button START_NOW;
    public void Scene3(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent root = (Parent)loader.load();
        stage=(Stage)START_NOW.getScene().getWindow();
        stage.setScene(new Scene(root,1000,800));
        stage.show();


        IModel model=new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        MyViewController view = loader.getController();
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
    }
}
