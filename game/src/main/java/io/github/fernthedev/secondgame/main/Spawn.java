//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.fernthedev.secondgame.main;

import com.github.fernthedev.server.gameHandler.ServerSpawn;
import com.github.fernthedev.universal.GameObject;
import com.github.fernthedev.universal.ThingHandler;
import com.github.fernthedev.universal.entity.Ball;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Spawn extends ServerSpawn {

    private final Handler handler;
    private final HUD hud;
    private int scoreKeep = 0;
    private final Random r = new Random();
    private int timer;
    private int nexttimer;

    public Spawn(Handler handler, HUD hud) {
        this.handler = handler;
        this.hud = hud;
    }

    public void tick() {
        scoreKeep++;
        int coinspawn = hud.getScore() + r.nextInt(512);

        hud.setLevel(hud.getLevel() + 1);
        timer++;
        scoreKeep = 0;


        if (timer >= nexttimer) {
            nexttimer = r.nextInt(15) + 7;
            timer = 0;

            List<GameObject> objects = new ArrayList<>(ThingHandler.gameObjects);
            for(GameObject gameObject :  objects) {
                if(gameObject instanceof Ball) {
                    gameObject.setVelX(gameObject.getVelX() +0.5f);

                    gameObject.setVelY(gameObject.getVelY() +0.5f);
                }
            }
            scoreKeep = 250;
            hud.setLevel(hud.getLevel() - 1);
        }

    }
}
