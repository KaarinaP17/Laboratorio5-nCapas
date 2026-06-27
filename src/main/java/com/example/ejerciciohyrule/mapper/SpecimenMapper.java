package com.example.ejerciciohyrule.mapper;

import com.example.ejerciciohyrule.dto.request.CreateSpecimenRequest;
import com.example.ejerciciohyrule.dto.request.UpdateSpecimenRequest;
import com.example.ejerciciohyrule.dto.response.specimen.SpecimenResponse;
import com.example.ejerciciohyrule.entity.Specimen;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SpecimenMapper {

    public Specimen toEntityCreate(CreateSpecimenRequest request) {
        return Specimen.builder()
                .name(request.getName())
                .region(request.getRegion())
                .dangerLevel(request.getDangerLevel())
                .isFriendly(request.getIsFriendly())
                .build();
    }

    public Specimen toEntityUpdate(UpdateSpecimenRequest request, UUID id) {
        return Specimen.builder()
                .id(id)
                .name(request.getName())
                .region(request.getRegion())
                .dangerLevel(request.getDangerLevel())
                .isFriendly(request.getIsFriendly())
                .build();
    }

    public SpecimenResponse toDto(Specimen specimen) {
        return SpecimenResponse.builder()
                .id(specimen.getId())
                .name(specimen.getName())
                .region(specimen.getRegion())
                .dangerLevel(specimen.getDangerLevel())
                .isFriendly(specimen.getIsFriendly())
                .build();
    }

    public Page<SpecimenResponse> toDtoPage(Page<Specimen> specimens) {
        return specimens.map(this::toDto);
    }
}

