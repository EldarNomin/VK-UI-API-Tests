package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.http.ContentType;
import models.*;
import org.apache.hc.core5.http.HttpStatus;
import java.io.File;
import java.util.List;
import static io.restassured.RestAssured.given;

public class VkApiUtils {
    private static final ISettingsFile USER_DATA = new JsonSettingsFile("testData.json");
    private static final String PATTERN = "%s%s_%s";

    public static Entry postEntry(String message) {
        return given()
                .queryParam(RequestOptions.MESSAGE.getValue(), message)
                .when().post(Endpoints.POST.getValue())
                .then().statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getObject(RequestOptions.RESPONSE.getValue(), Entry.class);
    }

    public static UploadedPhoto uploadPhoto(UploadLink uploadLink, String pathToPhoto) {
        return given()
                .contentType(ContentType.MULTIPART)
                .multiPart(new File(pathToPhoto))
                .when().post(uploadLink.getUploadUrl())
                .then().statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getObject(RequestOptions.NOTHING.getValue(), UploadedPhoto.class);
    }

    public static UploadLink getUploadLink() {
        return given()
                .queryParam(RequestOptions.USER_ID.getValue(), USER_DATA.getValue(UserDataValue.ID.getValue()))
                .when().post(Endpoints.UPLOAD_PHOTO.getValue())
                .then().statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getObject(RequestOptions.RESPONSE.getValue(), UploadLink.class);
    }

    public static List<SavedPhoto> savePhoto(UploadedPhoto uploadedPhoto) {
        return given()
                .queryParam(RequestOptions.ITEM_ID.getValue(), USER_DATA.getValue(UserDataValue.ID.getValue()))
                .queryParam(RequestOptions.PHOTO.getValue(), uploadedPhoto.getPhoto())
                .queryParam(RequestOptions.SERVER.getValue(), String.valueOf(uploadedPhoto.getServer()))
                .queryParam(RequestOptions.HASH.getValue(), uploadedPhoto.getHash())
                .when().post(Endpoints.SAVE_PHOTO.getValue())
                .then().statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getList(RequestOptions.RESPONSE.getValue(), SavedPhoto.class);
    }

    public static Entry updateEntry(Entry postedEntry, String message, SavedPhoto savedPhoto) {
        return given()
                .queryParam(RequestOptions.OWNER_ID.getValue(), USER_DATA.getValue(UserDataValue.ID.getValue()))
                .queryParam(RequestOptions.POST_ID.getValue(), postedEntry.getPostId())
                .queryParam(RequestOptions.MESSAGE.getValue(), message)
                .queryParam(RequestOptions.ATTACHMENTS.getValue(),
                        String.format(PATTERN, RequestOptions.PHOTO.getValue(), savedPhoto.getOwnerId(), savedPhoto.getId()))
                .when().post(Endpoints.EDIT.getValue())
                .then().statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getObject(RequestOptions.RESPONSE.getValue(), Entry.class);
    }

    public static Comment createComment(Entry entry, String message) {
        return given().
                queryParam(RequestOptions.OWNER_ID.getValue(), USER_DATA.getValue(UserDataValue.ID.getValue())).
                queryParam(RequestOptions.POST_ID.getValue(), entry.getPostId()).
                queryParam(RequestOptions.MESSAGE.getValue(), message).
                when().post(Endpoints.ADD_COMMENT.getValue()).
                then().statusCode(HttpStatus.SC_OK).
                extract().jsonPath().getObject(RequestOptions.RESPONSE.getValue(), Comment.class);
    }

    public static Like isLiked(Entry entry) {
        return given()
                .queryParam(RequestOptions.USER_ID.getValue(), USER_DATA.getValue(UserDataValue.ID.getValue()))
                .queryParam(RequestOptions.TYPE.getValue(), RequestOptions.POST.getValue())
                .queryParam(RequestOptions.OWNER_ID.getValue(), USER_DATA.getValue(UserDataValue.ID.getValue()))
                .queryParam(RequestOptions.ITEM_ID.getValue(), entry.getPostId())
                .when().post(Endpoints.IS_LIKED.getValue())
                .then().statusCode(HttpStatus.SC_OK)
                .extract().jsonPath().getObject(RequestOptions.RESPONSE.getValue(), Like.class);
    }

    public static DeletedEntry deleteEntry(Entry entry) {
        return given()
                .queryParam(RequestOptions.OWNER_ID.getValue(), USER_DATA.getValue(UserDataValue.ID.getValue()))
                .queryParam(RequestOptions.POST_ID.getValue(), entry.getPostId())
                .when().post(Endpoints.DELETE.getValue())
                .then().statusCode(HttpStatus.SC_OK)
                .extract().as(DeletedEntry.class);
    }
}