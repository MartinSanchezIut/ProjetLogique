package projetlogique.formules;

import projetlogique.utils.Couple;

import java.util.ArrayList;

public class Formule {

    private String formule;

    public Formule(String formule) {
        this.formule = formule;
    }

    public String getFormule() {
        return formule;
    }




    public Couple<String> split() {
        Couple<String> ret = new Couple<>() ;

        // TEMPORAZIRE
        ret.setFirst(formule.substring(0, formule.length()/2));
        ret.setSecond(formule.substring(formule.length()/2, formule.length() ));

        return ret;
    }





    public boolean isValid() {
        return true;
    }
}
