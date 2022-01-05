package model;

import exceptions.AttemptToKillDead;
import exceptions.WrongMassException;

import java.io.Serializable;

public abstract class Animal implements Serializable {
    protected int id;
    protected String name;
    protected boolean isAlive;
    protected float weight;

    public Animal() {
        this.id = 0;
        this.name = "Unknown creature";
        this.isAlive = true;
        this.weight = 0.0F;
    }

    public Animal(String name, float weight) {
        if (weight <= 0)
            throw new WrongMassException();
        this.id = -1;
        this.name = name;
        this.isAlive = true;
        this.weight = weight;
    }

    public void die() {
        if (!isAlive)
            throw new AttemptToKillDead();

        this.isAlive = false;
    }

    public abstract void eat(Food food);

    public String getInfo() {
        return "id = " + id
                + "; name = " + name
                + "; isAlive = " + isAlive
                + "; weight = " + weight;
    }

    public String getShortInfo(){
        return name + ", " + weight + " кг";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
