package storage;


import model.Animal;
import model.Grass;
import model.Herbivore;
import model.Predator;
import org.json.JSONObject;
import resources.Resources;

import java.awt.*;
import java.util.Scanner;

public class DataManager {
    public static Storage storage;

    public final static int ALL_ANIMALS = 1;
    public final static int ALL_ALIVE_ANIMALS = 2;
    public final static int ALL_HERBIVORES = 3;
    public final static int ALL_PREDATORS = 4;
    public final static int ALL_ALIVE_HERBIVORES = 5;
    public final static int ALL_ALIVE_PREDATORS = 6;
    public final static int ALL_FOOD = 7;
    private static final String msg = "[MESSAGE] ";

    public static void initialise(int initialise) {
        storage = Storage.getInstance(initialise);
    }

    // Создание -----------------------------------------------------
    public static Predator createPredator(String name, Float weigh) {
       return storage.create(new Predator(name, weigh));
    }

    public static Herbivore createHerbivore(String name, Float weigh) {
        return storage.create(new Herbivore(name, weigh));
    }

    public static Grass createGrass(String name, Float weigh) {
        return storage.create(new Grass(name, weigh));
    }
    //---------------------------------------------------------------


    //Убить ---------------------------------------------------------

    public static String killHerbivore(int selection, boolean form) {
        Herbivore herbivore;
        if (form) {
            herbivore = (Herbivore) storage.getAllAliveHerbivores().get(selection);
            if(herbivore == null) return msg+ Resources.rb.getString("ERROR_ANIMAL_DIDNT_FIND")+ '\n';
            selection = herbivore.getId();
        }
        herbivore = storage.findHerbivoreById(selection);
        //Убить травоядное
        if(herbivore == null) return  msg + Resources.rb.getString("ERROR_ANIMAL_DIDNT_FIND")+ '\n';
            herbivore.die();
        //Обновить статус в хранилище на "Убит"
        if(storage.update(herbivore) == null) return  msg + Resources.rb.getString("ERROR_UNABLE_TO_UPDATE_ANIMAL")+ '\n';
        return msg +Resources.rb.getString("MESSAGE_ANIMAL_UPDATED") + herbivore.getShortInfo() + (herbivore.isAlive() ? "," + Resources.rb.getString("STATUS_ALIVE") : "," + Resources.rb.getString("STATUS_DEAD"))+ '\n';
    }

    public static String killPredator(int selection, boolean form) {
        Predator predator;
        if (form) {
            predator = (Predator) storage.getAllAlivePredators().get(selection);
            if(predator == null) return msg + Resources.rb.getString("ERROR_ANIMAL_DIDNT_FIND")+ '\n';
            selection = predator.getId();
        }
        predator = storage.findPredatorById(selection);
        if(predator == null) return  msg+ Resources.rb.getString("ERROR_ANIMAL_DIDNT_FIND")+ '\n';
        //Убить хищника
        predator.die();
        //Обновить статус в хранилище на "Убит"
        if(storage.update(predator) == null) return  msg + Resources.rb.getString("ERROR_UNABLE_TO_UPDATE_ANIMAL")+ '\n';
        return msg+ Resources.rb.getString("MESSAGE_ANIMAL_UPDATED") + predator.getShortInfo() + (predator.isAlive() ? "," + Resources.rb.getString("STATUS_ALIVE") : "," + Resources.rb.getString("STATUS_DEAD")) + '\n';
    }
    //---------------------------------------------------------------

    //Покормить -----------------------------------------------------
    public static String feedHerbivore(int selection, int foodID, boolean form) {
        Herbivore herbivore;
        if (form) {
            herbivore = (Herbivore) storage.getAllAliveHerbivores().get(selection);
            if(herbivore == null) return msg+ Resources.rb.getString("ERROR_ANIMAL_DIDNT_FIND")+ '\n';
            selection = herbivore.getId();
        }
        herbivore = storage.findHerbivoreById(selection);
        Grass grass = storage.findGrassById(foodID);
        if(grass == null) return msg+ Resources.rb.getString("ERROR_UNABLE_TO_FIND_FOOD")+ '\n';
        //Покормить
        herbivore.eat(grass);
        //Обновить данные
        if(storage.update(herbivore) == null) return msg+Resources.rb.getString("ERROR_UNABLE_TO_UPDATE_ANIMAL")+ '\n';
        if(storage.update(grass) == null) return msg+Resources.rb.getString("ERROR_UNABLE_TO_UPDATE_FOOD")+ '\n';
        return msg + Resources.rb.getString("MESSAGE_ANIMAL_UPDATED") + herbivore.getShortInfo() + '\n' + msg + Resources.rb.getString("MESSAGE_FOOD_UPDATED") + grass.getShortInfo() + '\n';
    }

