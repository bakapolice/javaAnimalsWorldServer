package model;

import exceptions.AttemptToFeedAnotherFood;
import exceptions.AttemptToFeedDeadAnimal;

public class Herbivore extends Animal implements Food{
    public Herbivore() {
        super();
        this.setName("Unknown herbivore");
    }

    public Herbivore(String name, float weight) {
        super(name, weight);
    }

    @Override
    public boolean eat(Food food) {
        if(!this.isAlive)
            throw new AttemptToFeedDeadAnimal();

        if(!(food instanceof Grass))
            throw new AttemptToFeedAnotherFood();

        this.weight += 0.5F;
        ((Grass)food).weight -= 0.5F;
        return true;
    }
}
