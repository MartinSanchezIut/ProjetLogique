package projetlogique.utils;

import projetlogique.exceptions.CannotParseOperatorException;
import projetlogique.formules.Formule;
import projetlogique.formules.Operateurs;
import projetlogique.formules.SplitFormule;

import java.util.ArrayList;

public class Arbre {

    Formule formule;
    Noeud noeud;

    public static int nbContradiction = 0;

    public Arbre(String formule) {
        this.formule = new Formule(Operateurs.NOT + "(" + formule + ")");
        noeud = null;
    }

    public void creer() throws CannotParseOperatorException {
        System.out.println("Formule : " + formule );
        System.out.println("-------");
        ArrayList<Formule> array = new ArrayList<>();
        array.add(formule);
        noeud = new Noeud(array) ;

        noeud.continuer() ;
    }
}
