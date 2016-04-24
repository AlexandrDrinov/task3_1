package task3_1;

import java.util.concurrent.Callable;

public class CarThread implements Callable<Car> {

    private long distance;

    private final Car car;

    // amount sleep 
    private long sleep;

    public CarThread(Car car) {
        this.car = car;
        this.sleep = Consts.DEFAULT_VALUE_ZERO;
        this.distance = Consts.DEFAULT_VALUE_ZERO;
    }

    @Override
    public Car call() {
        try {
            while (distance < Race.getMaxDistance()) {
                Thread.sleep(car.getFriction());
                sleep += car.getFriction();
                
                // disqualify by time
                if (sleep < Race.getTimeDisqualify()) {
                    distance += Consts.DISTANCE_DELTA;
                    System.out.println(car.getName() + " on distance " + distance);
                } else {
                    Thread.currentThread().interrupt();
                }
            }
            // set champion status
            car.incCounter();
            if (car.getResult() == Consts.WINNER) {
                // champion race is determined
                Race.setWinnerOn(true);
            }
            System.out.println(car.getName() + " is " + car.getResult());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();            
            // logic disqualify - two variant
            if (sleep >= Race.getTimeDisqualify()) {
                System.out.println("Disqualify by time is " + car.getName());
            } else {
                System.out.println("Disqualify by time after Winner is " + car.getName());
            }
        }
        return car;
    }
}
