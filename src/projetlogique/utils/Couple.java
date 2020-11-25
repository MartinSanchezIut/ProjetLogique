package projetlogique.utils;

public class Couple {


    private String s1;
    private String s2;


    public Couple() {}

    public Couple(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    public void setRight(String right) {
        s2 = right;
    }

    public void setLeft(String left) {
        s1 = left;
    }


    public String getRight() {
        return s2;
    }

    public String getLeft() {
        return s1;
    }
}
