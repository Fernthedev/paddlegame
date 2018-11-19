//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.fernthedev.secondgame.main;

import com.github.fernthedev.packets.Packet;
import com.github.fernthedev.universal.GameObject;
import com.github.fernthedev.universal.ID;
import com.github.fernthedev.universal.UniversalHandler;
import com.github.fernthedev.universal.entity.Ball;
import com.github.fernthedev.universal.entity.EntityPlayer;
import com.github.fernthedev.universal.entity.MenuParticle;
import io.github.fernthedev.secondgame.main.UI.Menu;
import io.github.fernthedev.secondgame.main.inputs.joystick.JoystickHandler;
import io.github.fernthedev.secondgame.main.inputs.keyboard.KeyInput;
import io.github.fernthedev.secondgame.main.netty.client.Client;
import org.lwjgl.Version;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    public static final int WIDTH = 640,HEIGHT =  WIDTH / 12*9;
    private static final long serialVersionUID = -7016397730883067077L;

    private Thread thread;

    public static Graphics g;

    private static Handler handler;
    private final Random r;
    private static HUD hud;
    private final Spawn spawnner;
    private static io.github.fernthedev.secondgame.main.UI.Menu menu;
    private final KeyInput keyInput;

    private EntityPlayer oldPlayer = UniversalHandler.mainPlayer;




    private JoystickHandler testJoyStick;
    public static ClientManager clientManager;

    public static int fern$;

    /**
     * STATE OF PAUSED
     */
    public static boolean paused = false;

    /**
     * DIFFICULTY
     * 0 = NORMAL
     * 1 = HARD
     */
    public int dif = 0;

    //public static int frames = 0;


    /**
     * GAME STATES
     */
    public enum STATE {
        MENU,
        HELP,
        END,
        GAME,
        MULTIPLAYER,
        HOSTING,
        GETTING_CONNECT,
        IN_SERVER,
        JOINING;

        public boolean isMenu() {
            return gameState == MULTIPLAYER || gameState == HELP || gameState == MENU || gameState == JOINING || gameState == GETTING_CONNECT || gameState == END;
        }
    }


    /**
     * VARIABLE FOR ACCESSING STATES
     */
    public static STATE gameState = STATE.MENU;


    public static void sendPacket(Packet packet) {
        Client.getClientThread().sendObject(packet);
    }

    /**
     * MAIN
     */
    private Game() {
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage sprite_sheet = loader.loadImage("/icon.png");
        System.out.println("Loaded icon");

        hud = new HUD();

        handler = new Handler(hud);

        testJoyStick = new JoystickHandler(this);

        menu = new io.github.fernthedev.secondgame.main.UI.Menu(this,handler,hud);
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");
        keyInput = new KeyInput(handler,this);

        this.addKeyListener(keyInput);
        this.addMouseListener(menu);

        new Window(WIDTH, HEIGHT, "A NEW GAME", this);


        this.r = new Random();
        fern$ = this.r.nextInt(100);


        clientManager = new ClientManager();

        System.out.println(fern$);


        spawnner = new Spawn(handler, hud);
        if (gameState == STATE.GAME) {
            handler.addObject(new Ball(r.nextInt(WIDTH - 50),r.nextInt(HEIGHT - 50),ID.BALL, GameObject.entities));
        }else {
            int amount = r.nextInt(15);
            if (amount < 10) amount = 10;
            for (int i = 0; i < amount; i++) {
                handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MENU_PARTICLE, GameObject.entities));
            }
        }

    }


    /**
     * RUNNABLE METHOD
     */
    public void run() {
/*        this.requestFocus();
        long lastTime= System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        //int frames = this.frames;
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames + " or formmated: " + NumberFormat.getNumberInstance(Locale.US).format(frames));
                frames = 0;
            }
        }
        stop();*/
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
      //  long lastLoopTime = System.nanoTime();

        while(UniversalHandler.running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

           // long updateLength = now - lastLoopTime;
           // lastLoopTime = now;

            while(delta >= 1) {
                    tick();
                //System.out.println(UniversalHandler.running);
                //updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
                //updates = 0;
            }



            try {Thread.sleep(UniversalHandler.tickWait);} catch(Exception e) {}

            //try { Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000); } catch (InterruptedException e) {e.printStackTrace(); }
        }





    }


    /**
     * TICK METHOD
     */
    private void tick() {
        try {

            if (gameState == STATE.GAME || gameState == STATE.HOSTING || gameState == STATE.IN_SERVER) {
                //System.out.println("Running thing");

                if (!paused) {

                  //  System.out.println(UniversalHandler.running);
                    hud.tick();


                    if(oldPlayer == null) oldPlayer = UniversalHandler.mainPlayer;



                    if(UniversalHandler.mainPlayer != null && EntityPlayer.isTheSame(oldPlayer,UniversalHandler.mainPlayer)){
                        String[] differences = EntityPlayer.getDifference(UniversalHandler.mainPlayer,oldPlayer);
                        //System.out.println(differences[0] + " old is " + differences[1]);
                        oldPlayer = UniversalHandler.mainPlayer;
                    }



                    if (gameState == STATE.GAME) {
                        UniversalHandler.getThingHandler().tick();
                        spawnner.tick();
                    }


                    if (gameState == STATE.IN_SERVER || gameState == STATE.HOSTING) {

                        handler.serverTick();

                    }


                    keyInput.tick();
                    testJoyStick.tick();



                }
            } else if (gameState == STATE.MENU || gameState == STATE.END || gameState == STATE.HELP || gameState == STATE.MULTIPLAYER || gameState == STATE.GETTING_CONNECT) {
                menu.tick();
                UniversalHandler.getThingHandler().tick();
                testJoyStick.tick();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * Shutdown game.
     */
    public static void stopGame() {
        Random r = new Random();
        gameState = STATE.END;
        int amount = r.nextInt(15);
        if (amount < 10) amount = 10;
        for (int i = 0; i < amount; i++) {
            handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MENU_PARTICLE, GameObject.entities));
        }
    }

    /**
     * RENDERING OBJECTS AND GAME
     */
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

         g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0,WIDTH,HEIGHT);




        handler.render(g);

        if(paused){
            g.setColor(Color.WHITE);
            g.drawString("Paused",100,100);
        }
        if(gameState == STATE.GAME || gameState == STATE.IN_SERVER || gameState == STATE.HOSTING) {
            hud.render(g);
        }else if(gameState == STATE.MENU
                || gameState == STATE.HELP
                || gameState == STATE.END
                || gameState ==  STATE.MULTIPLAYER
                || gameState ==  STATE.JOINING
                || gameState == STATE.GETTING_CONNECT) {
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }



    /**
     * CREATING A BOX/LIMIT FOR A VARIABLE
     * @param var Variable being affected
     * @param min The minimum value
     * @param max The Max Value
     * @return Returns the max or min if either var is greater than either, if not returns var
     */

    public static float clamp(float var, float min, float max) {
        if(var >= max)
            return max;
        else if(var <= min)
            return min;
        else
            return var;
    }

    /**
     * STARTING METHOD
     */
    public static void main(String[] args) {
        new Game();
    }


    public static Handler getHandler() {
        return handler;
    }

    /**
     * Threads
     */
    public synchronized void start() {
        UniversalHandler.running = true;
        thread = new Thread(this);
        thread.start();
        UniversalHandler.threads.add(thread);
        System.out.println(UniversalHandler.threads.size() + " threads");
    }

    public static Menu getMenu() {
        return menu;
    }

    public static HUD getHud() {
        return hud;
    }


    /**
     * Stopping game threads
     */
    public synchronized void stop() {

        try{
            UniversalHandler.running = false;
            for(Thread threade : UniversalHandler.threads) {
                System.out.println("Closing " + threade);
                threade.join();
            }
            thread.join();
            System.exit(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
