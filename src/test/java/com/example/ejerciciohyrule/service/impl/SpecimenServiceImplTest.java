package com.example.ejerciciohyrule.service.impl;

import com.example.ejerciciohyrule.dto.request.CreateSpecimenRequest;
import com.example.ejerciciohyrule.dto.request.UpdateSpecimenRequest;
import com.example.ejerciciohyrule.dto.response.specimen.SpecimenResponse;
import com.example.ejerciciohyrule.entity.Specimen;
import com.example.ejerciciohyrule.mapper.SpecimenMapper;
import com.example.ejerciciohyrule.repository.SpecimenRepository;
import com.example.ejerciciohyrule.services.impl.SpecimenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpecimenServiceImplTest {

    @Mock
    private SpecimenRepository specimenRepository;

    @Mock
    private SpecimenMapper specimenMapper;

    @InjectMocks
    private SpecimenServiceImpl specimenService;

    private UUID specimenId;
    private CreateSpecimenRequest createRequest;
    private UpdateSpecimenRequest updateRequest;
    private Specimen specimenEntity;
    private SpecimenResponse specimenResponse;


    @BeforeEach
    void setUp() {
        specimenId = UUID.randomUUID();

        createRequest = CreateSpecimenRequest.builder()
                .name("Bokoblin")
                .region("Hyrule Field")
                .dangerLevel(1)
                .isFriendly(false)
                .build();

        updateRequest = UpdateSpecimenRequest.builder()
                .name("Blue Bokoblin")
                .build();

        specimenEntity = Specimen.builder()
                .id(specimenId)
                .name(createRequest.getName())
                .region(createRequest.getRegion())
                .dangerLevel(createRequest.getDangerLevel())
                .isFriendly(createRequest.getIsFriendly())
                .build();

        specimenResponse = SpecimenResponse.builder()
                .id(specimenId)
                .name(specimenEntity.getName())
                .build();
    }

    @Test
    void createSpecimen_ShouldReturnResponse() {
        when(specimenMapper.toEntityCreate(createRequest)).thenReturn(specimenEntity);
        when(specimenRepository.save(specimenEntity)).thenReturn(specimenEntity);
        when(specimenMapper.toDto(specimenEntity)).thenReturn(specimenResponse);

        SpecimenResponse result = specimenService.createSpecimen(createRequest);

        assertNotNull(result);
        assertEquals(specimenId, result.getId());
    }

    @Test
    void getSpecimenById_ShouldReturnResponse() {
        when(specimenRepository.findById(specimenId)).thenReturn(Optional.of(specimenEntity));
        when(specimenMapper.toDto(specimenEntity)).thenReturn(specimenResponse);

        SpecimenResponse result = specimenService.getSpecimenById(specimenId);

        assertNotNull(result);
        assertEquals(specimenId, result.getId());
    }

    @Test
    void updateSpecimen_ShouldReturnUpdatedResponse() {
        when(specimenRepository.findById(specimenId)).thenReturn(Optional.of(specimenEntity));
        when(specimenMapper.toEntityUpdate(updateRequest, specimenId)).thenReturn(specimenEntity);
        when(specimenRepository.save(specimenEntity)).thenReturn(specimenEntity);
        when(specimenMapper.toDto(specimenEntity)).thenReturn(specimenResponse);

        var result = specimenService.updateSpecimen(specimenId, updateRequest);

        assertNotNull(result);
        assertEquals(specimenId, result.getId());
    }

    @Test
    void deleteSpecimen_ShouldReturnDeletedResponse() {
        when(specimenRepository.findById(specimenId)).thenReturn(Optional.of(specimenEntity));
        when(specimenMapper.toDto(specimenEntity)).thenReturn(specimenResponse);

        var result = specimenService.deleteSpecimen(specimenId);

        verify(specimenRepository).deleteById(specimenId);
        assertEquals(specimenId, result.getId());
    }
}
