package com.esprit.examen.services;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.repositories.FactureRepository;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(Enclosed.class)
class FactureServiceImplTest {

    private static Facture anyFacture() {
        return Facture.builder()
                .idFacture(10L)
                .montantFacture(10.66F)
                .archivee(false)
                .dateDerniereModificationFacture(null)
                .dateCreationFacture(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .montantRemise(15F)
                .reglements(Collections.emptySet())
                .build();
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class RetrieveAllFactures {
        @Mock
        FactureRepository factureRepository;

        @InjectMocks
        FactureServiceImpl factureService;

        @Test
        public void retrieveAllFacture_ReturnsListOfFactures() {
            Facture facture1 = anyFacture();
            Facture facture2 = anyFacture();

            factureRepository.save(facture1);
            factureRepository.save(facture2);

            List<Facture> factureList = factureService.retrieveAllFactures();
            assertThat(factureList.size()).isEqualTo(2);
            assertThat(factureList.get(0)).isSameAs(facture1);
            assertThat(factureList.get(1)).isSameAs(facture2);
        }

        @Test
        public void retrieveAllFacture_ReturnsEmpty() {
            List<Facture> factureList = factureService.retrieveAllFactures();
            assertThat(factureList.size()).isEqualTo(0);
        }
    }

}