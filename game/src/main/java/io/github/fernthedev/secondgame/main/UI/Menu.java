package io.github.fernthedev.secondgame.main.UI;

import com.github.fernthedev.server.Server;
import com.github.fernthedev.universal.GameObject;
import com.github.fernthedev.universal.ID;
import com.github.fernthedev.universal.UniversalHandler;
import com.github.fernthedev.universal.entity.Ball;
import com.github.fernthedev.universal.entity.EntityPlayer;
import io.github.fernthedev.secondgame.main.Game;
import io.github.fernthedev.secondgame.main.HUD;
import io.github.fernthedev.secondgame.main.Handler;
import io.github.fernthedev.secondgame.main.netty.client.Client;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("EmptyMethod")
public class Menu extends MouseAdapter {

    private final Handler handler;
    private final Random r = new Random();
    private final HUD hud;

    private List<MouseOverUI> keepUIs = new ArrayList<>();

    private Game.STATE lastGameState;

    private int dots = 0;
    int ticked = 0;

    public void render(Graphics g) {
        MouseOverUI button;




        if(Game.gameState == Game.STATE.MENU) {
            MouseOverUI.mouseOverUIList = new ArrayList<>();
            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 30);

            g.setFont(fnt);
            g.setColor(Color.WHITE);
            g.drawString("MENU", 240, 70);

            g.setFont(fnt2);
            g.setColor(Color.WHITE);



            button = new MouseOverUI(g,210,150,200,64, Game.STATE.MENU) {
                @Override
                public void onClick() {
                    Game.gameState = Game.STATE.GAME;
                    startGame();
                }
            };

            button.drawString("Play",270,190);



           // g.drawRect(210, 150, 200, 64);
          //  g.drawString("Play", 270, 190);

           // g.drawRect(210, 150, 200, 64);
        //    g.drawString("Helpi", 270, 190);

            button = new MouseOverUI(g, 210, 70, 200, 64, Game.STATE.MENU) {
                @Override
                public void onClick() {
                    Game.gameState = Game.STATE.MULTIPLAYER;
                }
            };
            button.drawString("MULTIPLAYER",230,110);


            button = new MouseOverUI(g,210,250,200,64, Game.STATE.MENU) {
                @Override
                public void onClick() {
                    Game.gameState = Game.STATE.HELP;
                }
            };

            button.drawString("HELP",270,290);


           // g.drawRect(210, 250, 200, 64);
       //     g.drawString("HELP", 270, 290);

            button = new MouseOverUI(g,210,350,200,64, Game.STATE.MENU) {
                @Override
                public void onClick() {
                    System.exit(0);
                }
            };
            button.drawString("Quit",270,390);

        //    g.drawRect(210, 350, 200, 64);
        //    g.drawString("Quit", 270, 390);


        }else if(Game.gameState == Game.STATE.HELP) {
            MouseOverUI.mouseOverUIList = new ArrayList<>();

            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 30);

            g.setFont(fnt);
            g.setColor(Color.WHITE);
            g.drawString("HELP", 240, 70);

            g.setFont(fnt2);
            g.drawString("Use WASD or arrow keys please",150,200);


            g.setFont(fnt2);

            button = new MouseOverUI(g,210,350,200,64, Game.STATE.HELP) {
                @Override
                public void onClick() {
                    Game.gameState = Game.STATE.MENU;
                }
            };
            button.drawString("Back",270,390);


