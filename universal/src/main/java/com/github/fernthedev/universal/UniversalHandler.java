package com.github.fernthedev.universal;

import com.github.fernthedev.universal.entity.EntityPlayer;
import com.google.gson.Gson;

import java.util.Vector;

public class UniversalHandler {

    private static ThingHandler thingHandler;
    private static GameManager gameManager;

    private static UniversalHandler instance = null;

    public static final int WIDTH = 640,HEIGHT =  WIDTH / 12*9;

    public static int tickWait = 15;

    /**
     * Used for running threads.
     */
    public static boolean running = false;

    /**
     * Toggled if is running server or not
     * If run by server, always true
     */
    public static boolean isServer = false;

    /**
     * This is the mainplayer for the game
     */
    public static EntityPlayer mainPlayer = null;

    public static Gson gson = new Gson();

    public static final Vector<Thread> threads = new Vector<>();

    public static synchronized UniversalHandler getInstance() {
        return instance == null ? instance = new UniversalHandler() : instance;
    }

    /**
     * Manages methods aimed for use in game and server differently.
     * @param manager The manager that is extended
     */
    public static void setup(GameManager manager) {
        gameManager = manager;
    }

    public void setup(ThingHandler methodInterface,GameManager manager) {
        thingHandler = methodInterface;
        gameManager = manager;
    }

    public static synchronized GameManager getGameManager() {
        return gameManager;
    }

    public static synchronized ThingHandler getThingHandler() {
        return thingHandler == null ? thingHandler = new ThingHandler() : thingHandler;
    }

    /**
     * CREATING A BOX/LIMIT FOR A VARIABLE
     * @param var Variable being affected
     * @param min The minimum value
     * @param max The Max Value
     * @return Returns the max or min if either var is greater than either, if not returns var
     */
    public static float clamp(float var, float min, float max) {
        if(var >= max) {
          //  System.out.println(max + " is max");
            return max;
        }
        else if(var <= min) {
            //System.out.println(min + " is min");
            return min;
        }
        else {
            //System.out.println(var + " is normal");
            return var;
        }
    }
}
