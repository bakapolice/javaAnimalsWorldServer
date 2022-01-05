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
    public void eat(Food food) {
        if(!this.isAlive)
            throw new AttemptToFeedDeadAnimal();
            //System.out.println("Травоядное мертво!");
            //return;

        if(!(food instanceof Grass))
            throw new AttemptToFeedAnotherFood();
            //System.out.println("Травоядное такое не ест!");
            //return;

        this.weight += 0.5F;
        ((Grass)food).weight -= 0.5F;
    }
}
