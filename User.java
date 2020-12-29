package com.example.bai1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class User implements Parcelable {
    private String ten;
    private String sdt;
    private String matkhau;

    public User() {
    }

    public User(String sdt, String ten, String matkhau) {
        this.ten = ten;
        this.sdt = sdt;
        this.matkhau = matkhau;
    }

    protected User(Parcel in) {
        ten = in.readString();
        sdt = in.readString();
        matkhau = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    @Override
    public String toString() {
        return "User{" +
                "ten='" + ten + '\'' +
                ", sdt='" + sdt + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ten);
        dest.writeString(sdt);
        dest.writeString(matkhau);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(ten, user.ten) &&
                Objects.equals(sdt, user.sdt) &&
                Objects.equals(matkhau, user.matkhau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ten, sdt, matkhau);
    }
}
