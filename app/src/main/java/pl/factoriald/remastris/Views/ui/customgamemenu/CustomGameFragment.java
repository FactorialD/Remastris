package pl.factoriald.remastris.Views.ui.customgamemenu;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;

import pl.factoriald.remastris.Entity.Gamemode;
import pl.factoriald.remastris.Entity.Setting;
import pl.factoriald.remastris.Entity.Utility;
import pl.factoriald.remastris.Menu;
import pl.factoriald.remastris.R;
import pl.factoriald.remastris.Utilities.MinMaxFilter;

import static java.lang.Integer.parseInt;

public class CustomGameFragment extends Fragment {

    private CustomGameViewModel mViewModel;

    private Button backButton;
    private Button playCustomMode;
    private EditText fieldWidthEditText;
    private EditText fieldHeightEditText;

    private CheckBox gravityUpCheckbox;
    private CheckBox gravityDownCheckbox;
    private CheckBox gravityLeftCheckbox;
    private CheckBox gravityRightCheckbox;

    private SeekBar  fallSpeedSeekBar;



    public static CustomGameFragment newInstance() {
        return new CustomGameFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_game_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CustomGameViewModel.class);
        // TODO: Use the ViewModel
        initializeButtons(getActivity());
    }

    private void initializeButtons(FragmentActivity a) {

        playCustomMode = a.findViewById(R.id.start_custom_game_button);
        playCustomMode.setOnClickListener((e) -> {
            String fieldH = fieldHeightEditText.getText().toString();
            String fieldW = fieldWidthEditText.getText().toString();
            String tickerD = (fallSpeedSeekBar.getProgress() + 150) + "";
            String gUp = gravityUpCheckbox.isChecked()? "true":"false";
            String gDown = gravityDownCheckbox.isChecked()? "true":"false";
            String gLeft = gravityLeftCheckbox.isChecked()? "true":"false";
            String gRight = gravityRightCheckbox.isChecked()? "true":"false";
            SharedPreferences prefRead = getContext().getSharedPreferences(Setting.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE);
            if(
                    fieldH.equals("") ||  fieldW.equals("") || tickerD.equals("") ||
                            parseInt(fieldH) < parseInt(prefRead.getString(Setting.CURRENT_GAMEFIELD_MIN_HEIGHT.toString(), "0"))||
                            parseInt(fieldH) > parseInt(prefRead.getString(Setting.CURRENT_GAMEFIELD_MAX_HEIGHT.toString(), "100"))||

                            parseInt(fieldW) < parseInt(prefRead.getString(Setting.CURRENT_GAMEFIELD_MIN_WIDTH.toString(), "0"))||
                            parseInt(fieldW) > parseInt(prefRead.getString(Setting.CURRENT_GAMEFIELD_MAX_WIDTH.toString(), "100"))||

                            parseInt(tickerD + 150) < 150 ||

                            (gUp.equals("false") && gDown.equals("false") && gLeft.equals("false") && gRight.equals("false"))
            ) {
                Log.d("CUSTOM MENU", "some buttons has empty values");
            }else{


                SharedPreferences pref = a.getSharedPreferences(Setting.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE);
                pref.edit()
                        .putString(Setting.CURRENT_GAMEFIELD_WIDTH.toString(), fieldW)
                        .putString(Setting.CURRENT_GAMEFIELD_HEIGHT.toString(), fieldH)
                        .putString(Setting.CURRENT_GRAVITY_DOWN.toString(), gDown)
                        .putString(Setting.CURRENT_GRAVITY_UP.toString(), gUp)
                        .putString(Setting.CURRENT_GRAVITY_LEFT.toString(), gLeft)
                        .putString(Setting.CURRENT_GRAVITY_RIGHT.toString(), gRight)
                        .putString(Setting.CURRENT_TICKER_DELAY.toString(), tickerD)
                        .commit();
                Log.d("CUSTOM MODE", "Settings: filedH " + fieldH +
                        " fieldW " + fieldW +
                        " tickerD " + tickerD +
                        " gup " + gUp +
                        " gDown " + gDown +
                                " gLeft " + gLeft +
                                       "gRight" + gRight );
                Menu.openCustomMode(getActivity());
            }

        });

        backButton = a.findViewById(R.id.back_button10);
        backButton.setOnClickListener((e) -> {
            Menu.back(getActivity());
        });


        SharedPreferences pref = getContext().getSharedPreferences(Setting.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE);

        int minFieldWidth = Utility.getRandomNumber(
                parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MIN_WIDTH.toString(), "null")), parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MIN_WIDTH.toString(), "null"))
        );
        int maxFieldWidth = Utility.getRandomNumber(
                parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MIN_WIDTH.toString(), "null")), parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MAX_WIDTH.toString(), "null"))
        );
        int minFieldHeight = Utility.getRandomNumber(parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MIN_HEIGHT.toString(), "null")),
                    parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MIN_HEIGHT.toString(), "null"))
        );
        int maxFieldHeight = Utility.getRandomNumber(parseInt(pref.getString(Setting.CURRENT_TICKER_MIN_DELAY.toString(), "null")),
                    parseInt(pref.getString(Setting.CURRENT_GAMEFIELD_MAX_WIDTH.toString(), "null"))
        );
        fieldWidthEditText = a.findViewById(R.id.field_width_edit);
        //fieldWidthEditText.setFilters(new InputFilter[]{ new MinMaxFilter(minFieldWidth , maxFieldWidth )});

        fieldHeightEditText = a.findViewById(R.id.field_height_edit);
        //fieldHeightEditText.setFilters(new InputFilter[]{ new MinMaxFilter(minFieldHeight , maxFieldHeight )});

        gravityUpCheckbox = a.findViewById(R.id.checkBox_gravity_up);
        gravityUpCheckbox.setChecked(true);
        gravityDownCheckbox = a.findViewById(R.id.checkBox_gravity_down);
        gravityLeftCheckbox = a.findViewById(R.id.checkBox_gravity_left);
        gravityRightCheckbox = a.findViewById(R.id.checkBox_gravity_right);
        fallSpeedSeekBar = a.findViewById(R.id.fall_speed_seek_bar);
        int minSpeed = Utility.getRandomNumber(parseInt(pref.getString(Setting.CURRENT_TICKER_MIN_DELAY.toString(), "null")),
                parseInt(pref.getString(Setting.CURRENT_TICKER_MIN_DELAY.toString(), "null"))
        );
        int maxSpeed = Utility.getRandomNumber(parseInt(pref.getString(Setting.CURRENT_TICKER_MIN_DELAY.toString(), "null")),
                parseInt(pref.getString(Setting.CURRENT_TICKER_MAX_DELAY.toString(), "null"))
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fallSpeedSeekBar.setMin(minSpeed);
        }
        fallSpeedSeekBar.setMax(maxSpeed);

    }


}