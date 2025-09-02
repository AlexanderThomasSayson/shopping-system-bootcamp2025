package com.srllc.shopping_system_bootcamp2025.config.init;

import com.srllc.shopping_system_bootcamp2025.domain.dao.StatusDao;
import com.srllc.shopping_system_bootcamp2025.domain.entity.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatusInitializer implements ApplicationRunner {

    private final StatusDao statusDao;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        createStatusIfNotExists("PENDING");
        createStatusIfNotExists("IN-TRANSIT");
        createStatusIfNotExists("CANCELLED");
        createStatusIfNotExists("COMPLETED");

    }

    public void createStatusIfNotExists(String statusName){
        if(statusDao.findByStatusName(statusName)== null){
            Status status = new Status();
            status.setStatusName(statusName);
            statusDao.save(status);
            log.info("Status '{}' created successfully!", statusName);
        }
        else {
            log.info("Status '{}' already exists!", statusName);
        }
    }
}
