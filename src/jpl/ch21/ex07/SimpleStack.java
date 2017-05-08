package jpl.ch21.ex07;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

@SuppressWarnings("serial")
public class SimpleStack<E> {

    private List<E> list = new ArrayList<>();
    
    public boolean empty() {
        return list.isEmpty();
    }
    public E peek() throws EmptyStackException {
        if (empty()) {
            throw new EmptyStackException();
        }
        return list.get(list.size() - 1);
    }
    public E pop() throws EmptyStackException {
        if (empty()) {
            throw new EmptyStackException();
        }
        return list.remove(list.size() - 1);
    }
    public E push(E item) {
        if (item == null) {
            return null;
        }
        list.add(item);
        return item;
    }
    public int search(Object o) {
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).equals(o)) {
                return list.size() - i;
            }
        }
        return -1;
    }
    
    

}
