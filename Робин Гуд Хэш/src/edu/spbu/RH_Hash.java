package edu.spbu;

import java.util.ArrayList;


/**
 * Created by Ellen on 13.12.2016.
 */
public class RH_Hash<Key,Value> {

    class Pair<Key, Value> {
        Key ki;
        Value val;
    }

    Pair[] table;

    public int returnHash(Key x) {
        return x.hashCode();

    }

    public RH_Hash() {
        table = new Pair[100000];
    }

    int shift = 0;


    public void push(Key k0, Value v0) {
        int h0 = k0.hashCode() % 10;
        Pair p = new Pair();
        if (table[h0 + shift] != null){
            //Если (значение хэша у элемента1 в этой ячейке) < (значения хэша нашего эл-та0), то достаем эл-т1 из ячейки, кладем наш элемент0 в ячейку и вып проц. для эл1, к след. ячейке
            p = table[h0 + shift];
            Value v1 = (Value) p.val;
            Key k1 = (Key) p.ki;
            int h1 = k1.hashCode() % 10;
            if (h1 > h0) {
                p.val = v0;
                p.ki = k0;
                table[h0 + shift] = p;
                shift++;
                push(k1, v1);
            } else {
                shift++;
                push(k0, v0);
            }
        } else {
            p.val = v0;
            p.ki = k0;
            table[h0 + shift] = p;
            System.out.println("Добавлен: (" + k0 + ", " + v0 + ") в ячейку номер " + (h0 + shift));
            shift = 0;
        }
    }

    public Value get(Key k0) {
        int h0 = k0.hashCode() % 10;
        if (table[h0] != null) {
            Pair p = table[h0];
            while (p.ki != null) {
                if (p.ki == k0) {
                    System.out.println (p.val);
                    return (Value) p.val;
                    //break;
                } else {
                    h0++;
                    p = table[h0];
                }
            }
        }
        System.out.println("Объекта с таким ключом не существует:(");
        return null;
    }

    public void isEmpty() {
        for (int i = 0; i < 100000; i++) {
            if (table[i] != null) {
                System.out.println("Хэш-таблица содержит элементы");
                return;
            }
        }
        System.out.println("Хэш-таблица пуста");
    }
}