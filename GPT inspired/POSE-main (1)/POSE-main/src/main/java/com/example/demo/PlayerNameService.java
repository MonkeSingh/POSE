package com.example.demo;
import com.almasb.fxgl.core.EngineService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayerNameService extends EngineService {

    private final StringProperty playerName = new SimpleStringProperty("");

    public String getPlayerName() {
        return playerName.get();
    }

    public StringProperty playerNameProperty() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }
}
