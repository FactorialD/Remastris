package pl.factoriald.remastris.Views.ui.loadgamemenu;

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

public class LoadGameFragment extends Fragment {

    private LoadGameViewModel mViewModel;

    private Button backButton;

    public static LoadGameFragment newInstance() {
        return new LoadGameFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.load_game_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoadGameViewModel.class);
        // TODO: Use the ViewModel
        initializeButtons(getActivity());
    }

    private void initializeButtons(FragmentActivity a) {

        backButton = a.findViewById(R.id.back_button9);
        backButton.setOnClickListener((e) -> {
            Menu.openStartGame(getActivity());
        });
    }

}