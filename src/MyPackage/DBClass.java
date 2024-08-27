package MyPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class DBClass {

	WebDriver driver = new ChromeDriver();
	Connection con;
	Statement stmt ;
	ResultSet rs;
	
	Random rand1 = new Random();
	Random rand2 = new Random();
	int randomCustomerNumber = Math.abs(rand1.nextInt(8888) * rand2.nextInt(4561));
	/////////////////////////////////////////////////////////////////////////////////////////////
	@BeforeTest
	public void mysetup() throws SQLException {
		
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels","root","noor1234");
		//System.out.println(randomCustomerNumber);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test(priority = 1)
	public void InsertTest() throws SQLException {
    	stmt = con.createStatement();
    	int NumberofRowsAdded= stmt.executeUpdate("INSERT INTO customers (addressLine1, addressLine2, city, contactFirstName, contactLastName,country, creditLimit, customerName, customerNumber, phone, postalCode, salesRepEmployeeNumber, state) VALUES ('123 Main St', 'Suite 200', 'Amman', 'John', 'Doe','Jordan', 5000.00, 'Johnee',"+ randomCustomerNumber+", '123-456-7890','11111', 1370, 'Amman');");
    	System.out.println(NumberofRowsAdded);
		}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test(priority = 2)
	public void SelectTest1() throws SQLException {
		stmt = con.createStatement();
		rs =stmt.executeQuery("select * from customers where customerNumber ="+ randomCustomerNumber);
		while(rs.next()) {
			int CustomerNumber = rs.getInt("customerNumber");
			String CustomerName = rs.getNString("contactFirstName");
			System.out.println(CustomerNumber);
			System.out.println(CustomerName);
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test(priority = 3)
	public void UpdateTest() throws SQLException {
		stmt = con.createStatement();
		int NumberofRowsUpdated = stmt.executeUpdate("update customers SET contactFirstName = 'Noora' WHERE customerNumber ="+randomCustomerNumber);
		System.out.println(NumberofRowsUpdated);
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test(priority = 4)
	public void SelectTest() throws SQLException {
		stmt = con.createStatement();
		rs =stmt.executeQuery("select * from customers where customerNumber ="+ randomCustomerNumber);
		while(rs.next()) {
			int CustomerNumber = rs.getInt("customerNumber");
			String CustomerFirstName = rs.getNString("contactFirstName");
			String CustomerLastName = rs.getNString("contactLastName");
			System.out.println(CustomerNumber);
			System.out.println(CustomerFirstName);
			
			driver.get("https://magento.softwaretestingboard.com/");
			WebElement ClickonCreateAccount =driver.findElement(By.linkText("Create an Account"));
			ClickonCreateAccount.click();
			WebElement FirstName =driver.findElement(By.id("firstname"));
			WebElement LastName =driver.findElement(By.id("lastname"));
			FirstName.sendKeys(CustomerFirstName);
			LastName.sendKeys(CustomerLastName);
			
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test(priority = 5)
	public void DeleteTest() throws SQLException {
		stmt=con.createStatement();
		int NumberofRowsDeleted = stmt.executeUpdate("delete FROM customers where customerNumber ="+randomCustomerNumber);
		System.out.println(NumberofRowsDeleted);
	}
}