package pl.factoriald.remastris.Entity;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import pl.factoriald.remastris.Entity.Figures.Figure;
import pl.factoriald.remastris.Entity.Figures.FigureGenerator;
import pl.factoriald.remastris.Entity.Figures.UniversalFigure;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static pl.factoriald.remastris.Entity.CellState.BLOCK;
import static pl.factoriald.remastris.Entity.CellState.FIXED;
import static pl.factoriald.remastris.Entity.Direction.DOWN;
import static pl.factoriald.remastris.Entity.Direction.LEFT;
import static pl.factoriald.remastris.Entity.Direction.RIGHT;
import static pl.factoriald.remastris.Entity.Direction.UP;

@Data
public class GameField{
    private Cell [][] cells;
    private Direction[][] gravityMap;//map for falling fixed cells

    //private ArrayList<Gravity> gravities;
    private HashMap<Direction, Gravity> gravities = new HashMap<>();
    int timeScore = 0;
    int blockScore = 0;
    //private Figure[] figures = new Figure[4];
    private HashMap<Direction, Figure> figures = new HashMap<>();

    boolean endGame = false;

    HashMap<Setting, String> settings = new HashMap<>();

    public GameField(HashMap<Setting, String> ss) {


        initializeDefaultSettings();

        for (Map.Entry<Setting,String> ssEntry: ss.entrySet()
             ) {
            settings.put(ssEntry.getKey(), ssEntry.getValue());
            Log.d("LOADSETTINGS_GF", "SETTING_GET: " + ssEntry.getKey().toString() + "=" + ssEntry.getValue());
            Log.d("LOADSETTINGS_GF", "SETTING_SET: " + ssEntry.getKey().toString() + "=" + settings.get(ssEntry.getKey()));
        }

        int x = parseInt(settings.get(Setting.GAMEFIELD_WIDTH));
        int y = parseInt(settings.get(Setting.GAMEFIELD_HEIGHT));
        boolean g1 = parseBoolean(settings.get(Setting.GRAVITY1_DOWN));
        boolean g2 = parseBoolean(settings.get(Setting.GRAVITY2_UP));
        boolean g3 = parseBoolean(settings.get(Setting.GRAVITY3_LEFT));
        boolean g4 = parseBoolean(settings.get(Setting.GRAVITY4_RIGHT));

        Log.d("GAMEFIELD", "created game field with " + x + " " + y);
        this.cells = new Cell[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cells[i][j] = new Cell(i,j, CellState.EMPTY);
            }
        }
        this.gravityMap = new Direction[x][y];

        if(g1 && !g2 && !g3 && !g4){
            gravities.put(DOWN,new Gravity(cells[x/2-1][2], DOWN));
        }else if(!g1 && g2 && !g3 && !g4){
            gravities.put(UP,new Gravity(cells[x/2-1][y-1-2], UP));
        }else if(!g1 && !g2 && g3 && !g4){
            gravities.put(LEFT,new Gravity(cells[x-1-2][y/2-1], LEFT));
        }else if(!g1 && !g2 && !g3 && g4){
            gravities.put(RIGHT,new Gravity(cells[2][y/2-1], RIGHT));
        }else{
            if(g1){
                gravities.put(DOWN,new Gravity(cells[x/2+1][y/2+2], DOWN));
            }
            if(g2){
                gravities.put(UP, new Gravity(cells[x/2-1][y/2-2] , UP));
            }
            if(g3){
                gravities.put(LEFT, new Gravity(cells[x/2-3][y/2-2], LEFT));
            }
            if(g4){
                gravities.put(RIGHT, new Gravity(cells[x/2+3][y/2-1], RIGHT));
            }
        }

        //gravities.put(DOWN,new Gravity(cells  [x/2-1][3]      , DOWN));
        //gravities.put(UP, new Gravity(cells   [x/2][y/2]      , UP));
        //gravities.put(LEFT, new Gravity(cells [x-3][y/2-1]    , Direction.LEFT));
        //gravities.put(RIGHT, new Gravity(cells[x/2+5][y/2-1]  , Direction.RIGHT));


        generateGravityMap();

