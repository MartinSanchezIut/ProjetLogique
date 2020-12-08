package projetlogique.formules;

import java.util.ArrayList;

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

        Noeud n = new Noeud(f) ;

        return 0;
    }

    public static class Noeud {

        ArrayList<Formule> formules;
        Noeud f1;
        Noeud f2;

        public Noeud(Formule f) {
            formules = new ArrayList<>();
            formules.add(f) ;
            f1 = null;
            f2 = null;
        }
    }
}
