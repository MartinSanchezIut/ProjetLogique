package projetlogique.exceptions;

public class CannotParseOperatorException extends RuntimeException {

    private Character op;
    public CannotParseOperatorException(Character operator) {
            op = operator;
    }

    @Override
    public String getMessage() {
        return "Nous n'avons pas pu transformer " + op + " en un opérateur valide !";
    }
}
