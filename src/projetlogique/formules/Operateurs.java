package projetlogique.formules;

import java.util.ArrayList;

import projetlogique.exceptions.CannotParseOperatorException;


public enum Operateurs {
    AND('&'),
    OR('|'),
    NOT('!'),
    IMPLICATION('>'),
    EQUIVALENCE('~');

    private Character label;

    private static ArrayList<Character> validAND = new ArrayList<>() ;
    private static ArrayList<Character> validOR = new ArrayList<>() ;
    private static ArrayList<Character> validNOT = new ArrayList<>() ;
    private static ArrayList<Character> validIMPL = new ArrayList<>() ;
    private static ArrayList<Character> validEQUI = new ArrayList<>() ;
    static {
        validAND.add('∧');
        validAND.add('&');

        validOR.add('∨');
        validOR.add('|');

        validNOT.add('¬');
        validNOT.add('!');

        validIMPL.add('→');
        validIMPL.add('>');

        validEQUI.add('↔');
        validEQUI.add('~');
    }

    public static ArrayList<Character> getValidsOperators() {
        ArrayList<Character> valid = new ArrayList<>();
        valid.addAll(validAND);
        valid.addAll(validOR);
        valid.addAll(validNOT);
        valid.addAll(validIMPL);
        valid.addAll(validEQUI);
        return valid;
    }


    Operateurs(Character label) {
        this.label = label;
    }

    public Character getLabel() {
        return label;
    }

    public static Operateurs parse(Character operator) throws CannotParseOperatorException {
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
