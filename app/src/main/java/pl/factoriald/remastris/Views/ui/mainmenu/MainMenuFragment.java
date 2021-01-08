package pl.factoriald.remastris.Views.ui.mainmenu;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.factoriald.remastris.Menu;
import pl.factoriald.remastris.R;

public class MainMenuFragment extends Fragment {

    private MainMenuViewModel mViewModel;

    private Button startGameButton;
    private Button openShopButton;
    private Button openScoreBoardButton;
    private Button openSettingsButton;
    private Button aboutButton;
    private Button exitButton;


    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_menu_fragment, container, false);


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);
        // TODO: Use the ViewModel

        initializeButtons(getActivity());
    }


    private void initializeButtons(FragmentActivity a) {

        startGameButton = a.findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener((e) -> {
            Menu.openStartGame(getActivity());
        });

        openShopButton = a.findViewById(R.id.open_shop_button);
        openShopButton.setOnClickListener((e) -> {
            Menu.openShop(getActivity());
        });

        openScoreBoardButton = a.findViewById(R.id.open_scoreboard_button);
        openScoreBoardButton.setOnClickListener((e) -> {
            Menu.openScoreboard(getActivity());
        });

        openSettingsButton = a.findViewById(R.id.open_settings_button);
        openSettingsButton.setOnClickListener((e) -> {
            Menu.openSettings(getActivity());
        });

        aboutButton = a.findViewById(R.id.open_about_button);
        aboutButton.setOnClickListener((e) -> {
            Menu.openAbout(getActivity());
        });

        exitButton = a.findViewById(R.id.exit_button);
        exitButton.setOnClickListener((e) -> {
            Menu.exit(getActivity());
        });
    }


}