package pl.factoriald.remastris.Views.ui.scoreboardmenu;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import pl.factoriald.remastris.Entity.Setting;
import pl.factoriald.remastris.Menu;
import pl.factoriald.remastris.R;

public class ScoreBoardFragment extends Fragment {

    private ScoreBoardViewModel mViewModel;
    private TextView scoreView;

    private Button backButton;

    public static ScoreBoardFragment newInstance() {
        return new ScoreBoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.score_board_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ScoreBoardViewModel.class);
        // TODO: Use the ViewModel

        initializeButtons(getActivity());
    }

    private void initializeButtons(FragmentActivity a) {

        backButton = a.findViewById(R.id.back_button4);
        backButton.setOnClickListener((e) -> {
            Menu.openMainMenu(getActivity());
        });

        scoreView = a.findViewById(R.id.score_textView);
        SharedPreferences prefRead = getContext().getSharedPreferences(Setting.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE);
        String score = prefRead.getString(Setting.LAST_SCORE.toString(), "Not have yet)");
        scoreView.setText(score);
    }

}