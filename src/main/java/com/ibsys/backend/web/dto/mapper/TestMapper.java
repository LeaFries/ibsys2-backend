package com.ibsys.backend.web.dto.mapper;

import com.ibsys.backend.core.domain.Test;
import com.ibsys.backend.web.dto.TestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestMapper {

    TestDTO toTestDTO(Test test);
    Test toTest(TestDTO testDTO);

}
