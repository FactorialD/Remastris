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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import pl.factoriald.remastris.Entity.CellState;
import pl.factoriald.remastris.Entity.GameField;

public class DrawView extends View {

    PlayScreenViewModel mViewModel;

    Paint paint = new Paint();

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
        updateCellField(canvas);
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
}
