package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    private final IModel model;
    private int[][] maze;
    private Solution solution;
    int rowChar;
    int colChar;

    public MyViewModel(IModel model)
    {
        this.model = model;
        this.model.assignObserevr(this);
        this.maze=null;

    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    // when we click "generatemaze" button at view it go to ViewModel that go to Model and generate the maze
    public void getgenerateMaze(int row,int col)
    {
        this.model.generateMaze(row,col);
    }

    public void solveMaze(){
        model.solveMaze();
    }

    public IModel getModel() {
        return model;
    }

    public Maze getMaze() {
        return model.getMaze();
    }

    public int getRowChar() {
        return model.getRowChar();
    }

    public int getColChar() {
        return model.getColChar();
    }

    public Solution getSolution(){
        return model.getSolution();
    }

    public void moveCharacter(KeyEvent keyEvent)
    {

        int directory =-1;
        switch (keyEvent.getCode()) {
            case NUMPAD8 :
                directory= 8;
                break;
            case NUMPAD2 :
                directory=2;
                break;
            case NUMPAD6 :
                directory=6;
                break;
            case NUMPAD4:
                directory=4;
                break;
            case NUMPAD7 :
                directory=7;
                break;
            case NUMPAD9 :
                directory=9;
                break;
            case NUMPAD1:
                directory=1;
                break;
            case NUMPAD3 :
                directory=3;
                break;
        };

        model.updateCharacterLocation(directory);//convert the input and send it to the model

    }
}
