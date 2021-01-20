package pl.factoriald.remastris.Views.ui.playscreen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import pl.factoriald.remastris.Entity.CellState;
import pl.factoriald.remastris.Entity.Direction;
import pl.factoriald.remastris.Entity.GameField;
import pl.factoriald.remastris.Entity.Gravity;

public class DrawView extends View {

    PlayScreenViewModel mViewModel;

    Paint paint = new Paint();
    boolean marginSet = false;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setCanvasIfNeed();
        updateCellField(canvas);

    }

    private void setCanvasIfNeed(){
        if(!marginSet){
            int drawViewH;
            int drawViewW;
            int needWidth;
            int needHeight;
            int needMargin;
            int leftMargin;
            int downMargin;
            if(mViewModel.getGameField().getValue().getHeight() >= mViewModel.getGameField().getValue().getWidth()){
                drawViewH = getMeasuredHeight();
                drawViewW = getMeasuredWidth();
                needWidth = mViewModel.getGameField().getValue().getWidth() * drawViewH / mViewModel.getGameField().getValue().getHeight();
                needMargin = (drawViewW - needWidth)/2;
                leftMargin = 0;
                downMargin = 0;
                if(needMargin <= 0) {
                    leftMargin = 0;
                }else{
                    leftMargin = needMargin;
                }
            }else{
                drawViewH = getMeasuredHeight();
                drawViewW = getMeasuredWidth();
                needHeight = mViewModel.getGameField().getValue().getHeight() * drawViewW / mViewModel.getGameField().getValue().getWidth();
                needMargin = (drawViewH - needHeight)/2;
                leftMargin = 0;
                downMargin = 0;
                if(needMargin <= 0) {
                    downMargin = 0;
                }else{
                    downMargin = needMargin;
                }
            }

            //Log.d("MARGIN", "drawViewH ="+ drawViewH + "; drawViewW="+drawViewW+";needWidth="+needWidth+";needMargin="+needMargin+";leftMargin="+leftMargin);

            setMargins(this,leftMargin,downMargin,leftMargin,downMargin);
            marginSet = true;
        }

    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public void setViewModel(PlayScreenViewModel mViewModel) {
        this.mViewModel = mViewModel;
    }

    public void updateCellField(Canvas canvas) {

        GameField gf = mViewModel.getGameField().getValue();
        Log.d("MVIEWMODEL", "mViewModel in DrawView: " + mViewModel.getGameField());
        Log.d("LOG" , "Gamefield is " + gf);
        int viewH = this.getHeight();
        int viewW = this.getWidth();
        int cellH;
        int cellW;
        if(viewH >= viewW){
            //portrait mode;
            cellW = viewW / gf.getCells().length;
            cellH = viewH / gf.getCells()[0].length;

        }else{
            //landscape mode;
            cellH = viewW / gf.getCells().length;
            cellW = viewH / gf.getCells()[0].length;
        }
        drawGravities(canvas);
        
        for (int i = 0; i < gf.getCells().length; i++) {
            for (int j = 0; j < gf.getCells()[0].length; j++) {
                //paint.setColor(gf.getCells()[i][j].getColor());
                paint.setColor(
                        gf.getCells()[i][j].getState() == CellState.EMPTY? Color.TRANSPARENT:gf.getCells()[i][j].getColor()
                        );
                Rect rect = new Rect(0 + i * cellW, 0 + j*cellH, 0 + i * cellW + cellW, 0 + j*cellH + cellH);

                canvas.drawRect(rect, paint );
                //canvas.drawPaint(paint);

//                paint.setColor(gf.getCells()[i][j].getFigure() == null?Color.BLACK:Color.RED);
//                paint.setTextSize(14);
//                canvas.drawText(
//                        gf.getCells()[i][j].getX() + "/" +
//                                gf.getCells()[i][j].getY() +
//                                gf.getFigure()!=null?(
//                                        gf.getFigure()+""
//                                        ):"nil" , 0+i*cellW + cellW/2, 0 + j*cellH + cellH/2, paint
//                );
            }
        }
    }

    private void drawGravities(Canvas canvas) {
        GameField gf = mViewModel.getGameField().getValue();
        for (Map.Entry<Direction, Gravity> gravityEntry : gf.getGravities().entrySet()
             ) {
            Gravity g = gravityEntry.getValue();
            int viewH = this.getHeight();
            int viewW = this.getWidth();
            int cellH;
            int cellW;
            if(viewH >= viewW){
                //portrait mode;
                cellW = viewW / gf.getCells().length;
                cellH = viewH / gf.getCells()[0].length;

            }else{
                //landscape mode;
                cellH = viewW / gf.getCells().length;
                cellW = viewH / gf.getCells()[0].length;
            }
            int pointUpX = cellW/2;
            int pointDownX = cellW/2;
            int pointLeftX = 0;
            int pointRightX = cellW;
            int pointUpY = 0;
            int pointDownY = cellH;
            int pointLeftY = cellH/2;
            int pointRightY = cellH/2;


            int p1X =0,p2X=0,p3X=0,p4X=0,p1Y=0,p2Y=0,p3Y=0,p4Y=0;
            if(g.getDirection() == Direction.DOWN){
                p1X = pointUpX;
                p2X = pointDownX;
                p3X = pointLeftX;
                p4X = pointRightX;

                p1Y = pointUpY;
                p2Y = pointDownY;
                p3Y = pointLeftY;
                p4Y = pointRightY;
            }
            if(g.getDirection() == Direction.UP){
                p2X  = pointUpX;
                p1X = pointDownX;
                p4X  = pointLeftX;
                p3X  = pointRightX;

                p2Y= pointUpY;
                p1Y = pointDownY;
                p4Y = pointLeftY;
                p3Y = pointRightY;
            }
            if(g.getDirection() == Direction.LEFT){
                p1X = pointRightX;
                p2X = pointLeftX;
                p3X = pointUpX;
                p4X = pointDownX;

                p1Y = pointRightY;
                p2Y = pointLeftY;
                p3Y = pointUpY;
                p4Y = pointDownY;
            }
            if(g.getDirection() == Direction.RIGHT){
                p1X = pointLeftX;
                p2X = pointRightX;
                p3X = pointUpX;
                p4X = pointDownX;

                p1Y = pointLeftY;
                p2Y = pointRightY;
                p3Y = pointUpY;
                p4Y = pointDownY;
            }

            //drawGravityMap(g, gf, canvas);

            //figure spawns draw
            for (int i = g.getPoint().getX(); i < g.getPoint().getX()+2; i++) {
                for (int j = g.getPoint().getY(); j < g.getPoint().getY() + 2; j++) {
                    //paint.setColor(gf.getCells()[i][j].getColor());
                    paint.setColor(
                            Color.BLACK
                    );
                    paint.setStrokeWidth(2);
                    canvas.drawLine(p1X + i * cellW, p1Y + j * cellH, p2X + i * cellW , p2Y + j * cellH , paint);
                    canvas.drawLine(p3X + i * cellW, p3Y + j * cellH, p2X + i * cellW , p2Y + j * cellH , paint);
                    canvas.drawLine(p4X + i * cellW, p4Y + j * cellH, p2X + i * cellW , p2Y + j * cellH , paint);
                    //Rect rect = new Rect(0 + i * cellW, 0 + j * cellH, 0 + i * cellW + cellW, 0 + j * cellH + cellH);
                    //canvas.drawRect(rect, paint);
                    paint.setStrokeWidth(1);
                }
            }
        }
        }
        //Gravity g = gf.getGravity();

    private void drawGravityMap(Gravity g, GameField gf, Canvas canvas){
        int p1X =0,p2X=0,p3X=0,p4X=0,p1Y=0,p2Y=0,p3Y=0,p4Y=0;
        int viewH = this.getHeight();
        int viewW = this.getWidth();
        int cellH;
        int cellW;
        if(viewH >= viewW){
            //portrait mode;
            cellW = viewW / gf.getCells().length;
            cellH = viewH / gf.getCells()[0].length;

        }else{
            //landscape mode;
            cellH = viewW / gf.getCells().length;
            cellW = viewH / gf.getCells()[0].length;
        }
        int pointUpX = cellW/2;
        int pointDownX = cellW/2;
        int pointLeftX = 0;
        int pointRightX = cellW;
        int pointUpY = 0;
        int pointDownY = cellH;
        int pointLeftY = cellH/2;
        int pointRightY = cellH/2;
        for (int i = 0; i < gf.getCells().length; i++) {
            for (int j = 0; j < gf.getCells()[0].length; j++) {
                if(gf.getGravityMap()[i][j] == Direction.DOWN){
                    p1X = pointUpX;
                    p2X = pointDownX;
                    p3X = pointLeftX;
                    p4X = pointRightX;

                    p1Y = pointUpY;
                    p2Y = pointDownY;
                    p3Y = pointLeftY;
                    p4Y = pointRightY;
                }
                if(gf.getGravityMap()[i][j] == Direction.UP){
                    p2X  = pointUpX;
                    p1X = pointDownX;
                    p4X  = pointLeftX;
                    p3X  = pointRightX;

                    p2Y= pointUpY;
                    p1Y = pointDownY;
                    p4Y = pointLeftY;
                    p3Y = pointRightY;
                }
                if(gf.getGravityMap()[i][j] == Direction.LEFT){
                    p1X = pointRightX;
                    p2X = pointLeftX;
                    p3X = pointUpX;
                    p4X = pointDownX;

                    p1Y = pointRightY;
                    p2Y = pointLeftY;
                    p3Y = pointUpY;
                    p4Y = pointDownY;
                }
                if(gf.getGravityMap()[i][j] == Direction.RIGHT){
                    p1X = pointLeftX;
                    p2X = pointRightX;
                    p3X = pointUpX;
                    p4X = pointDownX;

                    p1Y = pointLeftY;
                    p2Y = pointRightY;
                    p3Y = pointUpY;
                    p4Y = pointDownY;
                }
                //paint.setColor(gf.getCells()[i][j].getColor());
                paint.setColor(
                        Color.RED
                );
                paint.setStrokeWidth(2);
                canvas.drawLine(p1X + i * cellW, p1Y + j * cellH, p2X + i * cellW , p2Y + j * cellH , paint);
                canvas.drawLine(p3X + i * cellW, p3Y + j * cellH, p2X + i * cellW , p2Y + j * cellH , paint);
                canvas.drawLine(p4X + i * cellW, p4Y + j * cellH, p2X + i * cellW , p2Y + j * cellH , paint);
                //Rect rect = new Rect(0 + i * cellW, 0 + j * cellH, 0 + i * cellW + cellW, 0 + j * cellH + cellH);
                //canvas.drawRect(rect, paint);
                paint.setStrokeWidth(1);
            }
        }
    }

}
