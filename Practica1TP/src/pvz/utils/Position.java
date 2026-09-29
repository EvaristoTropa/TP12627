package pvz.utils;

import pvz.logic.Game;

public class Position {
    private int row, col;
    
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    public int row() {
        return row;
    }
    
    public int column() {
        return col;
    }
    
    public boolean isHorizontallyAligned(Position p) {
        return row == p.row;
    }
    
    public boolean isVerticallyAligned(Position p) {
        return col == p.col;
    }
    
    @Override
    public String toString() {
        return '(' + row + ", " + col + ')';
    }
    
    @Override
    public boolean equals(Object o) {
        if (o.getClass() == Position.class)
        return ((Position) o).row == this.row && ((Position) o).col == this.col;
        else
        return false;
    }
    
    @Override
    public int hashCode() {
        return row * Game.NUM_COLS + col;
    }
}