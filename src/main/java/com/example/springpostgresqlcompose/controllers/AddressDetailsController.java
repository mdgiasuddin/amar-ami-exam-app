package com.example.springpostgresqlcompose.controllers;

import com.example.springpostgresqlcompose.dtos.AddressDetailsDTO;
import com.example.springpostgresqlcompose.services.AddressDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/address-details")
public class AddressDetailsController {

    private final AddressDetailsService addressDetailsService;

    @PostMapping
    public String saveAddressDetails(@RequestBody @Validated AddressDetailsDTO addressDetailsDTO) {
        return addressDetailsService.saveAddressDetails(addressDetailsDTO);
    }

//    @GetMapping("/student/address")
//    public List<StudentAddressDTO> getStudentByAddress(@RequestParam("address") String address) {
//        return addressDetailsService.getStudentByAddress(address);
//    }
}
