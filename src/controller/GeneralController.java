package controller;

import controller.Listener.NetListener;
import resources.Resources;
import storage.DataManager;

public class GeneralController {
    public static boolean isStarted;

    public static boolean startApp(){
        isStarted = Resources.startApp();
        if(isStarted)
        {
            DataManager.initialise(Resources.initialise);
            NetListener.startApp();
        }
        return isStarted;
    }

    public static void save(){
        DataManager.save();
    }
}
