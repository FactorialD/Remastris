package pl.factoriald.remastris.Views.ui.choosegamemodemenu;

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

public class ChooseGameModeMenu extends Fragment {

    private ChooseGameModeMenuViewModel mViewModel;

    private Button startClassicModeButton;
    private Button startRandomModeButton;
    private Button startCustomModeButton;
    private Button backButton;

    public static ChooseGameModeMenu newInstance() {
        return new ChooseGameModeMenu();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_game_mode_menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChooseGameModeMenuViewModel.class);
        // TODO: Use the ViewModel
        initializeButtons(getActivity());
    }

    private void initializeButtons(FragmentActivity a) {

        startClassicModeButton = a.findViewById(R.id.start_classic_mode_button);
        startClassicModeButton.setOnClickListener((e) -> {
            Menu.openClassicMode(getActivity());
        });

        startRandomModeButton = a.findViewById(R.id.start_random_mode_button);
        startRandomModeButton.setOnClickListener((e) -> {
            Menu.openRandomMode(getActivity());
        });

        startCustomModeButton = a.findViewById(R.id.start_custom_mode_button);
        startCustomModeButton.setOnClickListener((e) -> {
            Menu.openCustomModeMenu(getActivity());
        });

        backButton = a.findViewById(R.id.back_button2);
        backButton.setOnClickListener((e) -> {
            Menu.back(getActivity());
        });

    }

}