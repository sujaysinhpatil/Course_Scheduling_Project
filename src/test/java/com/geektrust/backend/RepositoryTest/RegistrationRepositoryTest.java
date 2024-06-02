package com.geektrust.backend.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.entity.Registration;
import com.geektrust.backend.repository.repoImpl.RegistrationRepositoryImpl;

public class RegistrationRepositoryTest {
    
    private RegistrationRepositoryImpl registrationRepository;
    private RegistrationDto registrationDto1;
    private RegistrationDto registrationDto2;

    @BeforeEach
    public void setUp() {
        registrationRepository = new RegistrationRepositoryImpl();
        registrationDto1 = new RegistrationDto("REG1", "john@example.com", "COURSE1", false);
        registrationDto2 = new RegistrationDto("REG2", "jane@example.com", "COURSE1", true);
    }

    @Test
    public void testSave() {
        String regId = registrationRepository.save(registrationDto1);
        assertNotNull(regId);
        assertEquals("REG1", regId);
    }

    @Test
    public void testFindAll() {
        registrationRepository.save(registrationDto1);
        registrationRepository.save(registrationDto2);
        List<RegistrationDto> registrations = registrationRepository.findAll();
        assertEquals(2, registrations.size());
    }

    @Test
    public void testFindById() {
        registrationRepository.save(registrationDto1);
        Optional<RegistrationDto> foundRegistration = registrationRepository.findById(registrationDto1.getRegId());
        assertTrue(foundRegistration.isPresent());
        assertEquals(registrationDto1.getRegId(), foundRegistration.get().getRegId());
    }

    @Test
    public void testExistsById() {
        registrationRepository.save(registrationDto1);
        assertTrue(registrationRepository.existsById(registrationDto1.getRegId()));
        assertFalse(registrationRepository.existsById("non-existent-id"));
    }

    @Test
    public void testDelete() {
        registrationRepository.save(registrationDto1);
        registrationRepository.delete(registrationDto1);
        assertFalse(registrationRepository.existsById(registrationDto1.getRegId()));
    }

    @Test
    public void testDeleteById() {
        registrationRepository.save(registrationDto1);
        registrationRepository.deleteById(registrationDto1.getRegId());
        assertFalse(registrationRepository.existsById(registrationDto1.getRegId()));
    }

    @Test
    public void testCount() {
        registrationRepository.save(registrationDto1);
        registrationRepository.save(registrationDto2);
        assertEquals(2, registrationRepository.count());
    }

    @Test
    public void testFindAllByCourseId() {
        registrationRepository.save(registrationDto1);
        registrationRepository.save(registrationDto2);
        List<RegistrationDto> courseRegistrations = registrationRepository.findAllByCourseId("COURSE1");
        assertEquals(2, courseRegistrations.size());
        assertTrue(courseRegistrations.stream().allMatch(reg -> reg.getCourseId().equals("COURSE1")));
    }

    @Test
    public void testGetRegistration() {
        Registration registration = registrationRepository.getRegistration(registrationDto1);
        assertEquals(registrationDto1.getRegId(), registration.getRegId());
        assertEquals(registrationDto1.getEmailId(), registration.getEmailId());
    }

    @Test
    public void testGetRegistrationDto() {
        Registration registration = new Registration("REG1", "john@example.com", "COURSE1", false);
        RegistrationDto registrationDto = registrationRepository.getRegistrationDto(registration);
        assertEquals(registration.getRegId(), registrationDto.getRegId());
        assertEquals(registration.getEmailId(), registrationDto.getEmailId());
    }

}
