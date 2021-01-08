package pl.factoriald.remastris.Views.ui.playscreen;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.factoriald.remastris.Entity.GameField;
import pl.factoriald.remastris.Entity.Gamemode;
import pl.factoriald.remastris.Entity.Tickable;
import pl.factoriald.remastris.Entity.Ticker;
import pl.factoriald.remastris.Menu;
import pl.factoriald.remastris.R;

public class  PlayScreenFragment extends Fragment  implements Tickable{

    private PlayScreenViewModel mViewModel;

    private TextView timeScoreView;
    private TextView blockScoreView;
    private Ticker ticker;
    private Button pauseButton;
    private Button leftButton;
    private Button rightButton;
    private Button downButton;
    private Button forceButton;
    private Button rotateButton;
    private DrawView drawView;
    private Gamemode mode;

//    public static PlayScreenFragment newInstance() {
//        return new PlayScreenFragment();
//    }

    public static Fragment newInstance(Gamemode classic) {

        PlayScreenFragment fragment = new PlayScreenFragment();

        Bundle args = new Bundle();
        args.putSerializable("mode",classic);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.play_screen_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PlayScreenViewModel.class);
        // TODO: Use the ViewModel
        Log.d("LOG", "mViewModel is:" + mViewModel);
        mViewModel.getGameField().observe(this, gameField -> {
            drawView.invalidate();
        });


        drawView = getActivity().findViewById(R.id.draw_view);
        drawView.setViewModel(mViewModel);
        drawView.invalidate();

        timeScoreView = getActivity().findViewById(R.id.time_score_view);
        blockScoreView = getActivity().findViewById(R.id.block_score);
        //timeScoreView.setText("test");
        initializeButtons(getActivity());

        //getView().setBackgroundColor(Color.argb(100, 0, 0, 0));

//        this.getView().setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("TOUCH", "Registered touch at View of PlayScreen Fragment");
//                mViewModel.getGameField().getValue().moveTick();
//                drawView.invalidate();
//                return false;
//            }
//        });

        ticker = new Ticker(this, 1000);
        ticker.start();
    }

    private void initializeButtons(FragmentActivity a) {

        pauseButton = a.findViewById(R.id.pause_button);
        pauseButton.setOnClickListener((e) -> {
            ticker.pause();
            Menu.openPause(getActivity());
        });

        leftButton = a.findViewById(R.id.buttonLeft);
        leftButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().moveFigureLeft();
            drawView.invalidate();
        });

        rightButton = a.findViewById(R.id.buttonRight);
        rightButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().moveFigureRight();
            drawView.invalidate();
        });

        forceButton = a.findViewById(R.id.buttonForce);
        forceButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().moveFigureForceDown();
            drawView.invalidate();
        });

        downButton = a.findViewById(R.id.buttonDown);
        downButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().moveFigureDown();
            drawView.invalidate();
        });

        rotateButton = a.findViewById(R.id.buttonRotate);
        rotateButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().rotateFigure();
            drawView.invalidate();
        });

    }



    @Override
    public void doTick() {
        //TODO make tick
        mViewModel.getGameField().getValue().addTimeScore();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeScoreView.setText(Integer.toString( mViewModel.getGameField().getValue().getTimeScore()));
                blockScoreView.setText(Integer.toString( mViewModel.getGameField().getValue().getBlockScore()));
                mViewModel.getGameField().getValue().moveTick();
                drawView.invalidate();
                if (mViewModel.getGameField().getValue().isEndGame()){

                    ticker.pause();
                    Menu.openScoreboard(getActivity());
                }
            }
        });




        Log.d("GAMEFIELD", "Tick");
    }

}