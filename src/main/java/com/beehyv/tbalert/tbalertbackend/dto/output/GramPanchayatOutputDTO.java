package com.beehyv.tbalert.tbalertbackend.dto.output;

import com.beehyv.tbalert.tbalertbackend.entity.Mandal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GramPanchayatOutputDTO {

    private Long id;
    private String name;
    private MandalOutputDTO mandal;
}
