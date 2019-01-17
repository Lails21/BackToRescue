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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private APIRest myapirest;
    public User user = new User("Laia", "munoz");
    TextView textViewName;
    TextView textViewMoney;
    TextView textViewLevel;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buton
        final Button button =(Button)findViewById(R.id.sbbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ScoreboardActivity.class);
                startActivity(intent);
            }
        });
        textViewName = findViewById(R.id.nametxt);
        textViewMoney = findViewById(R.id.moneytxt);
        textViewLevel = findViewById(R.id.leveltxt);

        Intent intent = getIntent();

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
                Log.i("BackToRescue" , user.username+ response.message());
                Character character = response.body();
               // textViewName.setText(character.username);
                //textViewMoney.setText((int)character.money);
                //textViewLevel.setText(character.level);
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.i("BackToRescue", "onFailure"+t.getMessage());
            }
        });


    }
}
