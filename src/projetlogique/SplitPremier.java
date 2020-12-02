package projetlogique;

import projetlogique.exceptions.CannotParseOperatorException;
import projetlogique.formules.Formule;
import projetlogique.formules.Operateurs;
import projetlogique.utils.Arbre;

import java.util.ArrayList;
import java.util.Arrays;

public class SplitPremier {

    public static void main(String[] args) {

        try {
            Formule f = new Formule("¬(p ∨ ((p ∧ q) ∨ q))");

            System.out.println(f.toString());
            System.out.println(f.getFormule());


            System.out.println(f.split().toString());

            System.out.println("---------------------------");

            Arbre a = new Arbre(f.toString()) ;
            a.creer();


           // simplifie("p → (¬(p → q) → q)");
        } catch (CannotParseOperatorException e) {
            e.printStackTrace();
        }
    }

    public static String simplifie(String formule) throws CannotParseOperatorException {
        String copy = formule ;
        int taille = copy.length() ;

        for (int i = 0; i < taille; i++) {
            if (Operateurs.getValidsOperators().contains(copy.charAt(i)+"")) {
                if ((Operateurs.parse(copy.charAt(i)) == Operateurs.NOT)) {

                    if ((i + 1 < taille) && (copy.charAt(i + 1) == '(')) {

                        int prio = 0;
                        boolean sortie = false;
                        int j = i+1;
                        while(j < taille && !sortie) {
                            if (copy.charAt(j) == '(') {
                                prio++;
                            }
                            if (copy.charAt(j) == ')') {
                                prio--;
                            }
                            if (prio == 0) { sortie = true;}
                            j++;
                        }
                        String nf = copy.substring(i, j);
                        System.out.println("le truc " + nf);

                        String p1 = copy.substring(0, i);
                        String p2 = copy.substring(j, taille);

                       // copy = p1 + evalNOT(nf) + p2 ;

                    }
                }
            }
        }


        return copy;
    }
/*
    public static String evalNOT(String formule) throws CannotParseOperatorException {
        // NON(Formule)

        ArrayList<String> split = split(formule.substring(1, formule.length())) ;

        if (Operateurs.parse(split.get(0)) == Operateurs.AND ) {
            return Operateurs.NOT + split.get(1) +  Operateurs.OR +   Operateurs.NOT + split.get(2)  ;
        }else if (Operateurs.parse(split.get(0)) == Operateurs.OR ) {
            return Operateurs.NOT + split.get(1) +  Operateurs.AND +   Operateurs.NOT + split.get(2) ;
        }else if (Operateurs.parse(split.get(0)) == Operateurs.IMPLICATION ) {
            return split.get(1) +  Operateurs.AND +   Operateurs.NOT + split.get(2) ;
        }
        return formule;
    }
*/

    /*
        static split ( String formule ) : retourne la formule simplifie par la methodee de l'arbre
     */
    public static ArrayList<String> split(String formule) throws CannotParseOperatorException {

        int taille = formule.length() ;

        /*
                Index et priorités des operateurs trouvé
         */
        ArrayList<Integer> index = new ArrayList<>() ;
        ArrayList<Integer> priorite = new ArrayList<>() ;
        ArrayList<Operateurs> operateursTrouve = new ArrayList<>() ;

        int prio = 0;

        for (int i = 0; i < taille; i++) {
            if (formule.charAt(i) == '(') {  prio++ ;}
            if (formule.charAt(i) == ')') {  prio-- ;}

            if (Operateurs.getValidsOperators().contains(formule.charAt(i))) {
                Operateurs op = Operateurs.parse(formule.charAt(i)) ;

                if (op != Operateurs.NOT) {
                    index.add(i);
                    priorite.add(prio);
                    operateursTrouve.add(op);
                }
            }
        }

        int minPrio = priorite.get(0) ;
        int minIndex = 0 ;
        for (int i = 0; i < priorite.size(); i++) {
            if (priorite.get(i) < minPrio) {
                minPrio = priorite.get(i) ;
                minIndex = i;
            }
        }


        System.out.println("Indexs " + index);
        System.out.println("Priorites " + priorite);
        System.out.println("Operateurs " + operateursTrouve);
        System.out.println("-=-=-=-=-=-==-=-=-=-=-=-=-=");
        System.out.println(operateursTrouve.get(minIndex) + " [ i: " + index.get(minIndex) + ", p: " + minPrio + "]");

        String p1 = formule.substring(0, index.get(minIndex)) ;
        String p2 = formule.substring(index.get(minIndex)+1, taille) ;

        ArrayList<String> ret = new ArrayList<>();
        ret.add(operateursTrouve.get(minIndex).toString());
        ret.add(p1);
        ret.add(p2);

        System.out.println(formule);
        System.out.println(p1 + " | " + p2);

        return ret;
    }
}
