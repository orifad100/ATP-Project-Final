package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

import java.util.Observer;

public interface IModel
{
    public void generateMaze(int rows, int cols);
    public void solveMaze();
    public Solution getSolution();
    public Maze getMaze();
    public void updateCharacterLocation(int dirction);
    public int getColChar() ;
    public int getRowChar();
    public void assignObserevr(Observer observer);
}
