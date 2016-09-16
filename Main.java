/**
 * Created by Игорь on 12.09.2016.
 */

import java.util.Random;

public class Main{
    public static void insertionSort(int[] arr) {
        for(int i = 1; i < arr.length; i++){
            int currElem = arr[i];
            int prevKey = i - 1;
            while(prevKey >= 0 && arr[prevKey] > currElem){
                arr[prevKey+1] = arr[prevKey];
                prevKey--;
            }
            arr[prevKey+1] = currElem;
        }
    }
    public static void main(String []arg){
        Random rand = new Random();
        int[] c = new int[10];
        for(int k=0; k < 10; k++ ){
            c[k]=rand.nextInt(50);
        }
        for(int k=0; k < 10; k++ ) {
            System.out.print(c[k]+" ");
        }
        insertionSort(c);
        System.out.println(" ");
        for(int k=0; k < 10; k++ ){
            System.out.print(c[k]+" ");
        }
    }
}