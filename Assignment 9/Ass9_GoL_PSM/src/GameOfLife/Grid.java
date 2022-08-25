
package GameOfLife;

import java.awt.*;
import javax.swing.*;
import java.util.Random;


/**
 * Object to represent the grid running the game of life
 * 
 * @author Sharif Shaker
 * @version 4/6/2017
 */
public class Grid {

    private final int  numRows;
    private final int numCols;
    private final JPanel grid;
    private final Cell[][] cellWindows;

    /**
     * takes the number of rows and columns of the grid as a parameter
     * @param rows number of rows in the grid
     * @param cols number of columns in the grid
     * @param deadColor color of a dead cell
     * @param aliveColor color of a live cell
     */
    public Grid(int rows, int cols, Color deadColor, Color aliveColor) {
        numRows = rows;
        numCols = cols;
        /* initializes a JPanel set up as a grid layout of the 
        specified number of rows and columns*/
        grid = new JPanel(new GridLayout(rows, cols, 1, 1));
        cellWindows = new Cell[rows][cols]; // represents a table of cells

        /*
        for each location in the grid layout create and add a cell
        then add that cell to the specified location of the cells table
        */
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // create new cell with given colors
                Cell cell = new Cell(deadColor, aliveColor);
                cell.setPreferredSize(new Dimension(10, 10));
                grid.add(cell);
                cellWindows[i][j] = cell;
            }
        }

    }

    /**
     * 
     * @return JPanel representation of the grid
     */
    public JPanel getGridPanel() {
        return grid;
    }

    /**
     *runs one generation -> checks what is dead or alive
     */
    public void runGeneration() {

        int aliveNeighbours;
        boolean[][] livingTable = new boolean[numRows][numCols];
        int top;
        int bot;
        int right;
        int left;
        /*
        for each cell, checks whether the neighbours are living, increment aliveNeighbours
        */
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                top = (j > 0 ? j - 1 : numCols - 1);
                bot = (j < numCols - 1 ? j + 1 : 0);
                right = (i < numRows - 1 ? i + 1 : 0);
                left = (i > 0 ? i - 1 : numRows - 1);
                aliveNeighbours = 0;
                aliveNeighbours = getAliveNeighbours(aliveNeighbours, top, bot, right, i, j);
                aliveNeighbours = getAliveNeighbours(aliveNeighbours, bot, top, left, i, j);

                /*
                using the number of aliveNeighbours, is every cell in the grid alive or dead?
                create a copy of the gird
                */
                livingTable[i][j] = cellWindows[i][j].isCellAlive(aliveNeighbours);

            }
        }

        /*
        each cell is set alive -> refering to the copy in living table
        */
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cellWindows[i][j].setAlive(livingTable[i][j]);
            }
        }

        grid.repaint();
    }

    private int getAliveNeighbours(int aliveNeighbours, int top, int bot, int right, int i, int j) {
        if (cellWindows[i][top].isLiving()) {
            aliveNeighbours++;
        }
        if (cellWindows[right][top].isLiving()) {
            aliveNeighbours++;
        }
        if (cellWindows[right][j].isLiving()) {
            aliveNeighbours++;
        }
        if (cellWindows[right][bot].isLiving()) {
            aliveNeighbours++;
        }
        return aliveNeighbours;
    }

    public void clear(){
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cellWindows[i][j].setAlive(false);
            }
        }

        grid.repaint();
    }

    public void random(){
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cellWindows[i][j].setAlive(random.nextBoolean());
            }
        }
        grid.repaint();
    }

}
