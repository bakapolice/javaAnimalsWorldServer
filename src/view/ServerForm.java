package view;

import java.awt.*;

public class ServerForm extends Frame {

    private Button bStart;
    private Button bStop;
    private Button bExit;
    private Label lPort;
    private TextField tfPort;
    private Label lLog;
    private TextField tfLog;

    public ServerForm() {

        this.setSize(400, 300);
        setLayout(new GridLayout(3, 1));


        Panel portPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 30, 30));
        lPort = new Label("Порт:");
        portPanel.add(lPort);
        tfPort = new TextField("5050", 20);
        tfPort.setEnabled(true);
        portPanel.add(tfPort);
        this.add(portPanel);
        portPanel.setBackground(new Color(240, 240, 240));


        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 70, 20));
        bStart = new Button("Старт");
        bStart.setEnabled(true);
        buttonPanel.add(bStart);

        bStop = new Button("Стоп");
        bStop.setEnabled(false);
        buttonPanel.add(bStop);

        bExit = new Button("Выход");
        bExit.setEnabled(true);
        buttonPanel.add(bExit);
        this.add(buttonPanel);
        buttonPanel.setBackground(new Color(240, 240, 240));


        Panel logPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 30, 10));
        lLog = new Label("Лог:");
        logPanel.add(lLog);
        tfLog=new TextField(40);
        logPanel.add(tfLog);
        this.add(logPanel);
        logPanel.setBackground(new Color(220, 220, 220));


        this.setLocationRelativeTo(null); //поставит форму в центр экрана после размещения компонентов

        this.setVisible(true);
    }

    public void startServer() {
        bStart.setEnabled(false);
        bStop.setEnabled(true);
        bExit.setEnabled(false);
        tfPort.setEnabled(false);
    }

    public void stopServer() {
        bStart.setEnabled(true);
        bStop.setEnabled(false);
        bExit.setEnabled(true);
        tfPort.setEnabled(true);
    }

    public void exitServer() {
        this.dispose();
    }


    public TextField getTfPort() {
        return tfPort;
    }

    public void setTfPort(TextField tfPort) {
        this.tfPort = tfPort;
    }

    public Label getlPort() {
        return lPort;
    }

    public void setlPort(Label lPort) {
        this.lPort = lPort;
    }

    public Label getlLog() {
        return lLog;
    }

    public void setlLog(Label lLog) {
        this.lLog = lLog;
    }

    public Button getbStart() {
        return bStart;
    }

    public void setbStart(Button bStrat) {
        this.bStart= bStart;
    }

    public Button getbStop() {
        return bStop;
    }

    public void setbStop(Button bStop) {
        this.bStop = bStop;
    }

    public Button getbExit() {
        return bExit;
    }

    public void setbExit(Button bExit) {
        this.bExit = bExit;
    }

    public TextField getTfLog() {
        return tfLog;
    }

    public void setTfLog(TextField tfLog) {
        this.tfLog = tfLog;
    }
}
