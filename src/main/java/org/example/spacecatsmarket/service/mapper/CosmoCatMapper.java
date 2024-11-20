package org.example.spacecatsmarket.service.mapper;

import org.example.spacecatsmarket.domain.CosmoCat;
import org.example.spacecatsmarket.dto.cat.CosmoCatDto;
import org.example.spacecatsmarket.dto.cat.CosmoCatListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CosmoCatMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "breed", target = "breed")
    CosmoCatDto toCosmoCatDto(CosmoCat cosmoCat);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "color", target = "color")
    @Mapping(source = "breed", target = "breed")
    CosmoCat toCosmoCat(CosmoCatDto cosmoCatDto);

    List<CosmoCatDto> toCosmoCatDto(List<CosmoCat> cosmoCats);

    default CosmoCatListDto toCosmoCatListDto(List<CosmoCat> cosmoCats) {
        return CosmoCatListDto.builder().cosmoCatDtos(toCosmoCatDto(cosmoCats)).build();
    }
}
