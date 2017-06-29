package com.example.ingvald.appstud.model;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Ingvald on 29/06/2017.
 */

public class OkHttpUtils {

    public static String sendGetOkHttpRequest(String url) throws Exception {
        Log.w("Tag",url);
        OkHttpClient client = new OkHttpClient();
        //Création de la requete
        Request request = new Request.Builder().url(url).build();
        //Execution de la requête
        Response response = client.newCall(request).execute();
        //Analyse du code retour
        if (response.code() != 200) {
            throw new Exception("Réponse du serveur incorrect : " + response.code());
        } else {
            //Résultat de la requete.
            return response.body().string();
        }
    }
}
