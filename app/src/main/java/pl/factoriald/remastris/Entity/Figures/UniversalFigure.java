package pl.factoriald.remastris.Entity.Figures;


import android.util.Log;

import pl.factoriald.remastris.Entity.Cell;
import pl.factoriald.remastris.Entity.CellState;
import pl.factoriald.remastris.Entity.Direction;
import pl.factoriald.remastris.Entity.GameField;
import pl.factoriald.remastris.Entity.Setting;

import static pl.factoriald.remastris.Entity.Figures.ElementType._1_1_DEFAULT;
import static pl.factoriald.remastris.Entity.Figures.ElementType._2_2_CUBE;
import static pl.factoriald.remastris.Entity.Figures.ElementType._2_2_LITTLE_R;

import static pl.factoriald.remastris.Entity.Figures.ElementType._3_3_LITTLE_T;
import static pl.factoriald.remastris.Entity.Figures.ElementType._3_3_X;
import static pl.factoriald.remastris.Entity.Figures.ElementType._4_4_I;


public class UniversalFigure extends Figure {
    @Override
    public void rotate() {

    }

    public static UniversalFigure newFigure(GameField gf, int x, int y, Direction direction, ElementType type){

        UniversalFigure f = new UniversalFigure();
        f.setType(type);
        if(gf.getFigures().get(direction) != null){
            int oldColor = gf.getFigures().get(direction).getColor();
            f.setColor(oldColor);
        }
        int [][] figureData = getFigureDataByType(type);
        f.cells = new Cell[figureData.length][figureData[0].length];
        for (int i = 0; i < f.cells.length; i++) {
            for (int j = 0; j < f.cells[0].length; j++) {
                if(figureData[i][j] == 1){
                    gf.getCells()[x + i][y + j].setFigure(f);
                    gf.getCells()[x + i][y + j].setState(CellState.BLOCK);
                }else{
                    if(x + i < gf.getCells()[0].length){
                        gf.getCells()[x + i][y + j].setFigure(null);
                        gf.getCells()[x + i][y + j].setState(CellState.EMPTY);
                    }

                }
                gf.getCells()[x+i][y+j].setColor(f.getColor());
                f.cells[i][j] = gf.getCells()[x+i][y+j];
            }
        }
        gf.setFigureByIndex(f, direction);
        return f;
    }

    public static ElementType getRandomFigure(GameField gf, Direction direction){
        String set = gf.getSettings().get(Setting.FIGURE_SET);
        int figureValue = 21;
        if(set.equals("FIGURE_ADVANCED")){
            figureValue = 30;
        }
        Log.d("RANDOM FIGURE", "set is : " + set);
        int r =(int) (Math.random() * (figureValue-1) % figureValue);
        Log.d("RANDOM FIGURE", "random seed is: " + r);

        switch (r){
            case 0:
            case 1:
            case 2:return _4_4_I;
            case 3:
            case 4:
            case 5:return ElementType._3_3_Z;
            case 6:
            case 7:
            case 8:return ElementType._3_3_MZ;
            case 9:
            case 10:
            case 11:return ElementType._3_3_ML;
            case 12:
            case 13:
            case 14:return ElementType._3_3_L;
            case 15:
            case 16:
            case 17:return _2_2_CUBE;
            case 18:
            case 19:
            case 20:return _3_3_LITTLE_T;
            case 21:
            case 22:
            case 23:return _2_2_LITTLE_R;
            case 24:
            case 25:
            case 26:return _1_1_DEFAULT;
            case 27:
            case 28:
            case 29:return _3_3_X;

            default: {
                Log.d("RANDOM FIGURE", "default figure. seed is: " + r);
                return _1_1_DEFAULT;
            }

        }

    }

