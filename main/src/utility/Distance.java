package utility;

public class Distance {
    private String header_1;
    private String header_2;
    private float jaccard_index;
    private float p_value;
    private float mash_distance;

    public Distance(String header_1, String header_2, float jaccard_index, float p_value, float mash_distance) {
        this.header_1 = header_1;
        this.header_2 = header_2;
        this.jaccard_index = jaccard_index;
        this.p_value = p_value;
        this.mash_distance = mash_distance;
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

    public float getP_value() {
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
}
