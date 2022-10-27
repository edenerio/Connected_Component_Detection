import java.util.*;
import java.io.*;

public class Enerio_Edison_main {
    public static void main(String[] args) throws IOException {
        if (args.length != 5) {
            System.out.println("Invalid Arguments, closing the program...");
            System.out.println(args.length);
            return;
        }

        Scanner inFile = new Scanner(new File(args[0]));
        BufferedWriter RFprettyPrintFile = new BufferedWriter(new FileWriter(args[2]));
        BufferedWriter labelFile = new BufferedWriter(new FileWriter(args[3]));
        BufferedWriter propertyFile = new BufferedWriter(new FileWriter(args[4]));

        int numRows = inFile.nextInt();
        int numCols = inFile.nextInt();
        int minVal = inFile.nextInt();
        int maxVal = inFile.nextInt();

        CClabel myCC = new CClabel(numRows, numCols, minVal, maxVal);

        // step 1
        myCC.zero2D(myCC.zeroFramedAry);

        // step 2
        myCC.loadImage(inFile);

        // step 3
        int whichConnectness = Integer.parseInt(args[1]);

        // step 4
        if (whichConnectness == 4) {
            myCC.zeroFramedAry = myCC.connect4Pass1(myCC.zeroFramedAry);
            RFprettyPrintFile.write("Result of Pass 1: \n");
            myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
            RFprettyPrintFile.write("EQ Ary after Pass 1: \n");
            myCC.printEQAry(RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
            myCC.zeroFramedAry = myCC.connect4Pass2(myCC.zeroFramedAry);
            RFprettyPrintFile.write("Result of Pass 2: \n");
            myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
            RFprettyPrintFile.write("EQ Ary after Pass 2: \n");
            myCC.printEQAry(RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
        }

        // step 5
        if (whichConnectness == 8) {
            myCC.zeroFramedAry = myCC.connect8Pass1(myCC.zeroFramedAry);
            RFprettyPrintFile.write("Result of Pass 1: \n");
            myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
            RFprettyPrintFile.write("EQ Ary after Pass 1: \n");
            myCC.printEQAry(RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
            myCC.zeroFramedAry = myCC.connect8Pass2(myCC.zeroFramedAry);
            RFprettyPrintFile.write("Result of Pass 2: \n");
            myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
            RFprettyPrintFile.write("EQ Ary after Pass 2:\n");
            myCC.printEQAry(RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
        }
        
        // step 6
        myCC.setTrueNum(myCC.manageEQAry());
        RFprettyPrintFile.write("EQ Ary after management: \n");
        myCC.printEQAry(RFprettyPrintFile);
        myCC.makeBorder(RFprettyPrintFile);
        myCC.setNewMin(0);
        myCC.setNewMax(myCC.getTrueNum());
        myCC.allocateCCproperty(myCC.getTrueNum());

        // step 7
        myCC.connectPass3(myCC.zeroFramedAry, myCC.CCproperty);

        // step 8
        RFprettyPrintFile.write("Result of Pass 3 \n");
        myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
        myCC.makeBorder(RFprettyPrintFile);

        // step 9
        RFprettyPrintFile.write("EQ Ary after Pass 3 \n");
        myCC.printEQAry(RFprettyPrintFile);
        myCC.makeBorder(RFprettyPrintFile);

        // step 10
        labelFile.write(Integer.toString(myCC.getNumRows()));
        labelFile.write(" ");
        labelFile.write(Integer.toString(myCC.getNumCols()));
        labelFile.write(" ");
        labelFile.write(Integer.toString(myCC.getNewMin()));
        labelFile.write(" ");
        labelFile.write(Integer.toString(myCC.getNewMax()));
        labelFile.write("\n");

        // step 11
        myCC.printImg(labelFile);

        // step 12
        myCC.printCCproperty(propertyFile);

        // step 13
        myCC.drawBoxes(myCC.zeroFramedAry, myCC.CCproperty);

        // step 14
        RFprettyPrintFile.write("After boxes:\n");
        myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
        myCC.makeBorder(RFprettyPrintFile);

        // step 15
        RFprettyPrintFile.write("True number of Connected Components: ");
        RFprettyPrintFile.write(Integer.toString(myCC.getTrueNum()));

        // step 16
        inFile.close();
        labelFile.close();
        RFprettyPrintFile.close();
        propertyFile.close();
    }
}