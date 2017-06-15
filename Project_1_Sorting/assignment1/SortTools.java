// SortTools.java 
/*
 * EE422C Project 1 submission by
 * Replace <...> with your actual data.
 * <Kiptoo Tonui>
 * <kt23347>
 * <76175>
 * Summer 2017
 * Slip days used: 
 */

package assignment1;
public class SortTools {
    /**
     * This method tests to see if the given array is sorted.
     * @param x is the array
     * @param n is the size of the input to be checked
     * @return true if array is sorted
     */
    public static boolean isSorted(int[] x, int n) {

        if (x.length == 0 || n == 0)
            return false;

        for (int j = 1; j < n; j++){
            if (x[j] < x[j-1])
                return false;
        }
        return true;
    }

    /**
     * This method implements binary search to return index where value is found, -1 if value is not present.
     * @param x is the array
     * @param n is the size of the input to be checked
     * @param v is the value to be found
     * @return index where value is found, else return -1 if not found
     */

    public static int find(int[] x, int n, int v){
        int beg = 0;
        int mid = 0;
        int end = n-1;
        while (beg <= end){
            mid = (beg + end) /2;
            if (v == x[mid]){
                return mid;
            }
            else if (v < x[mid]){
                end = mid -1;
            }
            else
                beg = mid+1;
        }	
        return -1;
    }

    /**
     * This method inserts a value in an array in a way to keep it sorted.
     * @param x is the array
     * @param n is the size of the input to be checked
     * @param v is the value to be found
     * @return sorted array
     */

    public static int[] insertGeneral(int [] x, int n, int v) {
        if (find(x,n,v) != -1)
            return x;

        int[] newX = new int[n+1];
        int j = 0;

        for (; j < x.length && v > x[j]; j++){
            newX[j] = x[j];
        }

        newX[j] = v;
        for(; j < n; j++){
            newX[j+1] = x[j];
        }
        return newX;
    }

    /**
     * This method inserts a value in an array in a way to keep it sorted.
     * @param x is the array
     * @param n is the size of the input to be checked
     * @param v is the value to be found
     * @return n if value is found, n+1 if value is added to array
     */

    public static int insertInPlace(int[] x, int n, int v){
        if (find(x,n,v) != -1)
            return n;
        int end = n;
        while (v < x[end]){
            x[end] = x[end-1];
            end -= 1;
        }
        x[end+1] = v;
        return n+1;
    }

    /**
     * This method sorts an array in a non-decreasing order using insertion sort algorithm.
     * @param x is the array
     * @param n is the size of the input to be checked
     */

    public static void insertSort(int[] x, int n){
        for (int j = 1; j < n; j++){

            int v = x[j];
            int k = j-1;

            while (k >= 0 && x[k] > v){
                x[k+1] = x[k];
                k -= 1;
            }
            x[k+1] = v;
        }
    }
}
