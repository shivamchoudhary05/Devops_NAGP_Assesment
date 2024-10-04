package com.redbus.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;

    /**
     * Read data from Excel file and store it in a 2D array.
     * 
     * @param Path      Path of the Excel file.
     * @param SheetName Name of the Excel sheet.
     * @return 2D array containing Excel data.
     * @throws Exception If an error occurs while reading Excel file.
     */
    public static String[][] getExcelDataIn2DArray(String Path, String SheetName) throws Exception {
        String[][] excelDataArray = null;
        try {
            // Open Excel file
            FileInputStream ExcelFile = new FileInputStream(Path);

            // Initialize workbook and sheet
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(SheetName);

            // Get the number of columns and rows in the Excel sheet
            int numOfColumns = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
            int numOfRows = ExcelWSheet.getPhysicalNumberOfRows();

            // Initialize the 2D array to store Excel data
            excelDataArray = new String[numOfRows - 1][numOfColumns];

            // Iterate through rows and columns to read data from Excel sheet
            for (int i = 1; i < numOfRows; i++) {
                for (int j = 0; j < numOfColumns; j++) {
                    Cell cell = ExcelWSheet.getRow(i).getCell(j);
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        excelDataArray[i - 1][j] = String.valueOf(cell.getNumericCellValue());
                    } else {
                        excelDataArray[i - 1][j] = cell.getStringCellValue();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return excelDataArray;
    }
}
