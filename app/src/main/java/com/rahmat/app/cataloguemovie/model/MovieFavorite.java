package com.rahmat.app.cataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by muhrahmatullah on 07/08/18.
 */
public class MovieFavorite implements Parcelable {
    private String id;
    private String title;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieFavorite(String id, String image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public MovieFavorite(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.image);
    }

    protected MovieFavorite(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.image = in.readString();
    }

    public static final Creator<MovieFavorite> CREATOR = new Creator<MovieFavorite>() {
        @Override
        public MovieFavorite createFromParcel(Parcel source) {
            return new MovieFavorite(source);
        }

        @Override
        public MovieFavorite[] newArray(int size) {
            return new MovieFavorite[size];
        }
    };
}
