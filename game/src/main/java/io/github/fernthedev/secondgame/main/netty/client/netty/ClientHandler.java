package io.github.fernthedev.secondgame.main.netty.client.netty;

import com.github.fernthedev.packets.Packet;
import com.github.fernthedev.universal.GameObject;
import com.github.fernthedev.universal.ID;
import com.github.fernthedev.universal.UniversalHandler;
import com.github.fernthedev.universal.entity.MenuParticle;
import io.github.fernthedev.secondgame.main.Game;
import io.github.fernthedev.secondgame.main.netty.client.Client;
import io.github.fernthedev.secondgame.main.netty.client.EventListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.ConnectException;
import java.util.Random;

import static io.github.fernthedev.secondgame.main.Game.HEIGHT;
import static io.github.fernthedev.secondgame.main.Game.WIDTH;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final EventListener listener;

    public ClientHandler(Client client, EventListener listener) {
        this.listener = listener;
        this.client = client;
    }

    private final Client client;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        Game.gameState = Game.STATE.IN_SERVER;
        Game.getMenu().startGame();
        client.registered = true;

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(msg instanceof Packet) {
            Packet packet = (Packet) msg;
            listener.recieved(packet);

           // if (!(msg instanceof PingPacket))
                //System.out.println("Packet received which is " + msg);
        }

        //ctx.close();
    }

    public void channelUnregistered(ChannelHandlerContext ctx) {
        System.out.println("Unregistered");
        resetGame();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        if(cause.getCause() instanceof ConnectException) {
            resetGame();
        }else ctx.fireExceptionCaught(cause);

        //ctx.fireExceptionCaught(cause);
    }

    private void resetGame() {
        if (!Game.gameState.isMenu()) {
            Game.getHandler().clearObjects();
            Game.gameState = Game.STATE.MENU;

            if(UniversalHandler.mainPlayer != null)
            UniversalHandler.mainPlayer.setHealth(100);

            Random r = new Random();
            int amount = r.nextInt(15);
            if (amount < 10) amount = 10;
            for (int i = 0; i < amount; i++) {

                Game.getHandler().addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MENU_PARTICLE, GameObject.entities));
            }

            Client.getClientThread().disconnect();
        }
    }
}
