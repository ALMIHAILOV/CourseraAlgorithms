package Queues;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        int defaultSize = 2;
        s = (Item[]) new Object[defaultSize];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N==0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null");
        }

        if (N == s.length) {
            resize(s.length*2);
        }
            s[N++] = item;
    }

    private void resize(int newLength) {
        Item[] newS = (Item[]) new Object[newLength];

        if (N >= 0) System.arraycopy(s, 0, newS, 0, N);

        s = newS;
    }

    // remove and return a random item
    public Item dequeue() {
        checkDequeIsEmpty();

        int index = StdRandom.uniform(N);
        Item item = s[index];

        s[index] = s[N - 1];
        s[N - 1] = null;
        N--;

        if (N > 0 && (N == s.length / 4)) {
            resize(s.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkDequeIsEmpty();

        int index = StdRandom.uniform(N);
        return s[index];
    }

    private void checkDequeIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queues.Deque is empty");
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] sCopy;

        private int nCopy;

        // Init with coping items and size
        private RandomizedQueueIterator()
        {
            nCopy = N;
            sCopy = (Item[]) new Object[nCopy];

            System.arraycopy(s, 0, sCopy, 0, nCopy);
        }

        @Override
        public boolean hasNext() {
            return nCopy>0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }

            int index = StdRandom.uniform(nCopy);
            Item item = sCopy[index];

            sCopy[index] = sCopy[nCopy - 1];
            sCopy[nCopy - 1] = null;
            nCopy--;

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
