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
    public static void main(){
        Random rand = new Random;
        int[] c = new int[100];
        for(int k=0; k < 100; k++ ){
            c[k]=rand.nextInt(50);
        }

        insertionSort(c);

    }
}