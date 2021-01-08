package pl.factoriald.remastris.Entity.Figures;

import pl.factoriald.remastris.Entity.Cell;
import pl.factoriald.remastris.Entity.CellState;
import pl.factoriald.remastris.Entity.GameField;

public class Figure3_3 extends Figure  {
    @Override
    public void rotate() {

    }

    public static Figure3_3 figure3_3_L(GameField gf, int x, int y){

        Figure3_3 f = new Figure3_3();
        f.setType(ElementType._3_3_L);
        if(gf.getFigure() != null){
            int oldColor = gf.getFigure().getColor();
            f.setColor(oldColor);
        }
        f.cells = new Cell[3][3];
        gf.getCells()[x][y]         .setFigure(null);
        gf.getCells()[x +1][y]      .setFigure(f);
        gf.getCells()[x +2][y]      .setFigure(null);
        gf.getCells()[x][y +1]      .setFigure(null);
        gf.getCells()[x +1][y +1]   .setFigure(f);
        gf.getCells()[x + 2][y +1]  .setFigure(null);
        gf.getCells()[x][y +2]      .setFigure(f);
        gf.getCells()[x +1][y +2]   .setFigure(f);
        gf.getCells()[x + 2][y +2]  .setFigure(null);

        gf.getCells()[x][y]         .setState(CellState.EMPTY);
        gf.getCells()[x +1][y]      .setState(CellState.BLOCK);
        gf.getCells()[x +2][y]      .setState(CellState.EMPTY);
        gf.getCells()[x][y +1]      .setState(CellState.EMPTY);
        gf.getCells()[x +1][y +1]   .setState(CellState.BLOCK);
        gf.getCells()[x + 2][y +1]  .setState(CellState.EMPTY);
        gf.getCells()[x][y +2]      .setState(CellState.BLOCK);
        gf.getCells()[x +1][y +2]   .setState(CellState.BLOCK);
        gf.getCells()[x + 2][y +2]  .setState(CellState.EMPTY);

        gf.getCells()[x][y]         .setColor(f.getColor());
        gf.getCells()[x +1][y]      .setColor(f.getColor());
        gf.getCells()[x +2][y]      .setColor(f.getColor());
        gf.getCells()[x][y +1]      .setColor(f.getColor());
        gf.getCells()[x +1][y +1]   .setColor(f.getColor());
        gf.getCells()[x + 2][y +1]  .setColor(f.getColor());
        gf.getCells()[x][y +2]      .setColor(f.getColor());
        gf.getCells()[x +1][y +2]   .setColor(f.getColor());
        gf.getCells()[x + 2][y +2]  .setColor(f.getColor());


        f.cells[0][0] = gf.getCells()[x][y]       ;
        f.cells[1][0] = gf.getCells()[x +1][y]    ;
        f.cells[2][0] = gf.getCells()[x +2][y]    ;
        f.cells[0][1] = gf.getCells()[x][y +1]    ;
        f.cells[1][1] = gf.getCells()[x +1][y +1] ;
        f.cells[2][1] = gf.getCells()[x + 2][y +1];
        f.cells[0][2] = gf.getCells()[x][y +2]    ;
        f.cells[1][2] = gf.getCells()[x +1][y +2] ;
        f.cells[2][2] = gf.getCells()[x + 2][y +2];
        gf.setFigure(f);
        return f;
    }

