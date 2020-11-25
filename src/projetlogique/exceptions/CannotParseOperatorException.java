package projetlogique.exceptions;

public class CannotParseOperatorException extends Exception {

    private String op;
    public CannotParseOperatorException(String operator) {
            op = operator;
    }

    @Override
    public String getMessage() {
        return "Nous n'avons pas pu transformer " + op + " en un opérateur valide !";
    }
}
