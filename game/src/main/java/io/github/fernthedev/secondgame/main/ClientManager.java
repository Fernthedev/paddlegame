package io.github.fernthedev.secondgame.main;

import com.github.fernthedev.universal.GameManager;

public class ClientManager extends GameManager {
    @Override
    public void onGameOver() {
        Game.stopGame();
    }
}
