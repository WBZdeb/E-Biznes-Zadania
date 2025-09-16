import org.junit.jupiter.api.*
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import io.github.bonigarcia.wdm.WebDriverManager
import org.apache.hc.core5.util.Timeout
import java.time.Duration

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FrontendTests {

    private lateinit var driver: WebDriver
    private lateinit var wait: WebDriverWait

    @BeforeAll
    fun setup() {
        WebDriverManager.firefoxdriver().setup()
        driver = FirefoxDriver()
        wait = WebDriverWait(driver, Duration.ofSeconds(5))
    }

    @AfterAll
    fun teardown() {
        driver.quit()
    }

    // 0. Driver
    @Test
    fun openGoogle() {
        driver.get("https://www.google.com")
        Assertions.assertTrue(driver.title.contains("Google"))
    }

    // 1. Strona produktów ładuje się
    @Test
    fun testProductsPageLoad() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        Assertions.assertTrue(driver.findElement(By.tagName("h2")).text.contains("Produkty")) // 1
        Assertions.assertTrue(driver.findElement(By.xpath("//ul/li[1]//button[contains(text(), 'Dodaj do koszyka')]")).isDisplayed) // 2
        Assertions.assertTrue(driver.findElement(By.xpath("//ul/li[2]//button[contains(text(), 'Dodaj do koszyka')]")).isDisplayed) // 3
        Assertions.assertTrue(driver.findElement(By.xpath("//ul/li[3]//button[contains(text(), 'Dodaj do koszyka')]")).isDisplayed) // 4
    }

    // 2. Dodanie produktów do koszyka
    @Test
    fun testAddProductsToCart() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        driver.findElement(By.xpath("//li[1]/button")).click()
        driver.findElement(By.xpath("//li[2]/button")).click()
        driver.findElement(By.linkText("Koszyk")).click()
        val cartItems = driver.findElements(By.cssSelector("ul li"))
        Assertions.assertEquals(2, cartItems.size) // 5
    }

    // 3. Suma ceny produktow
    @Test
    fun testSumOfAddedProducts() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        var btn1 = driver.findElement(By.xpath("//li[1]/button"))
        var btn2 = driver.findElement(By.xpath("//li[2]/button"))
        var btn3 = driver.findElement(By.xpath("//li[3]/button"))
        var btn4 = driver.findElement(By.xpath("//li[4]/button"))

        btn1.click()
        btn2.click()
        btn2.click()
        btn3.click()
        btn4.click()
        btn4.click()

        driver.findElement(By.linkText("Koszyk")).click()
        val cartItems = driver.findElements(By.cssSelector("ul li"))
        Assertions.assertTrue(cartItems[0].text.contains("— ilość: 1")) //46
        Assertions.assertTrue(cartItems[1].text.contains("— ilość: 2")) //47
        Assertions.assertTrue(cartItems[2].text.contains("— ilość: 1")) //48
        Assertions.assertTrue(cartItems[3].text.contains("— ilość: 2")) //49

        driver.findElement(By.xpath("//button[text()='Wyślij koszyk na serwer / Przejdź do płatności']")).click()
        val totalLine = driver.findElement(By.xpath("//*[contains(text(),'Do zapłaty')]"))
        Assertions.assertTrue(totalLine.text.contains("81.90 zł")) //50
    }

    // 4. Nawigacja do Koszyka
    @Test
    fun testNavigateToCart() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        val btn1 = driver.findElement(By.xpath("//li[1]/button"))
        val btn2 = driver.findElement(By.xpath("//li[2]/button"))
        btn1.click()
        btn1.click()
        btn2.click()
        driver.findElement(By.linkText("Koszyk")).click()

        Assertions.assertTrue(driver.currentUrl.contains("/cart")) // 6
        val cartItems = driver.findElements(By.cssSelector("ul li"))
        Assertions.assertTrue(cartItems.size >= 2) // 7
        val firstQuantityText = cartItems[0].text
        Assertions.assertTrue(firstQuantityText.contains("— ilość: 2")) // 8

        val totalQuantity = cartItems.sumOf {
            val match = Regex("ilość: (\\d+)").find(it.text)
            match?.groupValues?.get(1)?.toInt() ?: 0
        }
        Assertions.assertEquals(3, totalQuantity) // 9
    }

    // 5. Usuwanie produktów z koszyka
    @Test
    fun testRemoveProductsFromCart() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        driver.findElement(By.xpath("//li[1]/button")).click()
        driver.findElement(By.xpath("//li[2]/button")).click()
        driver.findElement(By.linkText("Koszyk")).click()

        var cartItems = driver.findElements(By.cssSelector("ul > li"))
        cartItems[0].findElement(By.tagName("button")).click()
        cartItems = driver.findElements(By.cssSelector("ul > li"))
        cartItems[0].findElement(By.tagName("button")).click()

        val emptyCartMessage = driver.findElement(By.xpath("//div[contains(text(),'Twój koszyk jest pusty')]"))
        Assertions.assertTrue(emptyCartMessage.isDisplayed)
    }

    // 6. Płatności – wyświetlenie formularza
    @Test
    fun testPaymentsForm() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/payments")
        val header = driver.findElement(By.tagName("h2"))
        Assertions.assertEquals("Płatności", header.text) // 13

        val nameInput = driver.findElement(By.xpath("//label[contains(text(),'Imię i nazwisko')]/input"))
        val cardInput = driver.findElement(By.xpath("//label[contains(text(),'Numer karty')]/input"))
        Assertions.assertTrue(nameInput.isDisplayed) // 11
        Assertions.assertTrue(cardInput.isDisplayed) // 12

        val payButton = driver.findElement(By.xpath("//button[text()='Zapłać']"))
        Assertions.assertTrue(payButton.isDisplayed) // 13
    }

    // 7. Płatność pozytywna
    @Test
    fun testPaymentSuccess() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/payments")

        val nameInput = driver.findElement(By.xpath("//label[contains(text(),'Imię i nazwisko')]/input"))
        val cardInput = driver.findElement(By.xpath("//label[contains(text(),'Numer karty')]/input"))
        nameInput.sendKeys("Jan Kowalski")
        cardInput.sendKeys("1111-2222-3333-4444")

        val payButton = driver.findElement(By.xpath("//button[text()='Zapłać']"))
        payButton.click()

        val wait = WebDriverWait(driver, Duration.ofSeconds(5))
        val message = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'sukces')]")
            )
        )

        Assertions.assertTrue(message.isDisplayed)
    }

    // 8. Test przycisków Dodaj do koszyka
    @Test
    fun testAddToCartButtons() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        Assertions.assertTrue(driver.findElement(By.xpath("//li[1]/button[text()='Dodaj do koszyka']")).isDisplayed) //15
        Assertions.assertTrue(driver.findElement(By.xpath("//li[2]/button[text()='Dodaj do koszyka']")).isDisplayed) //16
        Assertions.assertTrue(driver.findElement(By.xpath("//li[3]/button[text()='Dodaj do koszyka']")).isDisplayed) //17
    }

    // 9. Test widoczności linków menu
    @Test
    fun testMenuLinks() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        Assertions.assertTrue(driver.findElement(By.linkText("Produkty")).isDisplayed) //18
        Assertions.assertTrue(driver.findElement(By.linkText("Koszyk")).isDisplayed) //19
        Assertions.assertTrue(driver.findElement(By.linkText("Płatności")).isDisplayed) //20
    }

    // 10. Sprawdzenie nagłówków produktów
    @Test
    fun testProductHeaders() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        Assertions.assertTrue(driver.findElement(By.xpath("//li[1]/strong")).isDisplayed) //21
        Assertions.assertTrue(driver.findElement(By.xpath("//li[2]/strong")).isDisplayed) //22
        Assertions.assertTrue(driver.findElement(By.xpath("//li[3]/strong")).isDisplayed) //23
    }

    // 11. Sprawdzenie cen produktów
    @Test
    fun testProductPrices() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        val firstProduct = driver.findElement(By.xpath("//ul/li[1]"))
        val secondProduct = driver.findElement(By.xpath("//ul/li[2]"))
        val thirdProduct = driver.findElement(By.xpath("//ul/li[3]"))

        Assertions.assertTrue(firstProduct.text.contains("zł")) //25
        Assertions.assertTrue(secondProduct.text.contains("zł")) //26
        Assertions.assertTrue(thirdProduct.text.contains("zł")) //27
    }

    // 12. Sprawdzenie przycisku Zapłać
    @Test
    fun testPayButton() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        driver.findElement(By.xpath("//li[1]/button")).click()
        driver.findElement(By.linkText("Koszyk")).click()
        Assertions.assertTrue(driver.findElement(By.xpath("//button[text()='Wyślij koszyk na serwer / Przejdź do płatności']")).isDisplayed) //27
    }

    // 13. Puste koszyki – komunikaty
    @Test
    fun testEmptyCartMessage() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/cart")
        Assertions.assertTrue(driver.findElement(By.xpath("//div[text()='Twój koszyk jest pusty']")).isDisplayed) //28
    }

    // 14. Nawigacja wstecz
    @Test
    fun testBackNavigation() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/payments")
        driver.navigate().back()
        Assertions.assertTrue(driver.currentUrl.contains("/cart") || driver.currentUrl.contains("/")) // 29
    }

    // 15. Rozmiar okna i nagłówki
    @Test
    fun testWindowSize() {
        Thread.sleep(200)
        driver.manage().window().setSize(org.openqa.selenium.Dimension(1024, 768))
        driver.get("http://localhost:5173/")
        Assertions.assertTrue(driver.findElement(By.tagName("h2")).isDisplayed) //30
    }

    // 16. Dodanie i usunięcie produktu
    @Test
    fun testAddAndRemoveProduct() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        driver.findElement(By.xpath("//li[1]/button[text()='Dodaj do koszyka']")).click()
        driver.findElement(By.linkText("Koszyk")).click()
        driver.findElement(By.xpath("//li[1]/button[text()='Usuń']")).click()
        Assertions.assertTrue(driver.findElement(By.xpath("//div[text()='Twój koszyk jest pusty']")).isDisplayed) //31
    }

    // 17. Sprawdzenie nagłówków koszyka
    @Test
    fun testCartHeaders() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/cart")
        Assertions.assertTrue(driver.findElement(By.tagName("h2")).text.contains("Koszyk")) //32
    }

    // 18. Test pozycji elementów w koszyku
    @Test
    fun testCartItemPositions() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        driver.findElement(By.xpath("//li[4]/button")).click()
        driver.findElement(By.xpath("//li[2]/button")).click()
        driver.findElement(By.linkText("Koszyk")).click()

        val firstItem = driver.findElement(By.xpath("//li[1]"))
        Assertions.assertTrue(firstItem.isDisplayed) //33
        Assertions.assertTrue(firstItem.text.contains("Produkt ID 4")) //34
    }

    // 19. Sprawdzenie nagłówka płatności
    @Test
    fun testPaymentsHeader() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/payments")
        Assertions.assertTrue(driver.findElement(By.tagName("h2")).text.contains("Płatności")) //35
    }

    // 20. Test calego procesu
    @Test
    fun testMainProcess() {
        Thread.sleep(200)
        driver.get("http://localhost:5173/")
        Assertions.assertTrue(driver.findElement(By.xpath("//li[1]/button[text()='Dodaj do koszyka']")).isDisplayed) //36
        Assertions.assertTrue(driver.findElement(By.xpath("//li[2]/button[text()='Dodaj do koszyka']")).isDisplayed) //37
        Assertions.assertTrue(driver.findElement(By.xpath("//li[3]/button[text()='Dodaj do koszyka']")).isDisplayed) //38
        Assertions.assertTrue(driver.findElement(By.xpath("//li[4]/button[text()='Dodaj do koszyka']")).isDisplayed) //39
        driver.findElement(By.xpath("//li[2]/button")).click()

        driver.findElement(By.linkText("Koszyk")).click()
        Assertions.assertTrue(driver.findElement(By.xpath("//button[text()='Wyślij koszyk na serwer / Przejdź do płatności']")).isDisplayed) //40

        driver.findElement(By.xpath("//button[text()='Wyślij koszyk na serwer / Przejdź do płatności']")).click()
        Assertions.assertTrue(driver.findElement(By.xpath("//button[text()='Zapłać']")).isDisplayed) //41
        val totalLine = driver.findElement(By.xpath("//*[contains(text(),'Do zapłaty')]"))
        Assertions.assertTrue(totalLine.isDisplayed) // 42
        Assertions.assertTrue(totalLine.text.contains("8.20 zł")) // 43

        val payButton = driver.findElement(By.xpath("//button[text()='Zapłać']"))
        payButton.click()
        val wait = WebDriverWait(driver, Duration.ofSeconds(5))
        val message = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'sukces')]")
            )
        )

        Assertions.assertTrue(message.isDisplayed) // 44
        driver.findElement(By.linkText("Koszyk")).click()
        Assertions.assertTrue(driver.findElement(By.xpath("//div[text()='Twój koszyk jest pusty']")).isDisplayed) //45


    }
}
