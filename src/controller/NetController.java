package controller;


import Server.Server;
import controller.Listener.ServerListener;
import model.Grass;
import model.Herbivore;
import model.Predator;
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
    public static final int REQUEST_TYPE_SAVE = 6;

    private static JSONObject jsonRequest;
    private static JSONObject jsonResponse;


    public static void startServer(int port) throws Exception {
        try {
            server = new Server();
            server.setPort(port);
            new Thread(server).start();
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
            serverForm.getTfLog().setText(ex.getMessage());
            return false;
        }
    }

    public static void exitServer() {
        try {
            if (server!= null && server.isStarted())
                server.stopServer();
            GeneralController.save();
        } catch (Exception ex) {
            serverForm.getTfLog().setText(ex.getMessage());
            //serverForm.getTfLog().setText("Сервер не существует или отсутствуют разрешения.");
        }
    }

    public static void startApp() {
        try {
            serverForm = new ServerForm();
            serverListener = new ServerListener(serverForm);
            createJsonResponse(-1, "null");
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

    private static void createJsonResponse(int command, int selection, JSONObject data) {
        jsonResponse = new JSONObject();
        jsonResponse.put("request_type", command);
        jsonResponse.put("selection_id", selection);
        jsonResponse.put("data", data);
    }

    // На стороне сервера
    public static void getResponseFromServer(JSONObject jsonRequest) {
        switch (jsonRequest.getInt("request_type")) {
            case REQUEST_TYPE_CONNECT -> createJsonResponse(REQUEST_TYPE_CONNECT, "Connected");

            case REQUEST_TYPE_CREATE -> {
                String message = null;
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    Herbivore herbivore = DataManager.createHerbivore(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                    if (herbivore == null)
                        message = "Ошибка создания";
                    else message = "Создан: " + herbivore.getShortInfo();
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    Predator predator = DataManager.createPredator(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                    if (predator == null)
                        message = "Ошибка создания!";
                    else message = "Создан: " + predator.getShortInfo();
                }
                if (jsonRequest.getString("creature_type").equals("Grass")) {
                    Grass grass = DataManager.createGrass(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                    if (grass == null)
                        message = "Ошибка создания";
                    else message = "Создан: " + grass.getShortInfo();
                }
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_KILL -> {
                String message = null;
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    message = DataManager.killHerbivore(jsonRequest.getInt("selection_id"), jsonRequest.getBoolean("is_form"));
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    message = DataManager.killPredator(jsonRequest.getInt("selection_id"), jsonRequest.getBoolean("is_form"));
                }
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_FEED -> {
                String message = null;
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    message = DataManager.feedHerbivore(jsonRequest.getInt("selection_id"), jsonRequest.getInt("food_id"), jsonRequest.getBoolean("is_form"));
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    message = DataManager.feedPredator(jsonRequest.getInt("selection_id"), jsonRequest.getInt("food_id"), jsonRequest.getBoolean("is_form"));
                }
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_PRINT -> {
                String message = DataManager.print(jsonRequest.getInt("selection_id"));
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_LOAD -> createJsonResponse(jsonRequest.getInt("request_type"), jsonRequest.getInt("selection_id"), DataManager.loadData(jsonRequest.getInt("selection_id")));
            case REQUEST_TYPE_SAVE -> createJsonResponse(jsonRequest.getInt("request_type"), DataManager.save());
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
