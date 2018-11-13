package com.github.fernthedev.server.gameHandler;

import com.github.fernthedev.packets.LevelUp;
import com.github.fernthedev.server.Server;

import java.util.Random;

public class ServerSpawn {

    private int scoreKeep = 0;
    private final Random r = new Random();
    private int timer;
    private int nexttimer;
    private boolean spawned = false;

    private static int levels;

    public void tick() {
        scoreKeep++;
        if (scoreKeep >= 250) {
            timer++;
            scoreKeep = 0;

            if (Server.socketList.size() > 1 && !spawned) {
                spawned = true;
               // UniversalHandler.getThingHandler().addEntityObject(new Ball(r.nextInt(UniversalHandler.WIDTH - 50),r.nextInt(UniversalHandler.HEIGHT - 50),ID.BALL, GameObject.entities));
            }

            if (Server.socketList.size() < 1) {
                EntityHandler.gameObjects.clear();
            }
        }


        if (timer >= nexttimer) {
            nexttimer = r.nextInt(15) + 7;
            timer = 0;

            levels++;
            Server.sendObjectToAllPlayers(new LevelUp(levels));
            scoreKeep = 250;

        }
    }
}
