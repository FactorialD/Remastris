package pl.factoriald.remastris.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import pl.factoriald.remastris.Entity.Setting;
import pl.factoriald.remastris.Entity.Ticker;
import pl.factoriald.remastris.Menu;
import pl.factoriald.remastris.R;
import pl.factoriald.remastris.Views.ui.mainmenu.MainMenuFragment;
import pl.factoriald.remastris.Views.ui.pausescreen.PauseScreenFragment;
import pl.factoriald.remastris.Views.ui.playscreen.PlayScreenFragment;

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

        SharedPreferences pref = getSharedPreferences(Setting.PREFERENCES_NAME.toString(), Context.MODE_PRIVATE);
        if(!pref.contains(Setting.HAS_SETTINGS.toString())){
            pref.edit()
                .putString(Setting. DEFAULT_GAMEFIELD_MIN_WIDTH.toString(), "20")
                .putString(Setting. DEFAULT_GAMEFIELD_MIN_HEIGHT.toString(), "20")
                .putString(Setting. DEFAULT_GAMEFIELD_MAX_WIDTH.toString(), "30")
                .putString(Setting. DEFAULT_GAMEFIELD_MAX_HEIGHT.toString(), "30")
                .putString(Setting.DEFAULT_GRAVITY1_DOWN_UPGRADE.toString(), "true")
                .putString(Setting.DEFAULT_GRAVITY1_UP_UPGRADE.toString(), "false")
                .putString(Setting.DEFAULT_GRAVITY1_LEFT_UPGRADE.toString(), "false")
                .putString(Setting.DEFAULT_GRAVITY1_RIGHT_UPGRADE.toString(), "false")
                .putString(Setting.DEFAULT_SPECIAL_BUTTON_UPGRADE.toString(), "false")
                .putString(Setting.DEFAULT_TICKER_MIN_DELAY.toString(), "500")
                .putString(Setting.DEFAULT_TICKER_MAX_DELAY.toString(), "1500")
                    .putString(Setting.FIGURE_SET.toString(), Setting.FIGURE_STANDARD.toString())
                .putString(Setting.HAS_SETTINGS.toString(), "true")
            .commit();

            pref.edit()
                    .putString(Setting. CURRENT_GAMEFIELD_MIN_WIDTH.toString(),      pref.getString(Setting. DEFAULT_GAMEFIELD_MIN_WIDTH.toString(), "20"))
                    .putString(Setting. CURRENT_GAMEFIELD_MIN_HEIGHT.toString(),     pref.getString(Setting. DEFAULT_GAMEFIELD_MIN_HEIGHT.toString(), "20"))
                    .putString(Setting. CURRENT_GAMEFIELD_MAX_WIDTH.toString(),      pref.getString(Setting. DEFAULT_GAMEFIELD_MAX_WIDTH.toString(), "30"))
                    .putString(Setting. CURRENT_GAMEFIELD_MAX_HEIGHT.toString(),     pref.getString(Setting. DEFAULT_GAMEFIELD_MAX_HEIGHT.toString(), "30"))
                    .putString(Setting. CURRENT_GRAVITY1_DOWN_UPGRADE.toString(),    pref.getString(Setting.DEFAULT_GRAVITY1_DOWN_UPGRADE.toString(), "true"))
                    .putString(Setting. CURRENT_GRAVITY1_UP_UPGRADE.toString(),      pref.getString(Setting.DEFAULT_GRAVITY1_UP_UPGRADE.toString(), "false"))
                    .putString(Setting. CURRENT_GRAVITY1_LEFT_UPGRADE.toString(),    pref.getString(Setting.DEFAULT_GRAVITY1_LEFT_UPGRADE.toString(), "false"))
                    .putString(Setting. CURRENT_GRAVITY1_RIGHT_UPGRADE.toString(),   pref.getString(Setting.DEFAULT_GRAVITY1_RIGHT_UPGRADE.toString(), "false"))
                    .putString(Setting. CURRENT_SPECIAL_BUTTON_UPGRADE.toString(),   pref.getString(Setting.DEFAULT_SPECIAL_BUTTON_UPGRADE.toString(), "false"))
                    .putString(Setting. CURRENT_TICKER_MIN_DELAY.toString(),            pref.getString(Setting.DEFAULT_TICKER_MIN_DELAY.toString(), "500"))
                    .putString(Setting. CURRENT_TICKER_MAX_DELAY.toString(),            pref.getString(Setting.DEFAULT_TICKER_MAX_DELAY.toString(), "1500"))
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1 ){
            String tag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            if(Menu.lastFragmentIs(this, "PlayScreenFragment")){
                ((PlayScreenFragment)this.getSupportFragmentManager().getFragments().get(this.getSupportFragmentManager().getFragments().size()-1)).getMViewModel().getTicker().getValue().pause();
                Menu.openPause(this);
            }else if(Menu.lastFragmentIs(this, "PauseScreenFragment")){
               // ((PauseScreenFragment)this.getSupportFragmentManager().getFragments().get(this.getSupportFragmentManager().getFragments().size()-1)).getMViewModel().getTicker().getValue().unpause();
                Ticker ticker = Ticker.getInstanceUnchecked();
                ticker.unpause();
                Menu.back(this);
            }else{
                getSupportFragmentManager().popBackStack();
            }


        } else {
            Menu.exit(this);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.getSupportFragmentManager().getFragments().get(this.getSupportFragmentManager().getFragments().size()-1) instanceof PlayScreenFragment){
            ((PlayScreenFragment)this.getSupportFragmentManager().getFragments().get(this.getSupportFragmentManager().getFragments().size()-1)).getMViewModel().getTicker().getValue().pause();
            Menu.openPause(this);
        }

    }
}