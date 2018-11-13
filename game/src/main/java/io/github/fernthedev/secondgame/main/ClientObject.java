package io.github.fernthedev.secondgame.main;

import com.github.fernthedev.universal.GameObject;
import com.github.fernthedev.universal.ID;
import com.github.fernthedev.universal.entity.*;


import java.awt.*;

public class ClientObject extends GameObject {
        public ClientObject(float x, float y, ID id, int objectID) {
            super(x, y, id,objectID);
        }



        public ClientObject(GameObject gameObject) {
            super(gameObject);
        }

        public static GameObject getObjectType(GameObject gameObject) {
            switch (gameObject.id) {
                case PLAYER:
                    if(!(gameObject instanceof EntityPlayer))
                    return new EntityPlayer(gameObject);
                    else return gameObject;
                case BALL:
                    if(!(gameObject instanceof Ball))
                    return new Ball(gameObject);
                    else return gameObject;
                case MENU_PARTICLE:
                    if(!(gameObject instanceof MenuParticle))
                    return new MenuParticle(gameObject);
                    else return gameObject;
                case TRAIL:
                    if(!(gameObject instanceof Trail))
                        return new Trail(gameObject);
                        else return gameObject;
                default:
                    return gameObject;
            }
        }

    public static GameObject getObjectType(GsonObject gameObject) {
        switch (gameObject.id) {
            case PLAYER:
                return new EntityPlayer(gameObject);
            case BALL:
                return new Ball(gameObject);
            case MENU_PARTICLE:
                return new MenuParticle(gameObject);
            case TRAIL:
                return new Trail(gameObject);
            default:
                return gameObject;
        }
    }

        @Override
        public void tick() {
        }

        @Override
        public void render(Graphics g) {
        }

        @Override
        public Rectangle getBounds() {
            return null;
        }
}
