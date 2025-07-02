package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Long> {
}
