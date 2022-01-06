package controller.Listener;

import controller.NetController;
import view.ServerForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerListener implements ActionListener {

    private ServerForm sf;

    public ServerListener(ServerForm sf) {
        this.sf = sf;

        this.sf.getbExit().addActionListener(this);
        this.sf.getbStop().addActionListener(this);
        this.sf.getbStart().addActionListener(this);
        this.sf.getTfPort().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sf.getbStart()) {
            String sPort = sf.getTfPort().getText();
            try {

                if (sPort.isBlank()) {
                    throw new IllegalAccessException("Ошибка. Введите значение порта.");
                }
                int iPort;
                try {
                    iPort = Integer.parseInt(sPort);
                } catch (NumberFormatException ex) {
                    sf.getTfLog().setText(ex.getMessage());
                    throw new IllegalAccessException("Ошибка преобразования порта.");
                }
                sf.startServer();
                NetController.startServer(iPort);
            } catch (Exception ex) {
                sf.getTfLog().setText(ex.getMessage());
            }
        }

        if (e.getSource() == sf.getbStop()) {
            try {
                NetController.stopServer();
                sf.stopServer();
                sf.getTfLog().setText("Сервер остановлен.");
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSource() == sf.getbExit()) {
            try {

                if (NetController.exitServer()) {
                    sf.exitServer();
                } else {
                    sf.getTfLog().setText("Сервер не существует или отсутствуют разрешения.");
                }
            } catch (NumberFormatException ex) {
                sf.getTfLog().setText(ex.getMessage());
            }
        }
    }
}
