package main;

import controller.GeneralController;
import resources.Resources;

public class Main {

    public static void main(String[] args){
        if(!GeneralController.startApp()){
            System.out.println(Resources.rb.getString("MESSAGE_SETUP_ERROR"));
        }
    }

}