    public static Figure3_3 figure3_3_L_CW(GameField gf, int x, int y){

        Figure3_3 f = new Figure3_3();
        f.setType(ElementType._3_3_L_CW);
        if(gf.getFigure() != null){
            int oldColor = gf.getFigure().getColor();
            f.setColor(oldColor);
        }
        f.cells = new Cell[3][3];
        gf.getCells()[x][y]         .setFigure(f);
        gf.getCells()[x +1][y]      .setFigure(null);
        gf.getCells()[x +2][y]      .setFigure(null);
        gf.getCells()[x][y +1]      .setFigure(f);
        gf.getCells()[x +1][y +1]   .setFigure(f);
        gf.getCells()[x + 2][y +1]  .setFigure(f);
        gf.getCells()[x][y +2]      .setFigure(null);
        gf.getCells()[x +1][y +2]   .setFigure(null);
        gf.getCells()[x + 2][y +2]  .setFigure(null);

        gf.getCells()[x][y]         .setState(CellState.BLOCK);
        gf.getCells()[x +1][y]      .setState(CellState.EMPTY);
        gf.getCells()[x +2][y]      .setState(CellState.EMPTY);
        gf.getCells()[x][y +1]      .setState(CellState.BLOCK);
        gf.getCells()[x +1][y +1]   .setState(CellState.BLOCK);
        gf.getCells()[x + 2][y +1]  .setState(CellState.BLOCK);
        gf.getCells()[x][y +2]      .setState(CellState.EMPTY);
        gf.getCells()[x +1][y +2]   .setState(CellState.EMPTY);
        gf.getCells()[x + 2][y +2]  .setState(CellState.EMPTY);
//        gf.getCells()[x][y]         .setState(gf.getCells()[x][y].getFigure() == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +1][y]      .setState(gf.getCells()[x +1][y]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +2][y]      .setState(gf.getCells()[x +2][y]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x][y +1]      .setState(gf.getCells()[x][y +1]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +1][y +1]   .setState(gf.getCells()[x +1][y +1]  == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x + 2][y +1]  .setState(gf.getCells()[x + 2][y +1] == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x][y +2]      .setState(gf.getCells()[x][y +2]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +1][y +2]   .setState(gf.getCells()[x +1][y +2]  == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x + 2][y +2]  .setState(gf.getCells()[x + 2][y +2] == null?CellState.EMPTY:CellState.BLOCK);

        gf.getCells()[x][y]         .setColor(f.getColor());
        gf.getCells()[x +1][y]      .setColor(f.getColor());
        gf.getCells()[x +2][y]      .setColor(f.getColor());
        gf.getCells()[x][y +1]      .setColor(f.getColor());
        gf.getCells()[x +1][y +1]   .setColor(f.getColor());
        gf.getCells()[x + 2][y +1]  .setColor(f.getColor());
        gf.getCells()[x][y +2]      .setColor(f.getColor());
        gf.getCells()[x +1][y +2]   .setColor(f.getColor());
        gf.getCells()[x + 2][y +2]  .setColor(f.getColor());

        f.cells[0][0] = gf.getCells()[x][y]       ;
        f.cells[1][0] = gf.getCells()[x +1][y]    ;
        f.cells[2][0] = gf.getCells()[x +2][y]    ;
        f.cells[0][1] = gf.getCells()[x][y +1]    ;
        f.cells[1][1] = gf.getCells()[x +1][y +1] ;
        f.cells[2][1] = gf.getCells()[x + 2][y +1];
        f.cells[0][2] = gf.getCells()[x][y +2]    ;
        f.cells[1][2] = gf.getCells()[x +1][y +2] ;
        f.cells[2][2] = gf.getCells()[x + 2][y +2];
        gf.setFigure(f);
        return f;
    }

    public static Figure3_3 figure3_3_L_CCW(GameField gf, int x, int y){

        Figure3_3 f = new Figure3_3();
        f.setType(ElementType._3_3_L_CCW);
        if(gf.getFigure() != null){
            int oldColor = gf.getFigure().getColor();
            f.setColor(oldColor);
        }
        f.cells = new Cell[3][3];
        gf.getCells()[x][y]         .setFigure(null);
        gf.getCells()[x +1][y]      .setFigure(f);
        gf.getCells()[x +2][y]      .setFigure(f);
        gf.getCells()[x][y +1]      .setFigure(null);
        gf.getCells()[x +1][y +1]   .setFigure(f);
        gf.getCells()[x + 2][y +1]  .setFigure(null);
        gf.getCells()[x][y +2]      .setFigure(null);
        gf.getCells()[x +1][y +2]   .setFigure(f);
        gf.getCells()[x + 2][y +2]  .setFigure(null);

        gf.getCells()[x][y]         .setState(CellState.EMPTY);
        gf.getCells()[x +1][y]      .setState(CellState.BLOCK);
        gf.getCells()[x +2][y]      .setState(CellState.BLOCK);
        gf.getCells()[x][y +1]      .setState(CellState.EMPTY);
        gf.getCells()[x +1][y +1]   .setState(CellState.BLOCK);
        gf.getCells()[x + 2][y +1]  .setState(CellState.EMPTY);
        gf.getCells()[x][y +2]      .setState(CellState.EMPTY);
        gf.getCells()[x +1][y +2]   .setState(CellState.BLOCK);
        gf.getCells()[x + 2][y +2]  .setState(CellState.EMPTY);

//        gf.getCells()[x][y]         .setState(gf.getCells()[x][y].getFigure() == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +1][y]      .setState(gf.getCells()[x +1][y]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +2][y]      .setState(gf.getCells()[x +2][y]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x][y +1]      .setState(gf.getCells()[x][y +1]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +1][y +1]   .setState(gf.getCells()[x +1][y +1]  == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x + 2][y +1]  .setState(gf.getCells()[x + 2][y +1] == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x][y +2]      .setState(gf.getCells()[x][y +2]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +1][y +2]   .setState(gf.getCells()[x +1][y +2]  == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x + 2][y +2]  .setState(gf.getCells()[x + 2][y +2] == null?CellState.EMPTY:CellState.BLOCK);

        gf.getCells()[x][y]         .setColor(f.getColor());
        gf.getCells()[x +1][y]      .setColor(f.getColor());
        gf.getCells()[x +2][y]      .setColor(f.getColor());
        gf.getCells()[x][y +1]      .setColor(f.getColor());
        gf.getCells()[x +1][y +1]   .setColor(f.getColor());
        gf.getCells()[x + 2][y +1]  .setColor(f.getColor());
        gf.getCells()[x][y +2]      .setColor(f.getColor());
        gf.getCells()[x +1][y +2]   .setColor(f.getColor());
        gf.getCells()[x + 2][y +2]  .setColor(f.getColor());

        f.cells[0][0] = gf.getCells()[x][y]       ;
        f.cells[1][0] = gf.getCells()[x +1][y]    ;
        f.cells[2][0] = gf.getCells()[x +2][y]    ;
        f.cells[0][1] = gf.getCells()[x][y +1]    ;
        f.cells[1][1] = gf.getCells()[x +1][y +1] ;
        f.cells[2][1] = gf.getCells()[x + 2][y +1];
        f.cells[0][2] = gf.getCells()[x][y +2]    ;
        f.cells[1][2] = gf.getCells()[x +1][y +2] ;
        f.cells[2][2] = gf.getCells()[x + 2][y +2];
        gf.setFigure(f);
        return f;
    }

