
package com.rahmat.app.cataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//import javax.annotation.Generated;

//@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Movie implements Parcelable {

    @SerializedName("page")
    private Long mPage;
    @SerializedName("results")
    private List<MovieResult> mMovieResults;
    @SerializedName("total_pages")
    private Long mTotalPages;
    @SerializedName("total_results")
    private Long mTotalResults;

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public List<MovieResult> getResults() {
        return mMovieResults;
    }

    public void setResults(List<MovieResult> movieResults) {
        mMovieResults = movieResults;
    }

    public Long getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(Long totalPages) {
        mTotalPages = totalPages;
    }

    public Long getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(Long totalResults) {
        mTotalResults = totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mPage);
        dest.writeTypedList(this.mMovieResults);
        dest.writeValue(this.mTotalPages);
        dest.writeValue(this.mTotalResults);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.mPage = (Long) in.readValue(Long.class.getClassLoader());
        this.mMovieResults = in.createTypedArrayList(MovieResult.CREATOR);
        this.mTotalPages = (Long) in.readValue(Long.class.getClassLoader());
        this.mTotalResults = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
