package task3_1;

import java.util.concurrent.Callable;

public class Runner {

    public static void main(String[] args) {

        final long MAX_DISTANCE = 10000;
        final long MAX_TIME_DISKQUALIFY = 60000;
        final long MAX_TIME_DISKQUALIFY_AFTER_WINNER = 5000;

        Callable<Car> lada = new CarThread(new Car("Lada Kalina", 490));
        Callable<Car> ford = new CarThread(new Car("Ford Mondeo", 480));
        Callable<Car> audi = new CarThread(new Car("Audi A4", 400));
        Callable<Car> skoda = new CarThread(new Car("Skoda Superb", 390));

        Race race = new Race(MAX_DISTANCE, MAX_TIME_DISKQUALIFY, MAX_TIME_DISKQUALIFY_AFTER_WINNER);
        race.addRacer(audi);
        race.addRacer(ford);
        race.addRacer(skoda);
        race.addRacer(lada);
        race.start();        
        
    }
}
