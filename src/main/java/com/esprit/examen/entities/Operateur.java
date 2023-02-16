package com.esprit.examen.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Operateur implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOperateur;
	private String nom;
	private String prenom;

	public Operateur(Long idOperateur, String nom, String prenom, String password, Set<Facture> factures) {
		this.idOperateur = idOperateur;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.factures = factures;
	}

	public Operateur(Long idOperateur) {
		this.idOperateur = idOperateur;
	}

	public Long getIdOperateur() {
		return idOperateur;
	}
	private String password;
	@OneToMany
	@JsonIgnore
	private Set<Facture> factures;
	
}
