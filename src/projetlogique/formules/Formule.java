package projetlogique.formules;

import projetlogique.exceptions.CannotParseOperatorException;
import projetlogique.utils.Couple;

import java.util.ArrayList;

public class Formule {

    private String formule;

    public Formule(String formule) {
        String f = formule.replaceAll(" ", "");

        f = f.replaceAll("∧", "&");
        f = f.replaceAll("∨", "|");
        f = f.replaceAll("¬", "!");
        f = f.replaceAll("→", ">");
        f = f.replaceAll("↔", "~");

        this.formule = f;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder() ;

        for (int i = 0; i < formule.length(); i++) {

            if ((formule.charAt(i) == '(' && i+1 < formule.length() && formule.charAt(i+1) == '(') ||
                    (formule.charAt(i) == ')' && i+1 < formule.length() && formule.charAt(i+1) == ')' )){

                sb.append(formule.charAt(i)) ;

            }else {
                sb.append(formule.charAt(i) + " ") ;
            }

        }
        String f = sb.toString() ;

        f = f.replaceAll("&", "∧");
        f = f.replaceAll("\\|", "∨");
        f = f.replaceAll("!", "¬");
        f = f.replaceAll("N O T", "¬");
        f = f.replaceAll(">", "→");
        f = f.replaceAll("~", "↔");

        return f;
    }
    public String getFormule() { return formule; }


    public int nbLiteraux() {
        int retu = 0;
        for (int i = 0; i < formule.length(); i++) {
            if (Character.isAlphabetic(formule.charAt(i))) {
                retu++;
            }
        }
        return retu;
    }
    public int nbOperateurs() {
        int retu = 0;
        for (int i = 0; i < formule.length(); i++) {
            if (Operateurs.getValidsOperators().contains(formule.charAt(i))) {
                if (! Operateurs.NOT.getLabel().equals(formule.charAt(i))) {
                    retu++;
                }
            }
        }
        return retu;
    }
    public boolean isNegative() {  return Operateurs.NOT.getLabel().equals(formule.charAt(0)); }
    public boolean isSplitable() { return nbOperateurs()>0; }

    public SplitFormule split() throws CannotParseOperatorException {
        boolean wasNegative = false;
        if (isNegative()) {
            wasNegative = true;
            formule = formule.substring(2, formule.length()-1) ;
            //System.out.println(getFormule());
        }


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

        /*    DEBUG
        System.out.println("Indexs " + index);
        System.out.println("Priorites " + priorite);
        System.out.println("Operateurs " + operateursTrouve);
        System.out.println("-=-=-=-=-=-==-=-=-=-=-=-=-=");
        System.out.println(operateursTrouve.get(minIndex) + " [ i: " + index.get(minIndex) + ", p: " + minPrio + "]");
        */


        String p1 = formule.substring(0, index.get(minIndex)) ;
        String p2 = formule.substring(index.get(minIndex)+1, taille) ;

        SplitFormule ret = null;
        if (wasNegative) {
            if (operateursTrouve.get(minIndex) == Operateurs.AND) {
                ret = new SplitFormule(new Formule(Operateurs.NOT.getLabel()+"("+p1+")"), Operateurs.OR, new Formule(Operateurs.NOT.getLabel()+"("+p2+")"));
            }
            if (operateursTrouve.get(minIndex) == Operateurs.OR) {
                ret = new SplitFormule(new Formule(Operateurs.NOT.getLabel()+"("+p1+")"), Operateurs.AND, new Formule(Operateurs.NOT.getLabel()+"("+p2+")"));
            }
            if (operateursTrouve.get(minIndex) == Operateurs.IMPLICATION) {
                ret = new SplitFormule(new Formule(p1), Operateurs.OR, new Formule(Operateurs.NOT.getLabel()+"("+p2+")"));
            }
        }else {

            if (operateursTrouve.get(minIndex) == Operateurs.IMPLICATION) {
                ret = new SplitFormule(new Formule(Operateurs.NOT.getLabel() + "(" +p1+ ")"), Operateurs.OR, new Formule(p2));
            }else {

                ret = new SplitFormule(new Formule(p1), operateursTrouve.get(minIndex), new Formule(p2));
            }
        }

        return ret;
    }








    public void simplifier() {
        formule = formule.replaceAll(Operateurs.NOT.toString()+""+Operateurs.NOT.toString(), "") ;
    }

    public void evalNot() {
        if (isSplitable()) {
            SplitFormule tampon = this.split();

            if (! tampon.getF1().isNegative()) {

            }
            System.out.println(tampon.getF1() + " " + Operateurs.NOT.getLabel() + tampon.getF2());

        }

        if (getFormule().contains(Operateurs.NOT.toString()+""+Operateurs.NOT.toString())) {
            formule = formule.replaceAll(Operateurs.NOT.toString()+""+Operateurs.NOT.toString(), "") ;
        }
    }
}
