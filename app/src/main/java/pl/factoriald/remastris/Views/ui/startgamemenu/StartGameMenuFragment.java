package pl.factoriald.remastris.Views.ui.startgamemenu;

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

public class StartGameMenuFragment extends Fragment {

    private StartGameMenuViewModel mViewModel;

    private Button startNewGameButton;
    private Button continueGameButton;
    private Button backToMainMenuButton;

    public static StartGameMenuFragment newInstance() {
        return new StartGameMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.start_game_menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StartGameMenuViewModel.class);
        // TODO: Use the ViewModel

        initializeButtons(getActivity());
    }

    private void initializeButtons(FragmentActivity a) {


        startNewGameButton = a.findViewById(R.id.start_new_game_button);
        startNewGameButton.setOnClickListener((e) -> {
            Menu.openStartNewGame(getActivity());
        });

        continueGameButton = a.findViewById(R.id.continue_game_button);
        continueGameButton.setOnClickListener((e) -> {
            Menu.openContinueGame(getActivity());
        });


        backToMainMenuButton = a.findViewById(R.id.back_to_main_menu_button);
        backToMainMenuButton.setOnClickListener((e) -> {
            Menu.openMainMenu(getActivity());
        });
    }

}