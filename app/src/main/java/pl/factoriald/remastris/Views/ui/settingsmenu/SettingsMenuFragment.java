package pl.factoriald.remastris.Views.ui.settingsmenu;

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
import android.widget.RadioButton;

import pl.factoriald.remastris.Entity.Setting;
import pl.factoriald.remastris.Menu;
import pl.factoriald.remastris.R;

public class SettingsMenuFragment extends Fragment {

    private SettingsMenuViewModel mViewModel;

    private Button backButton;
    private RadioButton standardButton;
    private RadioButton advancedButton;

    public static SettingsMenuFragment newInstance() {
        return new SettingsMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsMenuViewModel.class);
        // TODO: Use the ViewModel
        initializeButtons(getActivity());
    }

    private void initializeButtons(FragmentActivity a) {

        backButton = a.findViewById(R.id.back_button7);
        backButton.setOnClickListener((e) -> {
            Menu.back(getActivity());
        });

        standardButton = a.findViewById(R.id.figure_set_standard_button);
        standardButton.setOnClickListener(view ->{
            SharedPreferences pref = getContext().getSharedPreferences(Setting.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE);
                pref.edit()
                        .putString(Setting.FIGURE_SET.toString(), Setting.FIGURE_STANDARD.toString())
                        .commit();
        });
        advancedButton = a.findViewById(R.id.figure_set_advanced_button);
        advancedButton.setOnClickListener(view ->{
            SharedPreferences pref = getContext().getSharedPreferences(Setting.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE);
            pref.edit()
                    .putString(Setting.FIGURE_SET.toString(), Setting.FIGURE_ADVANCED.toString())
                    .commit();
        });
    }

}