package jpl.ch11.ex01;

import jpl.ch02.ex13.Vehicle;

public class LinkedList<E> {

    // ++++++ fields ++++++
    private E element;
    private LinkedList<E> next;

    // ++++++ constructors ++++++
    public LinkedList(E element) {
        this.element = element;
    }

    public LinkedList(E element, LinkedList<E> next) {
        this(element);
        this.next = next;
    }

    // ++++++ accessor methods ++++++
    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public LinkedList<E> getNext() {
        return next;
    }

    public void setNext(LinkedList<E> next) {
        this.next = next;
    }

    // ++++++ other methods ++++++
    public String toString() {
        String desc = "[element]" + element.toString();
        if (next != null) {
            desc += " [next]" + next.toStringElement();
        }
        return desc;
    }
    
    private String toStringElement() {
        return element.toString();
    }

    public int length() {
        int count = 1;
        LinkedList<E> item = getNext();
        while (true) {
            if (item != null) {
                count++;
                item = item.getNext();
            } else {
                break;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Vehicle vehicleA = new Vehicle();
        Vehicle vehicleB = new Vehicle();

        LinkedList<Vehicle> linkedListA = new LinkedList<Vehicle>(vehicleA);
        LinkedList<Vehicle> linkedListB = new LinkedList<Vehicle>(vehicleB);

        linkedListA.next = linkedListB;

        System.out.println(linkedListA);
    }

}
