package com.medbis.controller;


import com.medbis.dto.PatientDTO;
import com.medbis.entity.Patient;
import com.medbis.entity.User;
import com.medbis.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TableController {

    UserService userService;
    private Object Patient;

    public TableController() {
    }

    @Autowired
    public TableController(@Qualifier(value = "PatientServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/patientsList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PatientDTO>> getData(){

        List<? extends User> allPatients = userService.findAll();
        List<PatientDTO> patientDTOlista = allPatients.stream()
                .map(x -> convertToDto((com.medbis.entity.Patient) x))
                .collect(Collectors.toList());

        return new ResponseEntity<>(patientDTOlista, HttpStatus.OK);
    }

    private PatientDTO convertToDto(Patient patient) {
        PatientDTO patientDTO = modelMapper.map(patient, PatientDTO.class);
//        patientDTO.setSubmissionDate(user.getSubmissionDate(),
//                userService.getCurrentUser().getPreference().getTimezone());
        return patientDTO;
    }
}
