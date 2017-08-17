package com.exorath.service.gamelevel.service;

import com.exorath.service.gamelevel.Service;
import com.exorath.service.gamelevel.res.AddExperienceSuccess;
import com.exorath.service.gamelevel.res.LevelFunction;
import com.exorath.service.gamelevel.res.LevelPlayer;
import com.exorath.service.gamelevel.res.Success;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;

/**
 * Created by toonsev on 8/16/2017.
 */
public class MongoService implements Service {

    private LevelFunction levelFunction;
    private MongoCollection<Document> playersCollection;

    public MongoService(MongoClient client, String databaseName, LevelFunction levelFunction) {
        MongoDatabase db = client.getDatabase(databaseName);
        playersCollection = db.getCollection("players");
        playersCollection.createIndex(new Document("playerId", 1).append("gameId", 1));
        this.levelFunction = levelFunction;
    }

    @Override
    public LevelFunction getLevelFunction() {
        return levelFunction;
    }

    @Override
    public AddExperienceSuccess addExperience(String gameId, String playerUuid, int xp) {
        try {
            Document returnedDocument = playersCollection.findOneAndUpdate(
                    getPlayerQuery(playerUuid, gameId),
                    new Document("$inc", new Document("xp", xp)).append("$inc", new Document("totalXp", xp)),
                    new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
            LevelPlayer updatedPlayer = fromDoc(returnedDocument);
            return new AddExperienceSuccess(handleLevelUp(updatedPlayer));
        } catch (Exception e) {
            e.printStackTrace();
            return new AddExperienceSuccess(e.getMessage(), -1);
        }
    }

    //Ugly function to recursively levelup the player
    public boolean handleLevelUp(LevelPlayer updated) {
        int required = getLevelFunction().getXp(updated.getLvl() + 1);
        if (required <= updated.getXp()) {
            if (levelUp(updated.getPlayerId(), updated.getGameId(), updated.getLvl() + 1, required)) {
                LevelPlayer newPlayer = new LevelPlayer(updated.getPlayerId(), updated.getGameId(), updated.getLvl() + 1, updated.getXp() - required, updated.getTotalXp(), null);
                handleLevelUp(newPlayer);
                return true;
            }
        }
        return false;

    }

    private Document getPlayerQuery(String playerUuid, String gameId) {
        return new Document("playerId", playerUuid).append("gameId", gameId);
    }

    private boolean levelUp(String playerUuid, String gameId, int newLevel, int deductExperience) {
        Document query = getPlayerQuery(playerUuid, gameId);
        if(newLevel == 1)
            query.append("lvl", new Document("$exists", false));
        else
            query.append("lvl", newLevel - 1);
        UpdateResult updateResult = playersCollection.updateOne(
                query,
                new Document("$set", new Document("lvl", newLevel)).append("$inc", new Document("xp", -deductExperience)).append("$push", new Document("consumable", newLevel))
        );
        return updateResult.getModifiedCount() > 0;
    }

    private LevelPlayer fromDoc(Document document) {
        List<Integer> consumable = null;
        if (document.containsKey("consumable"))
            consumable = document.get("consumable", List.class);
        return new LevelPlayer(document.getString("playerId"), document.getString("gameId"), document.getInteger("lvl", 0), document.getInteger("xp", 0), document.getInteger("totalXp", 0), consumable);
    }

    @Override
    public LevelPlayer getPlayer(String gameId, String playerUuid) {
        try {
            Document returnedDoc = playersCollection.find(getPlayerQuery(playerUuid, gameId)).first();
            return returnedDoc == null ? new LevelPlayer(playerUuid, gameId) : fromDoc(returnedDoc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Success consumeLevel(String gameId, String playerUuid, int level) {
        UpdateResult updateResult = playersCollection.updateOne(
                getPlayerQuery(playerUuid, gameId).append("consumable", new Document("$in", Arrays.asList(new Integer[]{level}))),
                new Document("$pull", new Document("consumable", level))
        );
        return new Success(updateResult.getModifiedCount() > 0);
    }
}
