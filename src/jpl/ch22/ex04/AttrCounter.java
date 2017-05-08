package jpl.ch22.ex04;

import java.util.Observable;
import java.util.Observer;

import jpl.ch22.ex04.AttributedImpl.AttributedOperation;

public class AttrCounter implements Observer {

    private AttributedImpl attributed;
    private int count = 0;
    
    public AttrCounter(AttributedImpl attributed) {
        this.attributed = attributed;
        this.attributed.addObserver(this);
    }
    
    public int total() {
        return count;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!o.equals(attributed)) {
            throw new IllegalArgumentException("Wrong Observable object.");
        }
        AttributedOperation operation = (AttributedOperation)arg;
        switch (operation) {
        case ADD:
            count++;
            break;
        case REMOVE:
            count--;
            break;
        case FIND:
        case ATTRS:
            break;
        default:
            break;
        }
    }

}
