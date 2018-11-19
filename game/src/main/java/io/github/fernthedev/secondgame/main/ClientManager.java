package io.github.fernthedev.secondgame.main;

import com.github.fernthedev.universal.GameManager;

/**
 * Handles universal game handling client side
 */
public class ClientManager extends GameManager {
    @Override
    public void onGameOver() {
        Game.stopGame();
    }
}