    public static String feedPredator(int selection, int foodID, boolean form) {
        Predator predator;
        Herbivore herbivore;
        if (form) {
            predator = (Predator) storage.getAllAlivePredators().get(selection);
            if(predator == null) return msg +Resources.rb.getString("ERROR_UNABLE_TO_FIND_PRED")+ '\n';

            herbivore = (Herbivore) storage.getAllAliveHerbivores().get(foodID);
            if(herbivore == null) return msg +Resources.rb.getString("ERROR_UNABLE_TO_FIND_HERB")+ '\n';

            selection = predator.getId();
            foodID = herbivore.getId();
        }
        predator = storage.findPredatorById(selection);
        if(predator == null) return msg +Resources.rb.getString("ERROR_UNABLE_TO_FIND_PRED")+ '\n';

        herbivore = storage.findHerbivoreById(foodID);
        if(herbivore == null) return msg +Resources.rb.getString("ERROR_UNABLE_TO_FIND_HERB")+ '\n';

        //Покормить
        boolean hunt = predator.eat(herbivore);
        //Обновить данные
        if(storage.update(predator) == null || storage.update(herbivore) == null) return msg +Resources.rb.getString("ERROR_UNABLE_TO_UPDATE_ANIMAL")+ '\n';

        if(!hunt) return msg +Resources.rb.getString("UNLUCKY_HUNT")+ '\n';
        return msg +Resources.rb.getString("LUCKY_HUNT")+ '\n';
    }
    //---------------------------------------------------------------

    //Вывести -------------------------------------------------------
    public static String print(int selection) {
        switch (selection) {
            case ALL_ANIMALS -> {
                return storage.printAllAnimals();
            }
            case ALL_ALIVE_ANIMALS -> {
                return storage.printAllAliveAnimals();
            }
            case ALL_HERBIVORES -> {
                return storage.printAllHerbivores();
            }
            case ALL_PREDATORS -> {
                return storage.printAllPredators();
            }
            case ALL_ALIVE_HERBIVORES -> {
                return storage.printAllAliveHerbivores();
            }
            case ALL_ALIVE_PREDATORS -> {
                return storage.printAllAlivePredators();
            }
            case ALL_FOOD -> {
                return storage.printAllGrasses();
            }
            default -> throw new IllegalArgumentException(Resources.rb.getString("MESSAGE_WRONG_MENU_POINT"));
        }
    }
    //---------------------------------------------------------------

    public static JSONObject loadData(int selection) {
        switch (selection) {
            case ALL_ANIMALS -> {
                JSONObject data = new JSONObject();
                int i = 0;
                for (Animal animal : storage.getAllAnimals())
                    data.put(String.valueOf(i++),animal.getShortInfo());
                return data;
            }
            case ALL_ALIVE_ANIMALS -> {
                JSONObject data = new JSONObject();
                int i = 0;
                for (Animal animal : storage.getAllAliveAnimals())
                    data.put(String.valueOf(i++),animal.getShortInfo());
                return data;
            }
            case ALL_HERBIVORES -> {
                JSONObject data = new JSONObject();
                int i = 0;
                for (Animal animal : storage.getAllHerbivores().values())
                    data.put(String.valueOf(animal.getId()),animal.getShortInfo());
                return data;
            }
            case ALL_PREDATORS -> {
                JSONObject data = new JSONObject();
                int i = 0;
                for (Animal animal : storage.getAllPredators().values())
                    data.put(String.valueOf(animal.getId()),animal.getShortInfo());
                return data;

            }
            case ALL_ALIVE_HERBIVORES -> {
                JSONObject data = new JSONObject();
                int i = 0;
                for (Animal animal : storage.getAllAliveHerbivores().values())
                    data.put(String.valueOf(animal.getId()),animal.getShortInfo());
                return data;

            }
            case ALL_ALIVE_PREDATORS -> {
                JSONObject data = new JSONObject();
                int i = 0;
                for (Animal animal : storage.getAllAlivePredators().values())
                    data.put(String.valueOf(animal.getId()),animal.getShortInfo());
                return data;
            }
            case ALL_FOOD -> {
                JSONObject data = new JSONObject();
                for (Grass grass : storage.getAllGrasses().values())
                    data.put(String.valueOf(grass.getId()),grass.getShortInfo());
                return data;
            }
            default -> throw new IllegalArgumentException(Resources.rb.getString("MESSAGE_WRONG_MENU_POINT"));
        }
    }

    public static String save() {
        return storage.save();
    }

}