        //this.gravity = new Gravity(cells[x/2-1][0], Direction.DOWN);

        //Log.d("GAMEFIELD", "created GameField object");
    }

    private void initializeDefaultSettings() {
        settings.put(Setting.GAMEFIELD_WIDTH, "10");
        settings.put(Setting.GAMEFIELD_HEIGHT, "20");
        settings.put(Setting.TICKER_DELAY, "800");
        settings.put(Setting.GRAVITY1_DOWN, "true");
        settings.put(Setting.GRAVITY2_UP, "false");
        settings.put(Setting.GRAVITY3_LEFT, "false");
        settings.put(Setting.GRAVITY4_RIGHT, "false");
        settings.put(Setting.FIGURE_STANDARD, "true");
        settings.put(Setting.FIGURE_SET_1, "false");
        settings.put(Setting.FIGURE_SET_2, "false");


    }


    public void moveTick(){

        for (Map.Entry<Direction,Gravity> gravityEntry: gravities.entrySet()
             ) {
            updateFigure(gravityEntry.getKey());
            doGravity(gravityEntry.getValue(), figures.get(gravityEntry.getKey()), gravityEntry.getKey());
            if (gravityFilled()) {
                endGame = true;
            }
            findAndDeleteAllFixedLines();//TODO make it
        }



        //logBlocksCoords();
        Log.d("MOVETICK", "Done tick.");
    }

    private boolean gravityFilled() {
//        for (int i = gravity.getPoint().getX(); i < gravity.getPoint().getX()+figure.getCells().length-1; i++) {
//            for (int j = gravity.getPoint().getY(); j < gravity.getPoint().getY()+figure.getCells()[0].length-1; j++) {
//                if((i <= getCells().length -1 && i >= 0) && (j <= getCells()[0].length -1 && j >= 0)){
//                    if(cells[i][j].getState() == FIXED){
//                        return true;
//                    }
//                }
//
//            }
//        }
        for (Map.Entry<Direction, Gravity> gravityEntry: gravities.entrySet()
             ) {
            Gravity g = gravityEntry.getValue();
            for (int i = g.getPoint().getX(); i < g.getPoint().getX()+2; i++) {
                for (int j = g.getPoint().getY(); j < g.getPoint().getY()+2; j++) {
                    if ((i >= 0 && i <= getCells().length - 1) && (j >= 0 && j <= getCells()[0].length - 1)) {
                        if (cells[i][j].getState() == FIXED) {
                            Log.d("GR_FILLED", "cell at " + i + " " + j + "has state " + cells[i][j].getState());
                            return true;
                        }
                    }
                }

            }

        }
        return false;
    }

    private void doGravity(Gravity g, Figure figure, Direction direction) {

            if(figure == null) return;
            if(g.getDirection() == DOWN){
                if (isFigureDownEmpty(figure)) {
                    moveFigureDown(figure);
                }
                else{

                    setFigureFixedState(figure, direction);
                    Log.d("DOGRAV", "Figure " + direction + " has fixed");
                }
            }else if(g.getDirection() == LEFT){
                if (isFigureLeftEmpty(figure)) {
                    moveFigureLeft(figure);
                }
                else{
                    setFigureFixedState(figure, direction);
                    Log.d("DOGRAV", "Figure " + direction + " has fixed");
                }
            }else if(g.getDirection() == RIGHT){
                if (isFigureRightEmpty(figure)) {
                    moveFigureRight(figure);
                }
                else{
                    setFigureFixedState(figure, direction);
                    Log.d("DOGRAV", "Figure " + direction + " has fixed");
                }
            }else if(g.getDirection() == UP){
                if (isFigureUpEmpty(figure)) {
                    moveFigureUp(figure);
                    Log.d("DOGRAV", "Figure " + direction + " has moved up");
                }
                else{
                    setFigureFixedState(figure, direction);
                    Log.d("DOGRAV", "Figure " + direction + " has fixed");
                }
            }


    }


    private void setFigureFixedState(Figure figure, Direction direction) {
//
//        for (int i = 0; i < cells.length; i++) {
//            for (int j = 0 ; j < cells[0].length; j++) {
//                if(cells[i][j].getFigure() != null){
//                    cells[i][j].setState(FIXED);
//                    cells[i][j].setFigure(null);
//
//                }
//            }
//                this.setFigureNull(figureIndex );
//        }

        for (int i = 0; i < figure.getCells().length; i++) {
            for (int j = 0 ; j < figure.getCells()[0].length; j++) {
                if(figure.getCells()[i][j].getState() == BLOCK){
                    figure.setCellState(i, j, FIXED);
                    figure.setCellFigure(i,j, null);
                    cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()].setState(FIXED);
                    cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()].setFigure(null);

                }
            }
            this.setFigureNull(direction );
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

    public void moveCellDown(Cell cell, Figure figure){

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
        //Log.d("MOVE", "Moved down cell " + cell.getX() + "," + cell.getY());
    }

    public void moveCellUp(Cell cell, Figure figure){

        int oldX = cell.getX();
        int oldY = cell.getY();
        int newX = cell.getX();
        int newY = cell.getY()-1;
        int firstColor = cell.getColor();
        int secondColor = cells[newX][newY].getColor();
        cells[newX][newY].setState(CellState.BLOCK);
        cells[newX][newY].setColor(firstColor);
        cells[newX][newY].setFigure(figure);
        cells[oldX][oldY].setState(CellState.EMPTY);
        cells[oldX][oldY].setColor(secondColor);
        cells[oldX][oldY].setFigure(null);
        //Log.d("MOVE", "Moved up cell " + cell.getX() + "," + cell.getY());
    }

    public void moveCellLeft(Cell cell, Figure figure){

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
        //Log.d("MOVE", "Moved left cell " + cell.getX() + "," + cell.getY());
    }

    public void moveCellRight(Cell cell, Figure figure){

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
        //Log.d("MOVE", "Moved right cell " + cell.getX() + "," + cell.getY());
    }

    public void moveFixedCellDown(Cell cell/*, Figure figure*/){
        int oldX = cell.getX();
        int oldY = cell.getY();
        int newX = cell.getX();
        int newY = cell.getY()+1;
        int firstColor = cell.getColor();
        int secondColor = cells[newX][newY].getColor();
        cells[newX][newY].setState(FIXED);
        cells[newX][newY].setColor(firstColor);
        //cells[newX][newY].setFigure(figure);
        cells[newX][newY].setFigure(null);
        cells[oldX][oldY].setState(CellState.EMPTY);
        cells[oldX][oldY].setColor(secondColor);
        cells[oldX][oldY].setFigure(null);
        Log.d("MOVE", "Moved fixed down cell " + cell.getX() + "," + cell.getY());
    }

    public void moveFixedCellUp(Cell cell){
        int oldX = cell.getX();
        int oldY = cell.getY();
        int newX = cell.getX();
        int newY = cell.getY()-1;
        int firstColor = cell.getColor();
        int secondColor = cells[newX][newY].getColor();
        cells[newX][newY].setState(FIXED);
        cells[newX][newY].setColor(firstColor);
        //cells[newX][newY].setFigure(figure);
        cells[newX][newY].setFigure(null);
        cells[oldX][oldY].setState(CellState.EMPTY);
        cells[oldX][oldY].setColor(secondColor);
        cells[oldX][oldY].setFigure(null);
        Log.d("MOVE", "Moved fixed down cell " + cell.getX() + "," + cell.getY());
    }

    public void moveFixedCellLeft(Cell cell/*, Figure figure*/){
        int oldX = cell.getX();
        int oldY = cell.getY();
        int newX = cell.getX()-1;
        int newY = cell.getY();
        int firstColor = cell.getColor();
        int secondColor = cells[newX][newY].getColor();
        cells[newX][newY].setState(FIXED);
        cells[newX][newY].setColor(firstColor);
        //cells[newX][newY].setFigure(figure);
        cells[newX][newY].setFigure(null);
        cells[oldX][oldY].setState(CellState.EMPTY);
        cells[oldX][oldY].setColor(secondColor);
        cells[oldX][oldY].setFigure(null);
        Log.d("MOVE", "Moved fixed down cell " + cell.getX() + "," + cell.getY());
    }

    public void moveFixedCellRight(Cell cell/*, Figure figure*/){
        int oldX = cell.getX();
        int oldY = cell.getY();
        int newX = cell.getX()+1;
        int newY = cell.getY();
        int firstColor = cell.getColor();
        int secondColor = cells[newX][newY].getColor();
        cells[newX][newY].setState(FIXED);
        cells[newX][newY].setColor(firstColor);
        //cells[newX][newY].setFigure(figure);
        cells[newX][newY].setFigure(null);
        cells[oldX][oldY].setState(CellState.EMPTY);
        cells[oldX][oldY].setColor(secondColor);
        cells[oldX][oldY].setFigure(null);
        Log.d("MOVE", "Moved fixed down cell " + cell.getX() + "," + cell.getY());
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

    public void updateFigure(Direction direction){

        if(figures.get(direction) == null){

            //FigureGenerator.getRandomFigure(this, direction);
            UniversalFigure.newFigure(this,
                    getGravities().get(direction).getPoint().getX(),
                    getGravities().get(direction).getPoint().getY(),
                    direction,
                    UniversalFigure.getRandomFigure(this, direction));

            Log.d("ADD", "add new figure " + direction);
            for (int i = 0; i < figures.get(direction).getCells().length; i++) {
                for (int j = 0; j < figures.get(direction).getCells()[0].length; j++) {

                    cells[figures.get(direction).getCells()[i][j].getX()][figures.get(direction).getCells()[i][j].getY()] = figures.get(direction).getCells()[i][j];
                }
            }
        }
    }

    public void moveFigureLeft(Figure figure) {
        if(figure == null) return;
        if(isFigureLeftEmpty(figure)){
            for (int i = 0; i < figure.getCells().length; i++) {
                for (int j = figure.getCells()[0].length-1; j >= 0; j--) {
                    if (figure.getCells()[i][j].getState() == CellState.BLOCK){
                        moveCellLeft(cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()], figure);

                        figure.setCell(i,j,cells[figure.getCells()[i][j].getX()-1][figure.getCells()[i][j].getY()]);

                    }else{
                        if(figure.getCells()[i][j].getX()-1 >= 0){
                            figure.setCell(i,j,cells[figure.getCells()[i][j].getX()-1][figure.getCells()[i][j].getY()]);
                        }

                    }

                }
            }
            Log.d("FMOVELEFT", "moved");
        }

    }

    public void moveFigureRight(Figure figure) {
        if(figure == null) return;
        if(isFigureRightEmpty(figure)){
            Log.d("MOVEFIGURERIGHT", "figure right is empty");
            for (int i = figure.getCells().length-1; i >=0; i--) {
                for (int j = 0; j < figure.getCells()[0].length ; j++) {
                    if (figure.getCells()[i][j].getState() == CellState.BLOCK){
                        //Log.d("MOVEFIGURERIGHT", "cell with coords in figure" + i + "," + j + "has internal coords" + figure.getCells()[i][j].getX() + "," + figure.getCells()[i][j].getY());
                        moveCellRight(cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()], figure);

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



    public void moveFigureDown(Figure figure) {
        if(figure == null) return;
        if(isFigureDownEmpty(figure)) {
            for (int j = figure.getCells()[0].length - 1; j >= 0; j--) {
                for (int i = 0; i < figure.getCells().length; i++) {
                    if (figure.getCells()[i][j].getState() == CellState.BLOCK) {
                        //Log.d("MOVEFIGUREDOWN", "cell with coords in figure" + i + "," + j + "has internal coords" + figure.getCells()[i][j].getX() + "," + figure.getCells()[i][j].getY());
                        moveCellDown(cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()], figure);

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

    public void moveFigureUp(Figure figure) {
        if(figure == null){
            Log.d("FMOVEEUP", "Figure is NULL!" );
            return;
        }
        if(isFigureUpEmpty(figure)) {
            for (int j =  0; j < figure.getCells()[0].length; j++) {
                for (int i = 0; i < figure.getCells().length; i++) {
                    if (figure.getCells()[i][j].getState() == CellState.BLOCK) {

                        moveCellUp(cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()], figure);

                        figure.setCell(i, j, cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY() - 1]);
                    } else {
                        if (figure.getCells()[i][j].getY() - 1 >= 0) {
                            figure.setCell(i, j, cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY() - 1]);
                        }
                    }

                }
            }
            Log.d("FMOVEEUP", "moved up" );
        }else{
            Log.d("FMOVEEUP", "cant move up, blocked from up" );
        }

    }

    public void moveFigureForceDown(Figure figure) {
        if(figure == null) return;
        while(isFigureDownEmpty(figure)){
            moveFigureDown(figure);
        }
    }
    public void moveFigureForceUp(Figure figure) {
        if(figure == null) return;
        while(isFigureUpEmpty(figure)){
            moveFigureUp(figure);
        }
    }
    public void moveFigureForceLeft(Figure figure) {
        if(figure == null) return;
        while(isFigureLeftEmpty(figure)){
            moveFigureLeft(figure);
        }
    }
    public void moveFigureForceRight(Figure figure) {
        if(figure == null) return;
        while(isFigureRightEmpty(figure)){
            moveFigureRight(figure);
        }
    }

    public void rotateFigure(Figure figure, Direction direction) {
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

        for (int i = figure.getCells().length-1; i > 0 ; i--) {
            if(figure.getCells()[0][0].getX() + i < 0){
                if(!isFigureRightEmpty(figure)){return;}
                figureX ++;
            }
            if(figure.getCells()[0][0].getX() + i > this.cells.length-1){
                if(!isFigureLeftEmpty(figure)){return;}
                figureX --;
            }
        }
        for (int j = figure.getCells()[0].length-1; j > 0; j--) {
            if(figure.getCells()[0][0].getY() + j < 0){
                if(!isFigureDownEmpty(figure)){return;}
                figureY ++;
            }
            if(figure.getCells()[0][0].getY() + j > this.cells[0].length-1){
                if(!isFigureUpEmpty(figure)){return;}
                figureY --;
            }
        }


        //FigureGenerator.getNextFigureByEnum( figure.getType(),this, figureX, figureY, figure, direction);
        UniversalFigure.getNextFigureByEnum( figure.getType(),this,
                figureX,
                figureY, figure, direction);
        for (int i = 0; i < figure.getCells().length; i++) {
            for (int j = 0; j < figure.getCells()[0].length; j++) {

                cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()] = figure.getCells()[i][j];

//                cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()].setState(CellState.EMPTY);
//                cells[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()].setFigure(null);

            }
        }

    }

    public boolean isFigureDownEmpty(Figure figure){
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

    public boolean isFigureUpEmpty(Figure figure){
        for (int i = 0; i < figure.getCells().length; i++) {
            for (int j = 0; j < figure.getCells()[0].length ; j++) {
                if(figure.getCells()[i][j].getState() == CellState.BLOCK){

                    if( isEmptyUp(getCells()[figure.getCells()[i][j].getX()][figure.getCells()[i][j].getY()])){
                        break;
                    }
                    else{
                        Log.d("isEmptyUp","cell has block up");
                        return false;
                    }

                }
            }
        }

        return true;

    }

    public boolean isFigureLeftEmpty(Figure figure){

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

    public boolean isFigureRightEmpty(Figure figure){
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

    public void findAndDeleteAllFixedLines(){//TODO сделать описанное ниже
        /*
            Сначала проходим по всем графитациям и удаляем целые строки
            потом используя алгоритм в моей толстой тетрадке
            проходим по спирали по полю и перемещаем все зафиксированные клеточки в нужное направление  по ближайшей граывитации
            аглоритм премещения:
                считаем расстояния до краев поля(если есть ссответствующая гравитация) и куда ближе, туда и двигаем
                для этого понадобится алгоритм определения какое число больше, опреденение дубликатов (уже есть)
                приоритеты такие: низ - верх - лево - право
         */

        for(Map.Entry<Direction, Gravity> gravityEntry : gravities.entrySet()){

                Gravity g = gravityEntry.getValue();
                if(g.getDirection() == DOWN){
                    findAndDeleteDownFixedLines2();
                }
                if(g.getDirection() == UP){
                    findAndDeleteUpFixedLines2();
                }
                if(g.getDirection() == LEFT){
                    findAndDeleteLeftFixedLines2();
                }
                if(g.getDirection() == RIGHT){
                    findAndDeleteRightFixedLines2();
                }

        }

    }
//    public void findAndDeleteDownFixedLines(){
//        int delRowCount = 0;
//        for (int i = 0; i < cells[0].length; i++) {
//            boolean fixedLine = true;
//            for (int j = cells.length-1; j >= 0; j--) {
//                if(cells[j][i].getState() != FIXED){
//                    fixedLine = false;
//                }
//            }
//            if(fixedLine){
//                for (int j = cells.length-1; j >= 0; j--) {
//                    cells[j][i].setState(CellState.EMPTY);
//                }
//                delRowCount++;
//                Log.d("FIXEDCLEAN","FixedLine " + i + " cleaned!");
//                forceDownAllLinesUp(i);
//            }
//        }
//        blockScore += delRowCount * delRowCount * cells.length;
//    }
    public void findAndDeleteDownFixedLines2(){
        int delRowCount = 0;
        for (int i = 0; i < cells[0].length; i++) {//y query
            boolean fixedLine = true;
            for (int j = cells.length-1; j >= 0; j--) {//x query
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
                for (int j = 0; j < cells[0].length; j++) {
                    forceDownAllFixedLines(i);
                }
            }
        }
        blockScore += delRowCount * delRowCount * cells.length;
    }
    public void findAndDeleteUpFixedLines2(){
        int delRowCount = 0;
        for (int i = cells[0].length-1; i >= 0; i--) {//y query
            boolean fixedLine = true;
            for (int j = cells.length-1; j >= 0; j--) {//x query
                if(cells[j][i].getState() != FIXED){
                    fixedLine = false;
                }
            }
            if(fixedLine){
                for (int j = cells.length-1; j >= 0; j--) {//x query
                    cells[j][i].setState(CellState.EMPTY);
                }
                delRowCount++;
                Log.d("FIXEDCLEAN","FixedLine " + i + " cleaned!");
                for (int j = 0; j <  cells[0].length; j++) {
                    forceUpAllFixedLines(i);
                }
            }
        }
        blockScore += delRowCount * delRowCount * cells.length;
    }

    public void findAndDeleteLeftFixedLines2(){
        int delRowCount = 0;
        for (int i = cells.length-1; i >= 0; i--) {//x query
            boolean fixedLine = true;
            for (int j = cells[0].length-1; j >= 0; j--) {//y query
                if(cells[i][j].getState() != FIXED){
                    fixedLine = false;
                }
            }
            if(fixedLine){
                for (int j = cells[0].length-1; j >= 0; j--) {//y query
                    cells[i][j].setState(CellState.EMPTY);
                }
                delRowCount++;
                Log.d("FIXEDCLEAN","FixedLine " + i + " cleaned!");
                for (int j = 0; j < cells.length; j++) {
                    forceLeftAllFixedLines(i);
                }
            }
        }
        blockScore += delRowCount * delRowCount * cells.length;
    }

    public void findAndDeleteRightFixedLines2(){
        int delRowCount = 0;
        for (int i = 0; i < cells.length; i++) {//x query
            boolean fixedLine = true;
            for (int j = cells[0].length-1; j >= 0; j--) {//y query
                if(cells[i][j].getState() != FIXED){
                    fixedLine = false;
                }
            }
            if(fixedLine){
                for (int j = cells[0].length-1; j >= 0; j--) {//y query
                    cells[i][j].setState(CellState.EMPTY);
                }
                delRowCount++;
                Log.d("FIXEDCLEAN","FixedLine " + i + " cleaned!");
                for (int j = 0; j < cells.length; j++) {
                    forceRightAllFixedLines(i);
                }
            }
        }
        blockScore += delRowCount * delRowCount * cells.length;
    }

//    public void findAndDeleteUpFixedLines(){
//        int delRowCount = 0;
//        for (int i = cells[0].length-1; i >= 0; i--) {
//            boolean fixedLine = true;
//            for (int j = cells.length-1; j >= 0; j--) {
//                if(cells[j][i].getState() != FIXED){
//                    fixedLine = false;
//                }
//            }
//            if(fixedLine){
//                for (int j = cells.length-1; j >= 0; j--) {
//                    cells[j][i].setState(CellState.EMPTY);
//                }
//                delRowCount++;
//                Log.d("FIXEDCLEAN","FixedLine " + i + " cleaned!");
//                forceUpAllLinesUp(i);
//            }
//        }
//        blockScore += delRowCount * delRowCount * cells.length;
//    }

    private void forceDownAllFixedLines(int y) {
        //for (int i = y-1; i >= 0; i--) {//y query

        for (int i = cells[0].length-1; i >= 0; i--) {//y query
            for (int j = cells.length - 1; j >= 0; j--) {
                if(gravityMap[j][i] == DOWN){
                    if (cells[j][i].getState() == FIXED) {
                        if (isEmptyDown(cells[j][i])) {
                            moveFixedCellDown(cells[j][i]);
                        }
                    }
                }

            }
        }
    }

    private void forceUpAllFixedLines(int y) {
        //for (int i = y+1; i >= cells[0].length; i++) {//error
        for (int i = 0; i < cells[0].length; i++) {
            for (int j = cells.length-1; j >= 0; j--) {
                if(gravityMap[j][i] == UP) {
                    if (cells[j][i].getState() == FIXED) {
                        if (isEmptyUp(cells[j][i])) {
                            moveFixedCellUp(cells[j][i]);
                        }
                    }
                }
            }
        }
    }

    private void forceLeftAllFixedLines(int x) {
        //for (int i = x+1; i < cells.length; i++) {//x query
        for (int i = 0; i < cells.length; i++) {//x query
            for (int j = cells[0].length -  1; j >= 0; j--) {//y query
                if(gravityMap[i][j] == LEFT){
                    if (cells[i][j].getState() == FIXED) {
                        if (isEmptyLeft(cells[i][j])) {
                            moveFixedCellLeft(cells[i][j]);
                        }
                    }
                }

            }
        }
    }

    private void forceRightAllFixedLines(int x) {
        //for (int i = x-1; i >= 0; i--) {//x query
        for (int i = cells.length-1; i >= 0; i--) {//x query
            for (int j = cells[0].length - 1; j >= 0; j--) {//y query
                if(gravityMap[i][j] == RIGHT){
                    if (cells[i][j].getState() == FIXED) {
                        if (isEmptyRight(cells[i][j])) {
                            moveFixedCellRight(cells[i][j]);
                        }
                    }
                }

            }
        }
    }

    public void moveFiguresLeft() {
        for (Map.Entry<Direction, Figure> figureEntry: figures.entrySet()
             ) {
            Figure figure = figureEntry.getValue();
            moveFigureLeft(figure);
        }
    }

    public void moveFiguresRight() {
        for (Map.Entry<Direction, Figure> figureEntry: figures.entrySet()
        ) {
            Figure figure = figureEntry.getValue();
            moveFigureRight(figure);
        }
    }

    public void moveFiguresForceDown() {
        for (Map.Entry<Direction, Figure> figureEntry: figures.entrySet()
        ) {
            Figure figure = figureEntry.getValue();
            Direction direction = figureEntry.getKey();
            if(direction == DOWN){
                moveFigureForceDown(figure);
            }
            if(direction == UP){
                moveFigureForceUp(figure);
            }
            if(direction == LEFT){
                moveFigureForceLeft(figure);
            }
            if(direction == RIGHT){
                moveFigureForceRight(figure);
            }

        }
    }

    public void moveFiguresDown() {
        for (Map.Entry<Direction, Figure> figureEntry: figures.entrySet()) {
            Figure figure = figureEntry.getValue();
            moveFigureDown(figure);
        }
    }

    public void moveFiguresUp() {
        for (Map.Entry<Direction, Figure> figureEntry: figures.entrySet()) {
            Figure figure = figureEntry.getValue();
            moveFigureUp(figure);
        }
    }

    public void rotateFigures() {
        for (Map.Entry<Direction, Figure> figureEntry: figures.entrySet()) {
            Figure figure = figureEntry.getValue();
            Direction direction = figureEntry.getKey();
            rotateFigure(figure, direction);
        }
    }

    private void generateGravityMap() {
        for (int i = 0; i < Math.max(gravityMap.length, gravityMap[0].length); i++) {
            for (Map.Entry<Direction, Gravity> gravityEntry: gravities.entrySet()
             ) {
            Gravity g = gravityEntry.getValue();

                if(g.getDirection()== DOWN){
                    fullFirstFreeLineDown();
                }
                if(g.getDirection()== UP){
                    fullFirstFreeLineUp();
                }
                if(g.getDirection()== LEFT){
                    fullFirstFreeLineLeft();
                }
                if(g.getDirection()== RIGHT){
                    fullFirstFreeLineRight();
                }

            }

        }
    }

    private void fullFirstFreeLineDown() {
        for (int i = gravityMap[0].length-1; i >=0; i--){//y query

            if(!isDirectionInLineExists(DOWN, i)){
                for (int j = 0; j < gravityMap.length; j++) {
                    if (gravityMap[j][i] == null){
                        gravityMap[j][i] = DOWN;
                    }
                }
                return;
            }

        }
    }

    private void fullFirstFreeLineUp() {
        for (int i = 0; i < gravityMap[0].length; i++){//y query

            if(!isDirectionInLineExists(UP, i)){
                for (int j = 0; j < gravityMap.length; j++) {
                    if (gravityMap[j][i] == null){
                        gravityMap[j][i] = UP;
                    }
                }
                return;
            }

        }
    }

    private void fullFirstFreeLineLeft() {
        for (int i = 0; i < gravityMap.length; i++){//x query

            if(!isDirectionInLineExists(LEFT, i)){
                for (int j = 0; j < gravityMap[0].length; j++) {
                    if (gravityMap[i][j] == null){
                        gravityMap[i][j] = LEFT;
                    }
                }
                return;
            }

        }
    }

    private void fullFirstFreeLineRight() {
        for (int i = gravityMap.length-1; i >= 0; i--){//x query

            if(!isDirectionInLineExists(RIGHT, i)){
                for (int j = 0; j < gravityMap[0].length; j++) {
                    if (gravityMap[i][j] == null){
                        gravityMap[i][j] = RIGHT;
                    }
                }
                return;
            }

        }
    }


    private boolean isDirectionInLineExists(Direction direction, int lineIndex){
        if(direction == DOWN){
            for (int j = 0; j < gravityMap.length; j++) {//x query
                if(gravityMap[j][lineIndex] == DOWN){
                    return true;
                }
            }
            return false;
        }
        else if(direction == UP){
            for (int j = 0; j < gravityMap.length; j++) {//x query
                if(gravityMap[j][lineIndex] == UP){
                    return true;
                }
            }
            return false;
        }
        else if(direction == LEFT){
            for (int j = 0; j < gravityMap[0].length; j++) {//y query
                if(gravityMap[lineIndex][j] == LEFT){
                    return true;
                }
            }
            return false;
        }
        else if(direction == RIGHT){
            for (int j = 0; j < gravityMap[0].length; j++) {//y query
                if(gravityMap[lineIndex][j] == RIGHT){
                    return true;
                }
            }
            return false;
        }else{
            throw new IllegalArgumentException();
        }

    }

    public void setFigureByIndex(Figure newFigure, Direction direction){
        //figures.remove(direction);
        figures.put(direction, newFigure);
    }

    public void setFigureNull(Direction direction){
        figures.put(direction, null);
    }

    public static boolean hasDuplicates(ArrayList<Integer> arrayList){
        HashSet<Integer> set = new HashSet<>();
        for (Integer i:arrayList
             ) {
            if(set.add(i) == false){
                return true;
            }
        }
        return false;
    }
}
