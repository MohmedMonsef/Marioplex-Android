
package com.example.spotify.SpotifyClasses.SearchClasses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Artists {

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("limit")
    @Expose
    private int limit;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("offset")
    @Expose
    private int offset;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("total")
    @Expose
    private int total;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Artists() {
    }

    /**
     * 
     * @param next
     * @param total
     * @param offset
     * @param previous
     * @param limit
     * @param href
     * @param items
     */
    public Artists(String href, List<Item> items, int limit, String next, int offset, Object previous, int total) {
        super();
        this.href = href;
        this.items = items;
        this.limit = limit;
        this.next = next;
        this.offset = offset;
        this.previous = previous;
        this.total = total;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
