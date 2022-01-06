package controller;


import Server.Server;
import controller.Listener.ServerListener;
import org.json.JSONObject;
import storage.DataManager;
import view.*;

public class NetController {

    private static Server server;
    private static ServerForm serverForm;
    private static ServerListener serverListener;

    public static final int REQUEST_TYPE_CONNECT = 0;
    public static final int REQUEST_TYPE_CREATE = 1;
    public static final int REQUEST_TYPE_KILL = 2;
    public static final int REQUEST_TYPE_FEED = 3;
    public static final int REQUEST_TYPE_PRINT = 4;
    public static final int REQUEST_TYPE_LOAD = 5;

    private static JSONObject jsonRequest;
    private static JSONObject jsonResponse;


    public static void startServer(int port) throws Exception {
        try {
            server.startServer(port);
        } catch (Exception ex) {
            serverForm.getTfLog().setText(ex.getMessage());
            throw new Exception("Ошибка запуска сервера на порту " + port);
        }
    }

    public static boolean stopServer() {
        try {
            server.stopServer();
            return true;
        } catch (Exception ex) {
            //sf.getTfLog().setText(ex.getMessage());
            return false;
        }
    }

    public static boolean exitServer() {
        try {
            if (server.isStarted())
                server.stopServer();
            GeneralController.save();
            return true;
        } catch (Exception ex) {
            //sf.getTfLog().setText(ex.getMessage());
            return false;
        }
    }

    public static void startApp() {
        try {
            server = new Server();
            serverForm = new ServerForm();
            serverListener = new ServerListener(serverForm);
        } catch (Exception ex) {
            serverForm.getTfLog().setText(ex.getMessage());
        }
    }

    private static void createJsonRequest(int command, String type, String name, Float weigh, Integer selectionId, Integer foodId, Boolean isForm) {
        jsonRequest = new JSONObject();
        jsonRequest.put("request_type", command);
        jsonRequest.put("creature_type", type);
        jsonRequest.put("name", name);
        jsonRequest.put("weigh", weigh);
        jsonRequest.put("selection_id", selectionId);
        jsonRequest.put("food_id", foodId);
        jsonRequest.put("is_form", isForm);
    }

    public static void createJsonResponse(int command, String message) {
        jsonResponse = new JSONObject();
        jsonResponse.put("request_type", command);
        jsonResponse.put("message", message);
    }

    private static void createJsonResponse(int command, int selection, String[] data) {
        jsonResponse = new JSONObject();
        for (String str : data) {
            int counter = 0;
            jsonResponse.put(String.valueOf(counter++), data);
        }
        jsonResponse.toString();
    }

    // На стороне сервера
    public static void getResponseFromServer(JSONObject jsonRequest) {
        switch (jsonRequest.getInt("request_type")) {
            case REQUEST_TYPE_CONNECT -> {
                createJsonResponse(REQUEST_TYPE_CONNECT, "Connected");
            }
            case REQUEST_TYPE_CREATE -> {
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    DataManager.createHerbivore(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    DataManager.createHerbivore(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                }
                if (jsonRequest.getString("creature_type").equals("Grass")) {
                    DataManager.createHerbivore(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                }
                String message = "Создано: + " + jsonRequest.getString("creature_type") + jsonRequest.getString("name") + (float) jsonRequest.getDouble("weigh");
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_KILL -> {
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    DataManager.killHerbivore(jsonRequest.getInt("selection_id"), jsonRequest.getBoolean("is_form"));
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    DataManager.killPredator(jsonRequest.getInt("selection_id"), jsonRequest.getBoolean("is_form"));
                }
                String message = "Убит: + " + jsonRequest.getString("creature_type");
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_FEED -> {
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    DataManager.feedHerbivore(jsonRequest.getInt("selection_id"), jsonRequest.getInt("food_id"), jsonRequest.getBoolean("is_form"));
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    DataManager.feedHerbivore(jsonRequest.getInt("selection_id"), jsonRequest.getInt("food_id"), jsonRequest.getBoolean("is_form"));
                }
                String message = "Убит: + " + jsonRequest.getString("creature_type");
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_PRINT -> {
                String message = DataManager.print(jsonRequest.getInt("selection_id"));
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_LOAD -> createJsonResponse(jsonRequest.getInt("request_type"), jsonRequest.getInt("selection_id"), DataManager.loadData(jsonRequest.getInt("selection_id")));
        }
    }


    public static JSONObject getJsonRequest() {
        return jsonRequest;
    }

    public static void setJsonRequest(JSONObject jsonRequest) {
        NetController.jsonRequest = jsonRequest;
    }

    public static JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public static void setJsonResponse(JSONObject jsonResponse) {
        NetController.jsonResponse = jsonResponse;
    }
}
