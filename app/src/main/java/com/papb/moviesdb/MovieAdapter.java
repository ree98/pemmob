package com.papb.moviesdb;

import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ArrayList<Movie> movies;

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie movie = movies.get(i);

        viewHolder.tvTitle.setText(movie.getJudul());
        viewHolder.tvYear.setText(movie.getIsi());
        // Load gambar menggunakan library Glide

Glide.with(viewHolder.ivThumbnail.getContext()).load(movie.getImage()).apply(new RequestOptions().placeholder(viewHolder.circularProgressDrawable)).into(viewHolder.ivThumbnail);
}
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircularProgressDrawable circularProgressDrawable;
        private ConstraintLayout itemContainer;
        private ImageView ivThumbnail;
        private TextView tvTitle, tvYear, tvType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            this.tvTitle = itemView.findViewById(R.id.tvTitle);
            this.tvYear = itemView.findViewById(R.id.tvYear);
            this.tvType = itemView.findViewById(R.id.tvNews);
            this.itemContainer = itemView.findViewById(R.id.itemContainer);
            this.itemContainer.setOnClickListener(this);

            circularProgressDrawable = new CircularProgressDrawable(itemView.getContext());
            circularProgressDrawable.setCenterRadius(10f);
            circularProgressDrawable.start();
        }

        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            // ubah respon dari web service kedalam bentuk String
            String response = new String(responseBody);
            try {
                JSONArray aa = new JSONArray(response);
                for (int count = 0; count < aa.length(); count++) {
                    Movie bb = Movie.fromJson(aa.getJSONObject(count));
                    movies.add(bb);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {

        }
    }
}