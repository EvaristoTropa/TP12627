package pvz.utils;

/**
 * Basic 2D board position class with useful methods for other classes.
 * 
 * @author Rodrigo Ferrer López
 */

public class Position {
    private int row, col;
    
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Fetches a position's row.
     * 
     * @return its row component.
     */
    
    public int row() {
        return row;
    }
    
    /**
     * Fetches a position's column.
     * 
     * @return its column component.
     */
    
    public int column() {
        return col;
    }
    
    /**
     * Checks if two positions are in the same row.
     * 
     * @param p Position to compare
     * 
     * @return <code>true</code> if both positions' row components match.
     */
    
    public boolean isHorizontallyAligned(Position p) {
        return row == p.row;
    }
    
    /**
     * Checks if two positions are in the same column.
     * 
     * @param p Position to compare
     * 
     * @return <code>true</code> if both positions' column components match.
     */
    
    public boolean isVerticallyAligned(Position p) {
        return col == p.col;
    }
    
    /**
     * Converts a position to string format.
     * 
     * @return a string containing the position's row and column.
     */
    
    @Override
    public String toString() {
        return row + " " + col;
    }
    
    /**
     * Checks if two positions are the same.
     * 
     * @param o Object (Position) to compare
     * 
     * @return <code>true</code> if both positions' row and column components match.
     */
    
    @Override
    public boolean equals(Object o) {
        if (o.getClass() == this.getClass())
        return ((Position) o).row == this.row && ((Position) o).col == this.col;
        else
        return false;
    }
    
    /**
     * Generates a position's hashcode.
     * 
     * @return a unique hashcode for the position.
     */
    
    @Override
    public int hashCode() {
        int hash = 17;
        hash = ((hash + col) << 5) - (hash + col);
        hash = ((hash + row) << 5) - (hash + row);
        return hash;
    }
}