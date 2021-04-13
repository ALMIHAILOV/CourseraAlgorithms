package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int dequeSize;
    private Node first, last;

    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
        dequeSize = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return dequeSize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return dequeSize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }

        Node newItem = new Node();
        newItem.item = item;
        newItem.next = first;
        newItem.previous = null;

        if (isEmpty()) {
            last = newItem;
        } else {
            first.previous = newItem;
        }

        first = newItem;
        dequeSize++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }

        Node newItem = new Node();
        newItem.item = item;
        newItem.next = null;
        newItem.previous = last;

        if (isEmpty()) {
            first = newItem;
        } else {
            last.next = newItem;
        }

        last = newItem;
        dequeSize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        checkDequeIsEmpty();

        Item item = first.item;
        first = first.next;
        dequeSize--;

        if (isEmpty()) {
            last = null;
        } else {
            first.previous = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        checkDequeIsEmpty();

        Item item = last.item;
        last = last.previous;
        dequeSize--;

        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }

        return item;
    }

    private void checkDequeIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queues.Deque is empty");
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            Item item = current.item;
            current   = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Unsupported operation");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {}

}
