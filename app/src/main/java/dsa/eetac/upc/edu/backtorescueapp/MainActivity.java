package dsa.eetac.upc.edu.backtorescueapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private APIRest myapirest;
    //public User user = new User("Laia", "Muñoz");
    public User user;
    public String user2;
    public String password2;
    TextView textViewName;
    TextView textViewMoney;
    TextView textViewLevel;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buton scoreboard
        final Button button =(Button)findViewById(R.id.sbbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScoreboardActivity.class);
                startActivity(intent);
            }
        });

        //Buton logout
        final Button button1 =(Button)findViewById(R.id.logoutBtn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        //Buton states
        final Button button2 =(Button)findViewById(R.id.statesBtn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StatisticsActivity.class);
                intent.putExtra(EXTRA_MESSAGE, user2);
                startActivity(intent);
            }
        });
        textViewName = findViewById(R.id.nametxt);
        textViewMoney = findViewById(R.id.moneytxt);
        textViewLevel = findViewById(R.id.leveltxt);

        Intent intent = getIntent();
        user2 = intent.getStringExtra("User");
        String[] userparts = user2.split(":");
        //updateTrackTitle.setText(userparts[1]);
        password2 = intent.getStringExtra("Password");
        String[] passwordparts = password2.split(":");
        //updateTrackSinger.setText(passwordparts[1]);




        user = new User(user2, password2);

        Log.i("BackToRescue", "User2statssi: " +user2);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        myapirest = APIRest.createAPIRest();

        getUser(user);

    }

    private void getUser(User user){

        Call<Character> characterCall = myapirest.getUser(user);
        characterCall.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                Log.i("BackToRescueonresponse" , user.username+ response.message());
                Character character = response.body();

                Log.i ("BackToRescue1", "username: "+character.username);
                textViewName.setText(character.username);
                textViewMoney.setText(""+character.money);
                textViewLevel.setText(""+character.level);
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.i("BackToRescue", "onFailure"+t.getMessage());
                progressDialog.hide();
            }
        });

    }

    private void logout(){
        Call<Void> logoutcall = myapirest.logout();
        logoutcall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
