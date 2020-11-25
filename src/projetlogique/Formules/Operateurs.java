package projetlogique.Formules;

import projetlogique.Exceptions.CannotParseOperatorException;

import java.util.ArrayList;


public enum Operateurs {
    AND("∧"),
    OR("∨"),
    NOT("¬"),
    IMPLICATION("→"),
    EQUIVALENCE("↔");

    private String label;

    private static ArrayList<String> validAND = new ArrayList<>() ;
    private static ArrayList<String> validOR = new ArrayList<>() ;
    private static ArrayList<String> validNOT = new ArrayList<>() ;
    private static ArrayList<String> validIMPL = new ArrayList<>() ;
    private static ArrayList<String> validEQUI = new ArrayList<>() ;
    static {
        validAND.add("∧");
        validAND.add("&");

        validOR.add("∨");
        validOR.add("|");

        validNOT.add("¬");
        validNOT.add("!");

        validIMPL.add("→");
        validIMPL.add("->");

        validEQUI.add("↔");
        validEQUI.add("<->");
    }

    public static ArrayList<String> getValidsOperators() {
        ArrayList<String> valid = new ArrayList<>();
        valid.addAll(validAND);
        valid.addAll(validOR);
        valid.addAll(validNOT);
        valid.addAll(validIMPL);
        valid.addAll(validEQUI);
        return valid;
    }


    Operateurs(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Operateurs parse(String operator) throws CannotParseOperatorException {
        if (validAND.contains(operator)) {
            return AND;
        }else if (validOR.contains(operator)){
            return OR;
        }else if (validNOT.contains(operator)){
            return NOT;
        }else if (validIMPL.contains(operator)){
            return IMPLICATION;
        }else if (validEQUI.contains(operator)){
            return EQUIVALENCE;
        }else {
            throw new CannotParseOperatorException(operator) ;
        }
    }
}
