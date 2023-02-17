package com.esprit.examen.services;

import com.esprit.examen.entities.DetailFournisseur;
import com.esprit.examen.entities.Fournisseur;
import com.esprit.examen.entities.SecteurActivite;
import com.esprit.examen.repositories.DetailFournisseurRepository;
import com.esprit.examen.repositories.FournisseurRepository;
import com.esprit.examen.repositories.SecteurActiviteRepository;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        public void addFournisseur_DelegatesDetailFournisseurToFournisseurRepository() {

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

        @Test
        public void addFournisseur_Fournisseur_addsFournisseurAndFournisseur() {

            Fournisseur fournisseur = Fournisseur.builder().build();
            Fournisseur result = fournisseurServiceImpl.addFournisseur(fournisseur);

            assertThat(result).isEqualTo(fournisseur);
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class updateFournisseur {

        @Mock
        private DetailFournisseurRepository detailFournisseurRepository;

        @Mock
        private FournisseurRepository FournisseurRepository;

        @InjectMocks
        FournisseurServiceImpl fournisseurServiceImpl;

        @Test
        public void updateFournisseur_DelegatesDetailFournisseurToDetailFournisseurRepository() {

            Fournisseur fournisseur = Fournisseur.builder()
                    .detailFournisseur(DetailFournisseur.builder()
                            .email("email")
                            .matricule("matricule")
                            .build())
                    .build();

            fournisseurServiceImpl.updateFournisseur(fournisseur);

            ArgumentCaptor<DetailFournisseur> captor = ArgumentCaptor.forClass(DetailFournisseur.class);
            verify(detailFournisseurRepository).save(captor.capture());

            assertThat(captor.getValue()).isInstanceOf(DetailFournisseur.class);
            assertThat(captor.getValue()).isEqualTo(fournisseur.getDetailFournisseur());
        }

        @Test
        public void updateFournisseur_Fournisseur_SetsDetailFournisseur() {

            Fournisseur fournisseur = Fournisseur.builder()
                    .detailFournisseur(DetailFournisseur.builder()
                            .email("email")
                            .matricule("matricule")
                            .build())
                    .build();

            Fournisseur result = fournisseurServiceImpl.updateFournisseur(fournisseur);

            assertThat(result.getDetailFournisseur()).isEqualTo(fournisseur.getDetailFournisseur());
        }

        @Test
        public void updateFournisseur_Fournisseur_SavesChangesAndReturnsFournisseur() {

            Fournisseur fournisseur = Fournisseur.builder()
                    .detailFournisseur(DetailFournisseur.builder()
                            .email("email")
                            .matricule("matricule")
                            .build())
                    .build();

            Fournisseur result = fournisseurServiceImpl.updateFournisseur(fournisseur);

            assertThat(result).isEqualTo(fournisseur);
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class deleteFournisseur {

        @Mock
        private FournisseurRepository fournisseurRepository;

        @InjectMocks
        FournisseurServiceImpl fournisseurServiceImpl;

        @Test
        public void deleteFournisseur_DelegatesFournisseurIdToFournieeurReposirtory() {

            fournisseurServiceImpl.deleteFournisseur(1L);

            ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
            verify(fournisseurRepository).deleteById(captor.capture());

            assertThat(captor.getValue()).isEqualTo(1L);
        }
    }

    @RunWith(MockitoJUnitRunner.class)
    public static class retrieveFournisseur {

        @Mock
        private FournisseurRepository fournisseurRepository;

        @InjectMocks
        FournisseurServiceImpl fournisseurServiceImpl;

        @Test
        public void retrieveFournisseur_DelegatesFournisseurIdToFournissuerRepository() {

            fournisseurServiceImpl.retrieveFournisseur(1L);

            ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
            verify(fournisseurRepository).findById(captor.capture());

            assertThat(captor.getValue()).isEqualTo(1L);
        }

        @Test
        public void retrieveFournisseur_FournisseurId_ReturnsFournisseur() {
            Mockito.when(fournisseurRepository.findById(any()))
                    .thenReturn(Optional.of(Fournisseur.builder()
                    .code("code")
                    .build()));

            fournisseurServiceImpl.retrieveFournisseur(1L);

            ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
            verify(fournisseurRepository).findById(captor.capture());

            assertThat(captor.getValue()).isEqualTo(1L);
        }

        @Test
        public void retrieveFournisseur_NonExistantFournisseur_ReturnsNull() {
            Mockito.when(fournisseurRepository.findById(any()))
                    .thenReturn(Optional.empty());

            Fournisseur result = fournisseurServiceImpl.retrieveFournisseur(1L);

            assertThat(result).isNull();
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class assignSecteurActiviteToFournisseur {

        @Mock
        private FournisseurRepository fournisseurRepository;

        @Mock
        private SecteurActiviteRepository secteurActiviteRepository;

        @InjectMocks
        FournisseurServiceImpl fournisseurServiceImpl;

        @Test
        public void assignSecteurActiviteToFournisseur_DelegatesFournisseurIdToFournisseurReposirtory() {

            Set<SecteurActivite> secteurActiviteSeteur = new HashSet<>();
            secteurActiviteSeteur.add(SecteurActivite.builder().build());
            //secteurActiviteSeteur.add(SecteurActivite.builder()
            //        .codeSecteurActivite("code_secteur")
             //       .build());

            when(fournisseurRepository.findById(any()))
                    .thenReturn(Optional.of(Fournisseur.builder()
                    .secteurActivites(secteurActiviteSeteur)
                    .build()));

            fournisseurServiceImpl.assignSecteurActiviteToFournisseur(2L,1L);

            ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
            verify(fournisseurRepository).findById(captor.capture());

            assertThat(captor.getValue()).isEqualTo(1L);
        }

        @Test
        public void assignSecteurActiviteToFournisseur_DelegatesFournisseurIdToSecteurActiviteReposirtory() {

            Set<SecteurActivite> secteurActiviteSeteur = new HashSet<>();
            secteurActiviteSeteur.add(SecteurActivite.builder().build());

            when(fournisseurRepository.findById(any()))
                    .thenReturn(Optional.of(Fournisseur.builder()
                            .secteurActivites(secteurActiviteSeteur)
                            .build()));

            fournisseurServiceImpl.assignSecteurActiviteToFournisseur(2L,1L);

            ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
            verify(secteurActiviteRepository).findById(captor.capture());

            assertThat(captor.getValue()).isEqualTo(2L);
        }
    }
}
