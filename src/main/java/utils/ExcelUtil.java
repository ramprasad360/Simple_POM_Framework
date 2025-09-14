package utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.util.*;

public class ExcelUtil {

	private String filePath;

	public ExcelUtil(String filePath) {
		this.filePath = filePath;
	}

	public List<Map<String, String>> getData(String sheetName) {
		List<Map<String, String>> testDataAllRows = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(filePath);
				Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);
			Row headerRow = sheet.getRow(0);
			int totalRows = sheet.getPhysicalNumberOfRows();
			int totalCols = headerRow.getPhysicalNumberOfCells();

			for (int i = 1; i < totalRows; i++) {
				Row currentRow = sheet.getRow(i);
				Map<String, String> dataMap = new HashMap<>();

				for (int j = 0; j < totalCols; j++) {
					String key = headerRow.getCell(j).getStringCellValue();
					Cell cell = currentRow.getCell(j);
					String value = cell == null ? "" : cell.toString();
					dataMap.put(key, value);
				}
				testDataAllRows.add(dataMap);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return testDataAllRows;
	}
}
