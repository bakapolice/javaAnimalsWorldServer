package Server;

import controller.Listener.NetListener;
import controller.Listener.ServerListener;
import resources.Resources;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    private boolean isStarted;
    private static ServerSocket serverSocket = null;
    private ArrayList<ServerThread> serverThreads = new ArrayList<>();
    private int port;


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(getPort());
            isStarted = true;
            while (isStarted) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);
                serverThreads.add(serverThread);
                Thread thread = new Thread(serverThread);
                thread.start();
                final String log = "[LOG] ";
                NetListener.getServerForm().getTextAreaLog().append(log + Resources.rb.getString("MESSAGE_CLIENT_CONNECTED") + socket.getPort() + "\n");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void stopServer() throws IOException {
        serverSocket.close();
        isStarted = false;
        for (ServerThread serverThread : serverThreads)
        {
            serverThread.getObjectInputStream().close();
            serverThread.getObjectInputStream().close();
            serverThread.getSocket().close();
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public void setPort(int port){
        this.port = port;
    }

    public int getPort(){
        return port;
    }
}
