package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    private boolean isStarted;
    private static ServerSocket serverSocket = null;
    private ArrayList<ServerThread> serverThreads = new ArrayList<>();


    @Override
    public void run() {
        isStarted = true;
        try {
            while (isStarted) {
                Socket socket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(socket);
                serverThreads.add(serverThread);
                Thread thread = new Thread(serverThread);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        Server server = new Server();
        Thread thread = new Thread(server);
        thread.start();
    }


    public void stopServer() throws IOException {
        for (ServerThread serverThread : serverThreads)
            serverThread.getSocket().close();
        serverSocket.close();
        isStarted = false;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
