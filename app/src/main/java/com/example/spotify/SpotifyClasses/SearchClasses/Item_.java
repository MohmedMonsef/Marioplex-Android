
package com.example.spotify.SpotifyClasses.SearchClasses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item_ {

    @SerializedName("album")
    @Expose
    private Album album;
    @SerializedName("artists")
    @Expose
    private List<Artist_> artists = null;
    @SerializedName("disc_number")
    @Expose
    private int discNumber;
    @SerializedName("duration_ms")
    @Expose
    private int durationMs;
    @SerializedName("explicit")
    @Expose
    private boolean explicit;
    @SerializedName("external_ids")
    @Expose
    private ExternalIds externalIds;
    @SerializedName("external_urls")
    @Expose
    private ExternalUrls____ externalUrls;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("is_local")
    @Expose
    private boolean isLocal;
    @SerializedName("is_playable")
    @Expose
    private boolean isPlayable;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private int popularity;
    @SerializedName("preview_url")
    @Expose
    private String previewUrl;
    @SerializedName("track_number")
    @Expose
    private int trackNumber;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("uri")
    @Expose
    private String uri;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist_> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist_> artists) {
        this.artists = artists;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public ExternalIds getExternalIds() {
        return externalIds;
    }

    public void setExternalIds(ExternalIds externalIds) {
        this.externalIds = externalIds;
    }

    public ExternalUrls____ getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrls____ externalUrls) {
        this.externalUrls = externalUrls;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public boolean isIsPlayable() {
        return isPlayable;
    }

    public void setIsPlayable(boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
