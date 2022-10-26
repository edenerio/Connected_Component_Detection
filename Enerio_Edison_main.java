import java.util.*;
import java.io.*;

public class Enerio_Edison_main {
    public static void main(String[] args) throws IOException {
        if(args.length != 5){
            System.out.println("Invalid Arguments, closing the program...");
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

        //step 1
        myCC.zero2D(myCC.zeroFramedAry);

        //step 2
        myCC.loadImage(inFile);

        //step 3
        int whichConnectness = Integer.parseInt(args[1]);

        //step 4
        if(whichConnectness==4){
            myCC.connect4Pass1(myCC.zeroFramedAry);
            myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
            RFprettyPrintFile.write("EQ Ary after Pass 1:");
            System.out.println("after pass 1: ");
            myCC.printEQAry(RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
            myCC.connect4Pass2(myCC.zeroFramedAry);
            myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
            RFprettyPrintFile.write("EQ Ary after Pass 2:");
            System.out.println("after pass 2:");
            myCC.printEQAry(RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
        }

        //step 5
        if(whichConnectness==8){
            myCC.connect8Pass1(myCC.zeroFramedAry);
            myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
            RFprettyPrintFile.write("EQ Ary after Pass 1:");
            System.out.println("after pass 1: ");
            myCC.printEQAry(RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
            myCC.connect8Pass2(myCC.zeroFramedAry);
            myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);
            RFprettyPrintFile.write("EQ Ary after Pass 2:");
            System.out.println("after pass 2:");
            myCC.printEQAry(RFprettyPrintFile);
            myCC.makeBorder(RFprettyPrintFile);
        }

        //step 6
        myCC.setTrueNum(myCC.manageEQAry());
        System.out.println(myCC.getTrueNum());
        RFprettyPrintFile.write("EQ Ary up to new label");
        System.out.println("from step 6");
        myCC.printEQAry(RFprettyPrintFile);
        myCC.setNewMin(0);
        myCC.setNewMax(myCC.getTrueNum());
        myCC.allocateCCproperty(myCC.getTrueNum());

        //step 7
        myCC.connectPass3(myCC.zeroFramedAry, myCC.CCproperty);

        //step 8
        myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);

        //step 9
        myCC.printEQAry(RFprettyPrintFile);

        //step 10
        labelFile.write(myCC.getNumRows());
        labelFile.write(" ");
        labelFile.write(myCC.getNumCols());
        labelFile.write(" ");
        labelFile.write(myCC.getNewMin());
        labelFile.write(" ");
        labelFile.write(myCC.getNewMax());
        labelFile.write("\n");

        //step 11
        myCC.printImg(labelFile);

        //step 12
        myCC.printCCproperty(propertyFile);

        //step 13
        myCC.drawBoxes(myCC.zeroFramedAry, myCC.CCproperty);

        //step 14
        myCC.imgReformat(myCC.zeroFramedAry, RFprettyPrintFile);

        //step 15
        RFprettyPrintFile.write("True number of Conected Components: ");
        RFprettyPrintFile.write(myCC.getTrueNum());

        //step 16
        inFile.close();
        labelFile.close();
        RFprettyPrintFile.close();
        propertyFile.close();
    }
}