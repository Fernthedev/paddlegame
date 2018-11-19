package io.github.fernthedev.secondgame.main.UI;

import io.github.fernthedev.secondgame.main.Game;

import java.awt.*;

/**
 * A text box
 */
public class TextOverBox extends MouseOverUI {

    private String text = "";
    private boolean selected = false;

    TextOverBox(Graphics g, int x, int y, int width, int height, Game.STATE state) {
        super(g, x, y, width, height, state);
    }

    /**
     * Returns if it is selected
     * @return if selected
     */
    public boolean isSelected() {
        if(Game.gameState != state) return false;

        return selected;
    }


    /**
     * If selected or not
     * @param selected The boolean
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    /**
     * Draw input text
     */
    void drawString() {
        if(text == null) text = "";

        string = text;
        super.drawString(text);

    }

    /**
     * Render method
     */
    public void render() {
        super.render();

        drawString();
    }

    /**
     * On click
     */
    @Override
    public void onClick() {
        selected = true;
    }

    /**
     * Get input text
     * @return input text
     */
    public String getText() {
        return text;
    }

    /**
     * Set input text
     * @param text Text
     */
    public void setText(String text) {
        this.text = text;
    }

}
