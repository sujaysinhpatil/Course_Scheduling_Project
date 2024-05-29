package com.geektrust.backend.repository.repoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.geektrust.backend.dto.RegistrationDto;
import com.geektrust.backend.entity.Registration;
import com.geektrust.backend.repository.IRegistrationRepository;

public class RegistrationRepositoryImpl implements IRegistrationRepository{

    private HashMap<String,Registration> regMap;

    public RegistrationRepositoryImpl() {
        regMap = new HashMap<>();
    }

    public RegistrationRepositoryImpl(HashMap<String, Registration> regMap) {
        this.regMap = regMap;
    }

    // @Override
    // public String save(Registration entity) {
    //     String email = entity.getEmailId();
    //     entity.setRegId("REG-COURSE-"+email.substring(0, email.indexOf("@"))+entity);
    //     // regMap.put(entity., entity);
    // }
    // REG-COURSE-<EMPLOYEE-NAME>-<COURSE-NAME>
    
    @Override
    public String save(RegistrationDto regDto) {
        // String email = regDto.getEmailId();
        // regDto.setRegId("REG-COURSE-"+email.substring(0, email.indexOf("@"))+regDto.);
        Registration registration=getRegistration(regDto);
        regMap.put(registration.getRegId(), registration);
        return registration.getRegId();
    }

    @Override
    public List<RegistrationDto> findAll() {
        return this.regMap.values().stream().map(registration->getRegistrationDto(registration)).collect(Collectors.toList());
    }

    @Override
    public Optional<RegistrationDto> findById(String id) {
        return Optional.of(getRegistrationDto(regMap.get(id)));
    }

    @Override
    public boolean existsById(String id) {
        return regMap.containsKey(id);
    }

    @Override
    public void delete(RegistrationDto regDto) {
        regMap.entrySet().removeIf(a -> a.getValue().equals(getRegistration(regDto)));        
    }

    @Override
    public void deleteById(String id) {
        regMap.remove(id);
    }

    @Override
    public long count() {
        return regMap.size();
    }

    @Override
    public List<RegistrationDto> findAllByCourseId(String courseId) {
        List<RegistrationDto> allRegDto = findAll(); 
        return allRegDto.stream().filter(regDto -> regDto.getCourseId().equals(courseId)).collect(Collectors.toList());
    }

    @Override
    public Registration getRegistration(RegistrationDto regDto){
        return new Registration(regDto.getRegId(), regDto.getEmailId(), regDto.getCourseId(),regDto.isAccepted());
    }
 
    @Override
    public RegistrationDto getRegistrationDto(Registration registration){
        return new RegistrationDto(registration.getRegId(), registration.getEmailId(), registration.getCourseId(), registration.isAccepted());
    }
    
}
