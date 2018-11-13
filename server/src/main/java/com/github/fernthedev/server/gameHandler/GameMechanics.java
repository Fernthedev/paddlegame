package com.github.fernthedev.server.gameHandler;

import com.github.fernthedev.packets.GameOverPacket;
import com.github.fernthedev.server.ClientPlayer;
import com.github.fernthedev.server.Server;
import com.github.fernthedev.universal.GameManager;
import com.github.fernthedev.universal.GameObject;
import com.github.fernthedev.universal.UniversalHandler;
import com.github.fernthedev.universal.entity.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class GameMechanics extends GameManager implements Runnable {

    public void tick() {

        ArrayList<GameObject> objects = new ArrayList<>(EntityHandler.gameObjects);

        for(GameObject gameObject : objects) {
            if(gameObject instanceof EntityPlayer) {
                entityCheck((EntityPlayer) gameObject);

            }
        }

        /*for(NetPlayer gameObject : EntityHandler.playerList) {
            ClientPlayer clientPlayer = ClientPlayer.getPlayerFromObject(gameObject.getObjectID());
            if(clientPlayer != null)
            entityCheck(clientPlayer.getPlayerObject());
        }*/
    }


    private synchronized void entityCheck(EntityPlayer playerObject) {

        List<ClientPlayer> clientPlayerList = new ArrayList<>(Server.socketList.values());

        //List<GameObject> objects = new ArrayList<>(EntityHandler.gameObjects);

        for (ClientPlayer clientPlayer : clientPlayerList) {

            if (playerObject.getHealth() <= 0 && clientPlayer != null) {
                clientPlayer.sendObject(new GameOverPacket());
                clientPlayer.close();
            }
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(UniversalHandler.running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                //System.out.println(UniversalHandler.running);
                //updates++;
                delta--;
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                //updates = 0;
            }
            try {Thread.sleep(UniversalHandler.tickWait);} catch(Exception e) {}
        }
    }

    @Override
    public void onGameOver() {
        Server.sendObjectToAllPlayers(new GameOverPacket());
    }
}
