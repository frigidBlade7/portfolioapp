package com.codedevtech.portfolioapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.codedevtech.portfolioapp.converters.DateConverter;
import com.codedevtech.portfolioapp.converters.RoomListConverter;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

@Entity
public class FolioUser implements Parcelable {
    @PrimaryKey
    @NonNull
    private String id;

    private String email;
    private String firstName;
    private String lastName;
    private String bio;
    private String photoUrl;
    @TypeConverters(DateConverter.class) @ServerTimestamp
    private Date createdAt;
    private int followCount;
    private int likeCount;

    @TypeConverters(RoomListConverter.class)
    private List<String> roleFlags;


    public FolioUser() {
    }

    protected FolioUser(Parcel in) {
        id = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        bio = in.readString();
        photoUrl = in.readString();
        followCount = in.readInt();
        likeCount = in.readInt();
        roleFlags = in.createStringArrayList();
    }

    public static final Creator<FolioUser> CREATOR = new Creator<FolioUser>() {
        @Override
        public FolioUser createFromParcel(Parcel in) {
            return new FolioUser(in);
        }

        @Override
        public FolioUser[] newArray(int size) {
            return new FolioUser[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getRoleFlags() {
        return roleFlags;
    }

    public void setRoleFlags(List<String> roleFlags) {
        this.roleFlags = roleFlags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = Timestamp.now().toDate();
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    //checks

    @Exclude
    public boolean isFirstNameValid(){
        return (firstName!=null && !firstName.isEmpty());
    }

    @Exclude
    public boolean isLastNameValid(){
        return (lastName!=null && !lastName.isEmpty());
    }

    @Exclude
    public boolean isIdValid(){
        return (id!=null && !id.isEmpty());

    }

    @Exclude
    public boolean isEmailValid(){
        return (email!=null && !email.isEmpty());
    }

    @Exclude
    public boolean isRoleFlagsValid(){
        return (roleFlags!=null /*&& roleFlags.size()<4*/ && roleFlags.size()>0);
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(photoUrl);
        parcel.writeString(bio);
        parcel.writeInt(followCount);
        parcel.writeInt(likeCount);
        parcel.writeStringList(roleFlags);
    }
}
