public class Property {
    private int label, numPixels, minR, minC, maxR, maxC;

    public void setLabel(int i){
        this.label = i;
    }

    public void setNumPixels(int i){
        this.numPixels = i;
    }

    public void setMinR(int i){
        this.minR = i;
    }

    public void setMinC(int i){
        this.minC = i;
    }

    public void setMaxR(int i){
        this.maxR = i;
    }

    public void setMaxC(int i){
        this.maxC = i;
    }

    public int getLabel(){
        return this.label;
    }

    public int getNumPixels(){
        return this.numPixels;
    }

    public int getMinR(){
        return this.minR;
    }

    public int getMinC(){
        return this.minC;
    }

    public int getMaxR(){
        return this.maxR;
    }

    public int getMaxC(){
        return this.maxC;
    }
}
