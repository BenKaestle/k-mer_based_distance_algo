package utility;

public class Distance {
    private String header_1;
    private String header_2;
    private float jaccard_index;
    private double p_value;
    private float mash_distance;
    private String filePath1;
    private String filePath2;
    private int sameHashes;

    public Distance(String header_1, String header_2, float jaccard_index, double p_value, float mash_distance, String filePath1, String filePath2, int sameHashes) {
        this.header_1 = header_1;
        this.header_2 = header_2;
        this.jaccard_index = jaccard_index;
        this.p_value = p_value;
        this.mash_distance = mash_distance;
        this.filePath1 = filePath1;
        this.filePath2 = filePath2;
        this.sameHashes = sameHashes;
    }

    public Distance(Distance distance){
        this.header_1 = distance.header_2;
        this.header_2 = distance.header_1;
        this.jaccard_index = distance.jaccard_index;
        this.p_value = distance.p_value;
        this.mash_distance = distance.mash_distance;
        this.filePath1 = distance.filePath2;
        this.filePath2 = distance.filePath1;
        this.sameHashes = distance.sameHashes;
    }

    public int getSameHashes() {
        return sameHashes;
    }

    public String getFilePath1() {
        return filePath1;
    }

    public String getFilePath2() {
        return filePath2;
    }

    public String getHeader_1() {
        return header_1;
    }

    public String getHeader_2() {
        return header_2;
    }

    public float getJaccard_index() {
        return jaccard_index;
    }

    public double getP_value() {
        return p_value;
    }

    public float getMash_distance() {
        return mash_distance;
    }

    public void print(){
        System.out.println("header1: "+ this.header_1);
        System.out.println("header2: "+ this.header_2);
        System.out.println("jaccard: "+ this.jaccard_index);
        System.out.println("MashDis: "+ this.mash_distance);
        System.out.println("p_Value: "+ this.p_value);
    }
    public void printShort(){
        System.out.println(this.filePath1 + "\t" + this.filePath2 + "\t" + this.mash_distance + "\t" + this.p_value + "\t" + this.sameHashes+"/");
    }
}