package com.beehyv.tbalert.tbalertbackend.util;

import com.beehyv.tbalert.tbalertbackend.dao.MedicationReminderDAO;
import com.beehyv.tbalert.tbalertbackend.dto.MedicationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SMSTemplateUtil {

    public String buildSmsMessage(String template, MedicationReminderDAO reminder) {
        log.info("Building SMSTemplate");

        // Join the medication names by comma
        String medicationList = reminder.getMedications().stream()
                .map(MedicationDTO::getMedicationName)
                .collect(Collectors.joining(", "));

        log.info("Medication list: {}", medicationList);

        Map<String, String> data = Map.of(
                "patientId", reminder.getPatientId(),
                "nikshayId", reminder.getNikshayId() == null ? "" : reminder.getNikshayId(),
                "firstName", reminder.getFirstName(),
                "lastName", reminder.getLastName(),
                "medication_date", LocalDate.now().toString(),
                "medication_names", medicationList,
                "org_phone_number", "+91 7982241975"
        );

        return replaceVariables(template, data);
    }

    private String replaceVariables(String template, Map<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            String replacement = entry.getValue() != null ? entry.getValue() : "";
            template = template.replace("{" + entry.getKey() + "}", replacement);
        }
        return template;
    }

}
