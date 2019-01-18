package dsa.eetac.upc.edu.backtorescueapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        getUserStats(user.username);
    }

    private void getUserStats(String username){
        Call<Character> statsCall = myapirest.getUserStats(username);
        statsCall.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                Character characters =response.body();

                textusername.setText(characters.username);
                textdamage.setText(String.valueOf("Damage: "+characters.damage));
                textdefense.setText(String.valueOf("Defense: "+characters.defense));
                texthealth.setText(String.valueOf("Health: "+characters.health));
                textlevel.setText(String.valueOf("Level: "+characters.level));
                textmana.setText(String.valueOf("Mana: "+characters.mana));
                textmoney.setText(String.valueOf("Money: "+characters.money));
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.i("BackToRescue", "Fallada stats"+t.getMessage());
                progressDialog.hide();
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
