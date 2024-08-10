package com.example.springpostgresqlcompose.constants;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AppConstants {

    public static final String INPUT_OUTPUT_FILE_DIRECTORY = "src/main/resources/files/admit_cards/";
    public static final String PROFILE_IMAGE_DIRECTORY = "src/main/resources/files/profile_images/";
    public static final String STATIC_RESOURCES_DIRECTORY = "src/main/resources/files/static_files/";
    public static final String AMAR_AMI_LOGO = STATIC_RESOURCES_DIRECTORY + "amar_ami.png";

    public static final String AMAR_AMI_WHITE_LOGO = STATIC_RESOURCES_DIRECTORY + "amar_ami_white.png";
    public static final String SIGNATURE_IMAGE = STATIC_RESOURCES_DIRECTORY + "gias_signature.png";
    public static final String SCRIPT_MT_BOLD = STATIC_RESOURCES_DIRECTORY + "ScriptMTBold.ttf";
    public static final String OLD_ENGLISH = STATIC_RESOURCES_DIRECTORY + "OLDENGL.TTF";
    public static final String WINDING = STATIC_RESOURCES_DIRECTORY + "wingding.ttf";

    public static final int TEN_PRIZE = 11;
    public static final int EIGHT_PRIZE = 11;
    public static final int FIVE_PRIZE = 12;
    public static final String EXAM_DATE = "Exam Date: Monday, 26 June, 9.00 am";
}
