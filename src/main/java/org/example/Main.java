package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] productos = {
                "Samsung galaxy s6",
                "Nokia lumia 1520",
                "Nexus 6"
        };

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.demoblaze.com");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try{
            for (String producto : productos) {
                seleccionarElemento(producto, wait);
                irACarrito(wait,driver);
                System.out.println(producto + " se añadió al carrito exitosamente.");
                wait.until(ExpectedConditions.presenceOfElementLocated( By.id("cartur") )).click();
                validarCompra(producto, wait);
                wait.until( ExpectedConditions.elementToBeClickable( By.xpath("//a[@href='index.html']") ) ).click();
            }
        }
        finally{
            driver.quit();
        }
    }

    private static void seleccionarElemento(String producto, WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable( By.linkText(producto) )).click();
        System.out.println(producto + " se seleccionó correctamente.");
    }

    private static void validarCompra(String producto, WebDriverWait wait) {
        if ( wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='"+producto+"']"))) != null ) {
            System.out.println(producto + " sí existe en el carrito");
        } else {
            System.out.println(producto + " no se existe en el carrito");
        }
    }

    public static void irACarrito(WebDriverWait wait, WebDriver driver) {
        wait.until(ExpectedConditions.presenceOfElementLocated( By.className("btn-success") )).click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }
}

// poner un until para esperar que cargue la página

// ID del contenedor general = "tbodyid", esperar a este
// CLASE del contenedor individual = "col-lg-4 col-md-6 mb-4"
// CLASE del elemento con el nombre del producto = "card-title", desde aqui se puede hacer click

// al hacer click te envía a otra página así que hay que poner un until

// esperar "tbodyid" otra vez
// CLASE del botón de añadir al carrito = "btn btn-success btn-lg"
// se puede buscar por tipo de elemento (a) pero hay varios asi q no creo
// se puede buscar con expresion XPATH("//button[contains(@onClick, 'addToCart()')]")

// al hacer click en añadir al carrito sale una advertencia, sepa como se quita eso

// para ir al carrito hay que ver el nav de la página
// ID del botón para ir al carrito = "cartur"
// el ID del carrito no está en todas las vistas pero solo necesita estar en las
//

// una vez en la vista del carrito, esperar a "tbodyid" (otra vez)
// el problema es que ese existe aunque no hayan cosas en el carrito, seria esperar un elemento dentro de este
// dentro de tbodyid hay varios tr con clase "success" pero no sé cómo separarlos, tal vez como arreglo
// para solo verificar sería buscar el td con el nombre, tambien para eliminar ese y el del boton
// ir a hogar con XPATH( "//a[@href='index.html']" )

// supongo que el cuarto caso se refiere a añadir un producto, ir al carrito y refrescar esa página, no entendí bien