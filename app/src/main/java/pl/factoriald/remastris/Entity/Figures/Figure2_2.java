package pl.factoriald.remastris.Entity.Figures;

import pl.factoriald.remastris.Entity.Cell;
import pl.factoriald.remastris.Entity.CellState;
import pl.factoriald.remastris.Entity.GameField;
import pl.factoriald.remastris.Entity.Gravity;

public class Figure2_2 extends Figure {

    //private Cell[][] cells;

    @Override
    public void rotate() {

    }

    public Figure2_2() {
        super();
    }

    public static Figure2_2 figure2_2_cube(GameField gf, int x, int y){

         Figure2_2 f = new Figure2_2();
         f.setType(ElementType._2_2_CUBE);
        if(gf.getFigure() != null){
            int oldColor = gf.getFigure().getColor();
            f.setColor(oldColor);
        }
        f.cells = new Cell[2][2];
        gf.getCells()[x][y].setFigure(f);
        gf.getCells()[x +1][y].setFigure(f);
        gf.getCells()[x][y +1].setFigure(f);
        gf.getCells()[x +1][y +1].setFigure(f);
        gf.getCells()[x][y]    .setState(CellState.BLOCK);
        gf.getCells()[x +1][y]  .setState(CellState.BLOCK);
        gf.getCells()[x][y +1]  .setState(CellState.BLOCK);
        gf.getCells()[x +1][y +1].setState(CellState.BLOCK);
        gf.getCells()[x][y]    .setColor(f.getColor());
        gf.getCells()[x +1][y]  .setColor(f.getColor());
        gf.getCells()[x][y +1]  .setColor(f.getColor());
        gf.getCells()[x +1][y +1].setColor(f.getColor());
         f.cells[0][0] = gf.getCells()[x][y];
         f.cells[1][0] = gf.getCells()[x +1][y];
         f.cells[0][1] = gf.getCells()[x][y +1];
         f.cells[1][1] = gf.getCells()[x +1][y +1];
        gf.setFigure(f);
         return f;
    }

    public static Figure2_2 figure2_2_littleR(GameField gf, int x, int y){


        Figure2_2 f = new Figure2_2();
        f.setType(ElementType._2_2_LITTLE_R);
        if(gf.getFigure() != null){
            int oldColor = gf.getFigure().getColor();
            f.setColor(oldColor);
        }
        f.cells = new Cell[2][2];
        gf.getCells()[x][y].setFigure(f);
        gf.getCells()[x +1][y].setFigure(f);
        gf.getCells()[x][y +1].setFigure(f);
        gf.getCells()[x +1][y +1].setFigure(null);
        gf.getCells()[x][y]    .setState(CellState.BLOCK);
        gf.getCells()[x +1][y]  .setState(CellState.BLOCK);
        gf.getCells()[x][y +1]  .setState(CellState.BLOCK);
        gf.getCells()[x +1][y +1].setState(CellState.EMPTY);
        gf.getCells()[x][y]    .setColor(f.getColor());
        gf.getCells()[x +1][y]  .setColor(f.getColor());
        gf.getCells()[x][y +1]  .setColor(f.getColor());
        gf.getCells()[x +1][y +1].setColor(f.getColor());
        f.cells[0][0] = gf.getCells()[x][y];
        f.cells[1][0] = gf.getCells()[x +1][y];
        f.cells[0][1] = gf.getCells()[x][y +1];
        f.cells[1][1] = gf.getCells()[x +1][y +1];
        gf.setFigure(f);
        return f;
    }

