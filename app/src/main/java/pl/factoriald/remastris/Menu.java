package pl.factoriald.remastris;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import pl.factoriald.remastris.Entity.Gamemode;
import pl.factoriald.remastris.Views.MainActivity;
import pl.factoriald.remastris.Views.ui.aboutmenu.AboutMenuFragment;
import pl.factoriald.remastris.Views.ui.choosegamemodemenu.ChooseGameModeMenu;
import pl.factoriald.remastris.Views.ui.customgamemenu.CustomGameFragment;
import pl.factoriald.remastris.Views.ui.loadgamemenu.LoadGameFragment;
import pl.factoriald.remastris.Views.ui.mainmenu.MainMenuFragment;
import pl.factoriald.remastris.Views.ui.pausescreen.PauseScreenFragment;
import pl.factoriald.remastris.Views.ui.playscreen.PlayScreenFragment;
import pl.factoriald.remastris.Views.ui.scoreboardmenu.ScoreBoardFragment;
import pl.factoriald.remastris.Views.ui.settingsmenu.SettingsMenuFragment;
import pl.factoriald.remastris.Views.ui.shopmenu.ShopMenuFragment;
import pl.factoriald.remastris.Views.ui.startgamemenu.StartGameMenuFragment;

public class Menu {

    public static void openStartGame(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, StartGameMenuFragment.newInstance())
                .addToBackStack("StartGameMenuFragment")
                .commit();
    }

    public static void openShop(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ShopMenuFragment.newInstance())
                .addToBackStack("ShopMenuFragment")
                .commit();
    }

    public static void openScoreboard(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ScoreBoardFragment.newInstance())
                .addToBackStack("ScoreBoardFragment")
                .commit();
    }

    public static void openSettings(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, SettingsMenuFragment.newInstance())
                .addToBackStack("SettingsMenuFragment")
                .commit();
    }

    public static void openAbout(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AboutMenuFragment.newInstance())
                .addToBackStack("AboutMenuFragment")
                .commit();
    }

    public static void exit(FragmentActivity activity) {
        activity.finishAffinity();
    }

    public static void openStartNewGame(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ChooseGameModeMenu.newInstance())
                .addToBackStack("ChooseGameModeMenu")
                .commit();
    }

    public static void openContinueGame(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, LoadGameFragment.newInstance())
                .addToBackStack("LoadGameFragment")
                .commit();
    }

    public static void back(FragmentActivity activity) {
        activity.getSupportFragmentManager().popBackStack();
    }

    public static void openClassicMode(FragmentActivity activity) {
        Log.d("MENU", "Player choose CLASSIC");
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PlayScreenFragment.newInstance(Gamemode.CLASSIC))
                .addToBackStack("PlayScreenFragment")
                .commit();
    }

    public static void openRandomMode(FragmentActivity activity) {
        Log.d("MENU", "Player choose RANDOM");
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PlayScreenFragment.newInstance(Gamemode.RANDOM))
                .addToBackStack("PlayScreenFragment")
                .commit();
    }

    public static void openCustomMode(FragmentActivity activity) {
        Log.d("MENU", "Player choose CUSTOM");
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PlayScreenFragment.newInstance(Gamemode.CUSTOM))
                .addToBackStack("PlayScreenFragment")
                .commit();
    }

    public static void openCustomModeMenu(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, CustomGameFragment.newInstance())
                .addToBackStack("CustomGameFragment")
                .commit();
    }

    public static boolean lastFragmentIs(MainActivity activity, String fragmentName){
        return activity.getSupportFragmentManager().getBackStackEntryAt(
                activity.getSupportFragmentManager().getBackStackEntryCount() - 1)
                .getName().equals(fragmentName);

    }

    public static void openPause(MainActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, PauseScreenFragment.newInstance())
                .addToBackStack("PauseScreenFragment")
                .commit();

    }
    public static void openPause(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, PauseScreenFragment.newInstance())
                .addToBackStack("PauseScreenFragment")
                .commit();

    }

    public static void openMainMenu(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, MainMenuFragment.newInstance())
                .addToBackStack("MainMenuFragment")
                .commit();

    }

    public static void openContinuePlay(FragmentActivity activity) {
        //TODO pausing/continuing
    }
}
