package hexlet.code.mapper;

import hexlet.code.DTO.LabelsDTO.LabelCreateDTO;
import hexlet.code.DTO.LabelsDTO.LabelDTO;
import hexlet.code.DTO.LabelsDTO.LabelUpdateDTO;
import hexlet.code.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class LabelMapper {
    public abstract Label map(LabelCreateDTO dto);
    public abstract LabelDTO map(Label model);
    public abstract void update(LabelUpdateDTO dto, @MappingTarget Label model);
}
