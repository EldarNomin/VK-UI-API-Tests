package tests;

import aquality.selenium.core.utilities.ISettingsFile;
import forms.MainPage;
import forms.MyPage;
import forms.WelcomePage;
import models.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import aquality.selenium.core.utilities.JsonSettingsFile;
import steps.AuthorizationAndSendingPhoto;
import utils.VkApiUtils;
import net.bytebuddy.utility.RandomString;
import java.util.List;

public class TestVkAPI extends BaseTest {
    private final WelcomePage welcomePage = new WelcomePage();
    private final MainPage mainPage = new MainPage();
    private final MyPage myPage = new MyPage();
    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    private static final String PASSWORD = TEST_DATA.getValue("/password").toString();
    private static final String EMAIL = TEST_DATA.getValue("/email").toString();
    private static final String PHOTO_PATH = TEST_DATA.getValue("/photo").toString();
    private final AuthorizationAndSendingPhoto steps = new AuthorizationAndSendingPhoto();
    private static int RANDOM_STRING_LENGTH = 10;
    private static int LIKE_PRESSED = 1;

    @Test
    public void testVkAPI() {
        Assert.assertTrue(welcomePage.state().waitForDisplayed(),
                "Welcome page is not displayed");

        log.info("Authorization");
        steps.authorization(EMAIL, PASSWORD);
        Assert.assertTrue(mainPage.state().waitForDisplayed(), "Main page is not displayed");

        log.info("Go to 'My Page");
        mainPage.clickLinkMyPage();
        Assert.assertTrue(myPage.state().waitForDisplayed(), "My page is not displayed");

        log.info("Create a post with randomly generated text on the wall and get the post id from the response");
        String message = RandomString.make(RANDOM_STRING_LENGTH);
        Entry postedEntry = VkApiUtils.postEntry(message);

        Assert.assertTrue(myPage.isEntryExist(postedEntry), "Entry is not exists");
        Assert.assertEquals(myPage.getTextEntry(postedEntry), message, "Text is not correct");

        log.info("Edit a post with API request - change the text and add (upload) any picture");
        List<SavedPhoto> savedPhoto = steps.pictureSending(PHOTO_PATH);
        String newMessage = RandomString.make(RANDOM_STRING_LENGTH);
        Entry updatedEntry = VkApiUtils.updateEntry(postedEntry, newMessage, savedPhoto.get(0));

        log.info("Make sure that the text of the message has changed and the picture is downloaded\n" +
              "(make sure the pictures are the same)");
        Assert.assertEquals(myPage.getTextEntry(updatedEntry), newMessage, "Text is not correct");
        Assert.assertTrue(myPage.checkPhoto(updatedEntry, savedPhoto.get(0)), "Photo has been added");

        log.info("Using an API request to add a comment to the post with random text");
        String randomMessage = RandomString.make(RANDOM_STRING_LENGTH);
        Comment comment = VkApiUtils.createComment(updatedEntry, randomMessage);

        log.info("Make sure that a comment is added to the desired entry");
        Assert.assertNotEquals(myPage.getCommentText(updatedEntry), randomMessage, "Comment is not added");

        log.info("Like the post using UI");
        myPage.likeTheEntry(postedEntry);

        log.info("Check for like using API");
        Like like = VkApiUtils.isLiked(postedEntry);
        Assert.assertEquals(like.getLiked(), LIKE_PRESSED, "Entry is not liked");

        log.info("Delete created entry using API");
        DeletedEntry deleteEntry = VkApiUtils.deleteEntry(postedEntry);

        log.info("Check that entry has been deleted using UI");
        Assert.assertFalse(myPage.isEntryDeleted(updatedEntry), "Entry is not deleted");
    }
}