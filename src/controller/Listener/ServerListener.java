package controller.Listener;

import resources.Resources;
import view.ServerForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerListener implements ActionListener {

    private ServerForm serverForm;

    public ServerListener(ServerForm serverForm) {
        this.serverForm = serverForm;

        this.serverForm.getButtonExit().addActionListener(this);
        this.serverForm.getButtonStop().addActionListener(this);
        this.serverForm.getButtonStart().addActionListener(this);
        this.serverForm.getTextFieldPort().addActionListener(this);

        this.serverForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                NetListener.exitServer();
                serverForm.exitServer();
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == serverForm.getButtonStart()) {
            String sPort = serverForm.getTextFieldPort().getText();
            try {

                if (sPort.isBlank()) {
                    serverForm.getTextAreaLog().append(Resources.rb.getString("MESSAGE_ERROR_ENTER_PORT") + '\n');
                }
                int iPort;
                try {
                    iPort = Integer.parseInt(sPort);
                } catch (NumberFormatException ex) {
                    serverForm.getTextAreaLog().append(Resources.rb.getString("WRONG_PORT_VALUE") + '\n');
                    return;
                }
                serverForm.startServer();
                NetListener.startServer(iPort);
            } catch (Exception ex) {
                serverForm.getTextAreaLog().append(ex.getMessage());
            }
        }

        if (e.getSource() == serverForm.getButtonStop()) {
            try {
                NetListener.stopServer();
                serverForm.stopServer();
                serverForm.getTextAreaLog().append(Resources.rb.getString("MESSAGE_SERVER_STOPPED")+'\n');
            } catch (NumberFormatException ex) {
                serverForm.getTextAreaLog().append(ex.getMessage());
            }
        }

        if (e.getSource() == serverForm.getButtonExit()) {
            try {
                NetListener.exitServer();
                serverForm.exitServer();
            } catch (NumberFormatException ex) {
                serverForm.getTextAreaLog().append(ex.getMessage());
            }
        }
    }
}
