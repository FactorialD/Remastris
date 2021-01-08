package pl.factoriald.remastris.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pl.factoriald.remastris.Menu;
import pl.factoriald.remastris.R;
import pl.factoriald.remastris.Views.ui.mainmenu.MainMenuFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainMenuFragment.newInstance())
                    .addToBackStack("MainMenuFragment")
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1 ){
            String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            if(Menu.lastFragmentIs(this, "PlayScreenFragment")){
                Menu.openPause(this);
            }else{
                getSupportFragmentManager().popBackStack();
            }

        } else {
            Menu.exit(this);

        }
    }

}