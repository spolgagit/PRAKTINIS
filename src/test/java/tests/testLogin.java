package tests;

import java.time.Duration;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class testLogin {
	
	WebDriver driver;
	WebDriverWait wait;	
	WebElement loginButton;
	WebElement logoutForm;
	WebElement userField;
	WebElement passField;
	WebElement messageSpan;
	String message, randomString;
	
	@BeforeTest
	public void setup() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.navigate().to("http://localhost:8080/skaiciuotuvas");
		driver.manage().window().maximize();	
		randomString = UUID.randomUUID().toString();	
	}
	
	@AfterTest
	public void close() {
		driver.quit();
	}
	
	@Ignore
	private void login(String username, String password, int type) {

		System.out.println("\nTESTO PRADZIA:");	
		
		//userField = driver.findElement(By.name("username"));
		userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		//passField = driver.findElement(By.name("password"));	
		passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));		
		//loginButton = driver.findElement(By.cssSelector("button[type=submit]"));
		loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=submit]")));
		
		userField.sendKeys(username);
		System.out.println("- Prisijungimo vardas ["+username+"] ivestas.");
		
		passField.sendKeys(password);
		System.out.println("- Slaptazodis ["+password+"] ivestas.");
		
		loginButton.click();
		System.out.println("- Mygtukas [Prisijungti] paspaustas.");

		if(type==1) {			
			//logoutForm= driver.findElement(By.cssSelector("#logoutForm"));
			logoutForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutForm")));	
			
			Assert.assertTrue(logoutForm.isDisplayed(), "[!] Vartotojas neprisijunge.");
			System.out.println("- Vartotojas prisijunge.");
			
			//driver.findElement(By.cssSelector("#logoutForm")).submit();
			logoutForm.submit();
		}
		else {
			//messageSpan = driver.findElement(By.xpath("/html/body/div/form/div/span[2]"));
			messageSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/form/div/span[2]")));
			
			String  mess = messageSpan.getText();
			System.out.println("- Gautas pranesimas:" + mess);

			message="Įvestas prisijungimo vardas ir/ arba slaptažodis yra neteisingi";
			Assert.assertEquals(mess, message, "[!] Pranesimai nesutampa.");
			System.out.println("- Pranesimai sutampa.");
		}
		System.out.println("TESTO PABAIGA.\n");		
	}
	
	
	
  @Test
  public void EmptyFields() {
	  login("","", 0);
  }
  
  @Test
  public void UserNameEmpty() {
	  login("",randomString, 0);
  }
  
  @Test
  public void PasswordEmpty() {
	  login(randomString,"", 0);
  }
  
  @Test
  public void WrongPassword() {
	  login("user",randomString, 0);
  }
  
  @Test
  public void UserNotExist() {
	  login(randomString,randomString, 0);
  } 
  
  @Test
  public void SqlInjection1() {
	  login("admin OR 1=1","admin OR 1=1", 0);
  } 
  
  @Test
  public void SqlInjection2() {
	  login("admin; DROP TABLE Users","admin; DROP TABLE Users", 0);
  } 
  
  @Test
  public void WrongFormat() {
	  login("-*//*\";'''{}","-*//*\\\";'''{}", 0);
  } 	
  
  @Test
  public void CorrectLogin() {
	  login("user","user",1);
  } 
		  
}