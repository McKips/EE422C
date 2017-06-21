/* 
 * This file is how you might test out your code.  Don't submit this, and don't 
 * have a main method in SortTools.java.
 */

package assignment1;
public class Main {
		public static void main(String [] args) {

				// call your test methods here
				// SortTools.isSorted() etc.

				int[] x = new int[]{1,2,4,7,8,9};
				int[] xExp = new int[]{0,1,2,3,4,5,6,7,8,9};
				x = SortTools.insertGeneral(x,6,3);
				x = SortTools.insertGeneral(x,7,5);
				x = SortTools.insertGeneral(x,8,6);
				x = SortTools.insertGeneral(x,9,0);
				for (int i = 0; i < 10; i++){
						if (x[i] != xExp[i]){
								System.out.println("insertSort Not Equal");
								break;
						}
				}

				int[] y = new int[]{2,1,5,6,3,4,7,0,9,8};
				int[] yExp = new int[]{0,1,2,3,4,5,6,7,8,9};
				SortTools.insertSort(y,10);
				for (int i = 0; i < 10; i++){
						if (y[i] != yExp[i]){
								System.out.println("insertSort Not Equal");
								break;
						}
				}
				System.out.println("Tests Finished");

				if(SortTools.isSorted(y,10)){
					System.out.println("True");
				}
		}
}
