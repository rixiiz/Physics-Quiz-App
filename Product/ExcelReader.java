import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelReader {

    /**
     * retrieves a text from an excel file according to the parameters
     * @param sheetName is the name of sheet in the excel file the program is going to search
     * @param rNum is the row number in the excel file the program is going to search starting at 0
     * @param cNum is the cell number in the excel file the program is going to search starting at 0
     * @return the string of the text in sheet 'sheetName', at row 'rNum', and at cell 'cNum' from the excel file
     */
    public String readExcel(String sheetName, int rNum, int cNum){
        String data = "";
        String excelLocation = "Test.xlsx"; //PATH TO BE CHANGED DEPENDING ON THE LOCATION OF WHERE IT IS STORED
        try {
            //create a workbook from the excel file linked
            FileInputStream fis = new FileInputStream(excelLocation);
            XSSFWorkbook wb = new XSSFWorkbook(fis);

            //get the necessary information to retrieve from the workbook created previously
            Sheet sheet = wb.getSheet(sheetName);
            Row row = sheet.getRow(rNum);
            Cell cell = row.getCell(cNum);
            data = cell.getStringCellValue();
        } catch (Exception e) {
            System.err.println("Error reading Excel file: " + excelLocation);
            e.printStackTrace();
        }
        return data;
    }
}

