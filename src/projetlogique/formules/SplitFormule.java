package projetlogique.formules;

public class SplitFormule {

    private Formule f1;
    private Formule f2;
    private Operateurs op;

    public SplitFormule(Formule f1, Operateurs op, Formule f2) {
        this.f1 = f1;
        this.f2 = f2;
        this.op = op;
    }

    public Formule getF1() {
        return f1;
    }

    public Formule getF2() {
        return f2;
    }

    public Operateurs getOp() {
        return op;
    }

    @Override
    public String toString() {
        return "SplitFormule{" +
                "f1=" + f1 +
                ", f2=" + f2 +
                ", op=" + op +
                '}';
    }
}
