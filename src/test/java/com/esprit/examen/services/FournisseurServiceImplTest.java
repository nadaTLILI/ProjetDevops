package com.esprit.examen.services;

import com.esprit.examen.entities.Fournisseur;
import com.esprit.examen.repositories.FournisseurRepository;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
class FournisseurServiceImplTest {

    @RunWith(MockitoJUnitRunner.class)
    public static class RetrieveAllFournisseurs {

        @Mock
        private FournisseurRepository fournisseurRepository;

        @InjectMocks
        FournisseurServiceImpl fournisseurServiceImpl;

        @Test
        public void retrieveAllFournisseurs_ReturnsListDesFournisseurs() {
            Fournisseur fournisseur1 = Fournisseur.builder()
                    .idFournisseur(100L)
                    .code("code_100L")
                    .build();
            Fournisseur fournisseur2 = Fournisseur.builder()
                    .idFournisseur(200L)
                    .code("code_200L")
                    .build();

            when(fournisseurRepository.findAll())
                    .thenReturn(asList(fournisseur1, fournisseur2));

            List<Fournisseur> result = fournisseurServiceImpl.retrieveAllFournisseurs();

            assertThat(result).containsExactlyInAnyOrder(fournisseur1, fournisseur2);
        }

        @Test
        public void retrieveAllFournisseurs_NoFournisseursExists_ReturnsEmptyList() {
            when(fournisseurRepository.findAll())
                    .thenReturn(Collections.emptyList());

            List<Fournisseur> result = fournisseurServiceImpl.retrieveAllFournisseurs();

            assertThat(result).isEqualTo(Collections.emptyList());
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class AddFournisseur {

        @Mock
        private FournisseurRepository fournisseurRepository;

        @InjectMocks
        FournisseurServiceImpl fournisseurServiceImpl;

        @Test
        public void addFournisseur_DelegatedDetailFournisseurToFournisseurRepository() {

            Fournisseur fournisseur = Fournisseur.builder().build();
            fournisseurServiceImpl.addFournisseur(fournisseur);

            ArgumentCaptor<Fournisseur> captor = ArgumentCaptor.forClass(Fournisseur.class);
            verify(fournisseurRepository).save(captor.capture());

            assertThat(captor.getValue()).isInstanceOf(Fournisseur.class);
            assertThat(captor.getValue()).isEqualTo(fournisseur);
        }

        @Test
        public void addFournisseur_Fournisseur_SetsDetailFournisseur() {

            Fournisseur fournisseur = Fournisseur.builder().build();
            Fournisseur result = fournisseurServiceImpl.addFournisseur(fournisseur);

            assertThat(result.getDetailFournisseur().getDateDebutCollaboration().getDate()).isEqualTo(new Date().getDate());
        }
    }
}
