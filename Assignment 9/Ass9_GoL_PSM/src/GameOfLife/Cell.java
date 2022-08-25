package GameOfLife;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/**
 * Cell in the Game of Life
 */
public class Cell extends JPanel{

    public static Rule rule = new Rule();
    private boolean living;

    private final Color aliveColor;
    /**
     * @param deadColor color of a cell that is dead
     * @param aliveColor color of a cell that is alive
     */
    public Cell(Color deadColor, Color aliveColor){
        /*
       mouse listener -> so that cell can be turned alive/dead by the user
        */


        MouseListener listener = new MouseAdapter() {
            //state from dead/alive
            public void mousePressed(MouseEvent e){
                living = !living;
                repaint();
            }
            
           //we can slide our mouse nicely
            public void mouseEntered(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e)){
                    living = !living;
                    repaint();
                }
            }
        };

        this.addMouseListener(listener);
        // set the background of the grid to the dead cell color
        setBackground(deadColor);

        this.aliveColor = aliveColor;

        Random random = new Random();
        living = random.nextBoolean();
    }
    

    public boolean isCellAlive(int aliveNeighbours){
        if(!living)
        {
            String[] numbers = rule.getRuleForDeadCells().replaceAll(" ", "").split(",");
            for (String s: numbers)
            {
                if(aliveNeighbours == Integer.parseInt(s))
                {
                    return true;
                }
            }
        }else if(living)
        {
            String[] numbers = rule.getRuleForAliveCells().split(",");
            for (String s: numbers)
            {
                if(aliveNeighbours == Integer.parseInt(s))
                {
                    return true;
                }
            }
        }
        return false;
    }
    

    public void setAlive(boolean alive){
        living = alive;
    }
    public void setDead(boolean alive){
        living = !alive;
    }
    

    public boolean isLiving(){
        return living;
    }


    // set fill color to aliveColor painting cells
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(aliveColor);
        if (living) {
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        } else {
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
    
}
