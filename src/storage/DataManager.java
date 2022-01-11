package storage;


import model.Animal;
import model.Grass;
import model.Herbivore;
import model.Predator;
import org.json.JSONObject;

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
            if(herbivore == null) return "Ошибка. Такое животное не найдено!";
            selection = herbivore.getId();
        }
        herbivore = storage.findHerbivoreById(selection);
        //Убить травоядное
        if(herbivore == null) return  "Ошибка. Такое животное не найдено!";
            herbivore.die();
        //Обновить статус в хранилище на "Убит"
        if(storage.update(herbivore) == null) return  "Ошибка. Не удалось обновить состояние животного в хранилище.!";
        return "Статус животного обновлен успешно! " + storage.findHerbivoreById(selection).getShortInfo();
    }

    public static String killPredator(int selection, boolean form) {
        Predator predator;
        if (form) {
            predator = (Predator) storage.getAllAlivePredators().get(selection);
            if(predator == null) return "Ошибка. Такое животное не найдено!";
            selection = predator.getId();
            selection = storage.getAllAlivePredators().get(selection).getId();
        }
        predator = storage.findPredatorById(selection);
        if(predator == null) return  "Ошибка. Такое животное не найдено!";
        //Убить хищника
        predator.die();
        //Обновить статус в хранилище на "Убит"
        if(storage.update(predator) == null) return  "Ошибка. Не удалось обновить состояние животного в хранилище.!";
        return "Статус животного обновлен успешно! " + storage.findPredatorById(selection).getShortInfo();
    }
    //---------------------------------------------------------------

    //Покормить -----------------------------------------------------
    public static String feedHerbivore(int selection, int foodID, boolean form) {
        Herbivore herbivore;
        if (form) {
            herbivore = (Herbivore) storage.getAllAliveHerbivores().get(selection);
            if(herbivore == null) return "Ошибка. Такое животное не найдено!";
            selection = herbivore.getId();
        }
        herbivore = storage.findHerbivoreById(selection);
        Grass grass = storage.findGrassById(foodID);
        if(grass == null) return "Ошибка! Такая еда не найдена!";
        //Покормить
        herbivore.eat(grass);
        //Обновить данные
        if(storage.update(herbivore) == null) return "Ошибка. Не удалось обновить состояние животного в хранилище!";
        if(storage.update(grass) == null) return "Ошибка. Не удалось обновить состояние животного в хранилище!";
        return "Статус животного обновлен успешно! " + storage.findPredatorById(selection).getShortInfo();
    }

    public static String feedPredator(int selection, int foodID, boolean form) {
        Predator predator;
        Herbivore herbivore;
        if (form) {
            predator = (Predator) storage.getAllAlivePredators().get(selection);
            if(predator == null) return "Ошибка. Такой хищник не найден!";

            herbivore = (Herbivore) storage.getAllAliveHerbivores().get(foodID);
            if(herbivore == null) return "Ошибка. Такое травоядное не найдено!";

            selection = predator.getId();
            foodID = herbivore.getId();
        }
        predator = storage.findPredatorById(selection);
        if(predator == null) return "Ошибка. Такой хищник не найден!";

        herbivore = storage.findHerbivoreById(foodID);
        if(herbivore == null) return "Ошибка. Такое травоядное не найдено!";

        //Покормить
        boolean hunt = predator.eat(herbivore);
        //Обновить данные
        if(storage.update(predator) == null) return "Ошибка. Не удалось обновить состояние животного в хранилище!";
        if(storage.update(herbivore) == null) return "Ошибка. Не удалось обновить состояние животного в хранилище!";

        if(!hunt) return "Хищнику не удалось поймать добычу!";
        return "Охота прошла успешно! Хищник поймал добычу";
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
            default -> throw new IllegalArgumentException("Неверный пункт меню!");
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
            default -> throw new IllegalArgumentException("Неверный пункт меню!");
        }
    }

    public static void save() {
        storage.save();
    }

}
