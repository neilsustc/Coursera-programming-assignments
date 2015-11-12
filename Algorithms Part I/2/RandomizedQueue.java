import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Failed tests:<br>
 * Create two nested iterators over same randomized queue<br>
 * Create two parallel iterators over same randomized queue<br>
 * Check randomness of iterator() by enqueueing strings, getting an iterator()
 * and repeatedly calling next() until a specific enqueued string appears<br>
 * N random calls to enqueue(), sample(), dequeue(), isEmpty(), and size(). [
 * Most likely one of your operations is not constant time. ]<br>
 * Create randomized queue of N objects, then iterate over the N objects by
 * calling next() and hasNext(). [ Could not complete tests in allotted time ]
 * 
 * @author 0.0
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item>
{
    private Node first, last;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        size = 0;
    }

    // is the queue empty?
    public boolean isEmpty()
    {
        return first == null;
    }

    // return the number of items on the queue
    public int size()
    {
        return size;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
            throw new NullPointerException();

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldLast.next = last;

        size++;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if (isEmpty())
            throw new NoSuchElementException();

        int random = StdRandom.uniform(size);
        Item oldItem;
        if (random == 0) // delete the first one
        {
            oldItem = first.item;
            first = first.next;

            if (random == size - 1) // delete the last one
            {
                last = first;
            }
        } else
        {
            Node before = first;
            for (int i = 0; i < random - 1; i++)
            {
                before = before.next;
            }
            oldItem = before.next.item;
            before.next = before.next.next;

            if (random == size - 1) // delete the last one
            {
                last = before;
            }
        }

        size--;
        return oldItem;
    }

    // return (but do not remove) a random item
    public Item sample()
    {
        if (isEmpty())
            throw new NoSuchElementException();

        int random = StdRandom.uniform(size);
        Node sample = first;
        for (int i = 0; i < random; i++)
        {
            sample = sample.next;
        }
        return sample.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    private class Node
    {
        private Item item;
        private Node next;
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private Node current = first;

        @Override
        public boolean hasNext()
        {
            return current != null;
        }

        @Override
        public Item next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.iterator().next();
        queue.enqueue(11);
        queue.enqueue(10);
        queue.dequeue();
        queue.enqueue(14);
        queue.enqueue(33);
        queue.enqueue(14);
        queue.enqueue(35);
        queue.dequeue();
        for (Integer integer : queue)
        {
            System.out.print(integer + " ");
        }
    }

    private void show()
    {
        if (isEmpty())
        {
            System.out.println("Empty");
        } else
        {
            Node current = first;
            while (current != null)
            {
                System.out.print(" " + current.item);
                current = current.next;
            }
            System.out.println();
        }
        System.out.println("---------------------");
    }
}
