package com.exorath.service.gamelevel;

import com.exorath.service.commons.portProvider.PortProvider;
import com.exorath.service.gamelevel.res.AddExperienceSuccess;
import com.exorath.service.gamelevel.res.Success;
import com.google.gson.Gson;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;


/**
 * Created by toonsev on 8/16/2017.
 */
public class Transport {
    private static final Gson GSON = new Gson();

    public static void setup(Service service, PortProvider portProvider){
        port(portProvider.getPort());
        post("/games/:gameId/players/:uuid/xp/:xp", getAddXpRoute(service), GSON::toJson);
        get("/games/:gameId/players/:uuid", getGetPlayerRoute(service), GSON::toJson);
        post("/games/:gameId/players/:uuid/consume/:lvl", getConsumeLevelRoute(service), GSON::toJson);

    }


    private static Route getAddXpRoute(Service service) {
        return (req, res) -> {
            try {
                return service.addExperience(req.params("gameId"), req.params("uuid"), Integer.valueOf(req.params("xp")));
            }catch (Exception e){
                e.printStackTrace();
                return new AddExperienceSuccess(e.getMessage(), -1);
            }
        };
    }

    private static Route getGetPlayerRoute(Service service) {
        return (req, res) -> {
            try {
                return service.getPlayer(req.params("gameId"), req.params("uuid"));
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        };
    }

    private static Route getConsumeLevelRoute(Service service) {
        return (req, res) -> {
            try {
                return service.consumeLevel(req.params("gameId"), req.params("uuid"),  Integer.valueOf(req.params("lvl")));
            }catch (Exception e){
                e.printStackTrace();
                return new Success(e.getMessage(), -1);
            }
        };
    }
}
