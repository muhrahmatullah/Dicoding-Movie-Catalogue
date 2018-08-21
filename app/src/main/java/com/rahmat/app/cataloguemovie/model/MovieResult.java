
package com.rahmat.app.cataloguemovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

//import javax.annotation.Generated;

//@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MovieResult implements Parcelable {

    @SerializedName("adult")
    private Boolean mAdult;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("genre_ids")
    private List<Long> mGenreIds;
    @SerializedName("id")
    private Long mId;
    @SerializedName("original_language")
    private String mOriginalLanguage;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("popularity")
    private Double mPopularity;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("video")
    private Boolean mVideo;
    @SerializedName("vote_average")
    private Double mVoteAverage;
    @SerializedName("vote_count")
    private Long mVoteCount;

    public Boolean getAdult() {
        return mAdult;
    }

    public void setAdult(Boolean adult) {
        mAdult = adult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public List<Long> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        mGenreIds = genreIds;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Double popularity) {
        mPopularity = popularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Boolean getVideo() {
        return mVideo;
    }

    public void setVideo(Boolean video) {
        mVideo = video;
    }

    public Double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        mVoteAverage = voteAverage;
    }

    public Long getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(Long voteCount) {
        mVoteCount = voteCount;
    }

    public MovieResult() {
    }

    @Override
    public String toString() {
        return getPosterPath() + getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mAdult);
        dest.writeString(this.mBackdropPath);
        dest.writeList(this.mGenreIds);
        dest.writeValue(this.mId);
        dest.writeString(this.mOriginalLanguage);
        dest.writeString(this.mOriginalTitle);
        dest.writeString(this.mOverview);
        dest.writeValue(this.mPopularity);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mTitle);
        dest.writeValue(this.mVideo);
        dest.writeValue(this.mVoteAverage);
        dest.writeValue(this.mVoteCount);
    }

    protected MovieResult(Parcel in) {
        this.mAdult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mBackdropPath = in.readString();
        this.mGenreIds = new ArrayList<Long>();
        in.readList(this.mGenreIds, Long.class.getClassLoader());
        this.mId = (Long) in.readValue(Long.class.getClassLoader());
        this.mOriginalLanguage = in.readString();
        this.mOriginalTitle = in.readString();
        this.mOverview = in.readString();
        this.mPopularity = (Double) in.readValue(Double.class.getClassLoader());
        this.mPosterPath = in.readString();
        this.mReleaseDate = in.readString();
        this.mTitle = in.readString();
        this.mVideo = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mVoteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.mVoteCount = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieResult> CREATOR = new Parcelable.Creator<MovieResult>() {
        @Override
        public MovieResult createFromParcel(Parcel source) {
            return new MovieResult(source);
        }

        @Override
        public MovieResult[] newArray(int size) {
            return new MovieResult[size];
        }
    };
}
