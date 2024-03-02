package se.michaelthelin.spotify.requests.authentication;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import se.michaelthelin.spotify.model_objects.credentials.error.AuthenticationError;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationErrorJsonUtilTest {
  private final AuthenticationError.JsonUtil jsonUtil = new AuthenticationError.JsonUtil();

  @Test
  void testCreateModelObjectWithNullJsonObject() {
    AuthenticationError result = jsonUtil.createModelObject((JsonObject) null);

    assertNull(result, "Creating an AuthenticationError with a null JsonObject should return null.");
  }

  @Test
  void testCreateModelObjectWithJsonNull() {
    JsonObject jsonObject = new JsonObject(); // JsonObject without properties will be interpreted as JSON null
    jsonObject.add("error", JsonNull.INSTANCE); // Explicitly set JSON null
    jsonObject.add("error_description", JsonNull.INSTANCE); // Explicitly set JSON null

    AuthenticationError result = jsonUtil.createModelObject(jsonObject);

    assertNull(result.getError(), "Creating an AuthenticationError with a JSON null 'error' should have null 'error'.");
    assertNull(result.getError_description(), "Creating an AuthenticationError with a JSON null 'error_description' should have null 'error_description'.");
  }

  @Test
  void testCreateModelObjectWithMissingProperties() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("error", "invalid_request");

    AuthenticationError result = jsonUtil.createModelObject(jsonObject);

    assertNotNull(result, "AuthenticationError should not be null when JsonObject has properties.");
    assertEquals("invalid_request", result.getError(), "Error should match the value in the JsonObject.");
    assertNull(result.getError_description(), "Error description should be null when not provided in the JsonObject.");
  }

  @Test
  void testCreateModelObjectWithAllProperties() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("error", "invalid_request");
    jsonObject.addProperty("error_description", "Missing required parameter.");

    AuthenticationError result = jsonUtil.createModelObject(jsonObject);

    assertNotNull(result, "AuthenticationError should not be null when JsonObject has all properties.");
    assertEquals("invalid_request", result.getError(), "Error should match the value in the JsonObject.");
    assertEquals("Missing required parameter.", result.getError_description(), "Error description should match the value in the JsonObject.");
  }

}
