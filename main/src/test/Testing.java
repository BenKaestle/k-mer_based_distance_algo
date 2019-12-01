package test;

import utility.WriteReadObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;

public class Testing {
    public static void main(String[] args) throws IOException {
//        Sketch sketch = new Sketch(new long[3], "header",3);
//        Sketch ssketch = new Sketch(new long[4], "header2",4);
//        Sketch[] sketches = new Sketch[]{sketch,ssketch};
//        WriteReadObject.writeObjectToFile(sketches,"main/resources/test");
//        Sketch[] s = (Sketch[])WriteReadObject.readObjectFromFile("main/resources/test");
//        System.out.println(s[0].getGenome_size());
//        System.out.println(s[1].getGenome_size());


//        String x = Test_Values.dist_table_original_18_1000;
//        String y = Test_Values.dist_table_java_21_1000;
//        printTable(WriteReadObject.readTSVtable(x));
//        WriteReadObject.writePhylipFile(WriteReadObject.readTSVtable(x),"original_mash_result_18_1000");
//        printTable(compareTables(WriteReadObject.readTSVtable(x), WriteReadObject.readTSVtable(y)));

//        for (int i : compareJaccard(WriteReadObject.readJaccardIndex("main/jaccard_index/18_1000_bloom.txt"), Test_Values.output_java_18_1000_bloom)){
//            System.out.print(i + ", ");
//        }

        testing(args);

    }

    public static void testing(String[] args) {

        writeTSVtable(readPhylip("dashing_original.phylip"));


    }

    public static void writeTSVtable(String[][] table) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("comparison2"+".txt");
            for (String[] row : table){
                for (String cell:row){
                    fileWriter.write(cell+"\t");
                }
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeTSVtable(float[][] table) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("comparison1"+".txt");
            for (float[] row : table){
                for (float cell:row){
                    fileWriter.write(cell+"\t");
                }
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] readTSVtable(String filepath) {
        BufferedReader reader;
        String table = "";
        String current;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            while ((current = reader.readLine()) != null) {
                table+= current+"\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(table);
        int columns = 0;
        int rows = 0;
        for (String row : table.split("\n")) {
            if (rows == 0) {
                for (String cell : row.split("\\s+|\t")) {
                    columns++;
                }
            }
            rows++;
        }
        String[][] result = new String[rows][columns];
        rows = 0;
        for (String row : table.split("\n")) {
            columns = 0;
            for (String cell : row.split("\\s+|\t")) {
                result[rows][columns] = cell;
                columns++;
            }
            rows++;
        }
        return result;
    }

    public static String[][] readPhylip(String filepath) {
        BufferedReader reader;
        String table = "";
        String current;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            reader.readLine();
            while ((current = reader.readLine()) != null) {
                table+= current.substring(12)+"\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(table);
        int columns = 0;
        int rows = 0;
        for (String row : table.split("\n")) {
            if (rows == 0) {
                for (String cell : row.split("\\s+|\t")) {
                    columns++;
                }
            }
            rows++;
        }
        String[][] result = new String[rows][columns];
        rows = 0;
        for (String row : table.split("\n")) {
            columns = 0;
            for (String cell : row.split("\\s+|\t")) {
                result[rows][columns] = cell;
                columns++;
            }
            rows++;
        }
        return result;
    }


    public static String[][] readAndCombineTable(String filepath) {

        String[][] table = readTSVtable(filepath);
        String[][] table2 = new String[table.length][table[0].length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (table[i][j]!="-") table[j][i]=table[i][j];
                if (i==j) table[i][j]="1";
            }
        }
        int x,y;
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (i==0) table2[i][j]=j+".fna";
                else if (j==0) table2[i][j]=i+".fna";
                else{
                    x=Integer.parseInt(table[0][j].replace(".fna",""));
                    y=Integer.parseInt(table[i][0].replace(".fna",""));
                    table2[x][y] = table[i][j];
                }

            }
        }

        return table2;
    }


    public static float harmonicMean(int[] register) {
        float ALPHA_M = (float) (1 / (2 * Math.log(2)));
        int registerSize = register.length;
        float denominator = 0;
        for (int i = 0; i < registerSize; i++) {
            denominator += Math.pow(2, -(register[i] + 1));
        }
        return (ALPHA_M * registerSize * registerSize) / denominator;
    }


    public static void readSortWriteList(String filepath) {
        ArrayList<String> data = WriteReadObject.readTxt(filepath);
        Collections.sort(data);
        WriteReadObject.writeTxt(filepath, data);
    }


    static int countZeros(long val, int prefixSize) {
        long y;
        int n = 64;
        y = val >> 32;
        if (y != 0) {
            n = n - 32;
            val = y;
        }
        y = val >> 16;
        if (y != 0) {
            n = n - 16;
            val = y;
        }
        y = val >> 8;
        if (y != 0) {
            n = n - 8;
            val = y;
        }
        y = val >> 4;
        if (y != 0) {
            n = n - 4;
            val = y;
        }
        y = val >> 2;
        if (y != 0) {
            n = n - 2;
            val = y;
        }
        y = val >> 1;
        if (y != 0)
            return n - 2;
        return n - (int) val;
    }


