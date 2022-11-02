import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameOfLifeController {
    /* This class interacts with the graphic user interface and the game. */

    @FXML
    private Canvas canvas;
    
    private GameOfLifeMatrix game;
    private GraphicsContext gc;
    private final double fillColor = 0.6;
    
    private static final int matrixSize = 10;
    
    public void initialize() {
        /* Initializes the GUI and a new game. */
        
        game = new GameOfLifeMatrix(matrixSize);
        
        gc = canvas.getGraphicsContext2D();
        gc.setFill(new Color(fillColor, fillColor, fillColor, fillColor));
        
        draw();
    }
    
    @FXML
    void nextGenPressed(ActionEvent event) {
        /* On button press, draw the next generation of the matrix. */
        
        game.nextGen();
        draw();
    }
    
    private void draw() {
        /* Get all matrix values and create strokes and cells
         * on the canvas according to their life status.
         */
        
        int matrixSize = game.getMatrixSize();
        double cell_width = canvas.getWidth() / matrixSize;
        double cell_height = canvas.getHeight() / matrixSize;
        
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i=0; i<matrixSize; i++) {
            gc.strokeLine(0, cell_height*i, canvas.getWidth(), cell_height*i); // Stroke rows
            gc.strokeLine(cell_width*i, 0, cell_width*i, canvas.getHeight()); // Stroke columns
            
            /* Fill canvas cells according to matrix values. */
            for (int j=0; j<matrixSize; j++) {
                if (game.getMatrixValue(i, j) == 1) {
                    gc.fillRect(cell_width*j, cell_height*i, cell_width, cell_height);
                }
            }
            
        }
        
        gc.strokeLine(0, canvas.getHeight(), canvas.getWidth(), canvas.getHeight()); // Stroke last row
        gc.strokeLine(canvas.getWidth(), 0, canvas.getWidth(), canvas.getHeight()); // Stroke last column
    }

}
