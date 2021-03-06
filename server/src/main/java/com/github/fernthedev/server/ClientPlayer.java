package com.github.fernthedev.server;

import com.github.fernthedev.packets.GameOverPacket;
import com.github.fernthedev.packets.Packet;
import com.github.fernthedev.universal.entity.EntityPlayer;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;

import static com.github.fernthedev.server.Server.socketList;

public class ClientPlayer {


    //private ObjectOutputStream out;
    //private ObjectInputStream in;

    private Thread thread;

    private boolean connected;
    private ServerThread serverThread;

    public boolean isChanged = false;

    private boolean isClosing = false;

    public boolean isClosing() {
        return isClosing;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    private EntityPlayer playerObject;

    public Channel channel;

    public void setThread(Thread thread, ServerThread serverThread) {
        this.thread = thread;
        this.serverThread = serverThread;
    }

    public boolean isConnected() {
        return connected;
    }

    public ClientPlayer(Channel channel, EntityPlayer universalPlayer) {
        this.channel = channel;
        playerObject = universalPlayer;
        connected = true;
    }

    /**
     * Send packet
     *
     * @param packet The packet to send
     */
    public synchronized void sendObject(Object packet) {
        if (packet instanceof Packet && channel != null) {
            //System.out.println("Sending packet");
            channel.writeAndFlush(packet);

            if (packet instanceof GameOverPacket) close();
            // out.flush();
           /* if(!(packet instanceof PingPacket)) {
                System.out.println("Sent " + packet);
            }*/

        } else {
            System.out.println("not packet");
        }
    }

    /**
     * Close client connection
     */
    public synchronized void close() {
        try {
            isClosing = true;
            serverThread.close();
            System.out.println("Closing player " + this.toString());
            //DISCONNECT FROM SERVER
            //RemovePlayerPacket packet = new RemovePlayerPacket();
            if (channel != null) {
                if (channel.isOpen()) {
                    channel.closeFuture();

                    connected = false;

                    socketList.remove(channel);


                }
            }
            //if(!scanner.nextLine().equals(""))


            connected = false;
            thread.join();

            //serverSocket.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public EntityPlayer getPlayerObject() {
        return playerObject;
    }


    public synchronized void setPlayerObject(EntityPlayer playerObject) {
        this.playerObject = playerObject;
    }

    @Override
    public String toString() {
        return "[ClientPlayer] IP: " + getAdress() + " name " + playerObject;
    }


    /**
     * Get client ip
     * @return The IP, return's "unknown" if no channel is associated
     */
    String getAdress() {
        if (channel == null || channel.remoteAddress() == null) {
            return "unknown";
        }

        return channel.remoteAddress().toString();
    }

    /**
     * Get client object from id
     *
     * @param id The Object ID
     * @return The clientplayer associated with the object id. May return null if not found
     */
    public static ClientPlayer getPlayerFromObject(int id) {
        List<ClientPlayer> clientPlayers = new ArrayList<>(Server.socketList.values());

        for (ClientPlayer clientPlayer : clientPlayers) {
            if (clientPlayer.playerObject.objectID == id) return clientPlayer;
        }

        return null;
    }

    /**
     * Get client from object
     * @param universalPlayer The object associated with the client
     * @return The client associated with the object. May return null if not found
     */
    public static ClientPlayer getPlayerFromObject(EntityPlayer universalPlayer) {
        List<ClientPlayer> clientPlayers = new ArrayList<>(Server.socketList.values());

        for (ClientPlayer clientPlayer : clientPlayers) {
            if (clientPlayer.playerObject == universalPlayer) return clientPlayer;
        }
        return null;
    }


}
