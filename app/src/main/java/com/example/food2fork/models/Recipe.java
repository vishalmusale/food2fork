package com.example.food2fork.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Arrays;

public class Recipe implements Parcelable {
    private String publisher;
    private String[] ingredients;
    private String sourceURL;
    private String recipeID;
    private String imageURL;
    private long socialRank;
    private String publisherURL;
    private String title;

    public Recipe(String publisher, String[] ingredients, String sourceURL, String recipeID,
                  String imageURL, long socialRank, String publisherURL, String title) {
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.sourceURL = sourceURL;
        this.recipeID = recipeID;
        this.imageURL = imageURL;
        this.socialRank = socialRank;
        this.publisherURL = publisherURL;
        this.title = title;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        publisher = in.readString();
        ingredients = in.createStringArray();
        sourceURL = in.readString();
        recipeID = in.readString();
        imageURL = in.readString();
        socialRank = in.readLong();
        publisherURL = in.readString();
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
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public long getSocialRank() {
        return socialRank;
    }

    public void setSocialRank(long socialRank) {
        this.socialRank = socialRank;
    }

    public String getPublisherURL() {
        return publisherURL;
    }

    public void setPublisherURL(String publisherURL) {
        this.publisherURL = publisherURL;
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
                ", sourceURL='" + sourceURL + '\'' +
                ", recipeID='" + recipeID + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", socialRank=" + socialRank +
                ", publisherURL='" + publisherURL + '\'' +
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
        dest.writeString(sourceURL);
        dest.writeString(recipeID);
        dest.writeString(imageURL);
        dest.writeLong(socialRank);
        dest.writeString(publisherURL);
        dest.writeString(title);
    }
}
