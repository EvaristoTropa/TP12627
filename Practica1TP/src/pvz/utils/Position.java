package utils;

import pvz.logic.Game.NUM_COLS;

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
    
    public String toString() {
        return '(' + row + ", " + col + ')';
    }
    
    @Override
    public boolean equals(Object o) {
        if (o.getClass() == Position.getClass())
        ((Position) o).row.equals(this.row) && ((Position) o).col equals(this.col);
        else
        return false;
    }
    
    @Override
    public int hashCode() {
        return row * NUM_COLS + col;
    }
}