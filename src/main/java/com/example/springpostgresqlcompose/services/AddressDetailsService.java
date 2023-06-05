package com.example.springpostgresqlcompose.services;

import com.example.springpostgresqlcompose.db.model.AddressDetails;
import com.example.springpostgresqlcompose.db.model.Student;
import com.example.springpostgresqlcompose.db.repositories.AddressDetailsRepository;
import com.example.springpostgresqlcompose.db.repositories.StudentRepository;
import com.example.springpostgresqlcompose.dtos.AddressDetailsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressDetailsService {

    private final AddressDetailsRepository addressDetailsRepository;
    private final StudentRepository studentRepository;

    public String saveAddressDetails(AddressDetailsDTO addressDetailsDTO) {
        Student student = studentRepository.findOneById(addressDetailsDTO.getStudentId());

        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setStudent(student);
        addressDetails.setAddress(addressDetailsDTO.getAddress());
        addressDetails.setUpazila(addressDetailsDTO.getUpazila());
        addressDetails.setDistrict(addressDetailsDTO.getDistrict());

        addressDetailsRepository.save(addressDetails);

        return "Address details saved successfully!";
    }

//    public List<StudentAddressDTO> getStudentByAddress(String address) {
//        List<Student> students = studentRepository.getStudentByAddress(address);
//
//        List<StudentAddressDTO> studentAddressDTOS = new ArrayList<>();
//        students.forEach(student -> {
//            studentAddressDTOS.add(StudentAddressDTO.builder()
//                    .studentId(student.getId())
//                    .studentName(student.getName())
//                    .studentRollNo(student.getRollNo())
//                    .address(student.getAddressDetails().getAddress())
//                    .upazila(student.getAddressDetails().getUpazila())
//                    .district(student.getAddressDetails().getDistrict())
//                    .build());
//        });
//
//        return studentAddressDTOS;
//    }
}