    public static Figure2_2 figure2_2_littleR_CW(GameField gf, int x, int y){

        Figure2_2 f = new Figure2_2();
        f.setType(ElementType._2_2_LITTLE_R_CW);
        if(gf.getFigure() != null){
            int oldColor = gf.getFigure().getColor();
            f.setColor(oldColor);
        }
        f.cells = new Cell[2][2];
        gf.getCells()[x][y].setFigure(f);
        gf.getCells()[x +1][y].setFigure(f);
        gf.getCells()[x][y +1].setFigure(null);
        gf.getCells()[x +1][y +1].setFigure(f);
        gf.getCells()[x][y]    .setState(CellState.BLOCK);
        gf.getCells()[x +1][y]  .setState(CellState.BLOCK);
        gf.getCells()[x][y +1]  .setState(CellState.EMPTY);
        gf.getCells()[x +1][y +1].setState(CellState.BLOCK);
        gf.getCells()[x][y]    .setColor(f.getColor());
        gf.getCells()[x +1][y]  .setColor(f.getColor());
        gf.getCells()[x][y +1]  .setColor(f.getColor());
        gf.getCells()[x +1][y +1].setColor(f.getColor());
        f.cells[0][0] = gf.getCells()[x][y];
        f.cells[1][0] = gf.getCells()[x +1][y];
        f.cells[0][1] = gf.getCells()[x][y +1];
        f.cells[1][1] = gf.getCells()[x +1][y +1];
        gf.setFigure(f);
        return f;
    }

    public static Figure2_2 figure2_2_littleR_CCW(GameField gf, int x, int y){

        Figure2_2 f = new Figure2_2();
        f.setType(ElementType._2_2_LITTLE_R_CCW);
        if(gf.getFigure() != null){
            int oldColor = gf.getFigure().getColor();
            f.setColor(oldColor);
        }
        f.cells = new Cell[2][2];
        gf.getCells()[x][y].setFigure(null);
        gf.getCells()[x +1][y].setFigure(f);
        gf.getCells()[x][y +1].setFigure(f);
        gf.getCells()[x +1][y +1].setFigure(f);
        gf.getCells()[x][y]    .setState(CellState.EMPTY);
        gf.getCells()[x +1][y]  .setState(CellState.BLOCK);
        gf.getCells()[x][y +1]  .setState(CellState.BLOCK);
        gf.getCells()[x +1][y +1].setState(CellState.BLOCK);
        gf.getCells()[x][y]    .setColor(f.getColor());
        gf.getCells()[x +1][y]  .setColor(f.getColor());
        gf.getCells()[x][y +1]  .setColor(f.getColor());
        gf.getCells()[x +1][y +1].setColor(f.getColor());
        f.cells[0][0] = gf.getCells()[x][y];
        f.cells[1][0] = gf.getCells()[x +1][y];
        f.cells[0][1] = gf.getCells()[x][y +1];
        f.cells[1][1] = gf.getCells()[x +1][y +1];
        gf.setFigure(f);
        return f;
    }

    public static Figure2_2 figure2_2_littleR_CCCW(GameField gf, int x, int y){

        Figure2_2 f = new Figure2_2();
        f.setType(ElementType._2_2_LITTLE_R_CCCW);
        if(gf.getFigure() != null){
            int oldColor = gf.getFigure().getColor();
            f.setColor(oldColor);
        }
        f.cells = new Cell[2][2];
        gf.getCells()[x][y].setFigure(f);
        gf.getCells()[x +1][y].setFigure(null);
        gf.getCells()[x][y +1].setFigure(f);
        gf.getCells()[x +1][y +1].setFigure(f);
        gf.getCells()[x][y]    .setState(CellState.BLOCK);
        gf.getCells()[x +1][y]  .setState(CellState.EMPTY);
        gf.getCells()[x][y +1]  .setState(CellState.BLOCK);
        gf.getCells()[x +1][y +1].setState(CellState.BLOCK);
        gf.getCells()[x][y]    .setColor(f.getColor());
        gf.getCells()[x +1][y]  .setColor(f.getColor());
        gf.getCells()[x][y +1]  .setColor(f.getColor());
        gf.getCells()[x +1][y +1].setColor(f.getColor());
        f.cells[0][0] = gf.getCells()[x][y];
        f.cells[1][0] = gf.getCells()[x +1][y];
        f.cells[0][1] = gf.getCells()[x][y +1];
        f.cells[1][1] = gf.getCells()[x +1][y +1];
        gf.setFigure(f);
        return f;
    }
}
