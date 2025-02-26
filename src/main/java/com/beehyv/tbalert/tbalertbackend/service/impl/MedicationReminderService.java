package com.beehyv.tbalert.tbalertbackend.service.impl;

import com.beehyv.tbalert.tbalertbackend.dao.MedicationReminderDAO;
import com.beehyv.tbalert.tbalertbackend.dao.MedicationReminderProjection;
import com.beehyv.tbalert.tbalertbackend.dto.MedicationDTO;
import com.beehyv.tbalert.tbalertbackend.repository.PatientRepo;
import com.beehyv.tbalert.tbalertbackend.repository.SettingRepo;
import com.beehyv.tbalert.tbalertbackend.util.SMSTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnBean(PlivoSmsService.class)
public class MedicationReminderService {

    @Value("${app.notification.sender-id}")
    private String senderId;

    private final PatientRepo patientRepo;
    private final SettingRepo settingRepo;
    private final PlivoSmsService smsService;
    private final SMSTemplateUtil smsTemplateUtil;

    public void sendMedicationReminder() {

        List<MedicationReminderDAO> reminders = getAllMedicationReminders();
        String smsTemplate = settingRepo.findByKeyName("sms_template").getValue();

//        String smsTemplate = "Hi {patientId}:{nikshayId} {firstName} {lastName},\n\nThis is your daily medicine reminder.\nYour scheduled medications for today ({medication_date}) is/are: {medication_names}.\n\nFor any queries, contact us at {org_phone_number}. ";

        log.info("Running Medication Reminder Service");
        reminders.forEach(medicationReminderDAO -> {
            try {
                String smsContent = smsTemplateUtil.buildSmsMessage(smsTemplate, medicationReminderDAO);
                log.info("SMS Template: {}", smsTemplate);

                smsService.sendSms(senderId, "+91" + medicationReminderDAO.getPhoneNumber(), smsContent);
            } catch (Exception e) {
                log.error("Failed to send SMS to patient {} (Phone: {}). Exception: {}",
                        medicationReminderDAO.getPatientId(),
                        medicationReminderDAO.getPhoneNumber(),
                        e.getMessage(),
                        e);
            }
        });
    }

    private List<MedicationReminderDAO> getAllMedicationReminders() {
        List<MedicationReminderProjection> rawReminders = patientRepo.findAllMedicationReminders();

        log.debug("Converting Medication Projection into MedicationReminderDAO");
        // Group by patientId
        return rawReminders.stream()
                .collect(Collectors.groupingBy(MedicationReminderProjection::getPatientId))
                .values()
                .stream()
                .map(records -> {
                    MedicationReminderProjection firstRecord = records.getFirst();

                    // Convert medications to DTO list
                    List<MedicationDTO> medications = records.stream()
                            .map(m -> new MedicationDTO(m.getMedicationId(), m.getMedicationName()))
                            .toList();

                    return new MedicationReminderDAO(
                            firstRecord.getPatientId(),
                            firstRecord.getNikshayId(),
                            firstRecord.getFirstName(),
                            firstRecord.getLastName(),
                            firstRecord.getPhoneNumber(),
                            firstRecord.getLastFollowupDate(),
                            medications
                    );
                })
                .toList();
    }

}
