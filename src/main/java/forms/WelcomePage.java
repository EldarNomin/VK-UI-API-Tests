package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class WelcomePage extends Form {
    public WelcomePage() {
        super(By.xpath("//span[@class='FlatButton__in']"), "Authorization button");
    }

    private final ITextBox textBoxEmail = getElementFactory().getTextBox(By.id("index_email"), "Email text box");
    private final ITextBox textBoxPassword = getElementFactory().getTextBox(By.name("password"), "Password text box");
    private final IButton buttonSignIn = getElementFactory().getButton(By.xpath("//*[@id='index_login']/div/form/button/span"), "Sign in button");
    private final IButton buttonSubmit = getElementFactory().getButton(By.xpath("//div[contains(@class, 'Button')]"), "Continue");

    public void setEmail(String email) {
        textBoxEmail.clearAndType(email);
    }

    public void setPassword(String password) {
        textBoxPassword.clearAndType(password);
    }

    public void clickBtnSignIn() {
        buttonSignIn.clickAndWait();
    }

    public void clickBtnContinue() {
        buttonSubmit.clickAndWait();
    }
}