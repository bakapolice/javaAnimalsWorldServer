package controller;

import resources.Resources;
import storage.DataManager;

import java.awt.*;

public class GeneralController {
    public static boolean isStarted;

    public final static int ALL_ANIMALS = 1;
    public final static int ALL_ALIVE_ANIMALS = 2;
    public final static int ALL_HERBIVORES = 3;
    public final static int ALL_PREDATORS = 4;
    public final static int ALL_ALIVE_HERBIVORES = 5;
    public final static int ALL_ALIVE_PREDATORS = 6;
    public final static int ALL_FOOD = 7;

    public static boolean startApp(){
        isStarted = Resources.startApp();
        if(isStarted)
        {
            DataManager.initialise(Resources.initialise);
            NetController.startApp();
        }
        return isStarted;
    }

    // Создание -----------------------------------------------------
    public static void createPredator(String name, Float weigh) {
        DataManager.createPredator(name, weigh);
    }

    public static void createHerbivore(String name, Float weigh) {
        DataManager.createHerbivore(name, weigh);
    }

    public static void createGrass(String name, Float weigh) {
        DataManager.createGrass(name, weigh);
    }
    //---------------------------------------------------------------


    //Убить ---------------------------------------------------------
    public static void killHerbivore(int selection, boolean form){
        DataManager.killHerbivore(selection,form);
    }

    public static void killPredator(int selection, boolean form){
        DataManager.killPredator(selection, form);
    }
    //---------------------------------------------------------------

    //Покормить -----------------------------------------------------
    public static void feedHerbivore(int selection, int foodID, boolean form){
        DataManager.feedHerbivore(selection, foodID, form);
    }

    public static void feedPredator(int selection, int foodID, boolean form){
        DataManager.feedPredator(selection, foodID, form);
    }
    //---------------------------------------------------------------

    //Вывести -------------------------------------------------------
    public static String print(int selection){
        return DataManager.print(selection);
    }
    //---------------------------------------------------------------

    public static String[] loadData(int selection){
        return DataManager.loadData(selection);
    }

    public static void save(){
        DataManager.save();
    }
}
