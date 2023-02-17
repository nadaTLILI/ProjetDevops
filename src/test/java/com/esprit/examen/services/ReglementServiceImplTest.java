package com.esprit.examen.services;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Reglement;
import com.esprit.examen.repositories.ReglementRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReglementServiceImplTest {

    @Mock
    ReglementRepository reglementRepository;

    @InjectMocks
    ReglementServiceImpl reglementService;

    @Test
    public void retrieveAllReglements_ReturnsListOfRegelement() {
        Reglement reglement = new Reglement();

        when(reglementRepository.findAll())
                .thenReturn(Collections.singletonList(reglement));

        List<Reglement> result = reglementService.retrieveAllReglements();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isSameAs(reglement);
    }

    @Test
    public void retrieveAllReglements_ReturnsEmptyListOfRegelement() {
        List<Reglement> result = reglementService.retrieveAllReglements();

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void addReglement_ReturnsAddedReglement() {
        Reglement reglement = new Reglement();

        Reglement result = reglementService.addReglement(reglement);

        assertThat(result).isSameAs(reglement);
    }

    @Test
    public void addReglement_ReturnsNull() {
        Reglement result = reglementService.addReglement(null);

        assertThat(result).isNull();
    }

    @Test
    public void retrieveReglement_ReturnsReglement() {
        Reglement reglement = new Reglement();
        reglement.setIdReglement(99L);

        when(reglementRepository.findById(any()))
                .thenReturn(Optional.of(reglement));

        Reglement result = reglementService.retrieveReglement(99L);

        assertThat(result).isSameAs(reglement);
    }

    @Test
    public void retrieveReglement_ReturnsNull() {
        Reglement result = reglementService.retrieveReglement(null);

        assertThat(result).isNull();
    }

    @Test
    public void retrieveReglementByFacture_ReturnsListOfReglement() {
        Facture facture = new Facture();
        facture.setIdFacture(99L);

        Reglement reglement = new Reglement();
        reglement.setFacture(facture);

        when(reglementRepository.retrieveReglementByFacture(any()))
                .thenReturn(Collections.singletonList(reglement));

        List<Reglement> result = reglementService.retrieveReglementByFacture(99L);

        assertThat(result.get(0)).isSameAs(reglement);
        assertThat(result.get(0).getFacture()).isSameAs(facture);
    }

    @Test
    public void retrieveReglementByFacture_ReturnsEmptyList() {
        List<Reglement> result = reglementService.retrieveReglementByFacture(99L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void getChiffreAffaireEntreDeuxDate_ReturnsFloat() {
        when(reglementRepository.getChiffreAffaireEntreDeuxDate(any(), any()))
                .thenReturn(1366.2F);

        float result = reglementService.getChiffreAffaireEntreDeuxDate(any(), any());

        assertThat(result).isEqualTo(1366.2F);
    }

    @Test
    public void getChiffreAffaireEntreDeuxDate_ReturnsZero() {
        float result = reglementService.getChiffreAffaireEntreDeuxDate(any(), any());

        assertThat(result).isEqualTo(0F);
    }

}