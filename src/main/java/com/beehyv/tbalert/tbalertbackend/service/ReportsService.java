package com.beehyv.tbalert.tbalertbackend.service;

import java.io.IOException;

public interface ReportsService {

    Integer getAllDead() throws IOException;

    void getAllPatients() throws IOException;
}
