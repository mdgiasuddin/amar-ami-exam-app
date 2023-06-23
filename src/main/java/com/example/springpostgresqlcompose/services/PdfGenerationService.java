package com.example.springpostgresqlcompose.services;


import com.example.springpostgresqlcompose.constants.AppConstants;
import com.example.springpostgresqlcompose.db.model.Student;
import com.example.springpostgresqlcompose.db.repositories.StudentRepository;
import com.example.springpostgresqlcompose.dtos.AttendanceSheetData;
import com.example.springpostgresqlcompose.dtos.UnregisteredStudents;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.example.springpostgresqlcompose.constants.AppConstants.EXAM_DATE;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PdfGenerationService {

    private final StudentRepository studentRepository;

    public void generateAdmitCard(List<Student> studentList, String filename) throws IOException, DocumentException {
        final int SPACING = 20;

        Rectangle pageSize = new Rectangle(594, 426);
        pageSize.setBackgroundColor(new BaseColor(192, 192, 192));
        final float marginTopBottom = 25;
        final float marginLeftRight = 35;
        Document document = new Document(pageSize, marginLeftRight, marginLeftRight, marginTopBottom, marginTopBottom);

        BaseFont scriptMTBold =
            BaseFont.createFont(AppConstants.SCRIPT_MT_BOLD, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont oldEnglish = BaseFont.createFont(AppConstants.OLD_ENGLISH, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont winding = BaseFont.createFont(AppConstants.WINDING, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font fontItalic = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.ITALIC, BaseColor.BLACK);
        Font fontNormal = new Font(Font.FontFamily.TIMES_ROMAN, 8f, Font.NORMAL, BaseColor.BLACK);
        Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 8f, Font.NORMAL, new BaseColor(128, 0, 0));

        Font oldEnglish22 = new Font(oldEnglish, 22, Font.NORMAL, BaseColor.BLACK);
        Font oldEnglishIT18 = new Font(oldEnglish, 18, Font.ITALIC, BaseColor.BLACK);
        Font scriptMTBold11 = new Font(scriptMTBold, 11, Font.NORMAL, BaseColor.BLACK);
        Font controllerFont = new Font(scriptMTBold, 11f, Font.NORMAL, new BaseColor(128, 0, 0));
        Font windingFont = new Font(winding, 11f, Font.NORMAL);

        PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filename)));

        document.open();

        Image logoImage = Image.getInstance(AppConstants.AMAR_AMI_LOGO);
        Image signImage = Image.getInstance(AppConstants.SIGNATURE_IMAGE);

        for (Student student : studentList) {

            logoImage.setAlignment(Element.ALIGN_LEFT);
            logoImage.setBorderWidth(SPACING);

            PdfPTable imageTable = new PdfPTable(2);
            imageTable.setWidthPercentage(100);
            imageTable.setWidths(new int[] {1, 5});

            PdfPCell imageCell = new PdfPCell();
            imageCell.addElement(logoImage);
            imageCell.setBorder(Rectangle.NO_BORDER);
            imageTable.addCell(imageCell);

            Font font2 = new Font(oldEnglish, 12, Font.NORMAL, BaseColor.BLACK);
            PdfPCell textCell = new PdfPCell();

            Paragraph paragraph = new Paragraph("Amar Ami\n", oldEnglish22);
            paragraph.add(new Chunk("Talent Evaluation Exam - " + LocalDate.now().getYear() + "\n", font2));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            textCell.addElement(paragraph);
            textCell.setBorder(Rectangle.NO_BORDER);

            imageTable.addCell(textCell);
            imageTable.setSpacingAfter(0);

            Paragraph paragraph1 = new Paragraph("Admit Card", oldEnglishIT18);
            paragraph1.setSpacingAfter(20);
            paragraph1.setAlignment(Element.ALIGN_CENTER);

            // Rectangle around 'Admit card'.
            PdfContentByte cb = writer.getDirectContent();
            cb.roundRectangle(250f, 288f, 95f, 20f, 5f);
            cb.setColorStroke(new BaseColor(209, 0, 0));
            cb.stroke();

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(95);
            table.setWidths(new int[] {8, 5});

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Name: " + student.getName(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Roll No: " + student.getRollNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("School: " + student.getSchoolName(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Registration No: " + student.getRegNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            String classId = student.getClassId() + (student.getClassId().equals(student.getClassIdActual()) ? ""
                : " (" + student.getClassIdActual() + ")");
            cell = new PdfPCell(new Phrase("Class: " + classId, scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Class Roll: " + student.getSchoolRollNo(), scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            String centre = "Betbaria Secondary School";

            cell = new PdfPCell(new Phrase("Centre: " + centre, scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(EXAM_DATE, scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            table.setSpacingAfter(30);

            signImage.setAlignment(Element.ALIGN_LEFT);

            PdfPTable table2 = new PdfPTable(3);
            table2.setWidths(new int[] {24, 2, 3});
            table2.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPCell signImageCell = new PdfPCell();
            signImageCell.addElement(signImage);
            signImageCell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(signImageCell);

            cell = new PdfPCell(new Phrase("", scriptMTBold11));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPTable table3 = new PdfPTable(2);
            table3.setWidths(new int[] {8, 3});
            table3.setWidthPercentage(100);
            table3.setSpacingAfter(15);

            // Generate Barcode. Currently, not using.
            Barcode128 barcode128 = new Barcode128();
            barcode128.setCode(student.getRollNo() + "-" + student.getRegNo());
            barcode128.setFont(null);
            Image barCode128Image = barcode128.createImageWithBarcode(cb, null, null);
            cell = new PdfPCell(barCode128Image, true);
            cell.setFixedHeight(32);
            cell.setBorder(Rectangle.NO_BORDER);
//            table3.addCell(cell);

            cell = new PdfPCell(new Phrase(
                "Generated at: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM, yyyy hh:mm:ss a")),
                fontNormal));
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);

            Paragraph controllerExam = new Paragraph(new Chunk("Gias Uddin Ahmed\n", smallFont));
            controllerExam.add(new Chunk("Controller of Exam", controllerFont));
            cell = new PdfPCell(controllerExam);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);

            // Line before directions.
            cb.rectangle(35f, 125f, 524f, 0.3f);
            cb.setColorStroke(new BaseColor(0, 0, 0));
            cb.stroke();

            PdfPTable directions = new PdfPTable(2);
            directions.setWidthPercentage(100);
            directions.setWidths(new int[] {3, 97});

            char checked = '\u0076';
            PdfPCell markCell = new PdfPCell(new Phrase(String.valueOf(checked), windingFont));
            markCell.setBorder(Rectangle.NO_BORDER);

            directions.addCell(markCell);

            cell = new PdfPCell(new Phrase("Examinee must bring this card to the examination hall.", fontItalic));
            cell.setBorder(Rectangle.NO_BORDER);
            directions.addCell(cell);

            directions.addCell(markCell);

            cell = new PdfPCell(new Phrase(
                "Examinee must appear in the exam hall & take his/her seat at least 20 minutes before the exam start.",
                fontItalic));
            cell.setBorder(Rectangle.NO_BORDER);
            directions.addCell(cell);

            directions.addCell(markCell);

            cell = new PdfPCell(new Phrase(
                "Examinee must carry his/her own writing materials like pen, pencil, geometry instruments & calculator. But he/she cannot keep anything with him/her except these materials.",
                fontItalic));
            cell.setBorder(Rectangle.NO_BORDER);
            directions.addCell(cell);

            directions.addCell(markCell);

            cell = new PdfPCell(new Phrase(
                "In case of violating one or more rules by any examinee during the exam time, that examinee's exam will be canceled and legal actions against that examinee will be taken.",
                fontItalic));
            cell.setBorder(Rectangle.NO_BORDER);
            directions.addCell(cell);

            // QR Code in top right corner.
            BarcodeQRCode qrCode = new BarcodeQRCode(
                LocalDate.now().getYear() + ">>" + student.getRollNo() + ">>" + student.getName() + "<<"
                    + "<<" + student.getRegNo() + "<<" + student.getVerificationNo(), 1, 1, null);
            Image qrImage = qrCode.getImage();
            Image mask = qrCode.getImage();
            mask.makeMask();
            qrImage.setImageMask(mask);
            qrImage.setAbsolutePosition(480, 315);
            qrImage.scaleAbsolute(new Rectangle(90, 90));

            document.add(qrImage);
            document.add(imageTable);
            document.add(paragraph1);
            document.add(table);
            document.add(table2);
            document.add(table3);
            document.add(directions);

            document.newPage();
        }

        document.close();
    }

    public void generateBlankAdmitCard(List<Student> studentList, String filename)
        throws IOException, DocumentException {

        Rectangle pageSize = new Rectangle(297, 213);
        final float marginTopBottom = 15;
        final float marginLeftRight = 15;
        Document document = new Document(pageSize, marginLeftRight, marginLeftRight, marginTopBottom, marginTopBottom);

        Font largeFont = new Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLDITALIC, BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.ITALIC, BaseColor.BLACK);
        Font controllerSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 7f, Font.NORMAL, BaseColor.BLACK);

        PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filename)));

        document.open();

        Image signImage = Image.getInstance(AppConstants.SIGNATURE_IMAGE);

        for (Student student : studentList) {

            Paragraph paragraph = new Paragraph("Amar Ami\n", largeFont);
            paragraph.add(new Chunk("Talent Evaluation Exam - 2022\n", largeFont));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(15);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[] {8, 6});

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Name: ", font));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Roll No: " + student.getRollNo(), font));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("School: ", font));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Registration No: " + student.getRegNo(), font));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Class: " + student.getClassId(), font));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Verification No: " + student.getVerificationNo(), font));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);

            table.setSpacingAfter(15);

            signImage.setAlignment(Element.ALIGN_LEFT);

            PdfPTable table2 = new PdfPTable(3);
            table2.setWidths(new int[] {17, 2, 3});
            table2.setWidthPercentage(100);

            cell = new PdfPCell(new Phrase("", font));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPCell signImageCell = new PdfPCell();
            signImageCell.addElement(signImage);
            signImageCell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(signImageCell);

            cell = new PdfPCell(new Phrase("", font));
            cell.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell);

            PdfPTable table3 = new PdfPTable(2);
            table3.setWidths(new int[] {8, 5});
            table3.setWidthPercentage(100);
            table3.setSpacingAfter(15);

            cell = new PdfPCell(new Phrase("", font));
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);

            Paragraph controllerExam = new Paragraph(new Chunk("Gias Uddin Ahmed\n", controllerSmallFont));
            controllerExam.add(new Chunk("Controller of Exam", font));
            cell = new PdfPCell(controllerExam);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table3.addCell(cell);

            document.add(paragraph);
            document.add(table);
            document.add(table2);
            document.add(table3);

            document.newPage();
        }

        document.close();
    }

    public void generateSeatPlan(List<Student> studentList, String filename) throws IOException, DocumentException {

        final float margin = 5;
        Document document = new Document(PageSize.A4, margin, margin, margin, margin);

        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 21f, Font.NORMAL, BaseColor.BLACK);
        Font nameFont = new Font(Font.FontFamily.TIMES_ROMAN, 14f, Font.NORMAL, BaseColor.BLACK);

        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filename)));

        document.open();

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {1, 1, 1});

        for (Student student : studentList) {
            Paragraph paragraph = new Paragraph("Class: " + student.getClassId() + "\n", font);
            paragraph.add(new Chunk("Roll: " + student.getRollNo() + "\n", font));
            paragraph.add(new Chunk("Name: " + student.getName() + "\n", nameFont));

            PdfPCell cell = new PdfPCell(paragraph);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingBottom(15);
            cell.setPaddingTop(5);

            table.addCell(cell);
        }

        for (int i = 0; i < 3 - studentList.size() % 3; i++) {
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingBottom(15);
            cell.setPaddingTop(5);

            table.addCell(cell);
        }


        document.add(table);

        document.newPage();
        document.close();
    }

    public PdfPCell createMergedCell(String content, int colSpan, int rowSpan, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setColspan(colSpan);
        cell.setRowspan(rowSpan);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public void generateRoomDistribution(List<AttendanceSheetData> dataList) throws IOException, DocumentException {

        String filename = AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + "Room_Distribution.pdf";
        final float margin = 30;
        Document document = new Document(PageSize.A4, margin, margin, margin, margin);


        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 30f, Font.BOLD, BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 25f, Font.BOLD, BaseColor.BLACK);
        Font largeFont = new Font(Font.FontFamily.TIMES_ROMAN, 38f, Font.NORMAL, BaseColor.BLACK);

//        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 11f, Font.BOLD, BaseColor.BLACK);
//        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 11f, Font.BOLD, BaseColor.BLACK);
//        Font largeFont = new Font(Font.FontFamily.TIMES_ROMAN, 11f, Font.NORMAL, BaseColor.BLACK);

        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filename)));

        document.open();

        Map<String, List<AttendanceSheetData>> map = new HashMap<>();

        for (AttendanceSheetData data : dataList) {
            List<AttendanceSheetData> list = map.getOrDefault(data.getClassId(), new ArrayList<>());
            list.add(data);
            map.put(data.getClassId(), list);
        }

        for (Map.Entry<String, List<AttendanceSheetData>> entry : map.entrySet()) {
            Map<String, List<AttendanceSheetData>> centreData = new LinkedHashMap<>();
            for (AttendanceSheetData data : entry.getValue()) {
                List<AttendanceSheetData> list = centreData.getOrDefault(data.getCentre(), new ArrayList<>());
                list.add(data);
                centreData.put(data.getCentre(), list);
            }

            Paragraph headingParagraph = new Paragraph("Seat Plan", boldFont);
            headingParagraph.setAlignment(Element.ALIGN_CENTER);
            headingParagraph.setSpacingAfter(20);


            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[] {1, 2, 10});
            table.setSpacingAfter(15);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cell =
                createMergedCell(entry.getKey(), 1, entry.getValue().size() + centreData.size() * 2, boldFont);
            cell.setRotation(90);
            table.addCell(cell);
            for (Map.Entry<String, List<AttendanceSheetData>> centreEntry : centreData.entrySet()) {
                table.addCell(createMergedCell(centreEntry.getKey(), 2, 1, font));

                table.addCell(new Phrase("Room", font));
                PdfPCell cell1 = new PdfPCell(new Phrase("Roll Range", font));
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
                for (AttendanceSheetData sheetData : centreEntry.getValue()) {
                    table.addCell(new Phrase(sheetData.getRoomNo(), largeFont));
                    table.addCell(new Phrase(sheetData.getStartRollNo() + " - " + sheetData.getEndRollNo()
                        + " => " + (sheetData.getEndRollNo() - sheetData.getStartRollNo() + 1), largeFont));
                }
            }

            document.add(table);

            document.newPage();
        }

        document.close();
    }

    public void generateAttendanceSheet(List<AttendanceSheetData> dataList) throws IOException, DocumentException {

        String filename = AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + "Attendance_Sheet.pdf";
        final float margin = 25;
        Document document = new Document(PageSize.A4, margin, margin, margin, margin);

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD, BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.NORMAL, BaseColor.BLACK);

        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filename)));

        document.open();

        for (AttendanceSheetData data : dataList) {

            Paragraph headingParagraph = new Paragraph("Talent Evaluation Exam - 2022\n", font);
            headingParagraph.add(new Chunk("Room No: " + data.getRoomNo() + "\n", font));
            headingParagraph.add(new Chunk(data.getCentre() + "\n", font));
            headingParagraph.add(new Chunk(
                "Class: " + data.getClassId() + " (Roll: " + data.getStartRollNo() + " - " + data.getEndRollNo() +
                    ")\n", font));
            headingParagraph.setAlignment(Element.ALIGN_CENTER);
            headingParagraph.setSpacingAfter(20);

            List<Student> studentList =
                studentRepository.findByRollNoIsBetweenOrderByRollNoAsc(data.getStartRollNo(), data.getEndRollNo());

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[] {2, 15, 5, 5, 5, 10});

            table.addCell(new Phrase("Sl.", boldFont));
            table.addCell(new Phrase("Name", boldFont));
            table.addCell(new Phrase("Roll No.", boldFont));
            table.addCell(new Phrase("Reg No.", boldFont));
            table.addCell(new Phrase("Verify No.", boldFont));
            table.addCell(new Phrase("Signature of Examinee", boldFont));

            int i = 1;
            for (Student student : studentList) {
                table.addCell(new Phrase(i++ + ".", font));
                table.addCell(new Phrase(student.getName(), font));
                table.addCell(new Phrase(String.valueOf(student.getRollNo()), font));
                table.addCell(new Phrase(String.valueOf(student.getRegNo()), font));
                table.addCell(new Phrase(student.getVerificationNo(), font));
                table.addCell(new Phrase("", font));
            }

            table.setSpacingAfter(60);

            Paragraph signParagraph = new Paragraph("Name & Signature of Invigilator", boldFont);
            signParagraph.setAlignment(Element.ALIGN_RIGHT);

            document.add(headingParagraph);
            document.add(table);
            document.add(signParagraph);

            document.newPage();
        }

        document.close();
    }

    public void generateFinalResult(List<Student> tenStudent, List<Student> eightStudent, List<Student> fiveStudent,
                                    String filename) throws IOException, DocumentException {

        final float margin = 25;
        Document document = new Document(PageSize.A4, margin, margin, margin, margin);

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD, BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.NORMAL, BaseColor.BLACK);

        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filename)));

        document.open();

        Paragraph headingParagraph = new Paragraph("Talent Evaluation Exam - 2022\n", font);
        headingParagraph.add(new Chunk("Result List", font));
        headingParagraph.setAlignment(Element.ALIGN_CENTER);
        headingParagraph.setSpacingAfter(10);

        Paragraph tenParagraph = new Paragraph("Class - 10 (Ten)", boldFont);
        tenParagraph.setAlignment(Element.ALIGN_CENTER);
        tenParagraph.setSpacingAfter(10);

        PdfPTable tenTable = new PdfPTable(5);
        tenTable.setWidthPercentage(100);
        tenTable.setWidths(new int[] {4, 6, 15, 15, 5});

        tenTable.addCell(new Phrase("Merit", boldFont));
        tenTable.addCell(new Phrase("Roll No.", boldFont));
        tenTable.addCell(new Phrase("Name", boldFont));
        tenTable.addCell(new Phrase("School", boldFont));
        tenTable.addCell(new Phrase("Marks", boldFont));


        for (Student student : tenStudent) {

            tenTable.addCell(new Phrase(student.getMeritPosition() + ".", font));
            tenTable.addCell(new Phrase(student.getRollNo() + "", font));
            tenTable.addCell(new Phrase(student.getName(), font));
            tenTable.addCell(new Phrase(student.getSchoolName(), font));
            tenTable.addCell(new Phrase(student.getMarks() + "", font));
        }

        Paragraph eightParagraph = new Paragraph("Class - 8 (Eight)", boldFont);
        eightParagraph.setAlignment(Element.ALIGN_CENTER);
        eightParagraph.setSpacingAfter(10);

        PdfPTable eightTable = new PdfPTable(5);
        eightTable.setWidthPercentage(100);
        eightTable.setWidths(new int[] {4, 6, 15, 15, 5});

        eightTable.addCell(new Phrase("Merit", boldFont));
        eightTable.addCell(new Phrase("Roll No.", boldFont));
        eightTable.addCell(new Phrase("Name", boldFont));
        eightTable.addCell(new Phrase("School", boldFont));
        eightTable.addCell(new Phrase("Marks", boldFont));

        for (Student student : eightStudent) {

            eightTable.addCell(new Phrase(student.getMeritPosition() + ".", font));
            eightTable.addCell(new Phrase(student.getRollNo() + "", font));
            eightTable.addCell(new Phrase(student.getName(), font));
            eightTable.addCell(new Phrase(student.getSchoolName(), font));
            eightTable.addCell(new Phrase(student.getMarks() + "", font));
        }

        eightTable.setSpacingAfter(10);

        Paragraph fiveParagraph = new Paragraph("Class - 5 (Five)", boldFont);
        fiveParagraph.setSpacingAfter(10);

        PdfPTable fiveTable = new PdfPTable(5);
        fiveTable.setWidthPercentage(100);
        fiveParagraph.setAlignment(Element.ALIGN_CENTER);
        fiveTable.setWidths(new int[] {4, 6, 15, 15, 5});

        fiveTable.addCell(new Phrase("Merit", boldFont));
        fiveTable.addCell(new Phrase("Roll No.", boldFont));
        fiveTable.addCell(new Phrase("Name", boldFont));
        fiveTable.addCell(new Phrase("School", boldFont));
        fiveTable.addCell(new Phrase("Obtained Marks", boldFont));

        for (Student student : fiveStudent) {

            fiveTable.addCell(new Phrase(student.getMeritPosition() + ".", font));
            fiveTable.addCell(new Phrase(student.getRollNo() + "", font));
            fiveTable.addCell(new Phrase(student.getName(), font));
            fiveTable.addCell(new Phrase(student.getSchoolName(), font));
            fiveTable.addCell(new Phrase(student.getMarks() + "", font));
        }

        fiveTable.setSpacingAfter(60);


        document.add(headingParagraph);

        document.add(tenParagraph);
        document.add(tenTable);

        document.add(eightParagraph);
        document.add(eightTable);

        document.add(fiveParagraph);
        document.add(fiveTable);

