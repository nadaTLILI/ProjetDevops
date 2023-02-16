package com.esprit.examen.services;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Operateur;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OperateurServiceImplTest {
    @Autowired
    IOperateurService iOperateurService ;

    @Test
   public void testretrieveAllOperateurs() throws ParseException {

    };
    @Test
    public void testaddOperateur() throws ParseException {

        Set<Facture> s = new HashSet<Facture>();
        Operateur o = new Operateur(1L, "Ahmed","salhi" , "123" , s);
        Operateur operateur = iOperateurService.addOperateur(o);
        System.out.print("addOperateur "+operateur);
        assertNotNull(operateur.getIdOperateur());
        assertTrue(operateur.getNom().length() > 0);
        iOperateurService.deleteOperateur(operateur.getIdOperateur());

    };
    @Test
    public void testdeleteOperateur() throws ParseException {
        Set<Facture> s = new HashSet<Facture>();
        Operateur c = new Operateur(1L, "Ahmed","salhi" , "123" , s);
        Operateur operateur = iOperateurService.addOperateur(c);
        iOperateurService.deleteOperateur(operateur.getIdOperateur());
        Long t = operateur.getIdOperateur() ;
        assertNull(iOperateurService.retrieveOperateur(t)) ;
    };
    @Test
    public void testupdateOperateur() throws ParseException {

    };
     @Test
    public void testretrieveOperateur() throws ParseException {
         Set<Facture> s = new HashSet<Facture>();
         Operateur c = new Operateur(1L, "Ahmed","salhi" , "123" , s);
         Operateur operateur = iOperateurService.retrieveOperateur(c.getIdOperateur());
         boolean expected = operateur == null;
         int expectedF ;
         if (expected== true)
             expectedF = 1 ;
         else
             expectedF = 0 ;
         Operateur c2 =new Operateur(1L, "Ahmed","salhi" , "123" , s);
         Operateur operateur2 = iOperateurService.addOperateur(c2);
         assertEquals(expectedF + 1, iOperateurService.retrieveOperateur(1L).equals(null));
         iOperateurService.deleteOperateur(operateur2.getIdOperateur());
     } ;

}
