package com.example.ingvald.appstud.model;

import android.util.Log;

import com.example.ingvald.appstud.model.bean.Result;
import com.example.ingvald.appstud.model.bean.Resultat;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Ingvald on 29/06/2017.
 */

public class WebServicesUtils {

    private static final String URL = " https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    private static final String URL_FIN = "&radius=2000&type=restaurant&key=AIzaSyBm4vkbIXLXNR4iwaheDWJuoc5l2UvuFOs";

    public static ArrayList<Result> getPlace(String position) throws Exception {

        String reponseInJson = OkHttpUtils.sendGetOkHttpRequest(URL + position + URL_FIN);

        Gson gson = new Gson();

        Resultat resultat = gson.fromJson(reponseInJson, Resultat.class);
        if (resultat == null) {
            throw new Exception("Variable est nulle");
        }

        ArrayList<Result> temp = resultat.getResults();
        Log.w("Tag",""+temp.size());
        return temp;
    }
}
