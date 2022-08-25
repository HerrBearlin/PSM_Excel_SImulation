
package GameOfLife;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
@Sources on code of https://www.youtube.com/watch?v=ncRA1OAbMmk
 */
public class LifeSimulationRunner {

    private static Grid grid;
    private static final JFrame window = new JFrame();
    private static boolean isRunning = true;
    private static int pause = 500;

    public LifeSimulationRunner() {
        // construct an initial grid with preset values
        grid = new Grid(20, 10, Color.WHITE, Color.GREEN);
        
        // set up main JFrame that holds various the various gui components
        window.setSize(900, 500);
        window.setLayout(new BorderLayout());

        isRunning = false;
        // set up simulation control panel
        JPanel startStopPanel = new JPanel();
        startStopPanel.setLayout(new FlowLayout());
        JLabel status = new JLabel("Paused...");
        JButton startBut = new JButton("START");
        JButton pauseBut = new JButton("PAUSE");
        JButton stepBut = new JButton("STEP");
        JButton clearGridBut = new JButton("CLEAR");
        JButton randomGridBut = new JButton("RANDOM");
        startStopPanel.add(status);
        startStopPanel.add(startBut);
        startStopPanel.add(pauseBut);
        startStopPanel.add(stepBut);
        startStopPanel.add(clearGridBut);
        startStopPanel.add(randomGridBut);
        // add control panel to JFrame
        window.add(startStopPanel, BorderLayout.SOUTH);

        // set up panel to reset grid options
        JPanel gridOptionsPanel = new JPanel();
        gridOptionsPanel.setLayout(new FlowLayout());
        JButton resetBut = new JButton("RESET");
        JTextField pauseTime = new JTextField();
        pauseTime.setColumns(5);
        pauseTime.setText(String.valueOf(pause));
        JTextField gridRows = new JTextField();
        gridRows.setColumns(2);
        gridRows.setText("20");
        JTextField gridCols = new JTextField();
        gridCols.setColumns(2);
        gridCols.setText("10");



        JPanel ruleOptionsPanel = new JPanel();
        ruleOptionsPanel.setLayout(new BoxLayout(ruleOptionsPanel, BoxLayout.Y_AXIS));
        JTextField deadRule = new JTextField();
        deadRule.setText(Cell.rule.ruleForDeadCells);
        Cell.rule.setRuleForDeadCells(deadRule.getText());

        JTextField aliveRule = new JTextField();
        aliveRule.setText(Cell.rule.ruleForAliveCells);
        Cell.rule.setRuleForAliveCells(aliveRule.getText());


        JLabel deadLabel = new JLabel();
        JLabel aliveLabel = new JLabel();
        deadLabel.setText("DeadRules");
        aliveLabel.setText("AliveRules");

        JButton updateRulesButton = new JButton("Update Rules");


        deadRule.add(deadLabel);
        aliveRule.add(aliveLabel);

        ruleOptionsPanel.add(deadLabel);
        ruleOptionsPanel.add(deadRule);

        ruleOptionsPanel.add(aliveLabel);
        ruleOptionsPanel.add(aliveRule);
        ruleOptionsPanel.add(updateRulesButton);

        window.add(ruleOptionsPanel, BorderLayout.EAST);

        HashMap<String, Color> colorMap = CreateColourMap();

        // convert keys of map into list of strings
        Set<String> colorKeys = colorMap.keySet();
        String[] colorList = colorKeys.toArray(new String[0]);

        // use list of color strings to create two color spinner list models
        SpinnerModel deadColorModel = new SpinnerListModel(Arrays.asList(colorList));
        SpinnerModel aliveColorModel = new SpinnerListModel(Arrays.asList(colorList));
        

        /* 
        create the spinners with the list models for alive cells 
        and dead cells
         */
        JSpinner deadColorSpinner = new JSpinner(deadColorModel);
        deadColorSpinner.setValue("white");
        Component deadEditor = deadColorSpinner.getEditor();
        JFormattedTextField deadTextField = ((JSpinner.DefaultEditor) deadEditor).getTextField();
        deadTextField.setColumns(6);

        JSpinner aliveColorSpinner = new JSpinner(aliveColorModel);
        aliveColorSpinner.setValue("green");
        Component aliveEditor = aliveColorSpinner.getEditor();
        JFormattedTextField aliveTextField = ((JSpinner.DefaultEditor) aliveEditor).getTextField();
        aliveTextField.setColumns(6);

        // add components to options panel
        gridOptionsPanel.add(resetBut);
        gridOptionsPanel.add(new JLabel("delay:"));
        gridOptionsPanel.add(pauseTime);
        gridOptionsPanel.add(new JLabel("rows:"));
        gridOptionsPanel.add(gridRows);
        gridOptionsPanel.add(new JLabel("columns:"));
        gridOptionsPanel.add(gridCols);
        gridOptionsPanel.add(new JLabel("dead cell color:"));
        gridOptionsPanel.add(deadColorSpinner);
        gridOptionsPanel.add(new JLabel("living cell color:"));
        gridOptionsPanel.add(aliveColorSpinner);

        // add panel to JFrame
        window.add(gridOptionsPanel, BorderLayout.NORTH);

        // add the JPanel representation of the grid to the JFrame
        window.add(grid.getGridPanel(), BorderLayout.CENTER);

        // set to visible
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.setBackground(Color.DARK_GRAY);
        /*
        start button to set isRnning boolean to true to begin running 
        generations
         */
        startBut.addActionListener(e -> {
            status.setText("RUNNING...");
            isRunning = true;
        });

        /*
        pause button to set isRnning boolean to false to stop running 
        generations
         */
        pauseBut.addActionListener(e -> {
            status.setText("PAUSED");
            isRunning = false;
        });

        /*
        button to step the simulation through 1 generation
         */
        stepBut.addActionListener(e -> {
            // pause simulation if currently running
            if (isRunning) {
                status.setText("PAUSED");
                isRunning = false;
            }
            grid.runGeneration();
        });

        /*
        button pauses simulation and then clears grid so all cells are dead
         */
        clearGridBut.addActionListener(e -> {
            status.setText("PAUSED");
            isRunning = false;
            grid.clear();
        });

        /*
        RANDOMISE ALL THE CELL VALUES
         */
        randomGridBut.addActionListener(e -> {
            status.setText("PAUSED");
            isRunning = false;
            grid.random();
        });

        /*
        button pauses the simulation and then resets the grid using the input 
        delay, rows and columns values
         */
        resetBut.addActionListener(e -> {

            isRunning = false;
            //removes the current grid from the JFrame
            window.remove(grid.getGridPanel());
            // set pause value
            pause = Integer.parseInt(pauseTime.getText());

           //RESETING GRID TO BE A NEW GRID WITH VALUES
            grid = new Grid(Integer.parseInt(gridRows.getText()),
                    Integer.parseInt(gridCols.getText()),
                    colorMap.get((String) deadColorSpinner.getValue()),
                    colorMap.get((String) aliveColorSpinner.getValue()));

            //ADD NEW GRID
            window.add(grid.getGridPanel(), BorderLayout.CENTER);
            //Visibility
            window.setVisible(true);
            status.setText("PAUSED");

        });

        /*
        *
        * BUTTON SETTING THE RULES FOR THE ACTION
        * */
        updateRulesButton.addActionListener(e -> {
            Cell.rule.setRuleForDeadCells(deadRule.getText());
            Cell.rule.setRuleForAliveCells(aliveRule.getText());
        });

    }

    private HashMap<String, Color>  CreateColourMap(){
        // create hashmap of colors for color selection spinner
        HashMap<String, Color> colorMap = new HashMap<>();
        colorMap.put("black", Color.BLACK);
        colorMap.put("blue", Color.BLUE);
        colorMap.put("cyan", Color.CYAN);
        colorMap.put("dark gray", Color.DARK_GRAY);
        colorMap.put("gray", Color.GRAY);
        colorMap.put("green", Color.GREEN);
        colorMap.put("light gray", Color.LIGHT_GRAY);
        colorMap.put("magenta", Color.MAGENTA);
        colorMap.put("orange", Color.ORANGE);
        colorMap.put("pink", Color.PINK);
        colorMap.put("red", Color.RED);
        colorMap.put("white", Color.WHITE);
        colorMap.put("yellow", Color.YELLOW);
        return colorMap;
    }

    public void startLife() {

        // while program is running
        while (true) {
            // if simulation is running update generation
            if (isRunning) {
                grid.runGeneration();
            }
            // sleep time = pausetime
            try {
                Thread.sleep(pause);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }





}
