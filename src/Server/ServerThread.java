package Server;

import controller.NetController;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerThread implements Runnable {
    private Socket socket = null;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    String requestFromClient;
    String serverResponse;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            NetController.createJsonResponse(-1, "null");
            NetController.getResponseFromServer(NetController.getJsonResponse());
            while (socket.isConnected()) {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                requestFromClient = objectInputStream.readObject().toString();

                NetController.getResponseFromServer(new JSONObject(requestFromClient));

                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                serverResponse = NetController.getJsonResponse().toString();
                objectOutputStream.writeObject(serverResponse);
            }
            System.out.println("Socket closed");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
