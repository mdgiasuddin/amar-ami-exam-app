package com.example.springpostgresqlcompose.utils;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClassOptionUtils {

    public Map<String, String> getOptionsOfClass(String classId) {
        Map<String, String> map = new HashMap<>();
        String admitCards = classId + "Admit.pdf";
        String watermarkAdmitCards = classId + "WM-Admit.pdf";
        String attendanceSheet = classId + "-Attendance.pdf";
        String resultSheet = classId + "-Result.pdf";

        int startingRollNo = 0;
        int startingRegNo = 0;
        int increasingRegNo = 0;

        if (classId.equalsIgnoreCase("Ten")) {
            startingRollNo = 137101;
            startingRegNo = 92;
            increasingRegNo = 461;
        } else if (classId.equalsIgnoreCase("Eight")) {
            startingRollNo = 875101;
            startingRegNo = 67;
            increasingRegNo = 241;
        } else if (classId.equalsIgnoreCase("Five")) {
            startingRollNo = 539101;
            startingRegNo = 19;
            increasingRegNo = 321;
        }

        map.put("admitCards", admitCards);
        map.put("watermarkAdmitCards", watermarkAdmitCards);
        map.put("attendanceSheet", attendanceSheet);
        map.put("resultSheet", resultSheet);
        map.put("startingRollNo", String.valueOf(startingRollNo));
        map.put("startingRegNo", String.valueOf(startingRegNo));
        map.put("increasingRegNo", String.valueOf(increasingRegNo));

        return map;
    }
}
