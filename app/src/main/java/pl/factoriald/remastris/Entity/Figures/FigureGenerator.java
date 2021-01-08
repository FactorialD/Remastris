package pl.factoriald.remastris.Entity.Figures;

import pl.factoriald.remastris.Entity.GameField;
import pl.factoriald.remastris.Entity.Gravity;

import static pl.factoriald.remastris.Entity.Figures.Figure2_2.figure2_2_cube;
import static pl.factoriald.remastris.Entity.Figures.Figure2_2.figure2_2_littleR;
import static pl.factoriald.remastris.Entity.Figures.Figure2_2.figure2_2_littleR_CCCW;
import static pl.factoriald.remastris.Entity.Figures.Figure2_2.figure2_2_littleR_CCW;
import static pl.factoriald.remastris.Entity.Figures.Figure2_2.figure2_2_littleR_CW;
import static pl.factoriald.remastris.Entity.Figures.Figure3_3.figure3_3_L;
import static pl.factoriald.remastris.Entity.Figures.Figure3_3.figure3_3_L_CCCW;
import static pl.factoriald.remastris.Entity.Figures.Figure3_3.figure3_3_L_CCW;
import static pl.factoriald.remastris.Entity.Figures.Figure3_3.figure3_3_L_CW;

public class FigureGenerator {

    public static Figure getRandomFigure(GameField gf){
        int r =(int) (Math.random() * 20) % 21;

        switch (r){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:return Figure2_2.figure2_2_littleR(gf, gf.getGravity().getPoint().getX(),gf.getGravity().getPoint().getY());
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14: case 15:
            case 16: return Figure3_3.figure3_3_L(gf, gf.getGravity().getPoint().getX(),gf.getGravity().getPoint().getY());
                case 17: case 18: case 19:




            default: return figure2_2_cube(gf, gf.getGravity().getPoint().getX(),gf.getGravity().getPoint().getY());

        }

    }

    public static Figure getNextFigureByEnum(ElementType type, GameField gf, int x , int y){
        switch (type) {
            case _2_2_CUBE:
                return figure2_2_cube(gf, x, y);
            case _2_2_LITTLE_R:
                return figure2_2_littleR_CW(gf, x, y);
            case _2_2_LITTLE_R_CW:
                return figure2_2_littleR_CCW(gf, x, y);
            case _2_2_LITTLE_R_CCW:
                return figure2_2_littleR_CCCW(gf, x, y);
            case _2_2_LITTLE_R_CCCW:
                return figure2_2_littleR(gf, x, y);

            case _3_3_L:
                return figure3_3_L_CW(gf, x, y);
            case _3_3_L_CW:
                return figure3_3_L_CCW(gf, x, y);
            case _3_3_L_CCW:
                return figure3_3_L_CCCW(gf, x, y);
            case _3_3_L_CCCW:
                return figure3_3_L(gf, x, y);
        }
        return null;
    }

}