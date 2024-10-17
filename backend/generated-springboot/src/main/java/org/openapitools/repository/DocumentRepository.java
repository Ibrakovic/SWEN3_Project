package org.openapitools.repository;

import org.openapitools.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Mögliche benutzerdefinierte Abfragen
}
