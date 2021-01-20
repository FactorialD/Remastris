package pl.factoriald.remastris.Views.ui.playscreen;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import lombok.Getter;
import pl.factoriald.remastris.Entity.Direction;
import pl.factoriald.remastris.Entity.GameField;
import pl.factoriald.remastris.Entity.Gamemode;
import pl.factoriald.remastris.Entity.Gravity;
import pl.factoriald.remastris.Entity.Setting;
import pl.factoriald.remastris.Entity.Tickable;
import pl.factoriald.remastris.Entity.Ticker;
import pl.factoriald.remastris.Entity.Utility;
import pl.factoriald.remastris.Menu;
import pl.factoriald.remastris.R;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

public class  PlayScreenFragment extends Fragment  implements Tickable{
    @Getter
    private PlayScreenViewModel mViewModel;

    private TextView    timeScoreView;
    private TextView    blockScoreView;
    private Button      pauseButton;
    private Button      leftButton;
    private Button      rightButton;
    private Button      upButton;
    private Button      downButton;
    private Button      forceButton;
    private Button      rotateButton;
    private DrawView drawView;
    private Gamemode mode;

    private Thread enableButtonsThread = new Thread(() -> {

          while (true) {
              if (mViewModel.getTicker().getValue().isPaused()) {
                  disableAllButtons();
                  //Log.d("BUTTONS", "DISABLED");
              } else {
                  enableAllButtons();
                  //Log.d("BUTTONS", "ENABLED");
              }
          }

    });

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

        this.mode = (Gamemode) this.getArguments().get("mode");

        mViewModel = new ViewModelProvider(this).get(PlayScreenViewModel.class);

        //mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(PlayScreenViewModel.class);
        // TODO: Use the ViewModel
        Log.d("LOG", "mViewModel is:" + mViewModel);

        mViewModel.getGameField().observe(getViewLifecycleOwner(), gameField -> {
            drawView.invalidate();
        });


        drawView = getActivity().findViewById(R.id.draw_view);
        drawView.setViewModel(mViewModel);
        drawView.invalidate();

        timeScoreView = getActivity().findViewById(R.id.time_score_view);
        blockScoreView = getActivity().findViewById(R.id.block_score);
        //timeScoreView.setText("test");
        SharedPreferences pref = getContext().getSharedPreferences(Setting.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE);
        int width;
        int height;
        int delay;
        boolean gDown=false,gUp=false,gLeft=false,gRight=false;
        String figureSet = pref.getString(Setting.FIGURE_SET.toString(), "FIGURE_STANDARD"); ;

