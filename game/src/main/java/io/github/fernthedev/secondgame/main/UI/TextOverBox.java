package io.github.fernthedev.secondgame.main.UI;

import io.github.fernthedev.secondgame.main.Game;

import java.awt.*;

public class TextOverBox extends MouseOverUI {

    private String text = "";
    private boolean selected = false;

    TextOverBox(Graphics g, int x, int y, int width, int height, Game.STATE state) {
        super(g, x, y, width, height, state);
    }

    public boolean isSelected() {
        if(Game.gameState != state) return false;

        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    void drawString() {
        if(text == null) text = "";

        string = text;
        super.drawString(text);

    }

    public void render() {
        super.render();
       // System.out.println("Rendering box " + text + " " + selected);

        drawString();
    }

    @Override
    public void onClick() {
        selected = true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
