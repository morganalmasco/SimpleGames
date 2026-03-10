import java.util.*;
public class TicTacToe
{
    private char[][] grid;
    private Scanner scan;
    private Random ran;
    private boolean startingPlayer;
    private int level, turns;

    public TicTacToe()
    {
        scan = new Scanner(System.in);
        ran = new Random();
        grid = new char[3][3];
        turns = 0;

        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col < grid[row].length; col++)
            {
                grid[row][col] = '-';
            }
        }
        //if startingPlayer == true: player/player1 starts
        //else if startingPlayer == false: computer/player2 starts
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
            System.out.print("\033[H\033[2J");
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
                else if(level == 2)
                    level2();

                turns++;
                startingPlayer = true;
            }

            char winner = findWinner();
            if(winner != 'N')
            {
                System.out.print("\033[H\033[2J");
                System.out.println("Winner is " + winner);
                displayGrid();
                break;
            }
            else if(gridIsFull())
            {
                System.out.print("\033[H\033[2J");
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

    //will attempt to place marks based on nearby marks, uses strategies.
    public void level2()
    {
        //if computer starts, places in the center or corner
        if(!startingPlayer && turns == 0)
        {
            boolean ranMove = ran.nextBoolean();
            if(ranMove)
                grid[1][1] = 'O';
            else
                grid[0][0] = 'O';
        }
        else if(turns == 1)
        {
            //if player does not start in center, computer places O in center.
            if(cellIsEmpty(1,1))
                grid[1][1] = 'O';

            //if player starts in center, computer places in a random corner.
            else
            {
                int ranCorner = ran.nextInt(1,5);
                switch(ranCorner)
                {
                    case 1:
                        grid[0][0] = 'O';
                        break;
                    case 2:
                        grid[0][2] = 'O';
                        break;
                    case 3:
                        grid[2][0] = 'O';
                        break;
                    default:
                        grid[2][2] = 'O';
                        break;
                }
            }
        }
        else
        {
            
            //create method where if computer has chance to win on its turn, it will \place mark there
            int[] winningMove = win();
            if(winningMove != null)
            {
                grid[winningMove[0]][winningMove[1]] = 'O';
            }
            else
            {
                int[] cellBlock = block();
                if(cellBlock != null)
                {
                    grid[cellBlock[0]][cellBlock[1]] = 'O';
                }
                //if
                else
                {
                    if(cellIsEmpty(0,0)||cellIsEmpty(0,2)||cellIsEmpty(2,0)||cellIsEmpty(2,2))
                    {
                        while (true) 
                        { 
                            int ranCorner = ran.nextInt(1,5);
                            if(cellIsEmpty(0,0) && ranCorner == 1)
                            {
                                grid[0][0] = 'O';
                                break;
                            }
                            else if(cellIsEmpty(0,2) && ranCorner == 2)
                            {
                                grid[0][2] = 'O';
                                break;
                            }
                            else if(cellIsEmpty(2,0) && ranCorner == 3)
                            {
                                grid[2][0] = 'O';
                                break;
                            }
                            else if(cellIsEmpty(2,2) && ranCorner == 4)
                            {
                                grid[2][2] = 'O';
                                break;
                            }
                        }   
                    }
                    else
                    {
                        while (true) 
                        { 
                            int ranEdge = ran.nextInt(1,5);
                            if(cellIsEmpty(0,1) && ranEdge == 1)
                            {
                                grid[0][1] = 'O';
                                break;
                            }
                            else if(cellIsEmpty(1,0) && ranEdge == 2)
                            {
                                grid[1][0] = 'O';
                                break;
                            }
                            else if(cellIsEmpty(1,2) && ranEdge == 3)
                            {
                                grid[1][2] = 'O';
                                break;
                            }
                            else if(cellIsEmpty(2,1) && ranEdge == 4)
                            {
                                grid[2][1] = 'O';
                                break;
                            }
                        }
                    }
                }
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

    public int[] win()
    {
        int[] cell = new int[2];
        cell[0] = 0;
        cell[1] = 0;
        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col <  grid[row].length; col++)
            {
                if(grid[row][col] == 'O')
                {
                    //if X exists in center, will check surrounding cells to see if player is about to win
                    if(isCenter(row,col))
                    {
                        //top left corner
                        if(grid[row-1][col-1] == 'O')
                        {
                            if(cellIsEmpty(row+1,col+1))
                            {
                                cell[0] = row+1;
                                cell[1] = col+1;
                                return cell;
                            }
                        }
                        //bottom right corner
                        if(grid[row+1][col+1] == 'O')
                        {
                            if(cellIsEmpty(row-1,col-1))
                            {
                                cell[0] = row-1;
                                cell[1] = col-1;
                                return cell;
                            }
                        }
                        //top right corner
                        if(grid[row-1][col+1] == 'O')
                        {
                            if(cellIsEmpty(row+1,col-1))
                            {
                                cell[0] = row+1;
                                cell[1] = col-1;
                                return cell;
                            }
                        }
                        //bottom left corner
                        if(grid[row+1][col-1] == 'O')
                        {
                            if(cellIsEmpty(row-1,col+1))
                            {
                                cell[0] = row-1;
                                cell[1] = col+1;
                                return cell;
                            }
                        }
                        //top edge
                        if(grid[row-1][col] == 'O')
                        {
                            if(cellIsEmpty(row+1,col))
                            {
                                cell[0] = row+1;
                                cell[1] = col;
                                return cell;
                            }
                        }
                        //bottom edge
                        if(grid[row+1][col] == 'O')
                        {
                            if(cellIsEmpty(row-1,col))
                            {
                                cell[0] = row-1;
                                cell[1] = col;
                                return cell;
                            }
                        }
                        //left edge
                        if(grid[row][col-1] == 'O')
                        {
                            if(cellIsEmpty(row,col+1))
                            {
                                cell[0] = row;
                                cell[1] = col+1;
                                return cell;
                            }
                        }
                        //right edge
                        if(grid[row][col+1] == 'O')
                        {
                            if(cellIsEmpty(row,col-1))
                            {
                                cell[0] = row;
                                cell[1] = col-1;
                                return cell;
                            }
                        }
                    }

                    if(isCorner(row,col))
                    {
                        if(row == 0)
                        {
                            //top left corner
                            if(col == 0)
                            {
                                //compares to adjacent, top edge
                                if(grid[row][col+1] == 'O')
                                {
                                    if(cellIsEmpty(row,col+2))
                                    {
                                        cell[0] = row;
                                        cell[1] = col+2;
                                        return cell;
                                    }
                                }

                                //compares to adjacent, left edge
                                if(grid[row+1][col] == 'O')
                                {
                                    if(cellIsEmpty(row+2,col))
                                    {
                                        cell[0] = row+2;
                                        cell[1] = col+2;
                                        return cell;
                                    }
                                }
                                //compares bottom left corner
                                if(grid[row+2][col] == 'O')
                                {
                                    if(cellIsEmpty(row+1,col))
                                    {
                                        cell[0] = row+1;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                                //compares top right corner
                                if(grid[row][col+2] == 'O')
                                {
                                    if(cellIsEmpty(row,col+1))
                                    {
                                        cell[0] = row;
                                        cell[1] = col+1;
                                        return cell;
                                    }
                                }
                            }
                            //top right corner
                            if(col == 2)
                            {
                                //compare to adjacent, top edge
                                if(grid[row][col-1] == 'O')
                                {
                                    if(cellIsEmpty(row,col-2))
                                    {
                                        cell[0] = row;
                                        cell[1] = col-2;
                                        return cell;
                                    }
                                }
                                //compares to adjacent, right edge
                                if(grid[row+1][col] == 'O')
                                {
                                    if(cellIsEmpty(row+2,col))
                                    {
                                        cell[0] = row+2;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                            }
                        }
                        if(row == 2)
                        {
                            //bottom left corner
                            if(col == 0)
                            {
                                //compares adjacent, left edge
                                if(grid[row-1][col] == 'O')
                                {
                                    if(cellIsEmpty(row-2,col))
                                    {
                                        cell[0] = row-2;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                                //compares adjacent, bottom edge
                                if(grid[row][col+1] == 'O')
                                {
                                    if(cellIsEmpty(row, col+2))
                                    {
                                        cell[0] = row;
                                        cell[1] = col+2;
                                        return cell;
                                    }
                                }
                                //compares top right corner
                                if(grid[row-2][col+2] == 'O')
                                {
                                    if(cellIsEmpty(1,1))
                                    {
                                        cell[0] = 1;
                                        cell[1] = 1;
                                        return cell;
                                    }
                                }
                            }
                            //bottom right corner
                            if(col == 2)
                            {
                                //compares adjacent, right edge
                                if(grid[row-1][col] == 'O')
                                {
                                    if(cellIsEmpty(row-2,col))
                                    {
                                        cell[0] = row-2;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                                //compares adjacent, bottom edge
                                if(grid[row][col-1] == 'O')
                                {
                                    if(cellIsEmpty(row,col-2))
                                    {
                                        cell[0] = row;
                                        cell[1] = col-2;
                                        return cell;
                                    }
                                }
                                //compares top left corner
                                if(grid[row-2][col-2] == 'O')
                                {
                                    if(cellIsEmpty(1,1))
                                    {
                                        cell[0] = 1;
                                        cell[1] = 1;
                                        return cell;
                                    }
                                }
                                //compares bottom left corner
                                if(grid[row][col-2] == 'O')
                                {
                                    if(cellIsEmpty(row,col-1))
                                    {
                                        cell[0] = row;
                                        cell[1] = col-1;
                                        return cell;
                                    }
                                }
                                //compares top right corner
                                if(grid[row-2][col] == 'O')
                                {
                                    if(cellIsEmpty(row-1,col))
                                    {
                                        cell[0] = row-1;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                            }
                        }
                    }

                }
                //only need to check corners after center; edges are checked with corners and center
            }
        }
        return null;
    }
    //if player is about to win, computer will \block 
    //returns array of cell[0] == row, cell[1] == col
    public int[] block()
    {
        int[] cell = new int[2];
        cell[0] = 0;
        cell[1] = 0;
        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col <  grid[row].length; col++)
            {
                if(grid[row][col] == 'X')
                {
                    //if X exists in center, will check surrounding cells to see if player is about to win
                    if(isCenter(row,col))
                    {
                        //top left corner
                        if(grid[row-1][col-1] == 'X')
                        {
                            if(cellIsEmpty(row+1,col+1))
                            {
                                cell[0] = row+1;
                                cell[1] = col+1;
                                return cell;
                            }
                        }
                        //bottom right corner
                        if(grid[row+1][col+1] == 'X')
                        {
                            if(cellIsEmpty(row-1,col-1))
                            {
                                cell[0] = row-1;
                                cell[1] = col-1;
                                return cell;
                            }
                        }
                        //top right corner
                        if(grid[row-1][col+1] == 'X')
                        {
                            if(cellIsEmpty(row+1,col-1))
                            {
                                cell[0] = row+1;
                                cell[1] = col-1;
                                return cell;
                            }
                        }
                        //bottom left corner
                        if(grid[row+1][col-1] == 'X')
                        {
                            if(cellIsEmpty(row-1,col+1))
                            {
                                cell[0] = row-1;
                                cell[1] = col+1;
                                return cell;
                            }
                        }
                        //top edge
                        if(grid[row-1][col] == 'X')
                        {
                            if(cellIsEmpty(row+1,col))
                            {
                                cell[0] = row+1;
                                cell[1] = col;
                                return cell;
                            }
                        }
                        //bottom edge
                        if(grid[row+1][col] == 'X')
                        {
                            if(cellIsEmpty(row-1,col))
                            {
                                cell[0] = row-1;
                                cell[1] = col;
                                return cell;
                            }
                        }
                        //left edge
                        if(grid[row][col-1] == 'X')
                        {
                            if(cellIsEmpty(row,col+1))
                            {
                                cell[0] = row;
                                cell[1] = col+1;
                                return cell;
                            }
                        }
                        //right edge
                        if(grid[row][col+1] == 'X')
                        {
                            if(cellIsEmpty(row,col-1))
                            {
                                cell[0] = row;
                                cell[1] = col-1;
                                return cell;
                            }
                        }
                    }

                    if(isCorner(row,col))
                    {
                        if(row == 0)
                        {
                            //top left corner
                            if(col == 0)
                            {
                                //compares to adjacent, top edge
                                if(grid[row][col+1] == 'X')
                                {
                                    if(cellIsEmpty(row,col+2))
                                    {
                                        cell[0] = row;
                                        cell[1] = col+2;
                                        return cell;
                                    }
                                }

                                //compares to adjacent, left edge
                                if(grid[row+1][col] == 'X')
                                {
                                    if(cellIsEmpty(row+2,col))
                                    {
                                        cell[0] = row+2;
                                        cell[1] = col+2;
                                        return cell;
                                    }
                                }
                                //compares bottom left corner
                                if(grid[row+2][col] == 'X')
                                {
                                    if(cellIsEmpty(row+1,col))
                                    {
                                        cell[0] = row+1;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                                //compares top right corner
                                if(grid[row][col+2] == 'X')
                                {
                                    if(cellIsEmpty(row,col+1))
                                    {
                                        cell[0] = row;
                                        cell[1] = col+1;
                                        return cell;
                                    }
                                }
                            }
                            //top right corner
                            if(col == 2)
                            {
                                //compare to adjacent, top edge
                                if(grid[row][col-1] == 'X')
                                {
                                    if(cellIsEmpty(row,col-2))
                                    {
                                        cell[0] = row;
                                        cell[1] = col-2;
                                        return cell;
                                    }
                                }
                                //compares to adjacent, right edge
                                if(grid[row+1][col] == 'X')
                                {
                                    if(cellIsEmpty(row+2,col))
                                    {
                                        cell[0] = row+2;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                            }
                        }
                        if(row == 2)
                        {
                            //bottom left corner
                            if(col == 0)
                            {
                                //compares adjacent, left edge
                                if(grid[row-1][col] == 'X')
                                {
                                    if(cellIsEmpty(row-2,col))
                                    {
                                        cell[0] = row-2;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                                //compares adjacent, bottom edge
                                if(grid[row][col+1] == 'X')
                                {
                                    if(cellIsEmpty(row, col+2))
                                    {
                                        cell[0] = row;
                                        cell[1] = col+2;
                                    }
                                }
                                //compares top right corner
                                if(grid[row-2][col+2] == 'X')
                                {
                                    if(cellIsEmpty(1,1))
                                    {
                                        cell[0] = 1;
                                        cell[1] = 1;
                                    }
                                }
                            }
                            //bottom right corner
                            if(col == 2)
                            {
                                //compares adjacent, right edge
                                if(grid[row-1][col] == 'X')
                                {
                                    if(cellIsEmpty(row-2,col))
                                    {
                                        cell[0] = row-2;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                                //compares adjacent, bottom edge
                                if(grid[row][col-1] == 'X')
                                {
                                    if(cellIsEmpty(row,col-2))
                                    {
                                        cell[0] = row;
                                        cell[1] = col-2;
                                        return cell;
                                    }
                                }
                                //compares top left corner
                                if(grid[row-2][col-2] == 'X')
                                {
                                    if(cellIsEmpty(1,1))
                                    {
                                        cell[0] = 1;
                                        cell[1] = 1;
                                        return cell;
                                    }
                                }
                                //compares bottom left corner
                                if(grid[row][col-2] == 'X')
                                {
                                    if(cellIsEmpty(row,col-1))
                                    {
                                        cell[0] = row;
                                        cell[1] = col-1;
                                        return cell;
                                    }
                                }
                                //compares top right corner
                                if(grid[row-2][col] == 'X')
                                {
                                    if(cellIsEmpty(row-1,col))
                                    {
                                        cell[0] = row-1;
                                        cell[1] = col;
                                        return cell;
                                    }
                                }
                            }
                        }
                    }

                }
                //only need to check corners after center; edges are checked with corners and center
            }
        }
        return null;
    }

    public boolean cellExists(int row, int col)
    {
        if(row > 2 || row < 0)
            return false;
        return !(col > 2 || col < 0);
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