package edu.spbu;

public class Main {

    public static void main(String[] args) {
        RH_Hash myHash = new RH_Hash<Integer, Double>();
        myHash.isEmpty();
        myHash.push(1,101);
        myHash.push(11,111);
        myHash.get(11);
        myHash.get(1);
        myHash.get(3);
        myHash.isEmpty();
    }
}
