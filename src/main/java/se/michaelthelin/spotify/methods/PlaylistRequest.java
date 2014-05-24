package se.michaelthelin.spotify.methods;

import com.google.common.util.concurrent.SettableFuture;
import net.sf.json.JSONObject;
import se.michaelthelin.spotify.JsonUtil;
import se.michaelthelin.spotify.exceptions.WebApiException;
import se.michaelthelin.spotify.models.Page;
import se.michaelthelin.spotify.models.Playlist;
import se.michaelthelin.spotify.models.PlaylistTrack;

import java.io.IOException;

public class PlaylistRequest extends AbstractRequest {

  public PlaylistRequest(Builder builder) {
    super(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public SettableFuture<Playlist> getAsync() {
    SettableFuture<Playlist> playlistFuture = SettableFuture.create();

    try {
      final String jsonString = getJson();
      final JSONObject jsonObject = JSONObject.fromObject(jsonString);

      throwIfErrorsInResponse(jsonObject);

      playlistFuture.set(JsonUtil.createPlaylist(jsonObject));
    } catch (Exception e) {
      playlistFuture.setException(e);
    }

    return playlistFuture;
  }

  public Playlist get() throws IOException, WebApiException {
    final String jsonString = getJson();
    final JSONObject jsonObject = JSONObject.fromObject(jsonString);

    throwIfErrorsInResponse(jsonObject);

    return JsonUtil.createPlaylist(jsonObject);
  }

  public static final class Builder extends AbstractRequest.Builder<Builder> {

    String username = "";
    String playlist = "";

    public Builder fields(String fields) {
      assert (fields != null);
      return parameter("fields", fields);
    }

    public Builder username(String username) {
      assert (username != null);
      this.username = username;
      return this;
    }

    public Builder playlist(String playlist) {
      assert (playlist != null);
      this.playlist = playlist;
      return this;
    }

    public Builder accessToken(String accessToken) {
      return header("Authorization", "Bearer " + accessToken);
    }

    public PlaylistRequest build() {
      path("/v1/users/" + username + "/playlists/" + playlist);
      return new PlaylistRequest(this);
    }

  }

}