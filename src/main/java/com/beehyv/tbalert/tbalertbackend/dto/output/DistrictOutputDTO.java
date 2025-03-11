package com.beehyv.tbalert.tbalertbackend.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictOutputDTO {

    private Long id;
    private String name;
    private StateOutputDTO state;
}
