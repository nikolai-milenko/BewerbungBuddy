package com.bewerbungsbuddy.backend.mapper;

import com.bewerbungsbuddy.backend.dto.CoverLetterResponseDto;
import com.bewerbungsbuddy.backend.dto.CoverLetterRequestDto;
import com.bewerbungsbuddy.backend.entity.CVDocument;
import com.bewerbungsbuddy.backend.entity.CoverLetter;
import com.bewerbungsbuddy.backend.entity.JobAdvertisement;
import com.bewerbungsbuddy.backend.entity.User;
import com.bewerbungsbuddy.backend.repository.CVDocumentRepository;
import com.bewerbungsbuddy.backend.repository.JobAdvertisementRepository;
import com.bewerbungsbuddy.backend.repository.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CoverLetterMapper {

    @Mapping(source = "userId", target = "user", qualifiedByName = "mapUser")
    @Mapping(source = "cvDocumentId", target = "cvDocument", qualifiedByName = "mapCVDocument")
    @Mapping(source = "jobAdvertisementId", target = "jobAdvertisement", qualifiedByName = "mapJobAdvertisement")
    CoverLetter toEntity(CoverLetterRequestDto dto, @Context UserRepository userRepository,
                         @Context CVDocumentRepository cvDocumentRepository,
                         @Context JobAdvertisementRepository jobAdvertisementRepository);

    CoverLetterResponseDto toResponseDto(CoverLetter coverLetter);

    @Named("mapUser")
    default User mapUser(Long userId, @Context UserRepository userRepository) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }

    @Named("mapCVDocument")
    default CVDocument mapCVDocument(Long cvDocumentId, @Context CVDocumentRepository cvDocumentRepository) {
        if (cvDocumentId == null) {
            return null;
        }
        return cvDocumentRepository.findById(cvDocumentId)
                .orElseThrow(() -> new RuntimeException("CVDocument not found: " + cvDocumentId));
    }

    @Named("mapJobAdvertisement")
    default JobAdvertisement mapJobAdvertisement(Long jobAdvertisementId, @Context JobAdvertisementRepository jobAdvertisementRepository) {
        if (jobAdvertisementId == null) {
            return null;
        }
        return jobAdvertisementRepository.findById(jobAdvertisementId)
                .orElseThrow(() -> new RuntimeException("JobAdvertisement not found: " + jobAdvertisementId));
    }
}

