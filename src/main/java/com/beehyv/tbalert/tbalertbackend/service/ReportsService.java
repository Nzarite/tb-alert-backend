package com.beehyv.tbalert.tbalertbackend.service;

import java.io.IOException;
import java.util.Map;

public interface ReportsService {

    Integer getAllDead() throws IOException;

    byte[] getPatients(Map<String, Object> input) ;

    void getTeleCallerOfAState(String state);
}
