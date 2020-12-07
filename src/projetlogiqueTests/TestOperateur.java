package projetlogiqueTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projetlogique.exceptions.CannotParseOperatorException;
import projetlogique.formules.Operateur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestOperateur {

    private Operateur non;
    private Operateur and;
    private Operateur or;
    private Operateur impl;
    private Operateur equi;



    @BeforeEach
    public void setUp() {
        non = Operateur.NOT;
        and = Operateur.AND;
        or = Operateur.OR;
        impl = Operateur.IMPLICATION;
        equi = Operateur.EQUIVALENCE ;
    }

    @Test
    public void testOperatorReturnLabels() {
        assertEquals(non.toString(), "¬") ;
        assertEquals(and.toString(), "∧") ;
        assertEquals(or.toString(), "∨") ;
        assertEquals(impl.toString(), "→") ;
        assertEquals(equi.toString(), "↔") ;

    }
    @Test
    public void testParseThrow() {
        assertThrows(CannotParseOperatorException.class, () -> {Operateur.parse('a'); });
    }
}
