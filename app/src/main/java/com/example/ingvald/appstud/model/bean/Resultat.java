package com.example.ingvald.appstud.model.bean;

/**
 * Created by Ingvald on 29/06/2017.
 */

import java.util.ArrayList;
import java.util.List;

public class Resultat {

    //-----------------
    // ATTRIBUT
    //-----------------
    private ArrayList<Object> html_attributions = null;
    private ArrayList<Result> results = null;
    private String status;

    public List<Object> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(ArrayList<Object> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}