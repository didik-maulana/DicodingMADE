package com.codingtive.dicodingmade.preload.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    private int id;
    private String name;
    private String nim;

    public Student() {

    }

    public Student(String name, String nim) {
        this.name = name;
        this.nim = nim;
    }

    protected Student(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.nim = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.nim);
    }
}
