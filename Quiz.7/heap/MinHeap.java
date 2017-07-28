/*
 * EE422C Quiz 9 submission by
 * <Student Name>
 * <Recitation Session>
 * <EID>
 */

package heap;

import java.util.*;

public class MinHeap {
    static final int MAXIMUM_POSSIBLE_SIZE = 100;
    int size;
    int[] queue = new int[MAXIMUM_POSSIBLE_SIZE + 1];// we assume the heap will have no more
    // than 100 elements.

    public static int leftPos(int pos) {
        return 2 * pos;
    }

    public static int rightPos(int pos) {
        return 2 * pos + 1;
    }

    public static int parentPos(int pos) {
        return pos / 2;
    }

    public void swapPositions(int p1, int p2){
        int temp = queue[p1];
        queue[p1] = queue[p2];
        queue[p2] = temp;
    }

    public void heapifyUp(int pos){
        if (pos == 0 || pos > size)
            return;
        if(parentPos(pos) == 0 || queue[pos] > queue[parentPos(pos)])
            return;
        swapPositions(pos,parentPos(pos));
        heapifyUp(parentPos(pos));
    }

    public void heapifyDown(int pos){
        if (pos == 0 || pos > size)
            return;

        int smallestPos = pos,left,right;
        left = leftPos(pos);
        right = rightPos(pos);

        if(left <= size && queue[left] < queue[smallestPos])
            smallestPos = left;
        if (right <= size && queue[right] < queue[smallestPos])
            smallestPos = right;

        if(smallestPos != pos){
            swapPositions(pos,smallestPos);
            heapifyDown(smallestPos);
        }

    }

    /**
     * Inserts the specified element into this MinHeap.
     */
    public void add(int value) {
        if (size == MAXIMUM_POSSIBLE_SIZE)
            return;
        size++;
        queue[size] = value;
        heapifyUp(size);
    }

    public ArrayList<Integer> heapSort() {
        ArrayList<Integer> result = new ArrayList<>();
        while (size != 0) {
            result.add(queue[1]);
            remove(1);
        }
        return result;
    }

    public void remove(int pos) {
        if (pos < 1 || pos > size)
            return;
        swapPositions(pos,size);
        size--;
        heapifyUp(pos);
        heapifyDown(pos);
    }
}