//        document.newPage();
//        }

        document.close();
    }

    public void generateUnregisteredStudentList(UnregisteredStudents unregisteredStudents)
        throws IOException, DocumentException {

        String filename = AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + "Unregistered_List.pdf";
        final float margin = 25;
        Document document = new Document(PageSize.A4, margin, margin, margin, margin);

        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.BOLD, BaseColor.BLACK);
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 12f, Font.NORMAL, BaseColor.BLACK);

        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filename)));

        document.open();

        document.add(getUnregisteredHeadingParagraph("10 (Ten)", font));
        document.add(generateUnregisteredTable(unregisteredStudents.getTenStudents(), boldFont, font));
        document.newPage();

        document.add(getUnregisteredHeadingParagraph("08 (Eight)", font));
        document.add(generateUnregisteredTable(unregisteredStudents.getEightStudents(), boldFont, font));
        document.newPage();

        document.add(getUnregisteredHeadingParagraph("05 (Five)", font));
        document.add(generateUnregisteredTable(unregisteredStudents.getFiveStudents(), boldFont, font));


        document.close();
    }

    private Paragraph getUnregisteredHeadingParagraph(String classId, Font font) {
        Paragraph headingParagraph = new Paragraph("Talent Evaluation Exam - 2022\n", font);
        headingParagraph.add(new Chunk("Class: " + classId, font));
        headingParagraph.setAlignment(Element.ALIGN_CENTER);
        headingParagraph.setSpacingAfter(20);

        return headingParagraph;
    }

    private PdfPTable generateUnregisteredTable(List<Student> studentList, Font boldFont, Font font)
        throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {2, 15, 15, 5, 5, 5});

        table.addCell(new Phrase("Sl.", boldFont));
        table.addCell(new Phrase("Name", boldFont));
        table.addCell(new Phrase("School Name", boldFont));
        table.addCell(new Phrase("Roll No.", boldFont));
        table.addCell(new Phrase("Reg No.", boldFont));
        table.addCell(new Phrase("Verify No.", boldFont));

        int i = 1;
        for (Student student : studentList) {
            table.addCell(new Phrase(i++ + ".", font));
            table.addCell(new Phrase("", font));
            table.addCell(new Phrase("", font));
            table.addCell(new Phrase(String.valueOf(student.getRollNo()), font));
            table.addCell(new Phrase(String.valueOf(student.getRegNo()), font));
            table.addCell(new Phrase(student.getVerificationNo(), font));
        }

        return table;
    }


}
