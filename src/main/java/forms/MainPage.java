package forms;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MainPage extends Form {
    public MainPage() {
        super(By.xpath("//li[contains(@class, 'HeaderNav__item')]"), "Profile navigation menu");
    }

    private final ILink linkMyPage =
            getElementFactory().getLink(By.xpath("//li[@id='l_pr']"), "Link My Page");

    public void clickLinkMyPage() {
        linkMyPage.click();
    }
}