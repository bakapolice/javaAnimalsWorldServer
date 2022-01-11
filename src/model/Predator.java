package model;

import exceptions.AttemptToFeedAnotherFood;
import exceptions.AttemptToFeedCarrion;
import exceptions.AttemptToFeedDeadAnimal;

public class Predator extends Animal{

    public Predator() {
        super();
        setName("Unknown Predator");
    }

    public Predator(String name, float weight) {
        super(name, weight);
    }

    @Override
    public boolean eat(Food food) {
        if (!this.isAlive)
            throw new AttemptToFeedDeadAnimal();

        if (!(food instanceof Herbivore))
            throw new AttemptToFeedAnotherFood();

        Herbivore herbivore = (Herbivore)food;
        if (!herbivore.isAlive)
            throw new AttemptToFeedCarrion();

        if(!hunt(food)) {
            System.out.println("Хищнику не удалось поймать добычу!");
            return false;
        }

        herbivore.die();
        this.weight += herbivore.weight;
        herbivore.weight -= 2.0F;
        System.out.println("Охота прошла успешно!");
        return true;
    }

    private boolean hunt(Food food) {
            return ((Herbivore)food).weight <= this.weight / 2;
    }
}
