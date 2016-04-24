package task3_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Race {

    private static long maxDistance;

    private static long timeDisqualify;

    private final long timeDisqualifyAfterWinner;

    private static volatile boolean winnerOn = false;

    private final ExecutorService es;

    List<Callable<Car>> cars = new ArrayList<Callable<Car>>();

    List<Future<Car>> results = new ArrayList<Future<Car>>();

    public Race(long maxDistance, long timeDisqualify, long timeDisqualifyAfterWinner) {
        validate(maxDistance, timeDisqualify, timeDisqualifyAfterWinner);
        Race.maxDistance = maxDistance;
        Race.timeDisqualify = timeDisqualify;
        this.timeDisqualifyAfterWinner = timeDisqualifyAfterWinner;
        es = Executors.newCachedThreadPool();
    }

    private void validate(long maxDistance, long timeDisqualify, long timeDisqualifyAfterWinner) {
        StringBuilder sb = new StringBuilder();
        
        if (maxDistance < 0) {
            sb.append("\nmaxDistance: ");
            sb.append(maxDistance);            
        }
        if (timeDisqualify < 0) {
            sb.append("\ntimeDisqualify: ");
            sb.append(timeDisqualify);            
        }
        if (timeDisqualifyAfterWinner < 0) {
            sb.append("\ntimeDisqualifyAfterWinner: ");
            sb.append(timeDisqualifyAfterWinner);            
        }
        if (sb.length()!=0){
            sb.insert(0,"Not valid parameters.");
            throw new RuntimeException(sb.toString());
        }        
    }

    public void addRacer(Callable<Car> car) {
        cars.add(car);
    }

    public void start() {
        for (Callable<Car> car : cars) {
            results.add(es.submit(car));
        }

        while (true) {
            try {
                if (winnerOn) {
                    // disqualify by time after winner
                    Thread.sleep(timeDisqualifyAfterWinner);
                    es.shutdownNow();
                    printResults();
                    break;
                }
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }

    private void printResults() {
        StringBuffer sb = new StringBuffer();
        Car car;
        for (Future<Car> fut : results) {
            try {
                car = fut.get();
                if (car.getResult() == Consts.WINNER) {
                    sb.append("\n");
                    sb.append("Race is over.\n");
                    sb.append("Winner is ");
                    sb.append(car.getName());
                    sb.append("!");
                }
            } catch (InterruptedException | ExecutionException ex) {
                System.out.println(ex);
            }
        }
        System.out.println(sb);
    }

    public static long getMaxDistance() {
        return maxDistance;
    }

    public static long getTimeDisqualify() {
        return timeDisqualify;
    }

    public static void setWinnerOn(boolean winOn) {
        Race.winnerOn = winOn;
    }

}
