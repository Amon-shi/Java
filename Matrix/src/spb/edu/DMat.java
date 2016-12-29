package spb.edu;//D-dense-плотный

import java.io.*;
import java.util.ArrayList;


public class DMat implements Matrix {

    public DMat(String fileName) {
        if (fileName != null) {
            this.list = new ArrayList();
            this.list = this.getMat(fileName);
            this.arr = this.toArray(this.list);
            this.list.clear();
            this.list = null;
        }
    }

    @Override
    public Matrix mul(Matrix bb) throws IOException {
        DMat resD;
        SMat resS;
        DMat a = this;

        if (bb instanceof DMat) {
            DMat b = (DMat) bb;
            resD = a.mulDD(b);                              //!!!!!!!!!!!!!!!!!!!!
            return resD;
        }
        if (bb instanceof SMat) {
            SMat b = (SMat) bb;
            resS = (SMat) a.mulDS(b);
            return resS;
        } else return null;


    }


    public Matrix pmul(Matrix b) throws IOException, InterruptedException {
        DMat resD;
        DMat a = this;
        resD = a.pmulDD((DMat) b);
        return resD;
    }

    private DMat pmulDD(DMat m2) throws InterruptedException, IOException {
        DMat m1 = this;
        DMat res = new DMat(null);
        m2 = m2.transpose(m2);
        res.arr = new double[m1.arr.length][m1.arr.length];
        Dispatch d = new Dispatch();
        DMat finalM = m2;
        class MyCode implements Runnable {
            public void run() {
                for (int i = d.next(); i < m1.arr.length; i = d.next()) {


                    for (int j = 0; j < finalM.arr.length; j++) {
                        for (int k = 0; k < finalM.arr.length; k++) {
                            res.arr[i][j] = res.arr[i][j] + m1.arr[i][k] * finalM.arr[j][k];
                        }
                    }
                }
            }

        }

        int core_count = Runtime.getRuntime().availableProcessors();
        Thread[] t = new Thread[core_count];
        for (int i = 0; i < core_count; i++) {
            Thread t1 = new Thread(new MyCode());
            t[i] = t1;
        }
        for (int i = 0; i < core_count; i++) {
            t[i].start();
        }
        for (int i = 0; i < core_count; i++) {
            t[i].join();
        }
        return res;
    }


    @Override
    public void saveToFile(String nameOfFile) throws IOException {
        DMat c = this;
        PrintWriter printWriter = new PrintWriter(new FileWriter(nameOfFile));
        for (int i = 0; i < c.arr.length; i++) {
            for (int j = 0; j < c.arr.length; j++) {
                printWriter.print(c.arr[i][j] + " ");
            }
            printWriter.println();
        }
        printWriter.close();
    }


    static BufferedReader r;
    static ArrayList<ArrayList> arrayList;
    static ArrayList<Double> row;
    private String fileName;
    public double[][] arr;
    public ArrayList list;

//    public static void main(String[] args) throws IOException {  }

//    private DMat mulDD1(DMat b) throws IOException {
//        DMat a = this;
//        DMat res = new DMat(null);
//        res.arr = new double[a.arr.length][b.arr.length];
//        double[] rowFactor = new double[a.arr.length];
//        for (int i = 0; i < a.arr.length; i++) {
//            rowFactor[i] = 0;
//            for (int j = 0; j < a.arr.length / 2; j++) {
//                rowFactor[i] = rowFactor[i] + a.arr[i][2 * j] * a.arr[i][2 * j + 1];
//            }
//        }
//        double[] columnFactor = new double[b.arr.length];
//        for (int i = 0; i < a.arr.length; i++) {
//            columnFactor[i] = 0;
//            for (int j = 0; j < a.arr.length / 2; j++) {
//                columnFactor[i] = columnFactor[i] + b.arr[2 * j][i] * b.arr[2 * j + 1][i];
//            }
//        }
//        for (int i = 0; i < a.arr.length; i++) {
//            for (int j = 0; j < b.arr.length; j++) {
//                res.arr[i][j] = -rowFactor[i] - columnFactor[j];
//                for (int k = 0; k < a.arr.length / 2; k++)
//                    res.arr[i][j] = res.arr[i][j] + (a.arr[i][2 * k] + b.arr[2 * k + 1][j]) * (a.arr[i][2 * k + 1] + b.arr[2 * k][j]);
//
//
//            }
//        }
//
//        if (a.arr.length % 2 == 1) {
//            for (int i = 0; i < a.arr.length; i++) {
//                for (int j = 0; j < b.arr.length; j++) {
//                    res.arr[i][j] = res.arr[i][j] + a.arr[i][a.arr.length - 1] * b.arr[a.arr.length - 1][j];
//                }
//            }
//
//
//        }
//
//
//        return res;
//    }

    private DMat mulDD(DMat b)  {
        DMat a = this;
        DMat res = new DMat(null);
        b=b.transpose(b);
        res.arr = new double[a.arr.length][a.arr.length];
        for (int i = 0; i < a.arr.length; i++) {
            for (int j = 0; j < a.arr.length; j++) {
                for (int k = 0; k < a.arr.length; k++) {
                    res.arr[i][j] = res.arr[i][j] + a.arr[i][k] * b.arr[j][k];
                }
            }
        }
        return res;
    }

    private Matrix mulDS(SMat b) throws IOException {
        SMat res;
        DMat a = this;
        SMat bT = b.transpose(b);
        res = (SMat) bT.mul(transpose(a));
        res = res.transpose(res);
        res.sizeOfMatrix = b.sizeOfMatrix;
        return res;
    }

    private DMat transpose(DMat a) {
        DMat newA = new DMat(null);
        newA.arr = new double[a.arr.length][a.arr.length];
        for (int i = 0; i < a.arr.length; i++) {
            for (int j = 0; j < a.arr.length; j++) {
                newA.arr[i][j] = a.arr[j][i];
            }
        }
        return newA;
    }

    public static void printf(DMat a) {
        for (int i = 0; i < a.arr.length; i++) {
            for (int j = 0; j < a.arr.length; j++) {
                System.out.print(a.arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void openFile(String fileName) {
        try {
            r = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        } catch (Exception e) {
            System.out.println("not found");
        }
    }

    private void readFile() {
        String s;
        try {
            s = r.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            s = null;
        }
        while (s != null) {
            for (String val : s.split(" ")) {
                row.add(Double.parseDouble(val));
            }
            arrayList.add((ArrayList) row.clone());
            row.clear();
            try {
                s = r.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public ArrayList<ArrayList> getMat(String fileName)  {
        arrayList = new ArrayList<ArrayList>();
        row = new ArrayList<Double>();
        this.fileName = fileName;
        openFile(fileName);
        readFile();
        return arrayList;
    }

    private double[][] toArray(ArrayList<ArrayList> arrayList) {
        double[][] res = new double[arrayList.size()][arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < arrayList.size(); j++) {
                res[i][j] = (double) arrayList.get(i).get(j);
            }
        }
        return res;
    }

    @Override
    public boolean equals(Object bm) {
        boolean ans;
        if (bm instanceof DMat) {
            boolean ans1=true;
            DMat a = this;
            DMat b = (DMat) bm;
            for (int i = 0; i < a.arr.length; i++) {
                for (int j = 0; j < b.arr.length; j++) {
                    if (a.arr[i][j] != b.arr[i][j]) ans1 = false;
                }
            }
            ans=ans1;
        }
        else ans=false;
        return ans;
    }
}