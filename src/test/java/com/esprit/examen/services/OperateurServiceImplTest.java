package com.esprit.examen.services;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    List<Operateur> testretrieveAllOperateurs() throws ParseException {};

    Operateur addOperateur(Operateur o) throws ParseException {};

    void deleteOperateur(Long id) throws ParseException {};

    Operateur updateOperateur(Operateur o) throws ParseException {};

    Operateur retrieveOperateur(Long id) throws ParseException {} ;

}
