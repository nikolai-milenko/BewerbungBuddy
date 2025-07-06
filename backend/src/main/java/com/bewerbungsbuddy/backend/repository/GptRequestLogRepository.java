package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.GptRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GptRequestLogRepository extends JpaRepository<GptRequestLog, Long> {
}
