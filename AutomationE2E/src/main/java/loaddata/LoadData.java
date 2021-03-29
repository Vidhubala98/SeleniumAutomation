package loaddata;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class LoadData {



	public String readWriteExcelFile(Workbook workBook, int num)     
	{ 
		try
		{    
			// Get the sheet at location num-1
			XSSFSheet sheet = (XSSFSheet) workBook.getSheetAt(num-1);   

			// Creating HashMap where all the extracted data will be stored
			HashMap< String, String > properties = new HashMap< String, String >();
			properties.put("FILENAME",workBook.getSheetName(num-1));

			// Iterator which will loop through each row.
			Iterator<Row> rowIterator = sheet.rowIterator(); 

			// Ignoring the First Row  (Contains Heading)
			XSSFRow row = (XSSFRow) rowIterator.next(); 

			int numOfURLs=1;
			// Iterating row by row
			while(rowIterator.hasNext())
			{     
				// Creating a reference to row by calling next method of the iterator
				row = (XSSFRow) rowIterator.next(); 
				Iterator<Cell> cellIterator = row.cellIterator(); 
				XSSFCell cell = (XSSFCell) cellIterator.next();
				String value= cell.getRichStringCellValue().toString();
				if(value.isEmpty())
					break;

				properties=readRow(properties, row, numOfURLs);
				numOfURLs++;
			}
			properties.put("NUMOFURLS", String.valueOf(numOfURLs-1)); 
			writeData(properties);

			System.out.println("Data added successfully");

			return workBook.getSheetName(num-1);
		} 
		catch (Exception e)
		{
			System.out.println("No Such Element.... Exception Occured..... ");
			e.printStackTrace();

			return null;
		}                
	}

	//Reads one row and appends in the HashMap
	public HashMap< String, String > readRow(HashMap< String, String > properties, XSSFRow row, int num)
	{
		// A cell for iterating over each element
		XSSFCell cell =null;   	
		// Giving the keys name
		String[] keys= { "Url"+num, "Username"+num, "Password"+num, "Title"+num, "Company"+num};

		// Creating a iterator which will contain each cell info related to that particular row
		Iterator<Cell> cellIterator = row.cellIterator(); 

		// Iterating over each cell and appending in HashMap                             
		for(int i=0;i<keys.length;i++)
		{
			cell = (XSSFCell) cellIterator.next();
			String value= cell.getRichStringCellValue().toString();
			properties.put(keys[i], value); 
		}
		return properties;
	}

	// Writes data into data.properties
	public void writeData(HashMap< String, String > properties) throws FileNotFoundException
	{
		Properties props = new Properties();
		String propertiesPath= System.getProperty("user.dir") + "//src//main//java//loaddata//data.properties";
		File propertiesFile = new File(propertiesPath);
		FileOutputStream xlsFos = new FileOutputStream(propertiesFile);
		// Writing into properties file
		try 
		{
			Iterator<String> mapIterator = properties.keySet().iterator();
			while(mapIterator.hasNext()) 
			{
				String key = mapIterator.next().toString();
				String value = properties.get(key);

				// Setting each properties file in props Object 
				props.setProperty(key, value);
			}
			// Finally storing into actual file
			props.store(xlsFos, null);
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("File Not Found ");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}


}    

