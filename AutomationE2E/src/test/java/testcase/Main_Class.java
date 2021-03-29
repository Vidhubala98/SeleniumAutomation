package testcase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.TestNG;
import org.testng.collections.Lists;

import listeners.SendMail;
import loaddata.LoadData;

public class Main_Class {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename=new String();
		// Loading the data from excel file into data.properties
		try {

			String email = "vanthinikalai@gmail.com";
			
			Workbook workBook = WorkbookFactory.create(new File(System.getProperty("user.dir") + "//excel//Authentication.xlsx"));
			int numofsheets=workBook.getNumberOfSheets();
			for (int i=0; i<numofsheets; i++) {
				System.out.println( (i+1) +" "+ workBook.getSheetName(i));
			}

			int [] sheets = null;
			sheets=new int[numofsheets];
				for(int i=1;i<=numofsheets;i++)
					sheets[i-1]=i;
			

			for(int i=0;i<sheets.length;i++)
			{
				System.out.println();
				System.out.println("======================================RUNNING FOR "+ workBook.getSheetName(sheets[i]-1) +"======================================");
				System.out.println();

				LoadData readWriteExcelProperties = new LoadData();
				filename=readWriteExcelProperties.readWriteExcelFile(workBook,sheets[i]); 

				if(filename!=null)
				{
					// Run TestNG
					TestNG testng = new TestNG();
					List<String> suites = Lists.newArrayList();
					suites.add(System.getProperty("user.dir") + "//testng.xml");
					testng.setTestSuites(suites);
					testng.run();

					// Send Email
					SendMail s=new SendMail();
					s.send_mail(filename, email);
					System.out.println("Reports sent to "+email);


					// Delete screenshots
					final String fname=filename;
					File root = new File(System.getProperty("user.dir")+"//target");
					FilenameFilter beginswith = new FilenameFilter()
					{ 
						public boolean accept(File directory, String filename) {
							return filename.startsWith("screenshotof"+fname);
						}
					}; 
					File[] files = root.listFiles(beginswith);
					for(File f : files){
						f.delete();
					}
					//System.out.println("Screenshots Deleted");

					// Delete data.properties file content
					BufferedWriter writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir") + "//src//main//java//loaddata//data.properties"));
					writer.write("");
					writer.flush();
					//System.out.println("data.properties Content Deleted");

					// Delete Custom Report
					root = new File(System.getProperty("user.dir")+"//test-output");
					beginswith = new FilenameFilter()
					{ 
						public boolean accept(File directory, String filename) {
							return filename.startsWith(fname+"_CustomReport");
						}
					}; 
					files = root.listFiles(beginswith);
					for(File f : files){
						f.delete();
					}
					//System.out.println("Custom Report Deleted");

					// Delete Extent Report
					root = new File(System.getProperty("user.dir"));
					beginswith = new FilenameFilter()
					{ 
						public boolean accept(File directory, String filename) {
							return filename.startsWith(fname+"_ExtentReport");
						}
					}; 
					files = root.listFiles(beginswith);
					for(File f : files){
						f.delete();
					}
					//System.out.println("Extent Report Deleted");

					// Delete Zip
					root = new File(System.getProperty("user.dir"));
					beginswith = new FilenameFilter()
					{ 
						public boolean accept(File directory, String filename) {
							return filename.startsWith(fname+".zip");
						}
					}; 
					files = root.listFiles(beginswith);
					for(File f : files){
						f.delete();
					}
					//System.out.println("Zip Deleted");
					System.out.println("Content Deleted..!");
				}
			}
		}
		catch(Exception e) {
			System.out.println(e);		
		}		        
	}
}



