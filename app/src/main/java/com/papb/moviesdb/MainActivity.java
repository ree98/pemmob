package com.papb.moviesdb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Kelas ini merupakan kelas activity utama yang menampilkan daftar film
 */
public class MainActivity extends AppCompatActivity {

    // konstanta SERVICE_URL digunakan untuk menyimpan alamat web service yang akan di akses
    private static final String SERVICE_URL = "https://filkom.ub.ac.id/page/news_json";
    // konstanta API_KEY digunakan untuk menyimpan api key yang diperlukan agar dapat mengakses web service
    private static final String API_KEY = "b40ddbbf";

    // variable-variable untuk recyclerview
    private RecyclerView rvMovies;
    private ArrayList<Movie> movies;
    private MovieAdapter adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        adapter = new MovieAdapter(movies);
        this.rvMovies.setAdapter(adapter);
        this.rvMovies.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // panggil method loadMoves() untuk mendapatkan film - film yang mengandung kata "iron" dan "man"
        loadMovies("barbie");
    }


    /**
     * Method ini digunakan untuk mendapatkan daftar film yang mengandung kata kunci tertentu
     * parameter keyword digunakan untuk menyimpan kata kunci yang akan digunakan dalam proses pencarian film
     */
    private void loadMovies(String keyword) {
        // buat objek AsyncHttpClient yang akan digunakan untuk mengirimkan request ke web service
        AsyncHttpClient client = new AsyncHttpClient();

        // Masukkan parameter parameter yang harus dikirimkan ke web service bersama dengan request
        // Web Service OMDb memerlukan parameter apikey dan s,
        // parameter apikey adalah apikey yang dapat didapatkan dengan mendaftar pada web OMDb
        // parameter s merupakan kata kunci yang ingin digunakan dalam proses pencarian film
        RequestParams params = new RequestParams();
        params.put("apikey", API_KEY);
        params.put("s", keyword);

        // kirim request GET ke web service
        // argumen pertama (SERVICE_URL) adalah alamat dari web service
        // argumen kedua (params) adalah parameter yang ingin dikirimkan bersamaan dengan request
        // argumen ketiga adalah object handler yang digunakan untuk meng-handle proses request (berisi method-method yang akan secara otomatis terpanggil tergantung dari hasil request)
        // jika gagal maka method onFailure yang akan terpanggil, jika berhasil maka method onSuccess yang akan terpanggil
        client.get(SERVICE_URL, new AsyncHttpResponseHandler() {
            // Method ini akan otomatis terpanggil apabila aplikasi telah mendapatkan respon dari web service
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // ubah respon dari web service kedalam bentuk String
                String response = new String(responseBody);
                try {
                    JSONArray aa = new JSONArray(response);
                    for (int count = 0; count < aa.length(); count++) {
                        Movie b = Movie.fromJson(aa.getJSONObject(count));
                        movies.add(b);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Method ini akan otomatis terpanggil apabila terjadi kesalahan dalam proses komunikasi dengan web service
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, "Cannot Get News", Toast.LENGTH_SHORT).show();
            }
        });
    }}
