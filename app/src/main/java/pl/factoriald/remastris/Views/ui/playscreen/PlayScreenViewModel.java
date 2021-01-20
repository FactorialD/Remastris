package pl.factoriald.remastris.Views.ui.playscreen;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

import pl.factoriald.remastris.Entity.GameField;
import pl.factoriald.remastris.Entity.Setting;
import pl.factoriald.remastris.Entity.Ticker;

import static java.lang.Integer.parseInt;

public class PlayScreenViewModel extends ViewModel {

    private MutableLiveData<GameField> gameField;
    private MutableLiveData<Ticker> ticker;
    private MutableLiveData<HashMap<Setting , String>> settings;


    // TODO: Implement the ViewModel



    public PlayScreenViewModel() {
        this.gameField = new MutableLiveData<>();
        this.ticker = new MutableLiveData<>();
        this.settings = new MutableLiveData<>();
        settings.setValue(new HashMap<>());
        //ticker.setValue(Ticker.getInstance(2000));
        //this.gameField.setValue(new GameField(50,50));

    }

    public MutableLiveData<GameField> getGameField() {
        return gameField;
    }

    public void setLiveDataGameField(GameField gameField) {
        this.gameField.setValue(gameField);
    }

    public MutableLiveData<Ticker> getTicker() {
        return ticker;
    }

    public void setLiveDataTicker(Ticker ticker) {
        this.ticker.setValue(ticker);
    }

    public MutableLiveData<HashMap<Setting, String>> getSettings() {
        return settings;
    }

    public void apply(){

        this.ticker.setValue(Ticker.getNewInstance(parseInt(settings.getValue().get(Setting.TICKER_DELAY))));
        Log.d("APPLY", "delay of applied = " + this.getTicker().getValue().getDelay());
        this.gameField.setValue(
                new GameField(settings.getValue())
        );

    }


}