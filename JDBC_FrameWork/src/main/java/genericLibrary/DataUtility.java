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
			ResultSet result = statement.executeQuery(getQueryFromExcel() + "'" + allEmail.get(i) + "';");

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

	public String getQueryFromExcel() throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(ExcelFile_Path);
		Workbook book = WorkbookFactory.create(fis);
		Sheet sh = book.getSheet(dbQuerySheetName);
		return sh.getRow(0).getCell(0).toString();
	}

}
