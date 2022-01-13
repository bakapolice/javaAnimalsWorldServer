package controller.Listener;

import controller.GeneralController;
import controller.NetController;
import view.ServerForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerListener implements ActionListener {

    private ServerForm serverForm;

    public ServerListener(ServerForm serverForm) {
        this.serverForm = serverForm;

        this.serverForm.getbExit().addActionListener(this);
        this.serverForm.getbStop().addActionListener(this);
        this.serverForm.getbStart().addActionListener(this);
        this.serverForm.getTfPort().addActionListener(this);

        this.serverForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                NetController.exitServer();
                serverForm.exitServer();
                System.exit(0);
            }
        });
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == serverForm.getbStart()) {
            String sPort = serverForm.getTfPort().getText();
            try {

                if (sPort.isBlank()) {
                    throw new IllegalAccessException("Ошибка. Введите значение порта.");
                }
                int iPort;
                try {
                    iPort = Integer.parseInt(sPort);
                } catch (NumberFormatException ex) {
                    serverForm.getTfLog().setText(ex.getMessage());
                    throw new IllegalAccessException("Ошибка преобразования порта.");
                }
                serverForm.startServer();
                NetController.startServer(iPort);
            } catch (Exception ex) {
                serverForm.getTfLog().setText(ex.getMessage());
            }
        }

        if (e.getSource() == serverForm.getbStop()) {
            try {
                NetController.stopServer();
                serverForm.stopServer();
                serverForm.getTfLog().setText("Сервер остановлен.");
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == serverForm.getbExit()) {
            try {
                NetController.exitServer();
                serverForm.exitServer();
            } catch (NumberFormatException ex) {
                serverForm.getTfLog().setText(ex.getMessage());
            }
        }
    }
}
