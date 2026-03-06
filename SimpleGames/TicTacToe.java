import java.util.*;
public class TicTacToe
{
    private char[][] grid;
    private Scanner scan;
    private Random ran;
    private boolean startingPlayer;
    private int level;

    public TicTacToe()
    {
        scan = new Scanner(System.in);
        ran = new Random();
        grid = new char[3][3];

        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col < grid[row].length; col++)
            {
                grid[row][col] = '-';
            }
        }

        startingPlayer = ran.nextBoolean();

        System.out.println("|1: Single Player||2: Two Players||Other: Exit|");
        int mode = scan.nextInt();

        switch(mode){
            case 1:
                singlePlayer();
                break;
            case 2:
                twoPlayers();
                break;
            default:
                break;
        }
    }

    public void singlePlayer()
    {
        int turns = 0;
        while (true) 
        { 
            System.out.println("|1: Level One||2: Level Two|");
            level = scan.nextInt();
            if(level == 1 || level == 2)
                break;
            else
                System.out.println("Invalid Level.");
        }
        while (true) { 
            if(startingPlayer)
            {
                if(turns == 0)
                    System.out.println("(X) Player starts.");
                displayGrid();

                while(true)
                {
                    System.out.print("Enter a row (1-3):");
                    int row = scan.nextInt()-1;
                    System.out.print("Enter a column (1-3):");
                    int col = scan.nextInt()-1;
                    try 
                    {
                        if(cellIsEmpty(row,col))
                        { 
                            grid[row][col] = 'X';
                            break;
                        }
                        else
                        {
                            System.out.println("Cell is taken.");
                        }
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("Row or Column is out of bounds.");
                    }
                }

                turns++;
                startingPlayer = false;
            }
            else if(!startingPlayer)
            {
                if(turns == 0)
                    System.out.println("(O) Computer Starts.");
                
                if(level == 1)
                    level1();

                turns++;
                startingPlayer = true;
            }

            char winner = findWinner();
            if(winner != 'N')
            {
                System.out.println("Winner is " + winner);
                displayGrid();
                break;
            }
            else if(gridIsFull())
            {
                if(winner != 'N')
                    System.out.println("Winner is " + winner);
                else
                    System.out.println("Stalemate!");
                displayGrid();
                break;
            }
        }

    }

    public char findWinner()
    {
        //should only be called once grid is full
        if(grid[0][0] == grid[0][1] && grid[0][1] == grid[0][2] && (grid[0][0] != '-'))
            return grid[0][0];
        else if(grid[1][0] == grid[1][1] && grid[1][1] == grid[1][2] && (grid[1][0] != '-'))
            return grid[1][0];
        else if(grid[2][0] == grid[2][1] && grid[2][1] == grid[2][2] && (grid[2][0] != '-'))
            return grid[2][0];
        else if(grid[0][0] == grid[1][0] && grid[1][0] == grid[2][0] && (grid[0][0] != '-'))
            return grid[0][0];
        else if(grid[0][1] == grid[1][1] && grid[1][1] == grid[2][1] && (grid[0][1] != '-'))
            return grid[0][1];
        else if(grid[0][2] == grid[1][2] && grid[1][2] == grid[2][2] && (grid[0][2] != '-'))
            return grid[0][2];
        else if(grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && (grid[0][0] != '-'))
            return grid[0][0];
        else if(grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0] && (grid[0][2] != '-'))
            return grid[0][2];

        return 'N';
    }


    public void twoPlayers()
    {
        
    }

    public void level1()
    {
        //randomly chooses an empty cell to place.
        while (true)
        { 
            int row = ran.nextInt(0,3);
            int col = ran.nextInt(0,3);
            if(cellIsEmpty(row,col))
            {
                grid[row][col] = 'O';
                break;
            }
            
        }
    }

    public void level2()
    {
        //will attempt to place marks based on nearby marks, uses strategies.
        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col < grid[row].length; col++)
            {
                
            }
        }
    }

    public void displayGrid()
    {
        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col < grid[row].length; col++)
            {
                System.out.print(grid[row][col]);
            }
            System.out.println();
        }
    }

    public boolean cellIsEmpty(int row, int col)
    {
        if(grid[row][col] == '-')
            return true;
        return false;
    }

    public boolean gridIsFull()
    {
        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col < grid[row].length; col++)
            {
                if(cellIsEmpty(row,col))
                    return false;
            }
        }
        return true;
    }

    public boolean isCenter(int row, int col)
    {
        return row == 1 && col == 1;
    }

    public boolean isCorner(int row, int col)
    {
        return ((row == 0 || row == 2) && (col == 0  || col == 2));
    }

    public boolean isEdge(int row, int col)
    {
        if(row == 0 && col == 1)
            return true;
        else if(row == 1 && (col == 0 || col == 2))
            return true;
        else if(row == 2 && col == 1)
            return true;
        return false;
    }


    public static void main(String[] args)
    {
        new TicTacToe();
    }

}