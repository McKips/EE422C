package heap;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class MinHeapTest {
    static int primes[] = {2,3,5,7,11,13,17,19};
    static int minPrimesLength = primes.length > MinHeap.MAXIMUM_POSSIBLE_SIZE ? MinHeap.MAXIMUM_POSSIBLE_SIZE : primes.length;

    public boolean repOk(MinHeap heap)
    {
        if(heap==null)
            return true;
        for(int i=heap.size; i>1; i--)
            if(heap.queue[i] < heap.queue[MinHeap.parentPos(i)])
                return false;
        return true;
    }

    @Test
    public void testInsert1() {
        MinHeap heap = new MinHeap();
        assertEquals(heap.size, 0);
        heap.add(1);
        assertEquals(heap.size, 1);
        assertTrue(repOk(heap));
    }

    @Test
    public void testInsertSomeNumbers() {
        MinHeap heap = new MinHeap();
        int all = MinHeap.MAXIMUM_POSSIBLE_SIZE/2;
        assertEquals(heap.size, 0);
        for(int i=all; i>=1; i--)
            heap.add(i);
        assertEquals(heap.size, all);
        assertTrue(repOk(heap));
    }

    @Test
    public void testInsertManyNumbers() {
        MinHeap heap = new MinHeap();
        int all = MinHeap.MAXIMUM_POSSIBLE_SIZE*2;
        assertEquals(heap.size, 0);
        for(int i=all; i>=1; i--)
            heap.add(i);
        assertEquals(heap.size, MinHeap.MAXIMUM_POSSIBLE_SIZE);
        assertTrue(repOk(heap));
    }

    @Test
    public void testHeapSort() {

        MinHeap heap = new MinHeap();

        for(int i = 0; i< minPrimesLength; ++i)
            heap.add(primes[i]);
        ArrayList<Integer> result = heap.heapSort();

        assertEquals(heap.size,0);
        assertEquals(result.size(), minPrimesLength);

        for(int i = 0; i< minPrimesLength; i++)
            assertEquals((int)result.get(i), primes[i]);
    }

    @Test
    public void testRemoveHeapifyDownAndUp() {
        int numbers [] = {1,200,3,400,500,6,7,800,900,1000,1100,12};

        MinHeap heap = new MinHeap();
        if(numbers.length > MinHeap.MAXIMUM_POSSIBLE_SIZE)
            return ;
        for(int i=0; i<numbers.length; ++i)
            heap.add(numbers[i]);
        heap.remove(4);
        assertEquals(heap.size, numbers.length-1);
        assertTrue(repOk(heap));
    }

    @Test
    public void testRemoveHeapifyDown() {
        MinHeap heap = new MinHeap();

        for(int i=0; i<minPrimesLength; ++i)
            heap.add(primes[i]);
        heap.remove(1);
        assertTrue(repOk(heap));
        assertEquals(heap.size, minPrimesLength-1);
    }

    @Test
    public void testRemoveEmpty() {
        MinHeap heap = new MinHeap();
        heap.remove(1);
        assertTrue(repOk(heap));
        assertEquals(heap.size, 0);
    }

    @Test
    public void testRemoveSingleEntry() {
        MinHeap heap = new MinHeap();
        heap.add(1);
        heap.remove(1);
        assertTrue(repOk(heap));
        assertEquals(heap.size, 0);
    }

    @Test
    public void testRemoveInvalidIndex() {
        MinHeap heap = new MinHeap();
        heap.add(1);
        heap.remove(100);
        heap.remove(0);
        assertTrue(repOk(heap));
        assertEquals(heap.size, 1);
    }

}