    public static String canonical_kmer(String kmer) {
        String reverse = "";
        for (int i = kmer.length() - 1; i >= 0; i--) {
            switch (kmer.charAt(i)) {
                case 'A':
                    reverse += "T";
                    break;
                case 'T':
                    reverse += "A";
                    break;
                case 'G':
                    reverse += "C";
                    break;
                case 'C':
                    reverse += "G";
                    break;
                default:
                    reverse += "N";
                    break;
            }
        }
        return kmer.compareTo(reverse) > 0 ? reverse : kmer;
    }

    //01.fna 02.fna 03.fna 04.fna 05.fna 06.fna 07.fna 08.fna 09.fna 10.fna 11.fna 12.fna 13.fna 14.fna 15.fna 16.fna 17.fna 18.fna 19.fna 20.fna 21.fna 22.fna 23.fna 24.fna 25.fna 26.fna 27.fna 28.fna 29.fna 30.fna 31.fna 32.fna 33.fna 34.fna 35.fna 36.fna 37.fna 38.fna 39.fna 40.fna 41.fna 42.fna 43.fna 44.fna 45.fna 46.fna 47.fna 48.fna 49.fna 50.fna


    private static int[] compareJaccard(int[] values1, int[] values2) {
        for (int i = 0; i < values1.length; i++) {
            values1[i] -= values2[i];
        }
        return values1;
    }


    private static float[][] compareTables(String[][] table1, String[][] table2) {
        System.out.println(Float.parseFloat(table1[4][1]));
        System.out.println(Float.parseFloat(table2[4][1]));
        float[][] results = new float[table1.length][table1[0].length];
        for (int i = 1; i < table1.length; i++) {
            for (int j = 1; j < table1[0].length; j++) {
                float first = Float.parseFloat(table1[i][j]);
                float second = Float.parseFloat(table2[i][j]);
                if (Math.abs(first - second) < .001f) results[i][j] = 0;
                else results[i][j] = first - second;
            }
        }
        return results;
    }

    private static void printTable(String[][] strings) {
        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[0].length; j++) {
                System.out.print(strings[i][j] + "\t");
            }
            System.out.println();
        }
    }

    private static void printTable(float[][] strings) {
        for (int i = 1; i < strings.length; i++) {
            for (int j = 1; j < strings[0].length; j++) {
                System.out.print(strings[i][j] + ", ");
            }
        }
    }

    public static void usingFileWriter() throws IOException {
        String fileContent = "4\n" +
                "LS1         0  0.083  0.25  0.458\n" +
                "LS2         0.083  0  0.167  0.392\n" +
                "LS3         0.25  0.167  0  0.392\n" +
                "LS4         0.458  0.392  0.392  0";

        FileWriter fileWriter = new FileWriter("main/test.phylip");
        fileWriter.write(fileContent);
        fileWriter.close();
    }


    public static int optimalK(long genome_size, float prob) {
        return (int) Math.ceil(Math.log(genome_size * (1 - prob) / prob) / Math.log(4));
    }

    public static long[][] getBinomialDistribution(int min, int max, long total) {
        int n = max - min;
        long[][] ret = new long[2][n + 1];
        int mean = (n + 1) / 2;
        float p = 1;
        if (n > 0) {
            p = (float) mean / (float) n;
        }

        long count = 0;
        for (int i = 0; i <= n; i++) {
            double p_i = combination(n, i) * Math.pow(p, i)
                    * Math.pow((1 - p), (n - i));
            long count_i = (long) (total * p_i);
            ret[0][i] = i + min;
            ret[1][i] = count_i;
            count += count_i;
        }

        while (count < total) {
            int i = 1;
            ret[1][i]++;
            count++;
        }

        return ret;
    }

    // calculate the combination
    // the value would be very large, so store it in the type of double
    public static double combination(int n, int k) {
        double ret = 1;
        while (k > 0) {
            ret = ret * ((double) n / (double) k);
            k--;
            n--;
        }
        return ret;
    }


    public static void test() {
        String str = "abcdbdbcbcbbcadbcda";
        long y = 0;
        for (int i = 0; i < 21; i++) {

            long x = (long) 4 * (long) Math.pow(5, 49 - i) % (long) Math.pow(2, 32);
            y += x;
            System.out.println(y);
        }
    }

    public static String[] splitBy(int cores, String sequence, int kmersize) {
        String[] split = new String[cores];
        float splitHere = sequence.length() / (float) cores;
        int addDown = (kmersize - 1) / 2;
        int addUp = (int) Math.ceil((kmersize - 1) / 2);

        for (int i = 0; i < cores; i++) {
            int start = (int) (splitHere * i) - addDown;
            int end = (int) (splitHere * (i + 1)) + addUp;
            if (start < 0)
                start = 0;
            if (end > sequence.length())
                end = sequence.length();
            split[i] = sequence.substring(start, end);
        }
        return split;
    }


}
