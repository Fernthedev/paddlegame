package com.github.fernthedev.universal.entity;

import com.github.fernthedev.universal.GameObject;
import com.github.fernthedev.universal.ID;
import com.github.fernthedev.universal.UniversalHandler;

import java.awt.*;

public class Ball extends GameObject {



    public Ball(int x, int y, ID id, int objectID) {
        super(x, y, id,objectID);


        velX = 5;
        velY = 5;
    }

    public Ball(GameObject gameObject) {
        super(gameObject);
    }

    public void tick() {
        x += velX;
        y += velY;

        if (x <= 0 || x >= UniversalHandler.WIDTH - 16) velX *= -1;
        if (y <= 0 || y >= UniversalHandler.HEIGHT - 32) velY *= -1;

        UniversalHandler.getThingHandler().addEntityObject(new Trail(x, y, ID.TRAIL, Color.GRAY, 16, 16, 0.02f,GameObject.entities));

        //System.out.println("i was ticked");
    }

    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect((int) x, (int) y, 16, 16);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }
}