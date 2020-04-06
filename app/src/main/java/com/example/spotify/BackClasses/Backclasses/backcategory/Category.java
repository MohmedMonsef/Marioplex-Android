
package com.example.spotify.BackClasses.Backclasses.backcategory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("category")
    @Expose
    private List<Category_> categories = null;

    public List<Category_> getCategories() {
        return categories;
    }

    public void setCategories(List<Category_> categories) {
        this.categories = categories;
    }

}
