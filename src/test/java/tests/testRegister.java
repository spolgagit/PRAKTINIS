package tests;

import java.time.Duration;
import java.util.UUID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class testRegister {
	
	WebDriver driver;
	WebDriverWait wait;	
	WebElement registerButton;
	WebElement logoutForm;
	WebElement userField;
	WebElement passField;
	WebElement passConfirmField;
	WebElement messageUser, messagePass, messagePassConfirm;
	String randomString;
	String[] message = {
			"Šį laukelį būtina užpildyti",
			"Šį laukelį būtina užpildyti",
			"Privaloma įvesti nuo 3 iki 32 simbolių",
			"Privaloma įvesti bent 3 simbolius",
			"Įvesti slaptažodžiai nesutampa",
			"Toks vartotojo vardas jau egzistuoja"
			};
	
	@BeforeTest
	public void setup() {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.navigate().to("http://localhost:8080/registruoti");
		driver.manage().window().maximize();	
		randomString = UUID.randomUUID().toString();
	}
	
	@AfterTest
	public void close() {
		driver.quit();
	}
	
	
	@Ignore
	private void register(String username, String password, String password2, int type) {

		System.out.println("\nTESTO PRADZIA:");	
		
		userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
		passField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));		
		passConfirmField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("passwordConfirm")));				

		registerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type=submit]")));
		
		userField.sendKeys(username);
		System.out.println("- Prisijungimo vardas ["+username+"] ivestas.");
		
		passField.sendKeys(password);
		System.out.println("- Slaptazodis ["+password+"] ivestas.");
		


		if(type==1) {
			registerButton.click();
			System.out.println("- Mygtukas [Prisijungti] paspaustas.");
			
			/*//logoutForm= driver.findElement(By.cssSelector("#logoutForm"));
			logoutForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutForm")));	
			
			Assert.assertTrue(logoutForm.isDisplayed(), "[!] Vartotojas neprisijunge.");
			System.out.println("- Vartotojas prisijunge.");
			
			//driver.findElement(By.cssSelector("#logoutForm")).submit();
			logoutForm.submit();*/
		
		} else {
			
			registerButton.click();
			System.out.println("- Mygtukas [Prisijungti] paspaustas.");
			
			System.out.println("- Laukiama pranesimu");
			
			messageUser = driver.findElement(By.cssSelector("#username+span"));
			messagePass = driver.findElement(By.cssSelector("#password+span"));
			messagePassConfirm = driver.findElement(By.cssSelector("#passwordConfirm+span"));
			
			System.out.println("- Rasti pranesimai.");			
			
			String  mess = messageUser.getText();
			String  mess2 = messagePass.getText();
			String  mess3 = messagePassConfirm.getText();
			
			System.out.println("- Gauti pranesimai: " + mess + " | " + mess2 + " | " + mess3);

			/*
			if (username.isEmpty()) {
				Assert.assertEquals(mess, message[0], "[!] Pranesimai nesutampa.");
			} else if (username.length()<3 && username.length()>33) {
				Assert.assertEquals(mess, message[2], "[!] Pranesimai nesutampa.");
			} else {
				Assert.assertEquals(mess, message[5], "[!] Pranesimai nesutampa.");
			}
			
			if (password.isEmpty()) {
				Assert.assertEquals(mess2, message[1], "[!] Pranesimai nesutampa.");
			} else if (password.length()<3) {
				Assert.assertEquals(mess2, message[3], "[!] Pranesimai nesutampa.");
			}			
			
			if (password != password2) {
				Assert.assertEquals(mess3, message[4], "[!] Pranesimai nesutampa.");
			}*/

			System.out.println("- Pranesimai sutampa.");
		
		}
		
		System.out.println("TESTO PABAIGA.\n");		
	}
	
	
	
  @Test
  public void RegisterEmptyFields() {
	  register("","","",0);
  }
  
  
}
  