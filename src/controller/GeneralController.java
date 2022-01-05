package controller;

import model.*;
import resources.Resources;
import storage.Storage;

import java.awt.*;
import java.util.Scanner;

public class GeneralController {
    public static Storage storage;
    public static final Scanner scanner = new Scanner(System.in);


    public final static int ALL_ANIMALS = 1;
    public final static int ALL_ALIVE_ANIMALS = 2;
    public final static int ALL_HERBIVORES = 3;
    public final static int ALL_PREDATORS = 4;
    public final static int ALL_ALIVE_HERBIVORES = 5;
    public final static int ALL_ALIVE_PREDATORS = 6;
    public final static int ALL_FOOD = 7;

    public static boolean startApp(){
        boolean isOk = Resources.startApp();
        if(isOk) storage = Storage.getInstance(Resources.initialise);
        return isOk;
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
    public static void killHerbivore(){
        //Список всех живых травоядных
        System.out.println(storage.printAllAliveHerbivores());
        //Выбрать травоядное
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
        int selection = scanner.nextInt();
        Herbivore herbivore = storage.findHerbivoreById(selection);
        //Убить травоядное
        herbivore.die();
        //Обновить статус в хранилище на "Убит"
        storage.update(herbivore);
    }

    public static void killPredator(){
        //Список всех живых хищников
        System.out.println(storage.printAllAlivePredators());
        //Выбрать хищника
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
        int selection = scanner.nextInt();
        Predator predator = storage.findPredatorById(selection);
        //Убить хищника
        predator.die();
        //Обновить статус в хранилище на "Убит"
        storage.update(predator);
    }
    //---------------------------------------------------------------

    //Покормить -----------------------------------------------------
    public static void feedHerbivore(){
        //Список всех живых травоядных
        System.out.println(storage.printAllAliveHerbivores());
        //Выбрать травоядное
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_FEED"));
        int selection = scanner.nextInt();
        Herbivore herbivore = storage.findHerbivoreById(selection);
        //Выбрать чем кормить
        System.out.println(storage.printAllGrasses());
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHAT_TO_FEED"));
        selection = scanner.nextInt();
        Grass grass = storage.findGrassById(selection);
        //Покормить
        herbivore.eat(grass);
        //Обновить данные
        storage.update(herbivore);
        storage.update(grass);
    }

    public static void feedPredator(){
        //Список всех живых хищников
        System.out.println(storage.printAllAlivePredators());
        //Выбрать хищника
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_FEED"));
        int selection = scanner.nextInt();
        Predator predator = storage.findPredatorById(selection);
        //Выбрать чем кормить
        System.out.println(storage.printAllAliveHerbivores());
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHAT_TO_FEED"));
        selection = scanner.nextInt();
        Herbivore herbivore = storage.findHerbivoreById(selection);
        //Покормить
        predator.eat(herbivore);
        //Обновить данные
        storage.update(predator);
        storage.update(herbivore);
    }
    //---------------------------------------------------------------

    //Вывести -------------------------------------------------------
    public static String print(int selection){
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

    public static void loadData(Choice choice, int selection){
        switch (selection) {
            case ALL_ANIMALS -> {
                for(Animal animal : storage.getAllAnimals())
                    choice.insert(animal.getShortInfo(), animal.getId());
            }
            case ALL_ALIVE_ANIMALS -> {
                for(Animal animal : storage.getAllAliveAnimals())
                    choice.insert(animal.getShortInfo(), animal.getId());
            }
            case ALL_HERBIVORES -> {
                for(Animal animal : storage.getAllHerbivores().values())
                    choice.insert(animal.getShortInfo(), animal.getId());
            }
            case ALL_PREDATORS -> {
                for(Animal animal : storage.getAllPredators().values())
                    choice.insert(animal.getShortInfo(), animal.getId());
            }
            case ALL_ALIVE_HERBIVORES -> {
                for(Animal animal : storage.getAllAliveHerbivores().values())
                    choice.insert(animal.getShortInfo(), animal.getId());
            }
            case ALL_ALIVE_PREDATORS -> {
                for(Animal animal : storage.getAllAlivePredators().values())
                    choice.insert(animal.getShortInfo(), animal.getId());
            }
            case ALL_FOOD -> {
                for(Grass grass : storage.getAllGrasses().values())
                    choice.insert(grass.getShortInfo(), grass.getId());
            }
            default -> throw new IllegalArgumentException("Неверный пункт меню!");
        }
    }

    public static void save(){
        storage.save();
    }
}
