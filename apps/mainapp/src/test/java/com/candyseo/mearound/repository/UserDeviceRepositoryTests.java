package com.candyseo.mearound.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import com.candyseo.mearound.model.entity.key.UserDeviceEntityKey;
import com.candyseo.mearound.model.entity.user.UserDeviceEntity;
import com.candyseo.mearound.repository.user.UserDeviceRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class UserDeviceRepositoryTests {
    
    @Autowired
    private UserDeviceRepository userDeviceRepository;

    @Test
    public void userDeviceRepositoryTests() {
        String userIdentifier = UUID.randomUUID().toString();
        String deviceIdentifier = UUID.randomUUID().toString();

        registUserDeviceRepository(userIdentifier, deviceIdentifier);
    }

    private void registUserDeviceRepository(String userIdentifier, String deviceIdentifier) {
        UserDeviceEntity registed = userDeviceRepository.save(new UserDeviceEntity(userIdentifier, deviceIdentifier, true));
        UserDeviceEntity search = userDeviceRepository.getById(new UserDeviceEntityKey(registed.getUserId(), registed.getDeviceId()));

        assertEquals(registed, search);
    }

    @Test
    public void returnUserDeviceEntityWhenGetById() {
        String userIdentifier = UUID.randomUUID().toString();
        String deviceIdentifier = UUID.randomUUID().toString();

        registUserDeviceRepository(userIdentifier, deviceIdentifier);

        UserDeviceEntity entity = userDeviceRepository.getById(new UserDeviceEntityKey(userIdentifier, deviceIdentifier));

        assertEquals(entity.getUserId(), userIdentifier);
        assertEquals(entity.getDeviceId(), deviceIdentifier);
    }

    @Test
    public void throwEntityNotFoundExceptionWhenGetById() {
        String userIdentifier = UUID.randomUUID().toString();
        String deviceIdentifier = UUID.randomUUID().toString();

        UserDeviceEntity entity = userDeviceRepository.getById(new UserDeviceEntityKey(userIdentifier, deviceIdentifier));

        assertThrows(EntityNotFoundException.class, () -> {
            assertEquals(entity.getUserId(), userIdentifier);
        });
    }
    
}
