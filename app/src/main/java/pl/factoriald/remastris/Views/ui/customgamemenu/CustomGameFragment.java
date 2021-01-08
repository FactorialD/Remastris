package pl.factoriald.remastris.Views.ui.customgamemenu;

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

public class CustomGameFragment extends Fragment {

    private CustomGameViewModel mViewModel;

    private Button backButton;
    private Button playCustomMode;

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
            Menu.openCustomMode(getActivity());
        });

        backButton = a.findViewById(R.id.back_button10);
        backButton.setOnClickListener((e) -> {
            Menu.back(getActivity());
        });
    }


}