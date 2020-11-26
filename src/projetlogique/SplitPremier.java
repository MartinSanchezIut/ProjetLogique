package projetlogique;

import projetlogique.exceptions.CannotParseOperatorException;
import projetlogique.formules.Operateurs;

import java.util.ArrayList;
import java.util.Arrays;

public class SplitPremier {

    public static void main(String[] args) {

        try {
            split("p → ((p → q) → q)");
        } catch (CannotParseOperatorException e) {
            e.printStackTrace();
        }
    }

    /*
        static split ( String formule ) : retourne la formule simplifie par la methodee de l'arbre
     */
    public static void split(String formule) throws CannotParseOperatorException {

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

            if (Operateurs.getValidsOperators().contains(formule.charAt(i)+"")) {
                Operateurs op = Operateurs.parse(formule.charAt(i)+"") ;

                if (op != Operateurs.NOT) {
                    index.add(i);
                    priorite.add(prio);
                    operateursTrouve.add(op);
                }
            }
        }

        int minPrio = priorite.get(0) ;
        int minIndex = index.get(0) ;
        for (int i = 0; i < priorite.size(); i++) {
            if (priorite.get(i) < minPrio) {
                minPrio = priorite.get(i) ;
                minIndex = index.get(i);
            }
        }

        System.out.println("Indexs " + index);
        System.out.println("Priorites " + priorite);
        System.out.println("Operateurs " + operateursTrouve);
        System.out.println("-=-=-=-=-=-==-=-=-=-=-=-=-=");
        System.out.println(operateursTrouve.get(minIndex) + " [ i: " + minIndex + ", p: " + minPrio + "]");

        String p1 = formule.substring(0, minIndex) ;
        String p2 = formule.substring(minIndex+1, taille) ;

        System.out.println(formule);
        System.out.println(p1 + " | " + p2);
    }
}
