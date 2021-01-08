package pl.factoriald.remastris.Entity;
import android.graphics.Color;
import android.util.Log;

import lombok.Data;
import pl.factoriald.remastris.Entity.Figures.Figure;
import pl.factoriald.remastris.Entity.Figures.FigureGenerator;

import static pl.factoriald.remastris.Entity.CellState.FIXED;

@Data
public class GameField{
    private Cell [][] cells;
    private Gravity gravity;
    int timeScore = 0;
    int blockScore = 0;
    private Figure figure;
    boolean endGame = false;

    public GameField(int x, int y) {
        this.cells = new Cell[x][y];
        for (int i = 0; i < x; i++) {
            //cells[i] = new Cell[y];
            for (int j = 0; j < y; j++) {
                cells[i][j] = new Cell(i,j, CellState.EMPTY);
            }
        }

        this.gravity = new Gravity(cells[x/2-1][0], Direction.DOWN);

        //Log.d("GAMEFIELD", "created GameField object");
    }

    public void moveTick(){
        updateFigure();

        doGravity();

        findAndDeleteFixedLines();

        if(gravityFilled()){
            endGame = true;
        }

        //logBlocksCoords();
        Log.d("MOVETICK", "Done tick.");
    }

    private boolean gravityFilled() {
        for (int i = gravity.getPoint().getX(); i < gravity.getPoint().getX()+3; i++) {
            for (int j = gravity.getPoint().getY(); j < gravity.getPoint().getY()+3; j++) {
                if(cells[i][j].getState() == FIXED){
                    return true;
                }
            }
        }
        return false;
    }

    private void doGravity() {
            if(figure == null) return;
         if (isFigureDownEmpty()) {
             Log.d("FIGDOWNEMPTY", "sFigureDownEmpty,moveFigureDown");
             moveFigureDown();


         }
         else{
             setFigureFixedState();
         }

    }


    private void setFigureFixedState() {

            for (int i = 0; i < cells.length; i++) {
                for (int j = 0 ; j < cells[0].length; j++) {
                if(cells[i][j].getFigure() != null){
                    cells[i][j].setState(FIXED);
                    cells[i][j].setFigure(null);

                }
            }this.setFigure(null);
        }
    }

