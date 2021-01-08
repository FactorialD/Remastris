package pl.factoriald.remastris.Views.ui.aboutmenu;

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

public class AboutMenuFragment extends Fragment {

    private AboutMenuViewModel mViewModel;

    private Button backButton;

    public static AboutMenuFragment newInstance() {
        return new AboutMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AboutMenuViewModel.class);
        // TODO: Use the ViewModel

        initializeButtons(getActivity());
    }

    private void initializeButtons(FragmentActivity a) {

        backButton = a.findViewById(R.id.back_button5);
        backButton.setOnClickListener((e) -> {
            Menu.back(getActivity());
        });
    }
}