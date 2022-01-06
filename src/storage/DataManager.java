package storage;


import model.Animal;
import model.Grass;
import model.Herbivore;
import model.Predator;

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
    public static void createPredator(String name, Float weigh) {
        storage.create(new Predator(name, weigh));
    }

    public static void createHerbivore(String name, Float weigh) {
        storage.create(new Herbivore(name, weigh));
    }

    public static void createGrass(String name, Float weigh) {
        storage.create(new Grass(name, weigh));
    }
    //---------------------------------------------------------------


    //Убить ---------------------------------------------------------

    public static void killHerbivore(int selection, boolean form) {
        if (form) selection = storage.getAllAliveHerbivores().get(selection).getId();

        Herbivore herbivore = storage.findHerbivoreById(selection);
        //Убить травоядное
        herbivore.die();
        //Обновить статус в хранилище на "Убит"
        storage.update(herbivore);
    }

    public static void killPredator(int selection, boolean form) {
        if (form) selection = storage.getAllAlivePredators().get(selection).getId();

        Predator predator = storage.findPredatorById(selection);
        //Убить хищника
        predator.die();
        //Обновить статус в хранилище на "Убит"
        storage.update(predator);
    }
    //---------------------------------------------------------------

    //Покормить -----------------------------------------------------
    public static void feedHerbivore(int selection, int foodID, boolean form) {
        if (form) selection = storage.getAllAliveHerbivores().get(selection).getId();

        Herbivore herbivore = storage.findHerbivoreById(selection);
        Grass grass = storage.findGrassById(foodID);
        //Покормить
        herbivore.eat(grass);
        //Обновить данные
        storage.update(herbivore);
        storage.update(grass);

    }

    public static void feedPredator(int selection, int foodID, boolean form) {
        if (form) {
            selection = storage.getAllAlivePredators().get(selection).getId();
            foodID = storage.getAllAliveHerbivores().get(foodID).getId();
        }
        Predator predator = storage.findPredatorById(selection);
        Herbivore herbivore = storage.findHerbivoreById(foodID);
        //Покормить
        predator.eat(herbivore);
        //Обновить данные
        storage.update(predator);
        storage.update(herbivore);
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

    public static String[] loadData(int selection) {
        switch (selection) {
            case ALL_ANIMALS -> {
                String[] data = new String[storage.getAllAnimals().size()];
                int counter = 0;
                for (Animal animal : storage.getAllAnimals())
                    data[counter++] = animal.getShortInfo();
                return data;
            }
            case ALL_ALIVE_ANIMALS -> {
                String[] data = new String[storage.getAllAliveAnimals().size()];
                int counter = 0;
                for (Animal animal : storage.getAllAliveAnimals())
                    data[counter++] = animal.getShortInfo();
                return data;
            }
            case ALL_HERBIVORES -> {
                String[] data = new String[storage.getAllHerbivores().size()];
                int counter = 0;
                for (Animal animal : storage.getAllHerbivores().values())
                    data[counter++] = animal.getShortInfo();
                return data;
            }
            case ALL_PREDATORS -> {
                String[] data = new String[storage.getAllPredators().size()];
                int counter = 0;
                for (Animal animal : storage.getAllPredators().values())
                    data[counter++] = animal.getShortInfo();
                return data;
            }
            case ALL_ALIVE_HERBIVORES -> {
                String[] data = new String[storage.getAllAliveHerbivores().size()];
                int counter = 0;
                for (Animal animal : storage.getAllAliveHerbivores().values())
                    data[counter++] = animal.getShortInfo();
                return data;
            }
            case ALL_ALIVE_PREDATORS -> {
                String[] data = new String[storage.getAllAlivePredators().size()];
                int counter = 0;
                for (Animal animal : storage.getAllAlivePredators().values())
                    data[counter++] = animal.getShortInfo();
                return data;
            }
            case ALL_FOOD -> {
                String[] data = new String[storage.getAllGrasses().size()];
                int counter = 0;
                for (Grass grass : storage.getAllGrasses().values())
                    data[counter++] = grass.getShortInfo();
                return data;
            }
            default -> throw new IllegalArgumentException("Неверный пункт меню!");
        }
    }

//    public static void loadData(Choice choice, int selection) {
//        switch (selection) {
//            case ALL_ANIMALS -> {
//                for (Animal animal : storage.getAllAnimals())
//                    choice.add(animal.getShortInfo());
//            }
//            case ALL_ALIVE_ANIMALS -> {
//                for (Animal animal : storage.getAllAliveAnimals())
//                    choice.add(animal.getShortInfo());
//            }
//            case ALL_HERBIVORES -> {
//                for (Animal animal : storage.getAllHerbivores().values())
//                    choice.add(animal.getShortInfo());
//            }
//            case ALL_PREDATORS -> {
//                for (Animal animal : storage.getAllPredators().values())
//                    choice.add(animal.getShortInfo());
//            }
//            case ALL_ALIVE_HERBIVORES -> {
//                for (Animal animal : storage.getAllAliveHerbivores().values())
//                    choice.add(animal.getShortInfo());
//            }
//            case ALL_ALIVE_PREDATORS -> {
//                for (Animal animal : storage.getAllAlivePredators().values())
//                    choice.add(animal.getShortInfo());
//            }
//            case ALL_FOOD -> {
//                for (Grass grass : storage.getAllGrasses().values())
//                    choice.add(grass.getShortInfo());
//            }
//            default -> throw new IllegalArgumentException("Неверный пункт меню!");
//        }
//    }

    public static void save() {
        storage.save();
    }

}
