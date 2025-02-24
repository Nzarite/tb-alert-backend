package com.beehyv.tbalert.tbalertbackend.dao;

import com.beehyv.tbalert.tbalertbackend.dto.MedicationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationReminderDAO {
    private String patientId;
    private String nikshayId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String lastFollowupDate;
    private List<MedicationDTO> medications;
}
