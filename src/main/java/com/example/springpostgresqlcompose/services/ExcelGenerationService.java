package com.example.springpostgresqlcompose.services;

import com.example.springpostgresqlcompose.constants.AppConstants;
import com.example.springpostgresqlcompose.dtos.ExcelData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;


@Component
public class ExcelGenerationService {

    public String getStringFromAllCellType(Cell cell) {
        if (cell == null)
            return "";
        if (cell.getCellType().equals(CellType.NUMERIC)) {
            String string = String.valueOf(cell.getNumericCellValue()).trim();
            if (string.endsWith(".0"))
                return string.substring(0, string.length() - 2);
            return string;
        }
        if (cell.getCellType().equals(CellType.STRING))
            return String.valueOf(cell.getStringCellValue()).trim();
        if (cell.getCellType().equals(CellType.BOOLEAN))
            return String.valueOf(cell.getBooleanCellValue()).trim();
        if (cell.getCellType().equals(CellType.ERROR))
            return String.valueOf(cell.getErrorCellValue()).trim();
        if (cell.getCellType().equals(CellType.FORMULA))
            return String.valueOf(cell.getArrayFormulaRange()).trim();

        return "";
    }

    public Integer getIntegerFromAllCellType(Cell cell) {
        if (cell == null)
            return -1;
        if (cell.getCellType().equals(CellType.NUMERIC))
            return (int) cell.getNumericCellValue();
        if (cell.getCellType().equals(CellType.STRING))
            return Integer.parseInt(cell.getStringCellValue());

        return -1;
    }

    public Double getDoubleFromAllCellType(Cell cell) {
        if (cell == null)
            return -1d;
        if (cell.getCellType().equals(CellType.NUMERIC))
            return cell.getNumericCellValue();
        if (cell.getCellType().equals(CellType.STRING))
            return Double.parseDouble(cell.getStringCellValue());

        return -1d;
    }

    public void createExcelFile(ExcelData excelData, String fileName) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(excelData.getSheetName());

        int rowNum = 0, colNum = 0;
        Row headerRow = sheet.createRow(rowNum);
        for (String headerCell : excelData.getHeaderRow()) {
            Cell cell = headerRow.createCell(colNum);
            cell.setCellValue(headerCell);
            sheet.autoSizeColumn(colNum);
            colNum++;

        }
        rowNum++;

        for (Object[] otherRow : excelData.getOtherRowList()) {
            Row row = sheet.createRow(rowNum);
            colNum = 0;
            for (Object otherRowCell : otherRow) {
                Cell cell = row.createCell(colNum);

                if (otherRowCell instanceof Integer || otherRowCell instanceof Long || otherRowCell instanceof Float || otherRowCell instanceof Double)
                    cell.setCellValue(Double.parseDouble(String.valueOf(otherRowCell)));
                else
                    cell.setCellValue(String.valueOf(otherRowCell));

                colNum++;
            }
            rowNum++;
        }

        String fileDirectory = AppConstants.INPUT_OUTPUT_FILE_DIRECTORY + fileName;
        FileOutputStream outputStream = new FileOutputStream(fileDirectory);
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();

    }

}
