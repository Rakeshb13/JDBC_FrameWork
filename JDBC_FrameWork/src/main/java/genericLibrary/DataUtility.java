package genericLibrary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DataUtility extends BaseTest {

	public String getDataFromProperties(String key) throws FileNotFoundException {

		FileInputStream fis = new FileInputStream(propertiesFile_Path);
		Properties pobj = new Properties();
		try {
			pobj.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pobj.getProperty(key);
	}


	public Object[][] getLoginCredentials() throws EncryptedDocumentException, IOException, SQLException {

		ArrayList<String> allEmail = getLoginEmail();
		int lastRowNum = allEmail.size();

		Object[][] arr = new Object[lastRowNum][2];

		for (int i = 0; i < lastRowNum; i++) {
			ResultSet result = statement.executeQuery(getUIQueryFromExcel() + "'" + allEmail.get(i) + "';");

			while (result.next()) {
				arr[i][0] = result.getString("Email");
				arr[i][1] = result.getString("Password");
			}
		}

		return arr;

	}
	
	public ArrayList<String> getLoginEmail() throws EncryptedDocumentException, IOException {

		FileInputStream fis = new FileInputStream(ExcelFile_Path);
		Workbook book = WorkbookFactory.create(fis);
		Sheet sh = book.getSheet(dbEmailSheetName);

		int lastRowNum = sh.getPhysicalNumberOfRows();
		ArrayList<String> l = new ArrayList<String>();

		for (int i = 0; i < lastRowNum; i++) {
			l.add(sh.getRow(i).getCell(0).toString());
		}

		return l;

	}

	public String getUIQueryFromExcel() throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(ExcelFile_Path);
		Workbook book = WorkbookFactory.create(fis);
		Sheet sh = book.getSheet(dbUIQuerySheetName);
		return sh.getRow(0).getCell(0).toString();
	}
	
	public ArrayList<String> getDBQueryFromExcel() throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(ExcelFile_Path);
		Workbook book = WorkbookFactory.create(fis);
		Sheet sh = book.getSheet(dbTestQuerySheetName);

		ArrayList<String> arr = new ArrayList<String>();
		int lastRowNum = sh.getLastRowNum();

		for (int i = 1; i < lastRowNum; i++) {
			arr.add(sh.getRow(i).getCell(0).toString());
		}
		return arr;
	}
	
	public ArrayList<String> getDBQueryResultFromExcel() throws EncryptedDocumentException, IOException
	{
		FileInputStream fis = new FileInputStream(ExcelFile_Path);
		Workbook book = WorkbookFactory.create(fis);
		Sheet sh = book.getSheet(dbTestQuerySheetName);
		
		ArrayList<String> arr = new ArrayList<String>();
		int lastRowNum = sh.getLastRowNum();
		
		for (int i = 1; i < lastRowNum; i++) {
			arr.add(sh.getRow(i).getCell(1).toString());
		}
		return arr;
	}
	
	public String getResult(int rowNum) throws EncryptedDocumentException, IOException
	{
		FileInputStream fis = new FileInputStream(ExcelFile_Path);
		Workbook book = WorkbookFactory.create(fis);
		Sheet sh = book.getSheet(dbTestQuerySheetName);
		return sh.getRow(rowNum+1).getCell(1).toString();
	}

}
