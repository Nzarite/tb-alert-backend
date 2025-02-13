package com.beehyv.tbalert.tbalertbackend.service;

import java.io.IOException;
import java.util.Map;

public interface ReportsService {

    Integer getAllDead() throws IOException;

    void getPatients(Map<String, Object> input) throws Exception;

    void getTeleCallerOfAState(String state);
}
