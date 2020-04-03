package com.example.spotify.SpotifyClasses;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album {

    @SerializedName("album_type")
    @Expose
    private String albumType;
    @SerializedName("artists")
    @Expose
    private List<Artist> artists = null;
    @SerializedName("available_markets")
    @Expose
    private List<String> availableMarkets = null;
    @SerializedName("external_urls")
    @Expose
    private ExternalUrls_ externalUrls;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("release_date_precision")
    @Expose
    private String releaseDatePrecision;
    @SerializedName("total_tracks")
    @Expose
    private Integer totalTracks;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("uri")
    @Expose
    private String uri;

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public Album withAlbumType(String albumType) {
        this.albumType = albumType;
        return this;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public Album withArtists(List<Artist> artists) {
        this.artists = artists;
        return this;
    }

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public Album withAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
        return this;
    }

    public ExternalUrls_ getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(ExternalUrls_ externalUrls) {
        this.externalUrls = externalUrls;
    }

    public Album withExternalUrls(ExternalUrls_ externalUrls) {
        this.externalUrls = externalUrls;
        return this;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Album withHref(String href) {
        this.href = href;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Album withId(String id) {
        this.id = id;
        return this;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Album withImages(List<Image> images) {
        this.images = images;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album withName(String name) {
        this.name = name;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Album withReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getReleaseDatePrecision() {
        return releaseDatePrecision;
    }

    public void setReleaseDatePrecision(String releaseDatePrecision) {
        this.releaseDatePrecision = releaseDatePrecision;
    }

    public Album withReleaseDatePrecision(String releaseDatePrecision) {
        this.releaseDatePrecision = releaseDatePrecision;
        return this;
    }

    public Integer getTotalTracks() {
        return totalTracks;
    }

    public void setTotalTracks(Integer totalTracks) {
        this.totalTracks = totalTracks;
    }

    public Album withTotalTracks(Integer totalTracks) {
        this.totalTracks = totalTracks;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Album withType(String type) {
        this.type = type;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Album withUri(String uri) {
        this.uri = uri;
        return this;
    }

}