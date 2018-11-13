package com.github.fernthedev.universal.entity;

import com.github.fernthedev.universal.GameObject;
import com.github.fernthedev.universal.ID;
import com.github.fernthedev.universal.UniversalHandler;
import com.github.fernthedev.universal.Velocity;

import java.awt.*;
import java.util.Random;

public class EntityPlayer extends GameObject {

    protected Color color;
    protected int health = 100;


    /**
     * This is used for keeping velocity while using different coordinates.
     * @param keepPlayer The player with the velocity to keep
     * @param newPlayer The player with the coordinates to keep
     */
    public EntityPlayer(EntityPlayer keepPlayer, EntityPlayer newPlayer) {
        this.velX = keepPlayer.getVelX();
        this.velY = keepPlayer.getVelY();

        this.health = newPlayer.getHealth();
        this.color = newPlayer.getColor();

        this.objectID = newPlayer.getObjectID();
        this.id = ID.PLAYER;

        this.x = newPlayer.getX();
        this.y = newPlayer.getY();
    }

    public EntityPlayer(GameObject gameObject) {
        super(gameObject);


        if(gameObject instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) gameObject;
            this.health = player.health;
            this.color = player.color;


            this.velY = player.getVelY();
            this.velX = player.getVelX();
            this.health = player.getHealth();
        }
    }

    public EntityPlayer(GsonObject gameObject) {
        super(gameObject);


            this.health = gameObject.getHealth();
            this.color = gameObject.getColor();

            this.velY = gameObject.getVelY();
            this.velX = gameObject.getVelX();
            this.health = gameObject.getHealth();

    }

    public EntityPlayer(int x, int y, ID id, int objectID) {
        super(x, y, id,objectID);

        Random r = new Random();

        this.color = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
    }

    public EntityPlayer(int x, int y, ID id, Velocity velX, Velocity velY, int objectID) {
        super(x, y, id,objectID,velX,velY);

        Random r = new Random();

        this.color = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
    }

    /////////////////////////////////////////////////////////////////////////////////////


    public EntityPlayer(EntityPlayer universalPlayer) {
        super(universalPlayer);
        this.color = universalPlayer.getColor();
        this.health = universalPlayer.getHealth();
    }

    public void tick() {
        UniversalHandler.getThingHandler().addEntityObject(new Trail(x,y,ID.TRAIL,color,32,32,0.05f, GameObject.entities));
            this.x = x + velX;
            this.y = y + velY;

           // System.out.println(x + " " + y + " " + velX + " " + velY + " oooooooooooooooooooooooooooold " + (x + velX) + " " + (y + velY));
            x = UniversalHandler.clamp(x,0,UniversalHandler.WIDTH - 37f);
            y = UniversalHandler.clamp(y,0,UniversalHandler.HEIGHT - 60f);


        /*
        x += velX;
        y += velY;
        x = Game.clamp(x,0,Game.WIDTH - 37f);
        y = Game.clamp(y,0,Game.HEIGHT - 60f);
        if(Game.gameState == Game.STATE.Game) {
            handler.addObject(new TRAIL(x, y, ID.TRAIL, Color.WHITE, 32, 32, 0.05f, GameObject.entities));
            collision();

        }*/

    }

    public static boolean isTheSame(EntityPlayer entityPlayer1,EntityPlayer entityPlayer2) {
        return entityPlayer1.getVelX() == entityPlayer2.getVelX() &&
                entityPlayer1.getVelY() == entityPlayer2.getVelY() &&
                entityPlayer1.getX() == entityPlayer2.getX() &&
                entityPlayer1.getY() == entityPlayer2.getY() &&
                entityPlayer1.getColor() == entityPlayer2.getColor() &&
                entityPlayer1.getHealth() == entityPlayer2.getHealth();
    }

    public static String[] getDifference(EntityPlayer entityPlayer1,EntityPlayer entityPlayer2) {
        String returnValueP1 = "";
        String returnValueP2 = "";
        if(entityPlayer1.getVelX() == entityPlayer2.getVelX()) {
            returnValueP1 += entityPlayer1.getVelX() + " velX ";
            returnValueP2 += entityPlayer2.getVelX() + " velX ";
        }
        if(entityPlayer1.getVelY() == entityPlayer2.getVelY()) {
            returnValueP1 += entityPlayer1.getVelY() + " velY ";
            returnValueP2 += entityPlayer2.getVelY() + " velY ";
        }

        if(entityPlayer1.getX() == entityPlayer2.getX()) {
            returnValueP1 += entityPlayer1.getX() + " X ";
            returnValueP2 += entityPlayer2.getX() + " X ";
        }

        if(entityPlayer1.getY() == entityPlayer2.getY()) {
            returnValueP1 += entityPlayer1.getY() + " Y ";
            returnValueP2 += entityPlayer2.getY() + " Y ";
        }

        if(entityPlayer1.getColor() == entityPlayer2.getColor()) {
            returnValueP1 += entityPlayer1.getColor() + " Color ";
            returnValueP2 += entityPlayer2.getColor() + " Color ";
        }

        if(entityPlayer1.getHealth() == entityPlayer2.getHealth()) {
            returnValueP1 += entityPlayer1.getHealth() + " Health ";
            returnValueP2 += entityPlayer2.getHealth() + " Health ";
        }

        return new String[]{returnValueP1, returnValueP2};
    }


    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, 32, 32);
    }



    public Color getColor() {
        return color;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }
}
