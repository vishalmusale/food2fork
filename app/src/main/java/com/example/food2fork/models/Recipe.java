package com.example.food2fork.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class Recipe implements Parcelable {
    private String publisher;
    private String[] ingredients;
    private String source_url;
    private String recipe_id;
    private String image_url;
    private String publisher_url;
    private String title;

    public Recipe(String publisher, String[] ingredients, String sourceURL, String recipeID,
                  String imageURL, String publisherURL, String title) {
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.source_url = sourceURL;
        this.recipe_id = recipeID;
        this.image_url = imageURL;
        this.publisher_url = publisherURL;
        this.title = title;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        publisher = in.readString();
        ingredients = in.createStringArray();
        source_url = in.readString();
        recipe_id = in.readString();
        image_url = in.readString();
        publisher_url = in.readString();
        title = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getSourceURL() {
        return source_url;
    }

    public void setSourceURL(String sourceURL) {
        this.source_url = sourceURL;
    }

    public String getRecipeID() {
        return recipe_id;
    }

    public void setRecipeID(String recipeID) {
        this.recipe_id = recipeID;
    }

    public String getImageURL() {
        return image_url;
    }

    public void setImageURL(String imageURL) {
        this.image_url = imageURL;
    }

    public String getPublisherURL() {
        return publisher_url;
    }

    public void setPublisherURL(String publisherURL) {
        this.publisher_url = publisherURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", sourceURL='" + source_url + '\'' +
                ", recipeID='" + recipe_id + '\'' +
                ", imageURL='" + image_url + '\'' +
                ", publisherURL='" + publisher_url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(publisher);
        dest.writeStringArray(ingredients);
        dest.writeString(source_url);
        dest.writeString(recipe_id);
        dest.writeString(image_url);
        dest.writeString(publisher_url);
        dest.writeString(title);
    }
}
