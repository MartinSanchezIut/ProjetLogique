package projetlogique.utils;

import projetlogique.exceptions.CannotParseOperatorException;
import projetlogique.formules.Formule;
import projetlogique.formules.Operateurs;
import projetlogique.formules.SplitFormule;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Noeud {

    ArrayList<Formule> listeFormules;

    Noeud f1;
    Noeud f2;

    public Noeud(ArrayList<Formule> listeElements) {
        this.listeFormules = listeElements;
        f1 = null;
        f2 = null;
    }

    public ArrayList<Formule> getListeFormules() {     return listeFormules;    }
    public Noeud getF1() {        return f1;    }
    public Noeud getF2() {        return f2;    }


    public void continuer() throws CannotParseOperatorException {

        if (hasContradiction()) { Arbre.nbContradiction++; }

        Formule f = firstSplitable();

        if (f != null) {

            SplitFormule sp = f.split();
            System.out.println(sp);


            if (sp.getOp() == Operateurs.AND) {
                ArrayList<Formule> lf = new ArrayList<>();
                lf.addAll(listeFormules);
                lf.add(sp.getF1()) ;
                lf.add(sp.getF2()) ;
                f1 = new Noeud(lf) ;

                f1.continuer();

            }else { // Le car du OR
                ArrayList<Formule> lf = new ArrayList<>();
                ArrayList<Formule> lf2 = new ArrayList<>();

                lf.addAll(listeFormules);
                lf2.addAll(listeFormules);

                lf.add(sp.getF1()) ;
                lf2.add(sp.getF2()) ;

                f1 = new Noeud(lf) ;
                f2 = new Noeud(lf2) ;

                f1.continuer();
                f2.continuer();
            }


        }



    }

    private boolean hasContradiction() {
        if (firstSplitable() != null) { return false;}
        for (Formule f: listeFormules  ) {
            Formule oppose = new Formule(Operateurs.NOT + f.getFormule()) ;
            if (listeFormules.contains(oppose)) {
                return true;
            }
        }
        return false;
    }


    private Formule firstSplitable() {
        for (int i = 0; i < listeFormules.size(); i++) {
            if (listeFormules.get(i).isSplitable()) {
                return listeFormules.get(i) ;
            }
        }
        return null;
    }


}