        if(mode == Gamemode.CLASSIC){
            Log.d("GAMEMODE", "CLASSIC");
            width = 10;
            height = 22;
            delay = 800;
            gDown = true;
        }else if(mode == Gamemode.RANDOM){
            Log.d("GAMEMODE", "OTHER");
            width = Utility.getRandomNumber(parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MIN_WIDTH.toString(), "null")),
                    parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MAX_WIDTH.toString(), "null"))
                    );
            height = Utility.getRandomNumber(parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MIN_HEIGHT.toString(), "null")),
                    parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MAX_HEIGHT.toString(), "null"))
            );
            delay = Utility.getRandomNumber(parseInt(pref.getString(Setting.CURRENT_TICKER_MIN_DELAY.toString(), "null")),
                    parseInt(pref.getString(Setting.CURRENT_TICKER_MAX_DELAY.toString(), "null"))
            );
            while(!gDown && !gUp && !gLeft && !gRight){
                int r1 = Utility.getRandomNumber(0,10);
                int r2 = Utility.getRandomNumber(0,10);
                int r3 = Utility.getRandomNumber(0,10);
                int r4 = Utility.getRandomNumber(0,10);
                Log.d("GENERATEDGRAVITIES", r1 + " " + r2 + " " + r3 + " " + r4);
                gDown = r1 >4;
                gUp = r2 >4;
                gLeft = r3 >4;
                gRight = r4 >4;

            }

        }else{
            Log.d("GAMEMODE", "OTHER");
            width = parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_WIDTH.toString(), "")) ;
            height = parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_HEIGHT.toString(), ""));
            delay = parseInt(pref.getString(Setting.CURRENT_TICKER_DELAY.toString(), ""));
            gDown = parseBoolean(pref.getString(Setting.CURRENT_GRAVITY_DOWN.toString(), ""));
            gUp = parseBoolean(pref.getString(Setting.CURRENT_GRAVITY_UP.toString(), ""));
            gLeft = parseBoolean(pref.getString(Setting.CURRENT_GRAVITY_LEFT.toString(), ""));
            gRight = parseBoolean(pref.getString(Setting.CURRENT_GRAVITY_RIGHT.toString(), ""));

        }
        mViewModel.getSettings().getValue().put(Setting.GAMEFIELD_WIDTH, (width + ""));
        mViewModel.getSettings().getValue().put(Setting.GAMEFIELD_HEIGHT, (height + ""));
        mViewModel.getSettings().getValue().put(Setting.TICKER_DELAY, (delay + ""));
        mViewModel.getSettings().getValue().put(Setting.GRAVITY1_DOWN, (gDown + ""));
        mViewModel.getSettings().getValue().put(Setting.GRAVITY2_UP, (gUp + ""));
        mViewModel.getSettings().getValue().put(Setting.GRAVITY3_LEFT, (gLeft + ""));
        mViewModel.getSettings().getValue().put(Setting.GRAVITY4_RIGHT, (gRight + ""));
        mViewModel.getSettings().getValue().put(Setting.FIGURE_SET, (figureSet));


        mViewModel.apply();
        mViewModel.getTicker().getValue().addTickable(this);
        mViewModel.getTicker().getValue().start();

        initializeButtons(getActivity());

        enableButtonsThread.start();
    }

    private void initializeButtons(FragmentActivity a) {

        int gravitySize = mViewModel.getGameField().getValue().getGravities().size();
        boolean g1DownExists =  parseBoolean(mViewModel.getSettings().getValue().get(Setting.GRAVITY1_DOWN));
        boolean g2UpExists =  parseBoolean(mViewModel.getSettings().getValue().get(Setting.GRAVITY2_UP));
        boolean g3LeftExists =  parseBoolean(mViewModel.getSettings().getValue().get(Setting.GRAVITY3_LEFT));
        boolean g4RightExists =  parseBoolean(mViewModel.getSettings().getValue().get(Setting.GRAVITY4_RIGHT));
        //Direction firstGravityDirection = mViewModel.getSettings().getValue().get(Setting.GRAVITY1_DOWN);
//                .getValue()
//                .getGravities()
//                .entrySet()
//                .iterator()
//                .next()
//                .getValue()
//                .getDirection();
        pauseButton = a.findViewById(R.id.pause_button);
        pauseButton.setOnClickListener((e) -> {
            mViewModel.getTicker().getValue().pause();
            Menu.openPause(getActivity());
        });

        leftButton = a.findViewById(R.id.buttonLeft);
        leftButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().moveFiguresLeft();
            drawView.invalidate();
        });
        if(!g1DownExists && !g2UpExists && !g3LeftExists && g4RightExists){
            leftButton.setVisibility(View.GONE);
        }else{
            leftButton.setVisibility(View.VISIBLE);
        }

        rightButton = a.findViewById(R.id.buttonRight);
        rightButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().moveFiguresRight();
            drawView.invalidate();
        });
        if(!g1DownExists && !g2UpExists && g3LeftExists && !g4RightExists){
            rightButton.setVisibility(View.GONE);
        }else{
            rightButton.setVisibility(View.VISIBLE);
        }

        forceButton = a.findViewById(R.id.buttonForce);
        forceButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().moveFiguresForceDown();
            drawView.invalidate();
        });


        downButton = a.findViewById(R.id.buttonDown);
        downButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().moveFiguresDown();
            drawView.invalidate();
        });
        if(!g1DownExists && g2UpExists && !g3LeftExists && !g4RightExists){
            downButton.setVisibility(View.GONE);
        }else{
            downButton.setVisibility(View.VISIBLE);
        }

        upButton = a.findViewById(R.id.buttonUp);
        upButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().moveFiguresUp();
            drawView.invalidate();
        });
        if(g1DownExists && !g2UpExists && !g3LeftExists && !g4RightExists){
            upButton.setVisibility(View.GONE);
        }else{
            upButton.setVisibility(View.VISIBLE);
        }

        rotateButton = a.findViewById(R.id.buttonRotate);
        rotateButton.setOnClickListener((e) -> {
            mViewModel.getGameField().getValue().rotateFigures();
            drawView.invalidate();
        });

    }

    public void disableAllButtons(){
        pauseButton .setClickable(false);
        leftButton  .setClickable(false);
        rightButton .setClickable(false);
        upButton    .setClickable(false);
        downButton  .setClickable(false);
        forceButton .setClickable(false);
        rotateButton.setClickable(false);
    }
    public void enableAllButtons(){
        pauseButton .setClickable  (true);
        leftButton  .setClickable  (true);
        rightButton .setClickable  (true);
        upButton    .setClickable  (true);
        downButton  .setClickable  (true);
        forceButton .setClickable  (true);
        rotateButton.setClickable  (true);
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
                    Log.d("GAME", "Ended");
                    mViewModel.getTicker().getValue().pause();
                    int endScore =  mViewModel.getGameField().getValue().getTimeScore() + mViewModel.getGameField().getValue().getBlockScore();
                    SharedPreferences prefRead = getContext().getSharedPreferences(Setting.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE);
                    prefRead.edit()
                            .putString(Setting.LAST_SCORE.toString(), endScore + "")
                            .commit();

                    Menu.openScoreboard(getActivity());
                }
            }
        });




        //Log.d("GAMEFIELD", "Tick");
    }

}