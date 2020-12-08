package projetlogique.formules;

public class Literal {

   boolean isNegative;
   Character literal;

    public Literal(boolean isNegative, Character literal) {
        this.isNegative = isNegative;
        this.literal = literal;
    }

    @Override
    public String toString() {
        if (isNegative) {
            return Operateur.NOT.toString() + literal;
        } else {
            return literal+"";
        }
    }

    public static void main(String[] args) {
        System.out.println(getNbContradiction(new Formule("¬(p → ((p → q) → q))")));
    }

    public static int getNbContradiction(Formule f) {
        System.out.println("Formule : " + f);

        
        return 0;
    }
}
