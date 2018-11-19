package io.github.fernthedev.secondgame.main.UI;

import io.github.fernthedev.secondgame.main.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mouse button
 */
public abstract class MouseOverUI {

    static List<MouseOverUI> mouseOverUIList = new ArrayList<>();



    protected final int x;
    protected final int width;
    protected final int y;
    protected final int height;

    protected String string = "";
    protected int stringX,stringY;

    protected final Game.STATE state;

    protected final Graphics g;

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    MouseOverUI(Graphics g, int x, int y, int width, int height, Game.STATE state) {
        this.x = x;
        this.width = width;
        this.y = y;
        this.height = height;
        this.state = state;
        this.g = g;

        g.drawRect(x, y, width, height);
        mouseOverUIList.add(this);
    }

    public void render() {
        Game.g.drawRect(x, y, width, height);
        if(string != null)
        Game.g.drawString(string,stringX,stringY);
    }

    void drawString(String string, int stringX, int stringY) {
        Game.g.drawString(string,stringX,stringY);
        this.string = string;
        this.stringX = stringX;
        this.stringY = stringY;
    }

    void drawString(String string) {
        this.drawString(string,x+60,y+40);
    }

    /**
     * Checks for a textbox that is selected
     * @return A selected textbox, if none found return null
     */
    public static TextOverBox isTextBoxSelected() {
        for(MouseOverUI ui : MouseOverUI.mouseOverUIList) {
            if(ui instanceof TextOverBox) {
                TextOverBox textOver = (TextOverBox) ui;
                if (textOver.getState() == Game.gameState && textOver.isSelected()) {
                    return textOver;
                }
            }
        }
        return null;
    }

    public abstract void onClick();


    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getStringX() {
        return stringX;
    }

    public void setStringX(int stringX) {
        this.stringX = stringX;
    }

    public int getStringY() {
        return stringY;
    }

    public void setStringY(int stringY) {
        this.stringY = stringY;
    }

    public Game.STATE getState() {
        return state;
    }

    @Override
    public String toString() {
        return string + " " + stringX + " " + stringY + " box " + x + " " + y + " " + width + ":" + height;
    }
}


