package com.esprit.examen.repositories;

import com.esprit.examen.TpAchatProjectApplication;
import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Fournisseur;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = TpAchatProjectApplication.class)
@RunWith(Enclosed.class)
class FactureRepositoryTest {

    private static Facture anyFacture() {
        Date dateCreation = Date.from(LocalDate.of(2023,2,12)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateModification = Date.from(LocalDate.of(2023,2,13)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Facture.builder()
                .idFacture(10L)
                .montantRemise(15F)
                .montantFacture(10.66F)
                .dateCreationFacture(dateCreation)
                .dateDerniereModificationFacture(dateModification)
                .archivee(false)
                .detailsFacture(emptySet())
                .reglements(emptySet())
                .build();
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class GetFactureParFournisseur {

        @Mock
        private FournisseurRepository fournisseurRepository;
        @Mock
        private FactureRepository factureRepository;

        @Test
        public void getFactureByFournisseur_ValidFournisseur_ReturnsListOfFactures() {
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setIdFournisseur(1L);
            fournisseurRepository.save(fournisseur);

            Facture facture = anyFacture();
            facture.setFournisseur(fournisseur);
            factureRepository.save(facture);

            List<Facture> factureByFournisseur = factureRepository.getFactureByFournisseur(fournisseur);

            assertThat(factureByFournisseur.size()).isEqualTo(1);
            assertThat(factureByFournisseur.get(0)).isSameAs(facture);
        }

        @Test
        public void getFactureByFournisseur_InvalidFournisseur_ReturnsEmptyList() {
            Fournisseur fournisseur = new Fournisseur();
            fournisseur.setIdFournisseur(1L);
            fournisseurRepository.save(fournisseur);

            Facture facture = anyFacture();
            facture.setFournisseur(fournisseur);
            factureRepository.save(facture);

            List<Facture> factureByFournisseur = factureRepository.getFactureByFournisseur(null);

            assertThat(factureByFournisseur.isEmpty()).isTrue();
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class GetTotalFacturesEntreDeuxDates {

        private final Date startDate = Date.from(LocalDate.of(2022, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        private final Date endDate = Date.from(LocalDate.of(2024, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        @Mock
        private FactureRepository factureRepository;

        @Test
        public void getTotalFacturesEntreDeuxDates_ValidStartAndEndDates_ReturnsSumOfMontantFactures() {
            Facture facture1 = anyFacture();
            factureRepository.save(facture1);

            Facture facture2 = anyFacture();
            factureRepository.save(facture2);
            float sum = factureRepository.getTotalFacturesEntreDeuxDates(startDate, endDate);

            assertThat(sum).isEqualTo(facture1.getMontantFacture() + facture2.getMontantFacture());
        }

        @Test
        public void getTotalFacturesEntreDeuxDates_InvalidStartOrEndDate_ReturnsSumOfMontantFacturesEqualToZero() {
            Facture facture1 = anyFacture();
            factureRepository.save(facture1);

            Facture facture2 = anyFacture();
            factureRepository.save(facture2);
            float sum = factureRepository.getTotalFacturesEntreDeuxDates(null, startDate);

            assertThat(sum).isEqualTo(0F);
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class UpdateFacture {

        @Mock
        private FactureRepository factureRepository;

        @Test
        public void updateFacture_ValidId_UpdatesArchivee() {
            factureRepository.save(anyFacture());

            factureRepository.updateFacture(10L);

            Facture byId = factureRepository.getById(10L);
            assertThat(byId.getArchivee()).isTrue();
        }
    }
}