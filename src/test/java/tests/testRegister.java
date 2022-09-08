package tests;

import java.time.Duration;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
//import org.testng.Assert;
//import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class testRegister {
	
	WebDriver driver;
	WebDriverWait wait;	
	WebElement registerButton;
	WebElement logoutForm, registerForm;
	WebElement userField;
	WebElement passField;
	WebElement passConfirmField;
	WebElement messageUser, messagePass, messagePassConfirm;
	String randomString;
	/*String[] message = {
			"Šį laukelį būtina užpildyti",
			"Šį laukelį būtina užpildyti",
			"Privaloma įvesti nuo 3 iki 32 simbolių",
			"Privaloma įvesti bent 3 simbolius",
			"Įvesti slaptažodžiai nesutampa",
			"Toks vartotojo vardas jau egzistuoja"
			};*/
	
	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.navigate().to("http://localhost:8080/registruoti");
		driver.manage().window().maximize();	
		randomString = StringUtils.left(UUID.randomUUID().toString(), 31);
	}
	
	@AfterMethod
	public void close() {
		driver.quit();
	}
	
	
	@Ignore
	private void register(String username, String password, String password2, int type) {
		//userField.clear();
		//passField.clear();
		//passConfirmField.clear();

		System.out.println("\nTESTO PRADZIA:");	
		
		userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));		
		passConfirmField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("passwordConfirm")));				

		registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=submit]")));
		
		userField.sendKeys(username);
		System.out.println("- Prisijungimo vardas ["+username+"] ivestas.");
		
		passField.sendKeys(password);
		System.out.println("- Slaptazodis ["+password+"] ivestas.");
		
		passConfirmField.sendKeys(password2);
		System.out.println("- Pakartotinis ["+password2+"] ivestas.");

		registerButton.click();
		System.out.println("- Mygtukas [Prisijungti] paspaustas.");
		
	
		if(type==1) {	
			logoutForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutForm")));
			Assert.assertTrue(logoutForm.isDisplayed(), "[!] Vartotojas neprisijunge.");
			System.out.println("- Vartotojas prisijunge.");			
			logoutForm.submit();
		
		} else {		
			registerForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userForm")));
			Assert.assertTrue(registerForm.isDisplayed(), "[!] Vartotojas prisijunge.");
			System.out.println("- Vartotojas neprisijunge.");	
		}
		
		System.out.println("TESTO PABAIGA.\n");		
	}
	

	  @Test
	  public void RegisterEmptyFields() { //Ivedami visi tusti laukai
		register("","","",0);
	  }	
	
	
  @Test
  public void RegisterEmptyUser() { //neivedamas vartotojo vardas
	register("",randomString,randomString,0);
  }
  
  @Test
  public void RegisterPassDifferent() { //ivedami skirtingi slaptazodziai
	  register(randomString,"sdffds",randomString,0);
  }
  
  @Test
  public void RegisterSqlInjection1() { //nenaudojama
	  register("admin OR 1=1","admin OR 1=1","admin OR 1=1", 0);
  } 
  
  @Test
  public void RegisterSqlInjection2() { //nenaudojama
	  register("admin; DROP TABLE Users","admin; DROP TABLE Users","admin; DROP TABLE Users", 0);
  }
  
  @Test
  public void RegisterUserExist() { //registruojtas vartotojas
	  register("user",randomString,randomString,0);
  }
  
  @Test
  public void RegisterOk() { //sekminga registracija
	  register(randomString,randomString,randomString,1);
  }
  
  
}
  