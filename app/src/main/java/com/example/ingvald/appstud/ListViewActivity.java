package com.example.ingvald.appstud;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ingvald.appstud.model.PlaceAdapter;
import com.example.ingvald.appstud.model.WebServicesUtils;
import com.example.ingvald.appstud.model.bean.Result;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

private RecyclerView rv;
    private PlaceAdapter placeAdapter;
    private ArrayList<Result> listResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        rv = (RecyclerView) findViewById(R.id.rv);

        listResult = new ArrayList<>();

        placeAdapter = new PlaceAdapter(listResult);

        rv.setAdapter(placeAdapter);

        rv.setLayoutManager(new LinearLayoutManager(this) );
        rv.setItemAnimator(new DefaultItemAnimator());

        MonAt monAt = new MonAt();
        monAt.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 25, 0, "Map");

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 25:
                Intent intent = new Intent(ListViewActivity.this, MapsActivity.class);
                startActivity(intent);
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    public class MonAt extends AsyncTask {
        private ArrayList<Result> resultat = null;
        private Exception exception = null;


        @Override
        protected Object doInBackground(Object[] objects) {
            ArrayList<Result> temp = null;

            try {
                temp = WebServicesUtils.getPlace(43.6,1.4333);
            } catch (Exception e) {
                exception=e;

            }

            return temp;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ArrayList<Result> resultat = (ArrayList<Result>) o;

           // listResult.clear();
            listResult.addAll(resultat);
            placeAdapter.notifyDataSetChanged();
        }
    }
}
