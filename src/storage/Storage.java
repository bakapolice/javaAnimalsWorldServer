package storage;

import model.Animal;
import model.Herbivore;
import model.Predator;
import model.Grass;
import main.Main;
import resources.Resources;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage implements Serializable {

    private static Storage uniqueInstance = null;
    private static final int MODE_LOAD_FROM_FILE_INIT = 1;
    private static final int MODE_DEFAULT_INIT = 2;
    private static final int MODE_EMPTY_INIT = 3;

    static String filenameAnimals = Resources.storagePath;

    private int herbivoresCounter = 0;
    private int predatorsCounter = 0;
    private int grassesCounter = 0;

    private HashMap<Integer, Herbivore> herbivores = new HashMap<Integer, Herbivore>();
    private HashMap<Integer, Predator> predators = new HashMap<Integer, Predator>();
    private HashMap<Integer, Grass> grasses = new HashMap<Integer, Grass>();


    private Storage(int initialise) {
        switch (initialise){
            case MODE_DEFAULT_INIT -> defaultInit();
            case MODE_EMPTY_INIT -> {}
            default -> throw new IllegalArgumentException("Неверный способ инициализации");
        }
    }

    private Storage(){}

    private void defaultInit(){
        create(new Predator("Волк", 60F));
        create(new Predator("Медведь", 400F));
        create(new Predator("Гиена", 50F));

        create(new Herbivore("Заяц", 3F));
        create(new Herbivore("Олень", 350F));
        create(new Herbivore("Лошадь", 380F));

        create(new Grass("Высокая Трава", 200F));
        create(new Grass("Низкая Трава", 50F));
        create(new Grass("Сухая трава", 70F));
    }

    public static Storage getInstance(int initialise) {
        if(uniqueInstance == null){
            switch (initialise){
                case MODE_LOAD_FROM_FILE_INIT ->
                        {
                            uniqueInstance = new Storage();
                            uniqueInstance.load();
                        }
                case MODE_DEFAULT_INIT, MODE_EMPTY_INIT -> uniqueInstance = new Storage(initialise);
                default -> throw new IllegalArgumentException("Неверный способ инициализации");
            }
        }
        return uniqueInstance;
    }

    public Predator create(Predator predator) {
        if(predator.getId()!=-1) throw new IllegalStateException("Объект уже имеет id!");
        predator.setId(predatorsCounter++);
        predators.put(predator.getId(), predator);
        return predator;
    }

    public Herbivore create(Herbivore herbivore) {
        if(herbivore.getId()!=-1) throw new IllegalStateException("Объект уже имеет id!");
        herbivore.setId(herbivoresCounter++);
        herbivores.put(herbivore.getId(), herbivore);
        return herbivore;
    }

    public Grass create(Grass grass) {
        if(grass.getId()!=-1) throw new IllegalStateException("Объект уже имеет id!");
        grass.setId(grassesCounter++);
        grasses.put(grass.getId(), grass);
        return grass;
    }

    public void update(Predator predator) {
        if (predators.replace(predator.getId(), predator) == null)
            throw new IllegalArgumentException("Нечего обновлять!");
    }

    public void update(Herbivore herbivore) {
        if (herbivores.replace(herbivore.getId(), herbivore) == null)
            throw new IllegalArgumentException("Нечего обновлять!");
    }

    public void update(Grass grass) {
        if (grasses.replace(grass.getId(), grass) == null)
            throw new IllegalArgumentException("Нечего обновлять!");
    }

    public void remove(Predator predator) {
        if (predators.remove(predator.getId()) == null)
            throw new IllegalArgumentException("Нечего удалять!");
    }

    public void remove(Herbivore herbivore) {
        if (herbivores.remove(herbivore.getId()) == null)
            throw new IllegalArgumentException("Нечего обновлять!");
    }

    public void remove(Grass grass) {
        if (grasses.remove(grass.getId()) == null)
            throw new IllegalArgumentException("Нечего обновлять!");
    }

    public ArrayList<Animal> getAllAnimals() {
        ArrayList<Animal> animals = new ArrayList<>();
        animals.addAll(predators.values());
        animals.addAll(herbivores.values());
        return animals;
    }

    public HashMap<Integer, Predator> getAllPredators() {
        return predators;
    }

    public void setAllPredators(HashMap<Integer, Predator> predators) {
        this.predators = predators;
    }

    public HashMap<Integer, Herbivore> getAllHerbivores() {
        return herbivores;
    }

    public void setAllHerbivores(HashMap<Integer, Herbivore> herbivores) {
        this.herbivores = herbivores;
    }

    public HashMap<Integer, Grass> getAllGrasses() {
        return grasses;
    }

    public void setAllGrasses(HashMap<Integer, Grass> grasses) {
        this.grasses = grasses;
    }

    public ArrayList<Animal> getAllAliveAnimals() {
        ArrayList<Animal> aliveAnimals = new ArrayList<>();
        aliveAnimals.addAll(getAllAlivePredators().values());
        aliveAnimals.addAll(getAllAliveHerbivores().values());

        return aliveAnimals;
    }

    public HashMap<Integer, Animal> getAllAlivePredators() {
        HashMap<Integer, Animal> alivePredators = new HashMap<Integer, Animal>();
        int counterP = 0;
        for (Predator pr : predators.values())
        {
            if (pr.isAlive()) alivePredators.put(counterP++, pr);
        }
        return alivePredators;
    }

    public HashMap<Integer, Animal> getAllAliveHerbivores() {
        HashMap<Integer, Animal> aliveHerbivores = new HashMap<Integer, Animal>();
        int counterH = 0;
        for (Herbivore hr : herbivores.values())
            if (hr.isAlive()) aliveHerbivores.put(counterH++, hr);
        return aliveHerbivores;
    }

    public String printAllAnimals() {
        return printAllPredators() + printAllHerbivores();
    }

    public String printAllPredators() {
        String pred = "";
        for (Predator pr : predators.values())
            pred += pr.getInfo() + "\n";
        return pred;
    }

    public String printAllHerbivores() {
        String herb = "";
        for (Herbivore h : herbivores.values())
            herb += h.getInfo() + "\n";
        return herb;
    }

    public String printAllGrasses() {
        String grass = "";
        for (Grass g : grasses.values())
            grass += g.getInfo() + "\n";
        return grass;
    }

    public String printAllAliveAnimals() {
        return printAllAlivePredators() + printAllAliveHerbivores();
    }

    public String printAllAlivePredators() {
        String alivePredators = "";
        for (Predator pr : predators.values())
            if (pr.isAlive()) {
                alivePredators += pr.getInfo() + "\n";
            }
        return alivePredators;
    }

    public String printAllAliveHerbivores() {
        String aliveHerbivores = "";
        for (Herbivore hr : herbivores.values())
            if (hr.isAlive()) {
                aliveHerbivores += hr.getInfo() + "\n";
            }
        return aliveHerbivores;
    }

    public void save(){
        try{
            FileOutputStream dataFile = new FileOutputStream(filenameAnimals);
            ObjectOutputStream saveFile = new ObjectOutputStream(dataFile);
            saveFile.writeObject(uniqueInstance);
            saveFile.close();
            dataFile.close();
            System.out.println(Resources.rb.getString("MESSAGE_SAVE"));
            }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void load(){
        try {
            FileInputStream dataFile = new FileInputStream(filenameAnimals);
            ObjectInputStream loadFile = new ObjectInputStream(dataFile);
            uniqueInstance = (Storage) loadFile.readObject();
            loadFile.close();
            dataFile.close();
            System.out.println(Resources.rb.getString("MESSAGE_LOAD"));
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(Resources.rb.getString("MESSAGE_LOAD_ERROR"));
            ex.printStackTrace();
            System.out.println("Не удалось считать данные из файла. Будет выполнена дефолтная инициализация!");
            defaultInit();
        }
    }

    public Predator findPredatorById(int id) {
        return predators.get(id);
    }

    public Herbivore findHerbivoreById(int id) {
        return herbivores.get(id);
    }

    public Grass findGrassById(int id) {
        return grasses.get(id);
    }

    public int getHerbivoresCounter() {
        return herbivoresCounter;
    }

    public void setHerbivoresCounter(int herbivoresCounter) {
        this.herbivoresCounter = herbivoresCounter;
    }

    public int getPredatorsCounter() {
        return predatorsCounter;
    }

    public void setPredatorsCounter(int predatorsCounter) {
        this.predatorsCounter = predatorsCounter;
    }

    public int getGrassesCounter() {
        return grassesCounter;
    }

    public void setGrassesCounter(int grassesCounter) {
        this.grassesCounter = grassesCounter;
    }

    public static Storage getUniqueInstance() {
        return uniqueInstance;
    }

    public static void setUniqueInstance(Storage uniqueInstance) {
        Storage.uniqueInstance = uniqueInstance;
    }
}