    public static Figure3_3 figure3_3_L_CCCW(GameField gf, int x, int y){

        Figure3_3 f = new Figure3_3();
        f.setType(ElementType._3_3_L_CCCW);
        if(gf.getFigure() != null){
            int oldColor = gf.getFigure().getColor();
            f.setColor(oldColor);
        }
        f.cells = new Cell[3][3];
        gf.getCells()[x][y]         .setFigure(null);
        gf.getCells()[x +1][y]      .setFigure(null);
        gf.getCells()[x +2][y]      .setFigure(null);
        gf.getCells()[x][y +1]      .setFigure(f);
        gf.getCells()[x +1][y +1]   .setFigure(f);
        gf.getCells()[x + 2][y +1]  .setFigure(f);
        gf.getCells()[x][y +2]      .setFigure(null);
        gf.getCells()[x +1][y +2]   .setFigure(null);
        gf.getCells()[x + 2][y +2]  .setFigure(f);

        gf.getCells()[x][y]         .setState(CellState.EMPTY);
        gf.getCells()[x +1][y]      .setState(CellState.EMPTY);
        gf.getCells()[x +2][y]      .setState(CellState.EMPTY);
        gf.getCells()[x][y +1]      .setState(CellState.BLOCK);
        gf.getCells()[x +1][y +1]   .setState(CellState.BLOCK);
        gf.getCells()[x + 2][y +1]  .setState(CellState.BLOCK);
        gf.getCells()[x][y +2]      .setState(CellState.EMPTY);
        gf.getCells()[x +1][y +2]   .setState(CellState.EMPTY);
        gf.getCells()[x + 2][y +2]  .setState(CellState.BLOCK);

//        gf.getCells()[x][y]         .setState(gf.getCells()[x][y].getFigure() == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +1][y]      .setState(gf.getCells()[x +1][y]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +2][y]      .setState(gf.getCells()[x +2][y]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x][y +1]      .setState(gf.getCells()[x][y +1]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +1][y +1]   .setState(gf.getCells()[x +1][y +1]  == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x + 2][y +1]  .setState(gf.getCells()[x + 2][y +1] == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x][y +2]      .setState(gf.getCells()[x][y +2]     == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x +1][y +2]   .setState(gf.getCells()[x +1][y +2]  == null?CellState.EMPTY:CellState.BLOCK);
//        gf.getCells()[x + 2][y +2]  .setState(gf.getCells()[x + 2][y +2] == null?CellState.EMPTY:CellState.BLOCK);

        gf.getCells()[x][y]         .setColor(f.getColor());
        gf.getCells()[x +1][y]      .setColor(f.getColor());
        gf.getCells()[x +2][y]      .setColor(f.getColor());
        gf.getCells()[x][y +1]      .setColor(f.getColor());
        gf.getCells()[x +1][y +1]   .setColor(f.getColor());
        gf.getCells()[x + 2][y +1]  .setColor(f.getColor());
        gf.getCells()[x][y +2]      .setColor(f.getColor());
        gf.getCells()[x +1][y +2]   .setColor(f.getColor());
        gf.getCells()[x + 2][y +2]  .setColor(f.getColor());

        f.cells[0][0] = gf.getCells()[x][y]       ;
        f.cells[1][0] = gf.getCells()[x +1][y]    ;
        f.cells[2][0] = gf.getCells()[x +2][y]    ;
        f.cells[0][1] = gf.getCells()[x][y +1]    ;
        f.cells[1][1] = gf.getCells()[x +1][y +1] ;
        f.cells[2][1] = gf.getCells()[x + 2][y +1];
        f.cells[0][2] = gf.getCells()[x][y +2]    ;
        f.cells[1][2] = gf.getCells()[x +1][y +2] ;
        f.cells[2][2] = gf.getCells()[x + 2][y +2];
        gf.setFigure(f);
        return f;
    }
}
