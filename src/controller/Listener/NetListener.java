package controller.Listener;


import Server.Server;
import controller.GeneralController;
import model.Grass;
import model.Herbivore;
import model.Predator;
import org.json.JSONObject;
import resources.Resources;
import storage.DataManager;
import view.*;

public class NetListener {

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
    private static final String msg = "[MESSAGE] ";


    public static void startServer(int port){
        try {
            server = new Server();
            server.setPort(port);
            new Thread(server).start();
            serverForm.getTextAreaLog().append(Resources.rb.getString("MESSAGE_SERVER_STARED") + port + '\n');
        } catch (Exception ex) {
            serverForm.getTextAreaLog().append(Resources.rb.getString("MESSAGE_UNABLE_TO_START") + port + '\n');
        }
    }

    public static void stopServer() {
        try {
            server.stopServer();
        } catch (Exception ex) {
            serverForm.getTextAreaLog().append(Resources.rb.getString("MESSAGE_ERROR_STOP_SERVER") + '\n');
        }
    }

    public static void exitServer() {
        try {
            if (server != null && server.isStarted())
                server.stopServer();
            GeneralController.save();
        } catch (Exception ex) {
            serverForm.getTextAreaLog().append(ex.getMessage());
        }
    }

    public static void startApp() {
        try {
            serverForm = new ServerForm();
            serverListener = new ServerListener(serverForm);
            createJsonResponse(-1, "null");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
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
            case REQUEST_TYPE_CONNECT -> createJsonResponse(REQUEST_TYPE_CONNECT, msg + "Connected" + '\n');

            case REQUEST_TYPE_CREATE -> {
                String message = null;
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    Herbivore herbivore = DataManager.createHerbivore(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                    if (herbivore == null)
                        message = msg + Resources.rb.getString("MESSAGE_ERROR_CREATE") + '\n';
                    else message = msg +  Resources.rb.getString("MESSAGE_CREATED") + herbivore.getShortInfo()  + '\n';
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    Predator predator = DataManager.createPredator(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                    if (predator == null)
                        message = msg + Resources.rb.getString("MESSAGE_ERROR_CREATE") + '\n';
                    else message = msg + Resources.rb.getString("MESSAGE_CREATED") + predator.getShortInfo()  + '\n';
                }
                if (jsonRequest.getString("creature_type").equals("Grass")) {
                    Grass grass = DataManager.createGrass(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                    if (grass == null)
                        message = msg + Resources.rb.getString("MESSAGE_ERROR_CREATE") + '\n';
                    else message = msg + Resources.rb.getString("MESSAGE_CREATED") + grass.getShortInfo()  + '\n';
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
        NetListener.jsonRequest = jsonRequest;
    }

    public static JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public static void setJsonResponse(JSONObject jsonResponse) {
        NetListener.jsonResponse = jsonResponse;
    }

    public static ServerForm getServerForm() {
        return serverForm;
    }
}
