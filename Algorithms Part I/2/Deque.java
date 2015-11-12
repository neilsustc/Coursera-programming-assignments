import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Failed tests:<br>
 * Worst-case constant memory allocated or deallocated per deque operation
 * 
 * @author 0.0
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item>
{
    private Item[] containers;
    private int head;
    private int tail;

    // construct an empty deque
    public Deque()
    {
        containers = (Item[]) new Object[2];
        head = 0;
        tail = 1;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size()
    {
        return tail - head - 1;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
            throw new NullPointerException();
        if (head == -1)
        {
            containers = resize(containers, 2, 0, containers.length);
            head += containers.length / 2;
            tail += containers.length / 2;
        }
        containers[head--] = item;
    }

    // add the item to the end
    public void addLast(Item item)
    {
        if (item == null)
            throw new NullPointerException();
        if (tail == containers.length)
            containers = resize(containers, 2, 0, 0);
        containers[tail++] = item;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (size() == 0)
            throw new NoSuchElementException();
        Item remove = containers[++head];
        containers[head] = null;

        if (containers.length * 3 / 4 < head) // shrink
        {
            containers = resize(containers, 0.25, containers.length * 3 / 4, 0);
            head -= containers.length * 3;
            tail -= containers.length * 3;
        }
        return remove;
    }

    // remove and return the item from the end
    public Item removeLast()
    {
        if (size() == 0)
            throw new NoSuchElementException();
        Item remove = containers[--tail];
        containers[tail] = null;

        if (containers.length / 4 > tail) // shrink
        {
            containers = resize(containers, 0.25, 0, 0);
        }
        return remove;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private int current = head;

        @Override
        public boolean hasNext()
        {
            return current + 1 != tail;
        }

        @Override
        public Item next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            return containers[++current];
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
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.addFirst(5);
        deque.addFirst(6);
        deque.addFirst(7);
        deque.addFirst(8);
        deque.addFirst(9);
        deque.addFirst(10);
        deque.show();
        deque.removeFirst();
        deque.show();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.removeFirst();
        deque.show();
    }

    // ======================================================================================

    /**
     * 
     * @param array
     *            array to be resized
     * @param radio
     * @param start
     *            the index after where elements will be copied in {@code array}
     * @param copyOffset
     *            the index where the first element of {@code array} will be
     *            placed in copyArray
     * @return
     */
    private Item[] resize(Item[] array, double radio, int start, int copyOffset)
    {
        int copyLength = (int) (array.length * radio);
        Item[] copy = (Item[]) new Object[copyLength];
        int length;
        if (radio < 1)
        {
            length = copyLength;
        } else
        {
            length = array.length;
        }
        for (int i = 0; i < length; i++)
        {
            copy[i + copyOffset] = array[i + start];
        }
        return copy;
    }

    private void show()
    {
        System.out.println("[size]: " + size());
        System.out.println("[containers]:");
        for (Item item : containers)
        {
            System.out.print(" " + item);
        }
        System.out.println("\n[head]: " + head);
        System.out.println("[tail]: " + tail);
        System.out.println("[iterator]:");
        for (Item item : this)
        {
            System.out.print(" " + item);
        }
        System.out.println("\n----------");
    }
}
