package io.github.fernthedev.secondgame.main.UI;

import io.github.fernthedev.secondgame.main.Game;

import java.awt.*;

public abstract class TextOverButton extends MouseOverUI {
    private TextOverBox textOverBox;

    public TextOverBox getTextOverBox() {
        return textOverBox;
    }

    TextOverButton(Graphics g, int x, int y, int width, int height, Game.STATE state, TextOverBox textOverBox) {
        super(g, x, y, width, height, state);
        this.textOverBox = textOverBox;
    }

    @Override
    public void onClick() {
       System.out.println(textOverBox.getText());
    }

}
