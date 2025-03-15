package com.example.food2fork.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.Objects;

@Entity(tableName = "recipes")
public class Recipe implements Parcelable {
    @PrimaryKey
    @NonNull
    private String recipe_id;
    @ColumnInfo(name = "publisher")
    private String publisher;
    @ColumnInfo(name = "ingredients")
    private String[] ingredients;
    @ColumnInfo(name = "source_url")
    private String source_url;
    @ColumnInfo(name = "image_url")
    private String image_url;
    @ColumnInfo(name = "publisher_url")
    private String publisher_url;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "social_rank")
    private float social_rank;
    @ColumnInfo(name = "timestamp")
    private int timestamp;

    @Ignore
    public Recipe() {
    }

    @Ignore
    public Recipe(Recipe recipe){
        this.title = recipe.title;
        this.publisher = recipe.publisher;
        this.ingredients = recipe.ingredients;
        this.recipe_id = recipe.recipe_id;
        this.image_url = recipe.image_url;
        this.social_rank = recipe.social_rank;
        this.timestamp = recipe.timestamp;
    }

    public Recipe(@NonNull String recipe_id, String publisher, String[] ingredients, String source_url, String image_url, String publisher_url, String title, float social_rank, int timestamp) {
        this.recipe_id = recipe_id;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.source_url = source_url;
        this.image_url = image_url;
        this.publisher_url = publisher_url;
        this.title = title;
        this.social_rank = social_rank;
        this.timestamp = timestamp;
    }

    protected Recipe(Parcel in) {
        publisher = in.readString();
        ingredients = in.createStringArray();
        source_url = in.readString();
        recipe_id = Objects.requireNonNull(in.readString());
        image_url = in.readString();
        publisher_url = in.readString();
        title = in.readString();
        social_rank = in.readFloat();
        timestamp = in.readInt();
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



    @NonNull
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
                ", socialRank=" + social_rank +
                ", timestamp=" + timestamp +
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
        dest.writeFloat(social_rank);
        dest.writeInt(timestamp);
    }

    @NonNull
    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(@NonNull String recipe_id) {
        this.recipe_id = recipe_id;
    }

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

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPublisher_url() {
        return publisher_url;
    }

    public void setPublisher_url(String publisher_url) {
        this.publisher_url = publisher_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(float social_rank) {
        this.social_rank = social_rank;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