    private boolean checkIsFigureMoving() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].getFigure() != null){
                    return true;
                }
            }
        }
        return false;
    }

    public void addTimeScore(){
        timeScore++;
    }

    public boolean isEmptyDown(Cell cell){
        if(cell.getY()+1 >= cells[0].length) return false;
        if(cells[cell.getX()][cell.getY()+1].getState() == CellState.EMPTY){
            return true;
        }else{
            return false;
        }
    }

    public boolean isEmptyUp(Cell cell){
        if(cell.getY()-1 < 0) return false;
        if(cells[cell.getX()][cell.getY()-1].getState() == CellState.EMPTY){
            return true;
        }else{
            return false;
        }
    }

    public boolean isEmptyLeft(Cell cell){
        if(cell.getX()-1 < 0) return false;
        if(cells[cell.getX()-1][cell.getY()].getState() == CellState.EMPTY){
            return true;
        }else{
            return false;
        }
    }

    public boolean isEmptyRight(Cell cell){
        if(cell.getX()+1 >= cells.length) return false;
        if(cells[cell.getX()+1][cell.getY()].getState() == CellState.EMPTY){
            return true;
        }else{
            return false;
        }
    }

    public int getHeight(){
        return cells[0].length;
    }

    public int getWidth(){
        return cells.length;
    }

    public void moveCellDown(Cell cell){

        int oldX = cell.getX();
        int oldY = cell.getY();
        int newX = cell.getX();
        int newY = cell.getY()+1;
        int firstColor = cell.getColor();
        int secondColor = cells[newX][newY].getColor();
        cells[newX][newY].setState(CellState.BLOCK);
        cells[newX][newY].setColor(firstColor);
        cells[newX][newY].setFigure(figure);
        cells[oldX][oldY].setState(CellState.EMPTY);
        cells[oldX][oldY].setColor(secondColor);
        cells[oldX][oldY].setFigure(null);
        Log.d("MOVE", "Moved down cell " + cell.getX() + "," + cell.getY());
    }

    public void moveFixedCellDown(Cell cell){

        int oldX = cell.getX();
        int oldY = cell.getY();
        int newX = cell.getX();
        int newY = cell.getY()+1;
        int firstColor = cell.getColor();
        int secondColor = cells[newX][newY].getColor();
        cells[newX][newY].setState(FIXED);
        cells[newX][newY].setColor(firstColor);
        cells[newX][newY].setFigure(figure);
        cells[oldX][oldY].setState(CellState.EMPTY);
        cells[oldX][oldY].setColor(secondColor);
        cells[oldX][oldY].setFigure(null);
        Log.d("MOVE", "Moved down cell " + cell.getX() + "," + cell.getY());
    }

    public void moveCellLeft(Cell cell){

        int oldX = cell.getX();
        int oldY = cell.getY();
        int newX = cell.getX()-1;
        int newY = cell.getY();
        int firstColor = cell.getColor();
        int secondColor = cells[newX][newY].getColor();
        cells[newX][newY].setState(CellState.BLOCK);
        cells[newX][newY].setColor(firstColor);
        cells[newX][newY].setFigure(figure);
        cells[oldX][oldY].setState(CellState.EMPTY);
        cells[oldX][oldY].setColor(secondColor);
        cells[oldX][oldY].setFigure(null);
        Log.d("MOVE", "Moved left cell " + cell.getX() + "," + cell.getY());
    }

    public void moveCellRight(Cell cell){

        int oldX = cell.getX();
        int oldY = cell.getY();
        int newX = cell.getX()+1;
        int newY = cell.getY();
        int firstColor = cell.getColor();
        int secondColor = cells[newX][newY].getColor();
        cells[newX][newY].setState(CellState.BLOCK);
        cells[newX][newY].setColor(firstColor);
        cells[newX][newY].setFigure(figure);
        cells[oldX][oldY].setState(CellState.EMPTY);
        cells[oldX][oldY].setColor(secondColor);
        cells[oldX][oldY].setFigure(null);
        Log.d("MOVE", "Moved right cell " + cell.getX() + "," + cell.getY());
    }

    public void moveCellTo(Cell oldCell, Cell newCell){
        int oldX = oldCell.getX();
        int oldY = oldCell.getY();
        int newX = newCell.getX()+1;
        int newY = newCell.getY();
        oldCell.setState(newCell.getState());
        oldCell.setColor(newCell.getColor());
        oldCell.setFigure(newCell.getFigure());
//        cells[oldX][oldY].setState(CellState.EMPTY);
//        cells[oldX][oldY].setColor(secondColor);
//        cells[oldX][oldY].setFigure(null);
        //Log.d("MOVE", "Moved right cell " + cell.getX() + "," + cell.getY());
    }

    public static int getRandomColor(){
        return Color.rgb((int)(Math.random()*255) % 256, (int)(Math.random()*255) % 256, (int)(Math.random()*255) % 256);
    }

    public void logBlocksCoords(){
        for (int i = 0; i < getWidth(); i++) {
            for (int j = getHeight()-1; j >= 0; j--) {//gravity dont work at last blocks
                if(cells[i][j].getState() == CellState.BLOCK){
                    Log.d("BLOCK", i + "," + j);
                }else if(cells[i][j].getState() == FIXED){
                    Log.d("FIXED", i + "," + j);
                }

            }
        }
    }

    public void updateFigure(){
        if(getFigure() == null){

            FigureGenerator.getRandomFigure(this);
            Log.d("ADD", "add new figure");
            for (int i = 0; i < getFigure().getCells().length; i++) {
                for (int j = 0; j < getFigure().getCells()[0].length; j++) {
                    cells[getFigure().getCells()[i][j].getX()][getFigure().getCells()[i][j].getY()] = getFigure().getCells()[i][j];
                }
            }
        }
    }

    public void moveFigureLeft() {
        if(figure == null) return;
        if(isFigureLeftEmpty()){
            for (int i = 0; i < figure.getCells().length; i++) {
                for (int j = figure.getCells()[0].length-1; j >= 0; j--) {
                    if (figure.getCells()[i][j].getState() == CellState.BLOCK){
                        moveCellLeft(cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()]);

                        figure.setCell(i,j,cells[figure.getCells()[i][j].getX()-1][figure.getCells()[i][j].getY()]);

                    }else{
                        if(figure.getCells()[i][j].getX()-1 >= 0){
                            figure.setCell(i,j,cells[figure.getCells()[i][j].getX()-1][figure.getCells()[i][j].getY()]);
                        }

                    }

                }
            }
        }

    }

    public void moveFigureRight() {
        if(figure == null) return;
        if(isFigureRightEmpty()){
            Log.d("MOVEFIGURERIGHT", "figure right is empty");
            for (int i = figure.getCells().length-1; i >=0; i--) {
                for (int j = 0; j < figure.getCells()[0].length ; j++) {
                    if (figure.getCells()[i][j].getState() == CellState.BLOCK){
                        Log.d("MOVEFIGURERIGHT", "cell with coords in figure" + i + "," + j + "has internal coords" + figure.getCells()[i][j].getX() + "," + figure.getCells()[i][j].getY());
                        moveCellRight(cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()]);

                        figure.setCell(i,j,cells[figure.getCells()[i][j].getX() +1][figure.getCells()[i][j].getY()]);

                    }
                    else{
                        if(figure.getCells()[i][j].getX()+1 < cells.length ) {
                            figure.setCell(i, j, cells[figure.getCells()[i][j].getX() + 1][figure.getCells()[i][j].getY()]);
                        }
                    }

                }
            }
        }
    }



    public void moveFigureDown() {
        if(figure == null) return;
        if(isFigureDownEmpty()) {
            for (int j = figure.getCells()[0].length - 1; j >= 0; j--) {
                for (int i = 0; i < figure.getCells().length; i++) {
                    if (figure.getCells()[i][j].getState() == CellState.BLOCK) {
                        Log.d("MOVEFIGUREDOWN", "cell with coords in figure" + i + "," + j + "has internal coords" + figure.getCells()[i][j].getX() + "," + figure.getCells()[i][j].getY());
                        moveCellDown(cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()]);

                        figure.setCell(i, j, cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY() + 1]);
                    } else {
                        if (figure.getCells()[i][j].getY() + 1 < cells[0].length) {
                            figure.setCell(i, j, cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY() + 1]);
                        }
                    }

                }
            }

        }
    }
    public void moveFigureForceDown() {
        if(figure == null) return;
        while(isFigureDownEmpty()){
            moveFigureDown();
        }
    }

    public void rotateFigure() {
        if(figure == null) return;

            Log.d("ADD", "add new figure");
        for (int i = 0; i < figure.getCells().length; i++) {
            for (int j = 0; j < figure.getCells()[0].length; j++) {
                if(getCells()[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()].getState() == FIXED ){
                    return;
                }
            }
        }
        Log.d("ROTATE", "Figure rotate: " + figure.getCells()[0][0].getX() + ", " + figure.getCells()[0][0].getY());
        int figureX = figure.getCells()[0][0].getX();
        int figureY = figure.getCells()[0][0].getY();
        // if figure need to be rotated, but no free place, then skip

        for (int i = figure.getCells().length, j = figure.getCells()[0].length; i > 0 || j > 0; i--, j--) {
            if(figure.getCells()[0][0].getX() + i < 0){
                if(!isFigureRightEmpty()){return;}
                figureX ++;
            }
            if(figure.getCells()[0][0].getX() + i > this.cells.length){
                if(!isFigureLeftEmpty()){return;}
                figureX --;
            }
            if(figure.getCells()[0][0].getY() + j < 0){
                if(!isFigureDownEmpty()){return;}
                figureY ++;
            }
            if(figure.getCells()[0][0].getY() + j > this.cells[0].length){
                if(!isFigureUpEmpty()){return;}
                figureY --;
            }
        }


        FigureGenerator.getNextFigureByEnum( figure.getType(),this,
               figureX,
                figureY);
        for (int i = 0; i < figure.getCells().length; i++) {
            for (int j = 0; j < figure.getCells()[0].length; j++) {

                cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()] = figure.getCells()[i][j];

//                cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()].setState(CellState.EMPTY);
//                cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()].setFigure(null);

            }
        }

    }

    public boolean isFigureDownEmpty(){
        for (int i = figure.getCells().length-1; i >=0; i--) {
            for (int j = figure.getCells()[0].length-1; j >=0 ; j--) {
                if(figure.getCells()[i][j].getState() == CellState.BLOCK){

                    if( isEmptyDown(getCells()[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()])){
                        break;
                    }
                    else{
                        return false;
                    }

                }
            }
        }
        return true;

    }

    public boolean isFigureUpEmpty(){
        for (int i = 0; i < figure.getCells().length; i++) {
            for (int j = figure.getCells()[0].length-1; j >=0 ; j--) {
                if(figure.getCells()[i][j].getState() == CellState.BLOCK){

                    if( isEmptyUp(getCells()[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()])){
                        break;
                    }
                    else{
                        return false;
                    }

                }
            }
        }
        return true;

    }

    public boolean isFigureLeftEmpty(){

        for (int i = 0; i < figure.getCells()[0].length; i++) {
            for (int j = 0; j < figure.getCells().length ; j++) {
                if(figure.getCells()[j][i].getState() == CellState.BLOCK){

                    if( isEmptyLeft(getCells()[figure.getCells()[j][i].getX()][figure.getCells()[j][i].getY()])){
                        break;
                    }
                    else{
                        return false;
                    }

                }
            }
        }
        return true;

    }

    public boolean isFigureRightEmpty(){
        Log.d("isFigureRightEmpty","go in function");

            for (int i = figure.getCells()[0].length-1; i >=0; i--) {
                for (int j = figure.getCells().length-1; j >= 0  ; j--) {
                if(figure.getCells()[j][i].getState() == CellState.BLOCK){

                    if( isEmptyRight(getCells()[figure.getCells()[j][i].getX()][figure.getCells()[j][i].getY()])){
                        Log.d("isEmptyRight","cell " + figure.getCells()[j][i].getX() + "," + figure.getCells()[j][i].getY());
                        break;
                    }
                    else{
                        Log.d("isEmptyRight","cell has block right" + figure.getCells()[j][i].getX() + "," + figure.getCells()[j][i].getY());
                        return false;
                    }


                }
                Log.d("isEmptyRight","cell has state: " + figure.getCells()[j][i].getX() + "," + figure.getCells()[j][i].getY() + " - " + figure.getCells()[j][i].getState());
            }

        }
        return true;

    }

    public void findAndDeleteFixedLines(){
        int delRowCount = 0;
        for (int i = 0; i < cells[0].length; i++) {
            boolean fixedLine = true;
            for (int j = cells.length-1; j >= 0; j--) {
                if(cells[j][i].getState() != FIXED){
                    fixedLine = false;
                }
            }
            if(fixedLine){
                for (int j = cells.length-1; j >= 0; j--) {
                    cells[j][i].setState(CellState.EMPTY);
                }
                delRowCount++;
                Log.d("FIXEDCLEAN","FixedLine " + i + " cleaned!");
                forceDownAllLinesUp(i);
            }
        }
        blockScore += delRowCount * delRowCount * cells.length;
    }

    private void forceDownAllLinesUp(int y) {
        for (int i = y-1; i >= 0; i--) {
            for (int j = cells.length -  1; j >= 0; j--) {
                if (cells[j][i].getState() == FIXED) {
                    while (isEmptyDown(cells[j][i])) {
                        moveFixedCellDown(cells[j][i]);
                    }
                }
            }
        }
    }
}
