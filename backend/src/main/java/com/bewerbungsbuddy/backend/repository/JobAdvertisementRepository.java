package com.bewerbungsbuddy.backend.repository;

import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import com.bewerbungsbuddy.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobAdvertisementRepository extends JpaRepository<JobAdvertisement, Long> {
    List<JobAdvertisement> findAllByUser(User user);
}
