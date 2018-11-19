package com.github.fernthedev.universal;

import com.github.fernthedev.exceptions.NewPlayerAddedException;
import com.github.fernthedev.universal.entity.Trail;
import com.github.fernthedev.universal.entity.EntityPlayer;

import java.util.*;

public class ThingHandler {


    public static Vector<GameObject> gameObjects = new Vector<>();

    public static Map<Integer,GameObject> gameObjectMap = new HashMap<>();

    public List<GameObject> getGameObjects() {
        //System.out.println("Client objects");
        return gameObjects;
    }

    public Map<Integer, GameObject> getGameObjectMap() {
        return gameObjectMap;
    }

    public void setGameObjectMap(Map<Integer, GameObject> gameObjectMap) {
        ThingHandler.gameObjectMap = gameObjectMap;
    }



    public void setGameObjects(List<GameObject> gameObjects) {

       ThingHandler.gameObjects = new Vector<>(gameObjects);
    }


    /**
     * This updates the player object for use of different instances.
     * @param clientPlayerE This is null
     * @param universalPlayer The player instance to use.
     */
    public void updatePlayerObject(Object clientPlayerE, EntityPlayer universalPlayer) {

        GameObject gameObjectOld = gameObjectMap.get(universalPlayer.getObjectID());

        if(gameObjectOld != null)
            removeEntityObject(gameObjectOld);

        addEntityObject(universalPlayer);


        boolean isMainPlayer = universalPlayer == UniversalHandler.mainPlayer || gameObjectOld == UniversalHandler.mainPlayer || UniversalHandler.mainPlayer.getObjectID() == universalPlayer.getObjectID();

        if(isMainPlayer) {
            UniversalHandler.mainPlayer = universalPlayer;
        }
    }

    /**
     * Adds an entity to the list appropiately
     * @param gameObject The entity
     */
    public void addEntityObject(GameObject gameObject) {

        if(gameObject == null) throw new NullPointerException("Added a null pointer");

        if(!gameObjects.contains(gameObject))
            gameObjects.add(gameObject);






        if(gameObject instanceof EntityPlayer)
            try {
                throw new NewPlayerAddedException((EntityPlayer) gameObject);
            } catch (NewPlayerAddedException e) {
                // e.printStackTrace();
            }
        if(!gameObjectMap.containsValue(gameObject))
            gameObjectMap.put(gameObject.getObjectID(),gameObject);



    }


    /**
     * Remove an entity from list
     * @param gameObject The entity
     */
    public void removeEntityObject(GameObject gameObject) {
        if(gameObject == null) throw new NullPointerException();

        gameObjects.remove(gameObject);
        gameObjectMap.remove(gameObject.getObjectID(), gameObject);

        if(gameObjects.contains(gameObject)) {
            System.out.println("Failed to delete variable");
            throw new NullPointerException();
        }


    }


    /**
     * The tick method, do not call
     */
    public void tick() {
        List<GameObject> objects = new ArrayList<>(UniversalHandler.getThingHandler().getGameObjects());

        if (!objects.isEmpty())
            for (GameObject tempObject : objects) {
                tempObject.tick();

                if(tempObject instanceof EntityPlayer)
                    UniversalHandler.getThingHandler().collisionCheck((EntityPlayer) tempObject);
            }
    }

    /**
     * The physics method, do not call
     * @param universalPlayer Player to check
     */
    private void collisionCheck(EntityPlayer universalPlayer) {
        List<GameObject> gameObjects = new ArrayList<>(UniversalHandler.getThingHandler().getGameObjects());
        for (GameObject tempObject : gameObjects) {
            if (tempObject.getId() == ID.BALL && universalPlayer.getBounds().intersects(tempObject.getBounds())) {
                    //COLLISION CODE
                    tempObject.velX = tempObject.velX * -1;
            }

        }
    }

    /**
     * The list of entities without trail objects
     * @param gameObjects1 The list of objects you want to check
     * @return The list of objects without trail
     */
    public synchronized List<GameObject> noTraiLlist(List<GameObject> gameObjects1) {
        List<GameObject> noTrail = new ArrayList<>();

        List<GameObject> gameObjects = new ArrayList<>(gameObjects1);

        for (Iterator<GameObject> iterator = gameObjects.iterator(); iterator.hasNext(); ) {
            GameObject tempObject = iterator.next();
            if(tempObject != null && !(tempObject instanceof Trail)) noTrail.add(tempObject);
        }

        return noTrail;
    }

    /**
     * The list of entities without trail objects
     * @return The list of objects without trail
     */
    public synchronized List<GameObject> noTraiLlist() {
        List<GameObject> noTrail = new ArrayList<>();

        List<GameObject> gameObjects = new ArrayList<>(UniversalHandler.getThingHandler().getGameObjects());
        for (Iterator<GameObject> iterator = gameObjects.iterator(); iterator.hasNext(); ) {
            GameObject tempObject = iterator.next();
            if(tempObject != null && !(tempObject instanceof Trail)) noTrail.add(tempObject);
        }

        return noTrail;
    }
}
