package task3_1;

import java.util.concurrent.atomic.AtomicInteger;

public class Car {

    private final String name;

    private final long friction;

    // justice counter
    private static volatile AtomicInteger counter;

    // champion status
    private int result;

    public Car(String name, long friction) {
        validate(name, friction);
        this.name = name;
        this.friction = friction;
        counter = new AtomicInteger(Consts.DEFAULT_VALUE_ZERO);
        result = Consts.DEFAULT_VALUE_ZERO;
    }
    
    private void validate(String name, long friction) {
        StringBuilder sb = new StringBuilder(); 
        if (name.isEmpty() || name==null) {
            sb.append("\nname: ");
            sb.append("null");            
        }
        if (friction < 0) {
            sb.append("\nfriction: ");
            sb.append(friction);            
        }       
        if (sb.length()!=0){
            sb.insert(0,"Not valid parameters.");
            throw new RuntimeException(sb.toString());
        }        
    }    
    
    public void incCounter() {
        counter.getAndIncrement();
        this.result = counter.get();
    }

    public long getFriction() {
        return friction;
    }

    public String getName() {
        return name;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Car{" + "name=" + name + ", friction=" + friction + ", result=" + result + '}';
    }
}