           // /g.drawRect(210, 350, 200, 64);
          //  g.drawString("Back", 270, 390);
        }else if(Game.gameState == Game.STATE.END) {
            MouseOverUI.mouseOverUIList = new ArrayList<>();

            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 30);

            g.setFont(fnt);
            g.setColor(Color.WHITE);
            g.drawString("GAME Over", 180, 70);

            g.setFont(fnt2);
            g.drawString("Score: " + hud.getScore(),150,200);
            g.drawString("Level: " + hud.getLevel(),150,230);





            button = new MouseOverUI(g,210,350,200,64, Game.STATE.END) {
                @Override
                public void onClick() {
                    Game.gameState = Game.STATE.MENU;
                    hud.setLevel(1);
                    hud.setScore(0);
                }
            };

            button.drawString("Try again",245,390);


           // g.drawRect(210, 350, 200, 64);
        //    g.drawString("Try Again", 245, 390);


        }else if (Game.gameState == Game.STATE.MULTIPLAYER) {
            MouseOverUI.mouseOverUIList = new ArrayList<>();

            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 30);

            g.setFont(fnt);
            g.setColor(Color.WHITE);
            g.drawString("MULTIPLAYER", 240, 70);

            g.setFont(fnt2);


            button = new MouseOverUI(g,210,250,200,64,Game.STATE.MULTIPLAYER) {
                @Override
                public void onClick() {
                    Game.gameState = Game.STATE.GETTING_CONNECT;


                    /*startGame();

                    Client client = new Client("localhost",2000);
                    Thread thread = new Thread(client);

                    thread.start();
                    UniversalHandler.threads.add(thread);*/


                }
            };

            button.drawString("Join");


            button = new MouseOverUI(g,210,160,200,64, Game.gameState) {
                @Override
                public void onClick() {
                    Game.gameState = Game.STATE.MENU;
                }
            };
            button.drawString("Back");



            button = new MouseOverUI(g,210,350,200,64, Game.STATE.MULTIPLAYER) {
                @Override
                public void onClick() {
                    Game.gameState = Game.STATE.HOSTING;
                    System.out.println(Game.gameState + "\n");
                    startGame();

                    UniversalHandler.isServer = true;

                    Server server = new Server(2000);
                    server.setPlayerStarter(UniversalHandler.mainPlayer);

                    Thread thread = new Thread(server);
                    server.setThread(thread);
                    thread.start();
                    UniversalHandler.threads.add(thread);

                    System.out.println(UniversalHandler.threads.size() + " threads");
                    //UniversalHandler.getThingHandler().addEntityObject();
                   // server.startServer();
                }
            };
            button.drawString("Host",270,390);


        } else if(Game.gameState == Game.STATE.GETTING_CONNECT) {

            MouseOverUI.mouseOverUIList = new ArrayList<>();

            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 30);

            g.setFont(fnt);
            g.setColor(Color.WHITE);
            g.drawString("ADDRESS", 240, 70);

            g.setFont(fnt2);

            button = new MouseOverUI(g,210,160,200,64, Game.gameState) {
                @Override
                public void onClick() {
                    Game.gameState = lastGameState;
                }
            };
            button.drawString("Back");

            if(keepUIs.isEmpty()) {
                TextOverBox textBox = new TextOverBox(g, 210, 350, 200, 64, Game.STATE.GETTING_CONNECT);


                button = new TextOverButton(g, 210, 250, 200, 64, Game.STATE.GETTING_CONNECT, textBox) {
                    @Override
                    public void onClick() {
                        Game.gameState = Game.STATE.JOINING;
                        String address = getTextOverBox().getText();

                        Client client = new Client(address, 2000);
                        Thread thread = new Thread(client);

                        thread.start();
                        UniversalHandler.threads.add(thread);


                    }
                };

                button.drawString("Join");


                keepUIs.add(textBox);
                keepUIs.add(button);
            }
        } else if (Game.gameState == Game.STATE.JOINING) {
            MouseOverUI.mouseOverUIList = new ArrayList<>();

            Font fnt = new Font("arial", Font.BOLD, 50);
            Font fnt2 = new Font("arial", Font.BOLD, 30);

            g.setFont(fnt);
            g.setColor(Color.WHITE);

            StringBuilder dotsThing = new StringBuilder();
            ticked++;
            if(ticked >= 100) {
                ticked = 0;
                dots++;
            }

            if(dots >= 4 || dots == 0) dots = 1;

            for(int i = 0; i < dots;i++) dotsThing.append(".");

            g.drawString("CONNECTING" +dotsThing, 160, 70);

        }

        List<MouseOverUI> checkUis = new ArrayList<>(keepUIs);

        for(MouseOverUI mouseOverUI : checkUis) {
            if(mouseOverUI.getState() != Game.gameState){
                keepUIs.remove(mouseOverUI);
                continue;
            }

            mouseOverUI.render();
        }

        MouseOverUI.mouseOverUIList.addAll(checkUis);
    }

    public Menu(Game game,Handler handler,HUD hud) {
        this.hud = hud;
        this.handler = handler;
    }

    @Deprecated
    public void tick() {

    }

    /**
     * Start the game accordingly
     */
    public void startGame() {
        if (Game.gameState != Game.STATE.IN_SERVER) {
            EntityPlayer player = new EntityPlayer(Game.WIDTH / 2 - 32, Game.HEIGHT / 2 - 32, ID.PLAYER, GameObject.entities);
            UniversalHandler.mainPlayer = player;

            handler.addObject(player);
        }
        handler.clearObjects();

        if (Game.gameState == Game.STATE.IN_SERVER) {
            UniversalHandler.isServer = true;
        }

        if (Game.gameState == Game.STATE.GAME) {
            UniversalHandler.setup(Game.clientManager);
            UniversalHandler.isServer = false;
            handler.addObject(new Ball(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BALL, GameObject.entities));

        }
    }


    /**
     * Mouse event
     * @param e Event variable
     */
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //System.out.println(GAME.gameState);
        for(MouseOverUI ui : MouseOverUI.mouseOverUIList) {
            if(ui.getState() == Game.gameState) {
                boolean result = mouseOver(mx, my, ui);
                if (result) {
                    Game.STATE rememberState = Game.gameState;
                    ui.onClick();



                    if(Game.gameState != rememberState) lastGameState = rememberState;
                    //System.out.println(Game.gameState + "\n");
                    return;
                }else{
                    if(ui instanceof TextOverBox) {
                        TextOverBox textBox = (TextOverBox) ui;
                        textBox.setSelected(false);
                    }
                }
            }
        }
    }



    public void mouseReleased(MouseEvent e) {

    }

    private boolean mouseOver(int mx,int my,int x, int y, int width, int height) {
        return (mx > x && mx < x + width) && (my > y && my < y + height);
    }

    private boolean mouseOver(int mx, int my, MouseOverUI ui) {
        return (mx > ui.getX() && mx < ui.getX() + ui.getWidth()) && (my > ui.getY() && my < ui.getY() + ui.getHeight());
    }




}
