package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;

public interface IView
{
    public void setViewModel(MyViewModel viewMode);
    public void generateMaze(ActionEvent actionEvent);
    public void keyPressed(KeyEvent keyEvent);

}
