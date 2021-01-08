package pl.factoriald.remastris.Views.ui.playscreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;
import lombok.Setter;
import pl.factoriald.remastris.Entity.GameField;
import pl.factoriald.remastris.Entity.Ticker;

public class PlayScreenViewModel extends ViewModel {

    private MutableLiveData<GameField> gameField;
    private MutableLiveData<Ticker> ticker;


    // TODO: Implement the ViewModel



    public PlayScreenViewModel() {
        this.gameField = new MutableLiveData<>();

        this.gameField.setValue(new GameField(15,20));

    }

    public LiveData<GameField> getGameField() {
        return gameField;
    }

    public void setLiveDataGameField(GameField gameField) {
        this.gameField.setValue(gameField);
    }
}