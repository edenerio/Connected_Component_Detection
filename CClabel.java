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
        this.newLabel = 0;
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

    public int[][] connect8Pass1(int [][]zfa) {
        int px;
        int a, b, c, d, k;
        int pxcase=0;
        for(int i=1; i<=this.numRows; i++){
            for(int j=1; j<=this.numCols; j++){
                px = zfa[i][j];
                if(px>0){
                    k = zfa[i][j];
                    a = zfa[i-1][j-1];
                    b = zfa[i-1][j];
                    c = zfa[i-1][j+1];
                    d = zfa[i][j-1];
                    if(a==0 && b==0 && c==0 && d==0){
                        this.newLabel++;
                        px = this.newLabel;
                        pxcase = 1;
                    }
                    if(a==b && a==c && a==d){
                        px = this.CCproperty[k].getLabel();
                        pxcase = 2;
                    }
                    else{
                        px = Math.min((Math.min(Math.min(a, b), c)), d);
                        pxcase = 3;
                    }
                }
                if(pxcase == 1 || pxcase == 3){
                    updateEQ(this.newLabel, px);
                }
            }
        }
        return zfa;
    }

    public int[][] connect8Pass2(int [][]zfa) {
        //update Equivalence theorem
        int px, lbl;
        int e, f, g, h, k;
        for(int i=this.numRows; i>=1; i--){
            for(int j=this.numCols; j>=1; j--){
                px = zfa[i][j];
                if(px>0){
                    k = zfa[i][j];
                    e = zfa[i][j+1];
                    f = zfa[i+1][j-1];
                    g = zfa[i+1][j];
                    h = zfa[i+1][j+1];
                    if(e==0 && f==0 && g==0 && h==0){
                        continue;
                    }
                    if(e==f && e==g && e==h){
                        continue;
                    }
                    else{
                        lbl = Math.min(Math.min((Math.min(Math.min(e, f), g)), h), px);
                        this.CCproperty[k].setLabel(lbl);
                    }
                }
            }
        }
        return zfa;
    }

    public int[][] connect4Pass1(int [][]zfa) {
        int px;
        int a, b, k;
        int pxcase=0;
        for(int i=1; i<=this.numRows; i++){
            for(int j=1; j<=this.numCols; j++){
                px = zfa[i][j];
                if(px>0){
                    k = zfa[i][j];
                    a = zfa[i+1][j];
                    b = zfa[i][j-1];
                    if(a==0 && b==0){
                        this.newLabel++;
                        px = this.newLabel;
                        pxcase = 1;
                    }
                    if(a==b){
                        px = this.CCproperty[k].getLabel();
                        pxcase = 2;
                    }
                    else{
                        px = Math.min(a, b);
                        pxcase = 3;
                    }
                }
                if(pxcase == 1 || pxcase == 3){
                    updateEQ(this.newLabel, px);
                }
            }
        }
        return zfa;
    }

    public int[][] connect4Pass2(int [][]zfa) {
        //update Equivalence theorem
        int px, lbl;
        int e, g, k;
        for(int i=this.numRows; i>=1; i--){
            for(int j=this.numCols; j>=1; j--){
                px = zfa[i][j];
                if(px>0){
                    k = zfa[i][j];
                    e = zfa[i][j+1];
                    g = zfa[i+1][j];
                    if(e==0 && g==0){
                        continue;
                    }
                    if(e==g){
                        continue;
                    }
                    else{
                        lbl = Math.min(Math.min(e, g), px);
                        this.CCproperty[k].setLabel(lbl);
                    }
                }
            }
        }
        return zfa;

    }

    public void connectPass3(int [][]zfa, Property []CCproperty) {
        int k=0;
        int numpx;
        int px;
        // algo in specs
        for(int i=1; i<this.trueNumCC; i++){
            this.CCproperty[i].setLabel(i);
            this.CCproperty[i].setNumPixels(0);
            this.CCproperty[i].setMinR(this.numRows);
            this.CCproperty[i].setMaxR(0);
            this.CCproperty[i].setMinC(this.numCols);
            this.CCproperty[i].setMaxC(0);
        }
        for(int r=1; r<this.numRows; r++){
            for(int c=1; c<this.numCols; c++){
                px = zfa[r][c];
                if(px > 0){
                    zfa[r][c] = this.EQAry[px];
                    k = zfa[r][c];
                    numpx = this.CCproperty[k].getNumPixels();
                    CCproperty[k].setNumPixels(++numpx);
                    if(r < this.CCproperty[k].getMinR()){
                        this.CCproperty[k].setMinR(r);
                    }
                    if(r > this.CCproperty[k].getMaxR()){
                        this.CCproperty[k].setMaxR(r);
                    }
                    if(c < this.CCproperty[k].getMinC()){
                        this.CCproperty[k].setMinC(c);
                    }
                    if(c > this.CCproperty[k].getMaxC()){
                        this.CCproperty[k].setMaxC(c);
                    }
                }
            }
        }
    }

    public void drawBoxes(int [][]zfa, Property []CCproperty) {
        // algo in specs
        int minRow, minCol, maxRow, maxCol, label;
        for(int index=1; index<=this.trueNumCC; index++){
            minRow = this.CCproperty[index].getMinR()+1;
            minCol = this.CCproperty[index].getMinC()+1;
            maxRow = this.CCproperty[index].getMaxR()+1;
            maxCol = this.CCproperty[index].getMaxC()+1;
            label = this.CCproperty[index].getLabel();

            for(int i=minCol; i<=maxCol; i++){
                zfa[minRow][i] = label;
                zfa[maxRow][i] = label;
            }
            for(int i=minRow; i<=maxRow; i++){
                zfa[i][minCol] = label;
                zfa[i][minCol] = label;
            }
        }
    }

    public void updateEQ(int x, int y) {
        this.EQAry[x] = y;
    }

    public int manageEQAry() {
        int counter = 0;
        for(int i=1; i<=this.EQAry.length; i++){
            //if()
        }
        return 0;
    }

    public void printCCproperty() {

    }

    public void printEQAry() {
        for(int i = 1; i<=this.newLabel; i++){
            System.out.println(this.EQAry[i]);
        }
    }

    public void printImg(BufferedWriter out) throws IOException {
        out.write(this.numRows);
        out.write(" ");
        out.write(this.numCols);
        out.write(" ");
        out.write(this.minVal);
        out.write(" ");
        out.write(this.maxVal);
        out.write("\n");

        String str = Integer.toString(this.maxVal);
        int width = str.length();

        for (int r = 1; r < this.numRows; r++) {
            for (int c = 1; c < this.numCols; c++) {
                out.write(Integer.toString(this.zeroFramedAry[r][c]));
                String str2 = Integer.toString(this.zeroFramedAry[r][c]);
                int WW = str2.length();
                out.write(" ");
                while (WW < width) {
                    out.write(" ");
                    WW++;
                }
            }
            out.write("\n");
        }
    }

    // setter/getter here
}
