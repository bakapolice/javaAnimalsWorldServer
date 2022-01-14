package Server;

import controller.Listener.NetListener;
import org.json.JSONObject;
import resources.Resources;

import java.io.*;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Socket socket;
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
            NetListener.createJsonResponse(-1, "null");
            NetListener.getResponseFromServer(NetListener.getJsonResponse());
            while (socket.isConnected()) {
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                requestFromClient = objectInputStream.readObject().toString();

                NetListener.getResponseFromServer(new JSONObject(requestFromClient));

                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                serverResponse = NetListener.getJsonResponse().toString();
                objectOutputStream.writeObject(serverResponse);
            }
        } catch (IOException | ClassNotFoundException e) {
            final String log = "[LOG] ";
            NetListener.getServerForm().getTextAreaLog().append(log + Resources.rb.getString("MESSAGE_CLIENT_DISCONNECTED") + socket.getPort() + "\n");
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
