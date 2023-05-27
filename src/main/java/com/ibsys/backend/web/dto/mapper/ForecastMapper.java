package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.entity.Forecast;
import com.ibsys.backend.web.dto.ForecastDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ForecastMapper {

    @Mapping(source = "period", target = "period")
    Forecast toForecast(ForecastDTO forecastDTO, Integer period);

}
