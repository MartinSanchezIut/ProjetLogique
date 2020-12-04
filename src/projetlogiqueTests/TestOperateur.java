package projetlogiqueTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projetlogique.exceptions.CannotParseOperatorException;
import projetlogique.formules.Operateurs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestOperateur {

    private Operateurs non;
    private Operateurs and;
    private Operateurs or;
    private Operateurs impl;
    private Operateurs equi;



    @BeforeEach
    public void setUp() {
        non = Operateurs.NOT;
        and = Operateurs.AND;
        or = Operateurs.OR;
        impl = Operateurs.IMPLICATION;
        equi = Operateurs.EQUIVALENCE ;
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
        assertThrows(CannotParseOperatorException.class, () -> {Operateurs.parse('a'); });
    }
}
