package com.example.ingvald.appstud.model;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ingvald.appstud.R;
import com.example.ingvald.appstud.model.bean.Result;

import java.util.ArrayList;

/**
 * Created by dev on 24/05/2017.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private ArrayList<Result> places;

    public static final String URL_PHOTO = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";
    public static final String URL_PHOTO_FIN="&key=AIzaSyBm4vkbIXLXNR4iwaheDWJuoc5l2UvuFOs";

    public PlaceAdapter(ArrayList<Result> places) {
        this.places = places;
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView ar_tv;
        public ImageView ar_image;
        public RelativeLayout root;




        public ViewHolder(View itemView) {
            super(itemView);
            ar_image = (ImageView) itemView.findViewById(R.id.ar_image);
            ar_tv = (TextView) itemView.findViewById(R.id.ar_tv);

            root = (RelativeLayout) itemView.findViewById(R.id.root);





        }
    }


    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row_place, parent, false);
        return new PlaceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder holder, int position) {
       Result result = places.get(position);
        holder.ar_tv.setText(result.getName());
        Log.w("TAG","URM : " +URL_PHOTO+ result.getReference()+URL_PHOTO_FIN );
        Glide.with(holder.itemView.getContext())
                .load(URL_PHOTO+ result.getPhotos().get(0).getPhoto_reference()+URL_PHOTO_FIN)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(holder.ar_image);
    }

    @Override
    public int getItemCount() {
        return places.size();

    }




}
