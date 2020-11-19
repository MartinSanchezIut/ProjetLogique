package projetlogique.Formules;

import java.util.ArrayList;

public class Formule {

    private String formule;

    public Formule(String formule) {
        this.formule = formule;
    }

    public String getFormule() {
        return formule;
    }

















    public boolean isValid() {
        boolean ret = true;

        int countParentesis = 0;
        boolean isEmpty = formule.length() == 0;


        for (int i = 0; i < formule.length(); i++) {
            if (formule.charAt(i) == '(') {  countParentesis++;  }
            if (formule.charAt(i) == ')') {  countParentesis--;  }

            /*
            if (! Character.isAlphabetic(formule.charAt(i))) {
                ret = false;

            }
             */



        }

        if (countParentesis != 0) {   ret = false;   }

        return ret && isEmpty;
    }
}
