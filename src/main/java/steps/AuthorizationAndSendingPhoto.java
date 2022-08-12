package steps;

import forms.WelcomePage;
import models.SavedPhoto;
import models.UploadLink;
import models.UploadedPhoto;
import utils.VkApiUtils;

import java.util.List;

public class AuthorizationAndSendingPhoto {

    private final WelcomePage welcomePage = new WelcomePage();

    public void authorization(String email, String password) {
        welcomePage.setEmail(email);
        welcomePage.clickBtnSignIn();
        welcomePage.setPassword(password);
        welcomePage.clickBtnContinue();
    }

    public List<SavedPhoto> pictureSending(String photoPath) {
        UploadLink uploadLink = VkApiUtils.getUploadLink();
        UploadedPhoto uploadedPhoto = VkApiUtils.uploadPhoto(uploadLink, photoPath);
        return VkApiUtils.savePhoto(uploadedPhoto);
    }
}