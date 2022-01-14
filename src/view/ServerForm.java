package view;

import resources.Resources;

import java.awt.*;

public class ServerForm extends Frame {

    private Button buttonStart;
    private Button buttonStop;
    private Button buttonExit;
    private Label labelPort;
    private TextField textFieldPort;
    private Label labelLog;
    private TextArea textAreaLog;

    public ServerForm() {
        labelPort = new Label(Resources.rb.getString("LABEL_PORT"));
        labelPort.setBounds(30, 61, 85, 30);
        this.add(labelPort);
        textFieldPort = new TextField("5050");
        textFieldPort.setBounds(157,61, 213, 30);
        textFieldPort.setEnabled(true);
        this.add(textFieldPort);


        buttonStart = new Button(Resources.rb.getString("BUTTON_START"));
        buttonStart.setBounds(30,121, 85,30);
        buttonStart.setEnabled(true);
        this.add(buttonStart);

        buttonStop = new Button(Resources.rb.getString("BUTTON_STOP"));
        buttonStop.setBounds(157,121, 85,30);
        buttonStop.setEnabled(false);
        this.add(buttonStop);

        buttonExit = new Button(Resources.rb.getString("BUTTON_EXIT"));
        buttonExit.setBounds(285,121, 85,30);
        buttonExit.setEnabled(true);
        this.add(buttonExit);

        labelLog = new Label(Resources.rb.getString("LABEL_LOG"));
        labelLog.setBounds(30,181, 85, 30);
        this.add(labelLog);
        textAreaLog = new TextArea(null, 10, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        textAreaLog.setBounds(30,231,340, 270);
        this.add(textAreaLog);

        this.setLayout(null);
        this.setSize(400, 531);
        this.setLocationRelativeTo(null); //поставит форму в центр экрана после размещения компонентов
        this.setVisible(true);
    }

    public void startServer() {
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);
        buttonExit.setEnabled(false);
        textFieldPort.setEnabled(false);
    }

    public void stopServer() {
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);
        buttonExit.setEnabled(true);
        textFieldPort.setEnabled(true);
    }

    public void exitServer() {
        this.dispose();
    }


    public TextField getTextFieldPort() {
        return textFieldPort;
    }

    public void setTextFieldPort(TextField textFieldPort) {
        this.textFieldPort = textFieldPort;
    }

    public Label getLabelPort() {
        return labelPort;
    }

    public void setLabelPort(Label labelPort) {
        this.labelPort = labelPort;
    }

    public Label getLabelLog() {
        return labelLog;
    }

    public void setLabelLog(Label labelLog) {
        this.labelLog = labelLog;
    }

    public Button getButtonStart() {
        return buttonStart;
    }

    public void setButtonStart(Button buttonStart) {
        this.buttonStart= buttonStart;
    }

    public Button getButtonStop() {
        return buttonStop;
    }

    public void setButtonExit(Button buttonStop) {
        this.buttonStop = buttonStop;
    }

    public Button getButtonExit() {
        return buttonExit;
    }

    public void setButtonStop(Button buttonExit) {
        this.buttonExit = buttonExit;
    }

    public TextArea getTextAreaLog() {
        return textAreaLog;
    }

    public void setTextAreaLog(TextArea textAreaLog) {
        this.textAreaLog = textAreaLog;
    }
}
