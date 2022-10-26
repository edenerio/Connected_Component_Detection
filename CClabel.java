import java.util.*;
import java.io.*;

public class CClabel extends Property {
    private int numRows, numCols, minVal, maxVal, newLabel, newMin, newMax;
    public int trueNumCC;
    public int zeroFramedAry[][];
    private int nonZeroNeighborAry[];
    private int EQAry[];
    Property[] CCproperty;

    CClabel(int numRows, int numCols, int minVal, int maxVal) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.newLabel = 0;
        this.trueNumCC = 0;
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
        for (int i = 1; i <= numRows; i++) {
            for (int j = 1; j <= numCols; j++) {
                this.zeroFramedAry[i][j] = b.nextInt();
            }
        }
    }

    public void imgReformat(int[][] zfa, BufferedWriter prettyprint) throws IOException {
        /*
         * Print zeroFramedAry to prettyprint. Reuse code from previous project
         */
        prettyprint.write(Integer.toString(this.numRows));
        prettyprint.write(" ");
        prettyprint.write(Integer.toString(this.numCols));
        prettyprint.write(" ");
        prettyprint.write(Integer.toString(this.minVal));
        prettyprint.write(" ");
        prettyprint.write(Integer.toString(this.maxVal));
        prettyprint.write(" ");
        String str = Integer.toString(this.newLabel);
        prettyprint.write("\n");
        int width = str.length();

        for (int r = 1; r <= this.numRows; r++) {
            for (int c = 1; c <= this.numCols; c++) {
                prettyprint.write(Integer.toString(zfa[r][c]));
                //1
                System.out.print(zfa[r][c]);
                //2
                String str2 = Integer.toString(zfa[r][c]);
                int WW = str2.length();
                prettyprint.write(" ");
                //1
                System.out.print(" ");
                //2
                while (WW < width) {
                    prettyprint.write(" ");
                    System.out.print(" ");
                    WW++;
                }
            }
            prettyprint.write("\n");
            System.out.println();
        }
    }

    public int[][] connect8Pass1(int[][] zfa) {
        int px, minLabel;
        int helper;
        int a, b, c, d;
        boolean flag = true;
        for (int i = 1; i <= this.numRows; i++) {
            for (int j = 1; j <= this.numCols; j++) {
                px = zfa[i][j];
                if (px > 0) {
                    helper = 0;
                    a = zfa[i - 1][j - 1];
                    b = zfa[i - 1][j];
                    c = zfa[i - 1][j + 1];
                    d = zfa[i][j - 1];
                    // case 1
                    if (a == 0 && b == 0 && c == 0 && d == 0) {
                        this.newLabel++;
                        updateEQ(this.newLabel, this.newLabel);
                        zfa[i][j] = this.newLabel;
                    } else {
                        if (a != 0) {
                            this.nonZeroNeighborAry[helper++] = a;
                        }
                        if (b != 0) {
                            this.nonZeroNeighborAry[helper++] = b;
                        }
                        if (c != 0) {
                            this.nonZeroNeighborAry[helper++] = c;
                        }
                        if (d != 0) {
                            this.nonZeroNeighborAry[helper++] = d;
                        }
                        for(int e=1; e<helper; e++){
                            if(this.nonZeroNeighborAry[e]!=this.nonZeroNeighborAry[e-1]){
                                flag = false;
                            }
                        }
                        // case 2
                        if(flag){
                            zfa[i][j] = this.nonZeroNeighborAry[0];
                            this.nonZeroNeighborAry = minus1D(this.nonZeroNeighborAry);
                        }
                        // case 3
                        else {
                            minLabel = findMin();
                            if(zfa[i - 1][j - 1] != 0){
                                updateEQ(zfa[i - 1][j - 1], minLabel);
                            }
                            if(zfa[i - 1][j] != 0){
                                updateEQ(zfa[i - 1][j], minLabel);
                            }
                            if(zfa[i - 1][j + 1] != 0){
                                updateEQ(zfa[i - 1][j + 1], minLabel);
                            }
                            if(zfa[i][j - 1] != 0){
                                updateEQ(zfa[i][j - 1], minLabel);
                            }
                            zfa[i][j] = minLabel;
                            updateEQ(px, minLabel);
                        }
                    }
                }
            }
        }
        return zfa;
    }

    public int[][] connect8Pass2(int[][] zfa) {
        // update Equivalence theorem
        int px, lbl;
        int e, f, g, h;
        int helper = 0;
        boolean flag = true;
        for (int i = this.numRows; i >= 1; i--) {
            for (int j = this.numCols; j >= 1; j--) {
                px = zfa[i][j];
                if (px > 0) {
                    helper = 0;
                    this.nonZeroNeighborAry[helper++] = px;
                    e = zfa[i][j + 1];
                    f = zfa[i + 1][j - 1];
                    g = zfa[i + 1][j];
                    h = zfa[i + 1][j + 1];
                    if(e != 0){
                        this.nonZeroNeighborAry[helper++] = e;
                        if(px != e){
                            flag = false;
                        }
                    }
                    if(f != 0){
                        this.nonZeroNeighborAry[helper++] = f;
                        if(px != f){
                            flag = false;
                        }
                    }
                    if(g != 0){
                        this.nonZeroNeighborAry[helper++] = g;
                        if(px != g){
                            flag = false;
                        }
                    }
                    if(h != 0){
                        this.nonZeroNeighborAry[helper++] = h;
                        if(px != h){
                            flag = false;
                        }
                    }
                    //case 3
                    if (!flag) {
                        lbl = findMin();
                        zfa[i][j] = lbl;
                        if(zfa[i][j + 1]!=0){
                            zfa[i][j + 1] = lbl;
                        }
                        if(zfa[i + 1][j - 1]!=0){
                            zfa[i + 1][j - 1] = lbl;
                        }
                        if(zfa[i + 1][j] != 0){
                            zfa[i + 1][j] = lbl;
                        }
                        if(zfa[i + 1][j + 1] != 0){
                            zfa[i + 1][j + 1] = lbl;
                        }
                        updateEQ(px, lbl);
                        flag=true;
                    }
                }
            }
        }
        return zfa;
    }

    public int[][] connect4Pass1(int[][] zfa) {
        int px, lbl;
        int a, b;
        int helper = 0;
        boolean flag = true;
        for (int i = 1; i <= this.numRows; i++) {
            for (int j = 1; j <= this.numCols; j++) {
                px = zfa[i][j];
                if (px > 0) {
                    helper = 0;
                    a = zfa[i - 1][j];
                    b = zfa[i][j - 1];
                    //case 1
                    if (a == 0 && b == 0) {
                        this.newLabel++;
                        updateEQ(this.newLabel, this.newLabel);
                        zfa[i][j] = this.newLabel;
                    } else {
                        if (a != 0) {
                            this.nonZeroNeighborAry[helper++] = a;
                        }
                        if (b != 0) {
                            this.nonZeroNeighborAry[helper++] = b;
                            if (a != b && a != 0) {
                                flag = false;
                            }
                        }
                        //case 2
                        if (flag){
                            zfa[i][j] = this.nonZeroNeighborAry[0];
                            this.nonZeroNeighborAry = minus1D(this.nonZeroNeighborAry);
                        }
                        //case 3
                        if(!flag) {
                            lbl = findMin();
                            if(zfa[i - 1][j]!=0){
                                updateEQ(zfa[i - 1][j], lbl);
                            }
                            if(zfa[i][j - 1]!=0){
                                updateEQ(zfa[i][j - 1], lbl);
                            }
                            zfa[i][j] = lbl;
                            flag = true;
                        }
                    }
                }
            }
        }
        return zfa;
    }

    public int[][] connect4Pass2(int[][] zfa) {
        // update Equivalence theorem
        int px, lbl;
        int e, g;
        int helper = 0;
        boolean flag = true;
        for (int i = this.numRows; i >= 1; i--) {
            for (int j = this.numCols; j >= 1; j--) {
                px = zfa[i][j];
                if (px > 0) {
                    helper = 0;
                    this.nonZeroNeighborAry[helper++] = px;
                    e = zfa[i][j + 1];
                    g = zfa[i + 1][j];
                    if(e != 0){
                        this.nonZeroNeighborAry[helper++] = e;
                        if(px != e){
                            flag = false;
                        }
                    }
                    if(g != 0){
                        this.nonZeroNeighborAry[helper++] = g;
                        if(px != g){
                            flag = false;
                        }
                    }
                    //case 3
                    if (!flag) {
                        lbl = findMin();
                        zfa[i][j] = lbl;
                        if(zfa[i + 1][j] != 0){
                            zfa[i + 1][j] = lbl;
                        }
                        if(zfa[i][j + 1] != 0){
                            zfa[i][j + 1] = lbl;
                        }
                        updateEQ(px, lbl);
                        flag=true;
                    }
                }
            }
        }
        return zfa;

    }

    public void connectPass3(int[][] zfa, Property[] CCproperty) {
        int k = 0;
        int numpx;
        int px;
        // algo in specs
        for (int i = 1; i <= this.trueNumCC; i++) {
            this.CCproperty[i].setLabel(i);
            this.CCproperty[i].setNumPixels(0);
            this.CCproperty[i].setMinR(this.numRows);
            this.CCproperty[i].setMaxR(0);
            this.CCproperty[i].setMinC(this.numCols);
            this.CCproperty[i].setMaxC(0);
        }
        for (int r = 1; r < this.numRows; r++) {
            for (int c = 1; c < this.numCols; c++) {
                px = zfa[r][c];
                if (px > 0) {
                    zfa[r][c] = this.EQAry[px];
                    k = zfa[r][c];
                    numpx = this.CCproperty[k].getNumPixels();
                    CCproperty[k].setNumPixels(++numpx);
                    if (r < this.CCproperty[k].getMinR()) {
                        this.CCproperty[k].setMinR(r);
                    }
                    if (r > this.CCproperty[k].getMaxR()) {
                        this.CCproperty[k].setMaxR(r);
                    }
                    if (c < this.CCproperty[k].getMinC()) {
                        this.CCproperty[k].setMinC(c);
                    }
                    if (c > this.CCproperty[k].getMaxC()) {
                        this.CCproperty[k].setMaxC(c);
                    }
                }
            }
        }
    }

    public void allocateCCproperty(int truenumcc){
        this.CCproperty = new Property[truenumcc+1];
        for(int i=0; i<=truenumcc; i++){
            this.CCproperty[i] = new Property();
        }
    }

    public void drawBoxes(int[][] zfa, Property[] CCproperty) {
        // algo in specs
        int minRow, minCol, maxRow, maxCol, label;
        for (int index = 1; index <= this.trueNumCC; index++) {
            minRow = this.CCproperty[index].getMinR() + 1;
            minCol = this.CCproperty[index].getMinC() + 1;
            maxRow = this.CCproperty[index].getMaxR() + 1;
            maxCol = this.CCproperty[index].getMaxC() + 1;
            label = this.CCproperty[index].getLabel();

            for (int i = minCol; i <= maxCol; i++) {
                zfa[minRow][i] = label;
                zfa[maxRow][i] = label;
            }
            for (int i = minRow; i <= maxRow; i++) {
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
        for (int i = 1; i < this.EQAry.length; i++) {
            if (i==this.EQAry[i]) {
                this.EQAry[i] = ++counter;
            }
            else {
                this.EQAry[i] = this.EQAry[this.EQAry[i]];
            }
        }
        for(int i = 1; i<=counter; i++){
            System.out.print(this.EQAry[i] + " ");
        }
        this.trueNumCC = counter;
        return counter;
    }

    public void printCCproperty(BufferedWriter out) throws IOException {
        out.write(this.numRows);
        out.write(" ");
        out.write(this.numCols);
        out.write(" ");
        out.write(this.minVal);
        out.write(" ");
        out.write(this.maxVal);
        out.write("\n");
        out.write(this.trueNumCC);
        out.write("\n");
        for(int i=1; i<=this.trueNumCC; i++){
            out.write(this.CCproperty[i].getLabel());
            out.write("\n");
            out.write(this.CCproperty[i].getNumPixels());
            out.write("\n");
            out.write(this.CCproperty[i].getMinR());
            out.write(" ");
            out.write(this.CCproperty[i].getMinC());
            out.write("\n");
            out.write(this.CCproperty[i].getMaxR());
            out.write(" ");
            out.write(this.CCproperty[i].getMaxC());
            out.write("\n");
        }
    }

    public void printEQAry(BufferedWriter out) throws IOException {
        for (int i = 1; i <= this.newLabel; i++) {
            out.write(Integer.toString(this.EQAry[i]));
            out.write(" ");
        }
        out.write("\n");
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

    private int findMin() {
        int retVal = this.nonZeroNeighborAry[0];
        for (int i = 1; i < this.nonZeroNeighborAry.length; i++) {
            if (retVal > this.nonZeroNeighborAry[i] && this.nonZeroNeighborAry[i] != -1) {
                retVal = this.nonZeroNeighborAry[i];
            }
        }
        this.nonZeroNeighborAry = minus1D(this.nonZeroNeighborAry);
        return retVal;
    }

    public void makeBorder(BufferedWriter out) throws IOException{
        for(int i=0; i<=this.numRows+1; i++){
            out.write("---");
        }
        out.write("\n");
    }

    // setter/getter here
    public void setTrueNum(int i){
        this.trueNumCC = i;
    }

    public void setNewMin(int i){
        this.newMin = i;
    }

    public void setNewMax(int i){
        this.newMax = i;
    }

    public int getTrueNum(){
        return this.trueNumCC;
    }

    public int getNumRows(){
        return this.numRows;
    }

    public int getNumCols(){
        return this.numCols;
    }

    public int getNewMin(){
        return this.newMin;
    }

    public int getNewMax(){
        return this.newMax;
    }
}
