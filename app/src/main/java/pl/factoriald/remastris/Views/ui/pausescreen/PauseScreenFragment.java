package pl.factoriald.remastris.Views.ui.pausescreen;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import lombok.Getter;
import pl.factoriald.remastris.Entity.Setting;
import pl.factoriald.remastris.Entity.Ticker;
import pl.factoriald.remastris.Menu;
import pl.factoriald.remastris.R;
import pl.factoriald.remastris.Views.ui.playscreen.PlayScreenViewModel;

public class PauseScreenFragment extends Fragment {

    @Getter
    private PlayScreenViewModel mViewModel;

    private Button continueGameButton;
    private Button settingsButton;
    private Button goToMainMenuButton;

    public static PauseScreenFragment newInstance() {
        return new PauseScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.pause_screen_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PlayScreenViewModel.class);
        //mViewModel = new ViewModelProvider(get, new ViewModelProvider.NewInstanceFactory()).get(PlayScreenViewModel.class);
        // TODO: Use the ViewModel
        initializeButtons(getActivity());

        //getView().setBackgroundColor(Color.argb(100, 0, 0, 0));
    }

    private void initializeButtons(FragmentActivity a) {


        continueGameButton = a.findViewById(R.id.continue_play_button);
        continueGameButton.setOnClickListener((e) -> {
            Ticker ticker = Ticker.getInstanceUnchecked();
            ticker.unpause();

            Menu.back(getActivity());
        });

        settingsButton = a.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener((e) -> {
            Menu.openSettings(getActivity());
        });


        goToMainMenuButton = a.findViewById(R.id.go_main_menu_button3);
        goToMainMenuButton.setOnClickListener((e) -> {
            Menu.openMainMenu(getActivity());
        });
    }


}