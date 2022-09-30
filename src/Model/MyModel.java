package Model;

import java.io.*;
import java.util.Observable;
import java.util.Observer;
import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import Server.Configurations;

public class MyModel extends Observable implements IModel
{
    Maze maze;
    int rowChar;
    int colChar;
    private Solution solution;
    private  Server mazeGeneratingServer;
    private   Server solveSearchProblemServer;
    private int flag =0;



    public MyModel()
    {
        this.maze = null;
        rowChar=0;
        colChar=0;
        try {
            Configurations configurations= new Configurations();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create servers
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        //Start the servers
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
        // mazeGeneratingServer.stop();
    }

    @Override
    public void updateCharacterLocation(int direction)
    {



        switch (direction)
        {
            case 8:
                if (rowChar!=0 )
                    if(maze.GetCellCalue(rowChar-1,colChar).getValue()!=1)
                        rowChar -= 1;
                break;
            case 2 :
                if(rowChar!=maze.getGoalPosition().getRowIndex())
                    if (maze.GetCellCalue(rowChar+1,colChar).getValue()!=1)
                        rowChar += 1;
                break;
            case 6 :
                if(colChar!= maze.getGoalPosition().getColumnIndex())
                    if(maze.GetCellCalue(rowChar,colChar+1).getValue()!=1)
                        colChar += 1;
                break;
            case 4 :
                if(colChar!=0)
                    if(maze.GetCellCalue(rowChar,colChar-1).getValue()!=1)
                        colChar -= 1;
                break;
            case 7 :
                if(rowChar!=0 &colChar!=0 )
                    if(maze.GetCellCalue(rowChar-1,colChar-1).getValue()!=1)
                    {
                        if(maze.GetCellCalue(rowChar-1,colChar).getValue()==0 || maze.GetCellCalue(rowChar,colChar-1).getValue()==0 )
                        {
                            rowChar -= 1;
                            colChar -= 1;
                        }

                    }

                break;
            case 9 :
                if(rowChar!=maze.getStartPosition().getRowIndex() )
                    if(colChar!=maze.getGoalPosition().getColumnIndex())
                        if(maze.GetCellCalue(rowChar-1,colChar+1).getValue()!=1)
                        {
                            if(maze.GetCellCalue(rowChar-1,colChar).getValue()==0 || maze.GetCellCalue(rowChar,colChar+1).getValue()==0 )
                            {
                                rowChar -= 1;
                                colChar += 1;
                            }


                        }
                break;

            case 1 :
                if(colChar!=0 & rowChar!=maze.getGoalPosition().getRowIndex() )
                    if(maze.GetCellCalue(rowChar+1,colChar-1).getValue()!=1)
                    {
                        if(maze.GetCellCalue(rowChar+1,colChar).getValue()==0 || maze.GetCellCalue(rowChar,colChar-1).getValue()==0 )
                        {
                            rowChar += 1;
                            colChar -= 1;
                        }

                    }
                break;

            case 3 :
                if (rowChar!=maze.getGoalPosition().getRowIndex()& colChar!=maze.getGoalPosition().getColumnIndex() )
                    if(maze.GetCellCalue(rowChar+1,colChar+1).getValue()!=1)
                    {
                        if(maze.GetCellCalue(rowChar+1,colChar).getValue()==0 || maze.GetCellCalue(rowChar,colChar+1).getValue()==0 )
                        {
                            rowChar += 1;
                            colChar += 1;
                        }


                    }


        }
        setChanged();

        notifyObservers("player moved");



    }

    public int getRowChar() {
        return rowChar;
    }

    @Override
    public void assignObserevr(Observer observer)
    {
        this.addObserver(observer);//Now Viewmodel is the observer of the Model

    }

    public int getColChar() {
        return colChar;
    }

    //View model will use this method
    public Maze getMaze() {
        return maze;
    }

    public void generateMaze(int rows, int cols) {

        CommunicateWithServer_MazeGenerating(rows, cols);
        setChanged();
        notifyObservers("maze generated");
        // mazeGeneratingServer.stop();
    }

    @Override
    public void solveMaze()
    {

        CommunicateWithServer_SolveSearchProblem();
        setChanged();
        notifyObservers("maze solved");


    }
    public void SaveSolution(Solution solution)
    {
        this.solution=solution;
    }
    public Solution getSolution() {
        return solution;
    }

    //Server Client part
    public  void CommunicateWithServer_MazeGenerating(int row,int col)
    {


        try {

            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        toServer.flush();

                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[row*col+12];
                        is.read(decompressedMaze);
                        Maze maze = new Maze(decompressedMaze);
                        SaveMaze(maze);
                    //    maze.print();
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }


    }

    public void SaveMaze(Maze maze)
    {
        this.maze=maze;
        rowChar=maze.getStartPosition().getRowIndex();
        colChar=maze.getStartPosition().getColumnIndex();
    }
    public  void CommunicateWithServer_SolveSearchProblem()
    {
        Maze maze1=this.maze;
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();

                        Maze maze = maze1;
                       // maze.print();
                        toServer.writeObject(maze);
                        toServer.flush();
                        Solution mazeSolution = (Solution)fromServer.readObject();
                        SaveSolution(mazeSolution);
                        System.out.println(String.format("Solution steps:   %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();

                        for(int i = 0; i < mazeSolutionSteps.size(); ++i) {
                            System.out.println(String.format("%s. %s", i, ((AState)mazeSolutionSteps.get(i)).toString()));
                        }
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }

    }

}