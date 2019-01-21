package dsa.eetac.upc.edu.backtorescueapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoreboardActivity extends AppCompatActivity {

    private APIRest myapirest;
    private Recycler recycler;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;

    TextView textusername;
    TextView textdamage;
    TextView textdefense;
    TextView texthealth;
    TextView textlevel;
    TextView textmana;
    TextView textmoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        //setupActionBar();

        recyclerView = (RecyclerView) findViewById(R.id.Recycler_View);
        recycler = new Recycler(this);
        recyclerView.setAdapter(recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        textusername=findViewById(R.id.namerec);
        textdamage=findViewById(R.id.damagerec);
        textdefense=findViewById(R.id.defenserec);
        texthealth=findViewById(R.id.healthrec);
        textlevel=findViewById(R.id.levelrec);
        textmana=findViewById(R.id.manarec);
        textmoney=findViewById(R.id.moneyrec);

        Intent intent= getIntent();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        myapirest = APIRest.createAPIRest();

        getScoreboard();

        }

      private void getScoreboard(){
        Call<List<Player>> charactercall = myapirest.getScoreboard();
        charactercall.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if(response.isSuccessful()){
                    List<Player> newcharacterlist = response.body();
                    recycler.addElements(newcharacterlist);
                }
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {

            }
        });
      }


}
