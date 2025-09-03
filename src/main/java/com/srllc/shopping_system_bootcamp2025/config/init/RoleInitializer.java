package com.srllc.shopping_system_bootcamp2025.config.init;

import com.srllc.shopping_system_bootcamp2025.domain.dao.RoleDao;
import com.srllc.shopping_system_bootcamp2025.domain.entity.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleInitializer implements ApplicationRunner {

    private final RoleDao roleDao;

    public void createRoleIfNotExists(String roleName){
        if(roleDao.findByRoleName(roleName) == null){
            Role role = new Role();
            role.setRoleName(roleName);
            roleDao.save(role);
            log.info("Role '{}' created successfully!", roleName);
        }
        else {
            log.info("Role '{}' already exists! ", roleName);

        }
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        createRoleIfNotExists("CUSTOMER");
        createRoleIfNotExists("ADMIN");
    }
}
