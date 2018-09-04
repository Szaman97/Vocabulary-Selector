package xyz.codemode.vocabularyselector.core;

import java.util.Random;

public class RandomMachine {
    public RandomMachine(int amountOfWordToDraw, int valueForInitRandom) {
        this.amountOfWordToDraw = amountOfWordToDraw;
        rand = new Random(valueForInitRandom);
    }

    public RandomMachine(int amountOfWordToDraw) {
        this.amountOfWordToDraw = amountOfWordToDraw;
        rand = new Random();
    }

    private int amountOfWordToDraw;
    private Random rand;
}
