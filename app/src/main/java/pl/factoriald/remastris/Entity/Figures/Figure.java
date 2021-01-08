package pl.factoriald.remastris.Entity.Figures;

import lombok.Getter;
import lombok.Setter;
import pl.factoriald.remastris.Entity.Cell;

import static pl.factoriald.remastris.Entity.GameField.getRandomColor;

public abstract class Figure {
    @Getter
    @Setter
    private int color = 0;
    @Getter
    Cell cells[][];
    @Getter
    @Setter
    ElementType type;

    public void setCell(int i, int j, Cell cell){
        cells[i][j] = cell;
    }

    public abstract void rotate();

    public Figure(){
        color = getRandomColor();
    }


}
