package com.drugstore.app.repository;

import com.drugstore.app.domain.Utilisateur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UtilisateurRepositoryWithBagRelationships {
    Optional<Utilisateur> fetchBagRelationships(Optional<Utilisateur> utilisateur);

    List<Utilisateur> fetchBagRelationships(List<Utilisateur> utilisateurs);

    Page<Utilisateur> fetchBagRelationships(Page<Utilisateur> utilisateurs);
}
