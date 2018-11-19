package io.github.fernthedev.secondgame.main.UI;

import io.github.fernthedev.secondgame.main.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A button that is accessing inputs from text boxes assigned to it
 */
public abstract class TextOverButton extends MouseOverUI {
    private List<TextOverBox> textOverBox;

    /**
     * Get text boxes assigned
     * @return All text boxes assigned
     */
    public List<TextOverBox> getTextOverBoxes() {
        return textOverBox;
    }

    /**
     * If is only one text box
     * @return The first textbox in the list
     */
    public TextOverBox getTextOverBox() {
        return textOverBox.get(0);
    }

    TextOverButton(Graphics g, int x, int y, int width, int height, Game.STATE state, TextOverBox... textOverBox) {
        super(g, x, y, width, height, state);
        this.textOverBox = new ArrayList<>(Arrays.asList(textOverBox));
    }

}