    public static Figure getNextFigureByEnum(ElementType type, GameField gf, int x , int y, Figure figure, Direction direction){
        switch (type) {

            case _2_2_CUBE:
                return newFigure(gf, x, y, direction, ElementType._2_2_CUBE);

            case _2_2_LITTLE_R:
                return newFigure(gf, x, y,direction, ElementType._2_2_LITTLE_R_CW);
            case _2_2_LITTLE_R_CW:
                return newFigure(gf, x, y,direction, ElementType._2_2_LITTLE_R_CCW);
            case _2_2_LITTLE_R_CCW:
                return newFigure(gf, x, y,direction, ElementType._2_2_LITTLE_R_CCCW);
            case _2_2_LITTLE_R_CCCW:
                return newFigure(gf, x, y, direction, ElementType._2_2_LITTLE_R);

            case _3_3_Z:
                return newFigure(gf, x, y, direction, ElementType._3_3_Z_CW);
            case _3_3_Z_CW:
                return newFigure(gf, x, y, direction, ElementType._3_3_Z);

            case _3_3_MZ:
                return newFigure(gf, x, y, direction,ElementType._3_3_MZ_CW);
            case _3_3_MZ_CW:
                return newFigure(gf, x, y, direction, ElementType._3_3_MZ);

            case _3_3_L:
                return newFigure(gf, x, y, direction, ElementType._3_3_L_CW);
            case _3_3_L_CW:
                return newFigure(gf, x, y, direction, ElementType._3_3_L_CCW);
            case _3_3_L_CCW:
                return newFigure(gf, x, y, direction,ElementType._3_3_L_CCCW);
            case _3_3_L_CCCW:
                return newFigure(gf, x, y, direction, ElementType._3_3_L);

            case _3_3_LITTLE_T:
                return newFigure(gf, x, y, direction, ElementType._3_3_LITTLE_T_CW);
            case _3_3_LITTLE_T_CW:
                return newFigure(gf, x, y, direction, ElementType._3_3_LITTLE_T_CCW);
            case _3_3_LITTLE_T_CCW:
                return newFigure(gf, x, y, direction,ElementType._3_3_LITTLE_T_CCCW);
            case _3_3_LITTLE_T_CCCW:
                return newFigure(gf, x, y, direction, ElementType._3_3_LITTLE_T);

            case _3_3_ML:
                return newFigure(gf, x, y, direction, ElementType._3_3_ML_CW);
            case _3_3_ML_CW:
                return newFigure(gf, x, y, direction, ElementType._3_3_ML_CCW);
            case _3_3_ML_CCW:
                return newFigure(gf, x, y, direction,ElementType._3_3_ML_CCCW);
            case _3_3_ML_CCCW:
                return newFigure(gf, x, y, direction, ElementType._3_3_ML);

            case _4_4_I:
                return newFigure(gf, x, y, direction, ElementType._4_4_I_CW);
            case _4_4_I_CW:
                return newFigure(gf, x, y, direction, ElementType._4_4_I);

            case _3_3_X:
                return newFigure(gf, x, y, direction, ElementType._3_3_X);

            default:
                return newFigure(gf, x,y,direction, ElementType._1_1_DEFAULT);
        }
    }

    public static int[][] getFigureDataByType(ElementType type){
        switch(type){
            case _2_2_CUBE:
                return new int[][]{
                    {1,1},
                    {1,1}
                };
            case _2_2_LITTLE_R:
                return new int[][]{
                        {1,1},
                        {1,0}
                };
            case _2_2_LITTLE_R_CW:
                return new int[][]{
                        {1,1},
                        {0,1}
                };
            case _2_2_LITTLE_R_CCW:
                return new int[][]{
                        {0,1},
                        {1,1}
                };
            case _2_2_LITTLE_R_CCCW:
                return new int[][]{
                        {1,0},
                        {1,1}
                };
            case _3_3_L:
                return new int[][]{
                        {0,1,0},
                        {0,1,0},
                        {0,1,1}
                };
            case _3_3_L_CW:
                return new int[][]{
                        {0,0,0},
                        {1,1,1},
                        {1,0,0}
                };
            case _3_3_L_CCW:
                return new int[][]{
                        {1,1,0},
                        {0,1,0},
                        {0,1,0}
                };
            case _3_3_L_CCCW:
                return new int[][]{
                        {0,0,1},
                        {1,1,1},
                        {0,0,0}
                };
            case _3_3_ML:
                return new int[][]{
                        {0,1,0},
                        {0,1,0},
                        {1,1,0}
                };
            case _3_3_ML_CW:
                return new int[][]{
                        {1,0,0},
                        {1,1,1},
                        {0,0,0}
                };
            case _3_3_ML_CCW:
                return new int[][]{
                        {0,1,1},
                        {0,1,0},
                        {0,1,0}
                };
            case _3_3_ML_CCCW:
                return new int[][]{
                        {0,0,0},
                        {1,1,1},
                        {0,0,1}
                };
            case _4_4_I:
                return new int[][]{
                        {0,0,1,0},
                        {0,0,1,0},
                        {0,0,1,0},
                        {0,0,1,0}
                };
            case _4_4_I_CW:
                return new int[][]{
                        {0,0,0,0},
                        {1,1,1,1},
                        {0,0,0,0},
                        {0,0,0,0}
                };
            case _3_3_Z:
                return new int[][]{
                        {1,1,0},
                        {0,1,1},
                        {0,0,0}
                };
            case _3_3_Z_CW:
                return new int[][]{
                        {0,1,0},
                        {1,1,0},
                        {1,0,0}
                };
            case _3_3_MZ:
                return new int[][]{
                        {0,1,1},
                        {1,1,0},
                        {0,0,0}
                };
            case _3_3_MZ_CW:
                return new int[][]{
                        {0,1,0},
                        {0,1,1},
                        {0,0,1}
                };
            case _3_3_LITTLE_T:
                return new int[][]{
                        {0,1,0},
                        {1,1,1},
                        {0,0,0}
                };
            case _3_3_LITTLE_T_CW:
                return new int[][]{
                        {0,1,0},
                        {0,1,1},
                        {0,1,0}
                };
            case _3_3_LITTLE_T_CCW:
                return new int[][]{
                        {0,0,0},
                        {1,1,1},
                        {0,1,0}
                };
            case _3_3_LITTLE_T_CCCW:
                return new int[][]{
                        {0,1,0},
                        {1,1,0},
                        {0,1,0}
                };
            case _3_3_X:
                return new int[][]{
                        {0,1,0},
                        {1,1,1},
                        {0,1,0}
                };
            case _1_1_DEFAULT:
            default: return new int[][]{
                    {1}
            };
        }
    }

}
