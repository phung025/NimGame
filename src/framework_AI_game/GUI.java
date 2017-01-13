package framework_AI_game;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * A class that creates GUI components for solving search problems.
 *
 * @author Nam Phung
 */
public class GUI extends JComponent {

    /**
     *
     * @param problem the problem the program will display
     * @param aCanvas canvas that display the initial state
     * @param width the width of the GUI
     * @param height the height of the GUI
     */
    public GUI(Game problem, Canvas aCanvas, int width, int height) {

        // Set frame size
        setPreferredSize(new Dimension(width, height));

        //Load problem and canvas
        this.game = problem;
        this.currentStateCanvas = aCanvas;

        //Set GUI Layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Text areas
        introductionTextArea.setText(problem.getIntroduction());
        introductionTextArea.setEditable(false);
        introductionTextArea.setLineWrap(true);
        introductionTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));

        // Update game status text area
        JScrollPane scrollPane = new JScrollPane(gameStatusTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gameStatusTextArea.setEditable(false);
        gameStatusTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
        
        // Score board text area
        JScrollPane scoreScrollPane = new JScrollPane(scoreBoardTextArea);
        scoreBoardTextArea.setEditable(false);
        scoreBoardTextArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
        
        resetProblem(); // Display the initial game status text area and score board

        //Add current state and final state visualization
        final JPanel currentStatePanel = new JPanel();
        currentStatePanel.add(currentStateCanvas);
        currentStatePanel.setBorder(BorderFactory.createTitledBorder(""));
        currentStatePanel.add(scoreScrollPane);
        updateCurrentStateGraphic();

        //Add reset button & new random game button to bottom panel
        resetButton.setText("RESET");
        resetButton.addActionListener((event) -> {
            resetProblem();
        });

        newRandomButton.setText("NEW RANDOM GAME");
        newRandomButton.addActionListener((event) -> {
            newRandomGame();
        });

        //Add the wrapper panels to outer panel
        add(introductionTextArea);
        add(currentStatePanel);
        add(scrollPane);

        //Add solve panel, reset button, stats panel, etc to the bottom panel
        bottomPanel.add(resetButton);
        bottomPanel.add(newRandomButton);
        add(bottomPanel);
    }

    /**
     * Reset the current problem
     */
    public void resetProblem() {
        game.resetGame();
        updateCurrentStateGraphic();
        gameStatusTextArea.setText(""); // Clear text area
        this.updateStatusTextArea(game.getCurrentGameState().toString());
        updateScoreTextArea();
    }

    public void newRandomGame() {
        game.newRandomGame();
        updateCurrentStateGraphic();
        gameStatusTextArea.setText(""); // Clear text area
        this.updateStatusTextArea(game.getCurrentGameState().toString());
        updateScoreTextArea();
    }

    /**
     * Update the canvas object for graphical illustration
     */
    private void updateCurrentStateGraphic() {

        //Get current state of problem
        currentStateCanvas.reload();

        //Repaint the state
        currentStateCanvas.render();
    }

    /**
     * Display a dialog box with input text
     *
     * @param input
     */
    public void display(String input) {
        JOptionPane.showMessageDialog(null, input);
    }

    public void updateStatusTextArea(String line) {
        gameStatusTextArea.append("\n" + line);
    }
    
    public void updateScoreTextArea() {
        scoreBoardTextArea.setText(game.toString());
    }

    /**
     * Getter for the problem object associated with this console.
     *
     * @return this console's problem object
     */
    private Game getGame() {
        return game;
    }

    public void setCurrentStateCanvas(Canvas canvas) {
        currentStateCanvas = canvas;
    }

    public Canvas getCurrentStateCanvas() {
        return currentStateCanvas;
    }

    /**
     * Text areas
     */
    final private JTextArea introductionTextArea = new JTextArea();
    final private JTextArea gameStatusTextArea = new JTextArea();
    final private JTextArea scoreBoardTextArea = new JTextArea();

    /**
     * Bottom panel containing reset button
     */
    final private JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    /**
     * Reset problem button & new random game button
     */
    final private JButton resetButton = new JButton();
    final private JButton newRandomButton = new JButton();

    /**
     * The puzzle problem object
     */
    final private Game game;

    /**
     * Canvas object for displaying current state of problem
     */
    private Canvas currentStateCanvas = null;

}
