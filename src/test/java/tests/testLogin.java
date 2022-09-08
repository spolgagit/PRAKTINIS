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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
	
	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.navigate().to("http://localhost:8080/skaiciuotuvas");
		driver.manage().window().maximize();	
		randomString = UUID.randomUUID().toString();	
	}
	
	@AfterMethod
	public void close() {
		driver.quit();
	}
	
	@Ignore
	private void login(String username, String password, int type) {

		System.out.println("\nTESTO PRADZIA:");	
		
		userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));		
		loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=submit]")));
		
		userField.sendKeys(username);
		System.out.println("- Prisijungimo vardas ["+username+"] ivestas.");
		
		passField.sendKeys(password);
		System.out.println("- Slaptazodis ["+password+"] ivestas.");
		
		loginButton.click();
		System.out.println("- Mygtukas [Prisijungti] paspaustas.");

		if(type==1) {//teisingam prisijungimui		
			logoutForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutForm")));	
			
			Assert.assertTrue(logoutForm.isDisplayed(), "[!] Vartotojas neprisijunge.");
			System.out.println("- Vartotojas prisijunge.");
			
			logoutForm.submit();
			
		} else { //klaidingam prisijungimui

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
  public void EmptyFields() { //Ivedai tusti prisijungimo laukai
	  login("","", 0);
  }
  
  @Test
  public void UserNameEmpty() { //Paliekamas prisijungimo vardas tuscias
	  login("",randomString, 0);
  }
  
  @Test
  public void PasswordEmpty() { //Paliekamas slaptazodis tuscias
	  login(randomString,"", 0);
  }
  
  @Test
  public void WrongPassword() { //Ivedamas klaidingas slaptazodis (egzistuojancio vartotojo)
	  login("user",randomString, 0);
  }
  
  @Test
  public void UserNotExist() { //Ivedami neegzistuojancio vartotojo duomenys
	  login(randomString,randomString, 0);
  } 
  
  
  @Test
  public void SqlInjection1() { //Nenaudojama 
	  login("admin OR 1=1","admin OR 1=1", 0);
  } 
  
  @Test
  public void SqlInjection2() { //Nenaudojama
	  login("admin; DROP TABLE Users","admin; DROP TABLE Users", 0);
  }
  
  @Test
  public void WrongFormat() { //Ivedami blogi simboliai
	  login("-*//*\";'''{}","-*//*\\\";'''{}", 0);
  } 	
  
  @Test
  public void CorrectLogin() { //Ivedami teisingi duomenys
	  login("user","user",1);
  } 
		  
}