package dsa.eetac.upc.edu.backtorescueapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticsActivity  extends AppCompatActivity {
    private APIRest myapirest;
    ProgressDialog progressDialog;


    public User user = new User("Laia", "Muñoz");
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
        setContentView(R.layout.statistics);
        setupActionBar();

        textusername=findViewById(R.id.namests);
        textdamage=findViewById(R.id.damagests);
        textdefense=findViewById(R.id.defensests);
        texthealth=findViewById(R.id.healthsts);
        textlevel=findViewById(R.id.levelsts);
        textmana=findViewById(R.id.manasts);
        textmoney=findViewById(R.id.moneysts);

        Intent intent= getIntent();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();


        myapirest = APIRest.createAPIRest();

        getUserStats();

    }
    private void getUserStats(){
        Call<Character> statsCall =myapirest.getUserStats(user.username);
        statsCall.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                Character characters =response.body();

                textusername.setText(characters.username);
                textdamage.setText(characters.damage);
                textdefense.setText(characters.defense);
                texthealth.setText(characters.health);
                textlevel.setText(characters.level);
                textmana.setText(characters.mana);
                textmoney.setText(""+characters.money);
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {

            }
        });
    }

    private void setupActionBar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("SCOREBOARD");
        }
    }
}
