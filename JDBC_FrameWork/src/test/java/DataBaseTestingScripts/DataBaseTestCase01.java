package DataBaseTestingScripts;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.Test;

import genericLibrary.DataUtility;
import genericLibrary.FrameWorkConstant;

public class DataBaseTestCase01 {
	
	@Test
	public void dataBaseTestCase01() throws EncryptedDocumentException, IOException, SQLException
	{
		DataUtility data_Utility = new DataUtility();
		Connection connection = DriverManager.getConnection(data_Utility.getDataFromProperties("dbTestUrl"), data_Utility.getDataFromProperties(FrameWorkConstant.dbUserName_Key), data_Utility.getDataFromProperties(FrameWorkConstant.dbPassword_Key));
		Statement statement= connection.createStatement();
		
		ArrayList<String> allQuery = data_Utility.getDBQueryFromExcel();
		//ArrayList<String> allQueryResult = data_Utility.getDBQueryResultFromExcel();
		
		for(String query:allQuery)
		{
			ResultSet result = statement.executeQuery(query);
			
			while(result.next())
			{

				if(result.getString(1).equals(data_Utility.getResult(allQuery.indexOf(query))))
				{
					System.out.println("Pass...");
				}
				else {
					System.err.println("Fail... Expected Result -"+data_Utility.getResult(allQuery.indexOf(query))+": But Found -"+result.getString(1));
					
				}
			}
		}
	}

}
