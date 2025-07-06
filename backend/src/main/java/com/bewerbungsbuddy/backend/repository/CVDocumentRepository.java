package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.CVDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVDocumentRepository extends JpaRepository<CVDocument, Long> {
}
