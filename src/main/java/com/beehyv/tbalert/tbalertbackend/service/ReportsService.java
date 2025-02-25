package com.beehyv.tbalert.tbalertbackend.service;

import java.io.IOException;
import java.util.Map;

public interface ReportsService {

    Integer getAllDead() throws IOException;

    byte[] getPatients(Map<String, Object> input) throws IOException;

    byte[] getTeleCallerOfAState(String state) throws IOException;

    byte[] getStateHeads() throws IOException;

    byte[] getPatientFollowUp(Map<String, Object> filter) throws IOException;

    byte[] getPatientFollowUpForToday(Map<String,Object>filter) throws IOException;
}
