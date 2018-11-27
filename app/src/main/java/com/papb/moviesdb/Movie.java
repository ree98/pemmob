package com.papb.moviesdb;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class Movie {

    private String judul, isi, image;

    public Movie(String judul, String isi, String image) {
        this.judul = judul;
        this.isi = isi;
        this.image = image;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static Movie fromJson(JSONObject jsonObject) throws JSONException {
        String judul = jsonObject.getString("judul");
        String isi = jsonObject.getString("isi");
        String image = jsonObject.getString("img");

        return new Movie(judul, isi, image);
    }
}
