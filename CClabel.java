import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class CClabel extends Property {
    private int numRows, numCols, minVal, maxVal, newLabel, trueNumCC, newMin, newMax;
    private int zeroFramedAry[][];
    private int nonZeroNeighborAry[];
    private int EQAry[];

    CClabel(int numRows, int numCols, int minVal, int maxVal) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.zeroFramedAry = new int[numRows + 2][numCols + 2];
        this.nonZeroNeighborAry = new int[5];
        this.EQAry = new int[(numRows * numCols) / 4];
    }

    public int[][] zero2D(int[][] arr) {
        /*
         * Initialize a 2-D array to zero
         */
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                arr[i][j] = 0;
            }
        }
        return arr;
    }

    public int[] minus1D(int[] arr) {
        // initialize a 1-D array to -1
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -1;
        }
        return arr;
    }

    public void loadImage(Scanner b) throws IOException {
        /*
         * Read from input file and write to zeroFramedAry
         * begin at (1,1)
         */
        for (int i = 1; i < numRows; i++) {
            for (int j = 1; j < numCols; j++) {
                this.zeroFramedAry[i][j] = b.nextInt();
            }
        }
    }

    public void imgReformat(int[][] zfa, int minVal, int maxVal, BufferedWriter prettyprint) throws IOException {
        /*
         * Print zeroFramedAry to prettyprint. Reuse code from previous project
         */
        prettyprint.write(Integer.toString(this.numRows));
        prettyprint.write(" ");
        prettyprint.write(Integer.toString(this.numCols));
        prettyprint.write(" ");
        prettyprint.write(Integer.toString(this.newMin));
        prettyprint.write(" ");
        prettyprint.write(Integer.toString(this.newMax));
        prettyprint.write(" ");
        String str = Integer.toString(newMax);
        prettyprint.write("\n");
        int width = str.length();

        for (int r = 1; r <= this.numRows; r++) {
            for (int c = 1; c <= this.numCols; c++) {
                prettyprint.write(Integer.toString(zfa[r][c]));
                String str2 = Integer.toString(zfa[r][c]);
                int WW = str2.length();
                prettyprint.write(" ");
                while (WW < width) {
                    prettyprint.write(" ");
                    WW++;
                }
            }
            prettyprint.write("\n");
        }
    }

    public void connect8Pass1() {

    }

    public void connect8Pass2() {

    }

    public void connect4Pass1() {

    }

    public void connect4Pass2() {

    }

    public void connectPass3() {
        // algo in specs
    }

    public void drawBoxes() {
        // algo in specs
    }

    public void updateEQ() {

    }

    public int manageEQAry() {

        return 0;
    }

    public void printCCproperty() {

    }

    public void printEQAry() {

    }

    public void printImg() {

    }

    // setter/getter here
}
