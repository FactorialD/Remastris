package pl.factoriald.remastris.Entity;

import android.graphics.Color;
import android.media.Image;
import lombok.Data;
import pl.factoriald.remastris.Entity.Figures.Figure;

import static pl.factoriald.remastris.Entity.GameField.getRandomColor;

@Data
public class Cell {
    private int x;
    private int y;
    private int color;
    private CellState state;
    private Figure figure;

//    public Cell(int x, int y, int color, CellState state) {
//        this.x = x;
//        this.y = y;
//        this.color = color;
//        this.state = CellState.EMPTY;
//    }

    public Cell(int x, int y, CellState state) {
        this.x = x;
        this.y = y;
        this.color = getRandomColor();
        this.state = state;
        figure = null;
    }

    public Cell(int x, int y, CellState state, Figure figure){
        this.x = x;
        this.y = y;
        this.color = figure.getColor();
        this.state = state;
        this.figure = figure;
    }

    public Cell(Cell cell,Figure figure){
        this.x = cell.x;
        this.y = cell.y;
        this.color = figure.getColor();
        this.state = cell.state;
        this.figure = cell.figure;
    }



}
