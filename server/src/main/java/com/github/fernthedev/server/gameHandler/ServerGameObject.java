package com.github.fernthedev.server.gameHandler;

import com.github.fernthedev.universal.GameObject;
import com.github.fernthedev.universal.ID;
import com.github.fernthedev.universal.entity.*;

import java.awt.*;

@Deprecated
public class ServerGameObject extends GameObject {
    public ServerGameObject(float x, float y, ID id,int objectID) {
        super(x, y, id,objectID);
    }

    public static GameObject getObjectType(GameObject gameObject) {
        //if(gameObject.id == ID.SmartEnemy) {
        ///        return new SmartEnemy(gameObject);
        //     }
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

            default:
                return gameObject;
        }
    }




    public ServerGameObject(GameObject gameObject) {
        super(gameObject);
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
