
public class GameOfLifeMatrix {
    /* Represents the main logic of the game.
     * 
     * In the first round the matrix cells are randomly filled with
     *  0 - dead
     *  1 - alive
     *  
     * In each generation:
     *   1) A dead cell with exactly 3 alive neighbors will rebirth.
     *   2) An alive cell with 2 or 3 alive neighbors will continue to be alive.
     *   3) An alive cell with 0 or 1 alive neighbors will die due to loneliness,
     *      and with 4+ alive neighbors will die due to overpopulation.
     *      
     * Note that the game matrix is of size matrixSize*matrixSize.
     */
    
    private int matrixSize;
    private byte[][] matrix;
    
    public GameOfLifeMatrix(int size) {
        /* Initializes a new game matrix and fill it.
         * 
         * Args:
         *  size: The size of the matrix will be size*size.
         */
        
        matrixSize = size;
        matrix = new byte[matrixSize][matrixSize];
        restart();
    }
    
    public int getMatrixSize() {
        /* The size of a row or a column in the matrix. */
        
        return matrixSize;
    }
    
    public byte getMatrixValue(int row, int column) {
        /* Retrieves information about a given cell. */
        
        return matrix[row][column];
    }
    
    public void restart() {
        /* Randomizes alive and dead cells for the matrix. */
        
        for (int i=0; i<matrixSize; i++) {
            for (int j=0; j<matrixSize; j++) {
                matrix[i][j] = (byte)Math.round(Math.random());
            }
        }
    }
    
    public void nextGen() {
        /* Sets the matrix of the new generation according to class rules. */
        
        int aliveNeighbors = 0;
        byte[][] nextGenMatrix = new byte[matrixSize][matrixSize];

        for (int i=0; i<matrixSize; i++) {
            for (int j=0; j<matrixSize; j++) {
                aliveNeighbors = calculateAliveNeighbors(i, j);
                nextGenMatrix[i][j] = matrix[i][j] == 1 ?
                        nextGenOfAlive(aliveNeighbors) : nextGenOfDead(aliveNeighbors);
            }
        }
        
        for (int i=0; i<matrixSize; i++) {
            for (int j=0; j<matrixSize; j++) {
                matrix[i][j] = nextGenMatrix[i][j];
            }
        }
    }
    
    private int calculateAliveNeighbors(int row, int column) {
        /* Neighbors are the 9 cells (or less for edges) next to a given cell.
         * 
         * Args:
         *  row: A given row in the matrix to look into.
         *  column: A given column in the matrix to look into.
         *  
         * Returns:
         *  Number of alive neighbors around the chosen cell.
         */
        
        int aliveNeighbors = 0;
        
        for (int i=row-1; i<=row+1; i++) {
            if (i < 0 || i >= matrixSize) {
                continue;
            }
            
            for (int j=column-1; j<=column+1; j++) {
                if (j < 0 || j >= matrixSize || (i == row && j == column)) {
                    continue;
                }
                
                if (matrix[i][j] == 1) {
                    aliveNeighbors++;
                }
            }
        }
        
        return aliveNeighbors;
    }
    
    private byte nextGenOfAlive(int aliveNeighbors) {
        /* If the cell is alive and has 2 or 3 alive neighbors it shall remains alive. */
        
        if (aliveNeighbors == 2 || aliveNeighbors == 3) {
            /* Staying alive. */
            return 1;
        }
        
        /* 0|1 results in death due to loneliness, 4+ due to overpopulation. */
        return 0;
    }
    
    private byte nextGenOfDead(int aliveNeighbors) {
        /* If the cell is dead and has exact 3 alive neighbors it shall rebirth. */
        
        if (aliveNeighbors == 3) {
            /* Birth. */
            return 1;
        }
        
        /* Staying dead. */
        return 0;
    }
}
