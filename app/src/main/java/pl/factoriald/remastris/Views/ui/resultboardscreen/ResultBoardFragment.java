package pl.factoriald.remastris.Views.ui.resultboardscreen;

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

public class ResultBoardFragment extends Fragment {

    private ResultBoardViewModel mViewModel;

    private Button goToMainMenuButton;

    public static ResultBoardFragment newInstance() {
        return new ResultBoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.result_board_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ResultBoardViewModel.class);
        // TODO: Use the ViewModel
        initializeButtons(getActivity());
    }

    private void initializeButtons(FragmentActivity a) {

        goToMainMenuButton = a.findViewById(R.id.go_main_menu_button2);
        goToMainMenuButton.setOnClickListener((e) -> {
            Menu.openStartGame(getActivity());
        });
    }
}