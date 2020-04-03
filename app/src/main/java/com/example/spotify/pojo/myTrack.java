package com.example.spotify.pojo;

import com.example.spotify.SpotifyClasses.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class myTrack {
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    @SerializedName("availableMarkets")
    @Expose
    private List<String> availableMarkets = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("trackNumber")
    @Expose
    private Integer trackNumber;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artistId")
    @Expose
    private String artistId;
    @SerializedName("albumId")
    @Expose
    private String albumId;
    @SerializedName("discNumber")
    @Expose
    private Integer discNumber;
    @SerializedName("explicit")
    @Expose
    private Boolean explicit;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("acousticness")
    @Expose
    private Integer acousticness;
    @SerializedName("danceability")
    @Expose
    private Integer danceability;
    @SerializedName("energy")
    @Expose
    private Integer energy;
    @SerializedName("instrumentalness")
    @Expose
    private Integer instrumentalness;
    @SerializedName("key")
    @Expose
    private Integer key;
    @SerializedName("liveness")
    @Expose
    private Integer liveness;
    @SerializedName("loudness")
    @Expose
    private Integer loudness;
    @SerializedName("mode")
    @Expose
    private Integer mode;
    @SerializedName("speechiness")
    @Expose
    private Integer speechiness;
    @SerializedName("tempo")
    @Expose
    private Integer tempo;
    @SerializedName("timeSignature")
    @Expose
    private String timeSignature;
    @SerializedName("valence")
    @Expose
    private Integer valence;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<String> getAvailableMarkets() {
        return availableMarkets;
    }

    public void setAvailableMarkets(List<String> availableMarkets) {
        this.availableMarkets = availableMarkets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Integer getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(Integer discNumber) {
        this.discNumber = discNumber;
    }

    public Boolean getExplicit() {
        return explicit;
    }

    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAcousticness() {
        return acousticness;
    }

    public void setAcousticness(Integer acousticness) {
        this.acousticness = acousticness;
    }

    public Integer getDanceability() {
        return danceability;
    }

    public void setDanceability(Integer danceability) {
        this.danceability = danceability;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Integer getInstrumentalness() {
        return instrumentalness;
    }

    public void setInstrumentalness(Integer instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public Integer getLiveness() {
        return liveness;
    }

    public void setLiveness(Integer liveness) {
        this.liveness = liveness;
    }

    public Integer getLoudness() {
        return loudness;
    }

    public void setLoudness(Integer loudness) {
        this.loudness = loudness;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getSpeechiness() {
        return speechiness;
    }

    public void setSpeechiness(Integer speechiness) {
        this.speechiness = speechiness;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public String getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }

    public Integer getValence() {
        return valence;
    }

    public void setValence(Integer valence) {
        this.valence = valence;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
