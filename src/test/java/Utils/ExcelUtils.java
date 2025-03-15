package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static FileInputStream fis;
	public static XSSFWorkbook wb;

	public static XSSFSheet sheet;

	public static XSSFRow Row;

	public static XSSFCell Cell;

	public static FileOutputStream fos;
	
	public static String file_path = System.getProperty("user.dir") + "\\" + PropertiesFile.getProperty("excel.path") + "\\" + PropertiesFile.getProperty("excel.name");

    
	
	public static int getRowCount(String sheetname) {
		
	
		System.out.println("Path"+file_path);
		int rowcount;
		
		try {
			fis = new FileInputStream(file_path);
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet(sheetname);

			rowcount = sheet.getLastRowNum();

			fis.close();

			wb.close();

		} catch (FileNotFoundException e) {

			System.out.println("Problem with File file_path");
			return 0;

		} catch (IOException e) {
			System.out.println("Problem with xssf workbook");
			return 0;

		}

		return rowcount+1;

	}

	public static int getCellCount(String sheetname) {
		int cellcount = 0;
	

		try {
			fis = new FileInputStream(file_path);
			wb = new XSSFWorkbook(fis);

			sheet = wb.getSheet(sheetname);

			cellcount = sheet.getRow(0).getLastCellNum();

			fis.close();

			wb.close();

		} catch (FileNotFoundException e) {

			System.out.println("Problem with File file_path");
			return 0;

		} catch (IOException e) {
			System.out.println("Problem with xssf workbook");
			return 0;

		}

		catch (NullPointerException e) {
			System.out.println("There are no rows");
			return 0;
		}

		return cellcount;

	}

	public static String getCellData(String sheetname, int row, int cell) {
	
		String data;
		try {
			fis = new FileInputStream(file_path);
			wb = new XSSFWorkbook(fis);

			sheet = wb.getSheet(sheetname);
			
		 XSSFCell cell2 = sheet.getRow(row).getCell(cell);
		 if(cell2.getCellType()==CellType.NUMERIC)
				data=NumberToTextConverter.toText(cell2.getNumericCellValue());
		 else

			data=cell2.getStringCellValue();

			fis.close();

			wb.close();

		} catch (FileNotFoundException e) {

			System.out.println("Problem with File file_path");
			return "";

		} catch (IOException e) {
			System.out.println("Problem with xssf workbook");
			return "";

		}

		catch (NullPointerException e) {
			System.out.println("There are no rows or no value in cell");
			return "";

		}

		return data;

	}

	public static void setCellData(String sheetname, int row, int cell, String data) {
	
		try {
			fis = new FileInputStream(file_path);
			wb = new XSSFWorkbook(fis);

			sheet = wb.getSheet(sheetname);

			Row = sheet.getRow(row);
			

			Cell = Row.createCell(cell);
			Cell.setCellValue(data);

			fos = new FileOutputStream(file_path);

			wb.write(fos);

			System.out.println("Data written successfull");
			fis.close();
			fos.close();
			wb.close();

		} catch (FileNotFoundException e) {

			System.out.println("Problem with File file_path");

		} catch (IOException e) {
			System.out.println("Problem with xssf workbook");

		}

		catch (NullPointerException e) {
			System.out.println("There are no rows from setcelldata");

		}

	}
	
	public static int getRownum(String sheetname, int columnindex, String value) {
        int rowindex = 0;
        try {
            fis = new FileInputStream(file_path);
            wb = new XSSFWorkbook(fis);
            sheet = wb.getSheet(sheetname);
            int rowcount = getRowCount(sheetname);

            for (int i = 1; i <= rowcount; i++) {
                String actualvalue = String.valueOf(getCellData(sheetname, i, columnindex));
                if (actualvalue.equals(value)) {
                    rowindex = i;
                    break; // Exit loop once found
                }
            }

            fis.close();
            wb.close();
        } catch (FileNotFoundException e) {
            System.out.println("Problem with File: " + file_path);
        } catch (IOException e) {
            System.out.println("Problem with xssf workbook");
        }

        return rowindex;
    }
	public static int getCellIndexByHeader(String sheetname, String headerName) {
	
	    int cellIndex = -1; // Default to -1 if not found
	    try {
	        fis = new FileInputStream(file_path);
	        wb = new XSSFWorkbook(fis);
	        sheet = wb.getSheet(sheetname);

	        // Get the header row (assuming it's the first row, index 0)
	        XSSFRow headerRow = sheet.getRow(0);

	        if (headerRow != null) {
	            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
	                XSSFCell cell = headerRow.getCell(i);
	                if (cell != null && cell.getStringCellValue().equalsIgnoreCase(headerName)) {
	                    cellIndex = i; // Update cell index if header name matches
	                    break; // Exit loop once found
	                }
	            }
	        } else {
	            System.out.println("Header row is empty or does not exist.");
	        }

	        fis.close();
	        wb.close();
	    } catch (FileNotFoundException e) {
	        System.out.println("Problem with File file_path");
	    } catch (IOException e) {
	        System.out.println("Problem with xssf workbook");
	    }

	    return cellIndex; // Return the index or -1 if not found
	}
	public static void fillCellGreen(String sheetname, int row, int cell) {
	
	    try {
	        fis = new FileInputStream(file_path);
	        wb = new XSSFWorkbook(fis);
	        sheet = wb.getSheet(sheetname);

	        XSSFCell cellToFill = sheet.getRow(row).getCell(cell);
	        XSSFCellStyle style = wb.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        cellToFill.setCellStyle(style);

	        fos = new FileOutputStream(file_path);
	        wb.write(fos);

	        System.out.println("Cell filled with green color successfully.");
	        fis.close();
	        fos.close();
	        wb.close();

	    } catch (FileNotFoundException e) {
	        System.out.println("Problem with File file_path");
	    } catch (IOException e) {
	        System.out.println("Problem with xssf workbook");
	    } catch (NullPointerException e) {
	        System.out.println("There are no rows from fillgreen");
	    }
	}

	// Method to fill a cell with red color
	public static void fillCellRed(String sheetname, int row, int cell) {
		
	
	    try {
	        fis = new FileInputStream(file_path);
	        wb = new XSSFWorkbook(fis);
	        sheet = wb.getSheet(sheetname);

	        XSSFCell cellToFill = sheet.getRow(row).getCell(cell);
	        XSSFCellStyle style = wb.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.RED.getIndex());
	        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        cellToFill.setCellStyle(style);

	        fos = new FileOutputStream(file_path);
	        wb.write(fos);

	        System.out.println("Cell filled with red color successfully.");
	        fis.close();
	        fos.close();
	        wb.close();

	    } catch (FileNotFoundException e) {
	        System.out.println("Problem with File file_path");
	    } catch (IOException e) {
	        System.out.println("Problem with xssf workbook");
	    } catch (NullPointerException e) {
	        System.out.println("There are no rows from fill red");
	    }
	}

}
