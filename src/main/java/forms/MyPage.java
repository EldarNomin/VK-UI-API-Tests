package forms;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import models.Entry;
import models.SavedPhoto;
import org.openqa.selenium.By;
import utils.UserDataValue;

public class MyPage extends Form {

    private final ISettingsFile userData = new JsonSettingsFile("testData.json");
    private static final String ENTRY_XPATH = "//div[@id='wpt%s_%s']";
    private static final String ENTRY_PHOTO_XPATH = "//a[contains(@href, '/photo%s_%s')]";
    private static final String LIKE_BUTTON_XPATH = "//div[contains(@class,'PostBottomActionContainer PostButtonReactionsContainer')]";
    private static final String LIKE_XPATH = "//div[@class='like_wrap _like_wall%s_%s ']%s";

    public MyPage() {
        super(By.id("profile"), "Profile form");
    }

    private ITextBox getEntry(Entry entry) {
        return getElementFactory().getTextBox(By.xpath(String.format(ENTRY_XPATH, userData.getValue(UserDataValue.ID.getValue()), entry.getPostId())), "Needed entry");
    }

    public boolean isEntryExist(Entry entry) {
        return getEntry(entry).state().waitForDisplayed();
    }

    public String getTextEntry(Entry entry) {
        return getEntry(entry).getText();
    }

    private IButton getPhoto(Entry entry, SavedPhoto savedPhoto) {
        return getElementFactory().getButton(By.xpath(String.format(
                ENTRY_XPATH, userData.getValue(UserDataValue.ID.getValue()), entry.getPostId(),
                String.format(ENTRY_PHOTO_XPATH, userData.getValue(UserDataValue.ID.getValue()),
                        savedPhoto.getId()))), "Needed entry");
    }

    public boolean checkPhoto(Entry entry, SavedPhoto savedPhoto) {
        return getPhoto(entry, savedPhoto).state().isClickable();
    }

    private IButton getLikeButton(Entry entry) {
        return getElementFactory().getButton(By.xpath(String.format(
                LIKE_XPATH, userData.getValue(UserDataValue.ID.getValue()), entry.getPostId(), LIKE_BUTTON_XPATH)), "Like button");
    }

    private IButton getComment(Entry entry) {
        return getElementFactory().getButton(By.xpath(String.format(
                ENTRY_XPATH, userData.getValue(UserDataValue.ID.getValue()), entry.getPostId())), "Needed comment");
    }

    public String getCommentText(Entry entry) {
        return getComment(entry).getText();
    }

    public void likeTheEntry(Entry entry) {
        getLikeButton(entry).click();
    }

    public boolean isEntryDeleted(Entry entry) {
        getEntry(entry).state().waitForNotDisplayed();
        return getEntry(entry).state().isDisplayed();
    }
}