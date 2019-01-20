package dsa.eetac.upc.edu.backtorescueapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    //Para recojer el texto del TextView al cliclarle boton
    public static final String EXTRA_MESSAGE = "Main";
    public static final String EXTRA_MESSAGE1 = "Statistics";
    private APIRest myapirest;
    //EditText usertext;
    public String usercharacter;
    public double money;
    public String user2;
    //EditText passwordtxt;
    public String password2;
    ProgressDialog progressDialog;
    private String user3;
    private String password3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myapirest = APIRest.createAPIRest();

        //Buton signin
        final Button signinbtn = (Button) findViewById(R.id.signinboton);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("BackToRescue1", "Entra");
                EditText usertext3 = (EditText) findViewById(R.id.logintexto);
                Log.i("BackToRescue1", "Usertextsign: " + usertext3);
                //Guardamos el valor del editText una variable tipo String
                user3 = usertext3.getText().toString();
                Log.i("BackToRescue1", "User3sign: " + user3);
                EditText passwordtxt3 = (EditText) findViewById(R.id.passwordtexto);
                Log.i("BackToRescue1", "Passwordtxtsign: " + passwordtxt3);
                password3 = passwordtxt3.getText().toString();
                Log.i("BackToRescue1", "Password3sign: " + password3);
                User user = new User(user3, password3);
                Log.i("BackToRescue1", "User: " + user);
                Log.i("BackToRescue1", "PreBien: ");
                createNewUser(user);
            }
        });


        //Buton login
        final Button button2 = (Button) findViewById(R.id.loginboton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("BackToRescue1", "Entra");
                EditText usertext2 = (EditText) findViewById(R.id.logintexto);
                Log.i("BackToRescue1", "Usertext: " + usertext2);
                //Guardamos el valor del editText una variable tipo String
                user2 = usertext2.getText().toString();
                Log.i("BackToRescue1", "User2: " + user2);
                EditText passwordtxt2 = (EditText) findViewById(R.id.passwordtexto);
                Log.i("BackToRescue1", "Passwordtxt: " + passwordtxt2);
                password2 = passwordtxt2.getText().toString();
                Log.i("BackToRescue1", "Password2: " + password2);
                User user = new User(user2, password2);
                Log.i("BackToRescue1", "User: " + user);

                getUser(user);

            }
        });

    }


    private void getUser(User user) {

        Call<Character> characterCall = myapirest.getUser(user);
        characterCall.enqueue(new Callback<Character>() {

            public void onResponse(Call<Character> call, Response<Character> response) {
                Log.i("BackToRescueonresponse", user.username + response.message());
                Character character = response.body();

                try {
                    Log.i("BackToRescue1", "username: " + character.username);
                    //usertext = findViewById(R.id.email);
                    //user2= String.valueOf(usertext);
                    money = character.money;
                    Log.i("BackToRescue1", "Damage: " + character.money);
                    if (money != 0) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //Para pasar el string de un a otro activity se lo defines de esta manera
                        Log.i("BackToRescue1", "User2si: " + user2);
                        //intent.putExtra(EXTRA_MESSAGE, user2);
                        intent.putExtra("User", user2);
                        intent.putExtra("Password", password2);
                        Log.i("BackToRescue1", "SIIIIIIIII: " + character.username);
                        startActivity(intent);
                        //progressDialog.hide();
                        //Log.i("BackToRescue1", "SIIIIIIIII: " + character.username);
                    }


                } catch (Exception e){
                    Log.i("BackToRescue1", "Noooo: ");
                    openDialog();
                }

            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.i("BackToRescueis", "onFailure" + t.getMessage());
                progressDialog.hide();
            }
        });

    }
    private void createNewUser(User user) {

        Log.i("BackToRescue1", "Bien1: ");
        Call<User> userCall=myapirest.createNewUser(user);
            Log.i("BackToRescue2", "Bien2: ");
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("BackToRescue1", "Bien2.1: ");
                if(response.isSuccessful()){
                    Log.i("BackToRescue1", "Bien3: ");
                    Toast.makeText(LoginActivity.this, "User created", Toast.LENGTH_LONG).show();
                    //finish();

                }
                else{
                    Log.e("No api connection", response.message());
                    Log.i("BackToRescue2", "Bien4: ");
                    //Show the alert dialog
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);

                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage(response.message())
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, which) -> finish());

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("No api connection: ", t.getMessage());

                //Show the alert dialog
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);

                alertDialogBuilder
                        .setTitle("Error")
                        .setMessage(t.getMessage())
                        .setCancelable(false)
                        .setPositiveButton("OK", (dialog, which) -> finish());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });



    }


    public void openDialog (){
       ExampleDialog exampleDialog = new ExampleDialog();
       exampleDialog.show(getSupportFragmentManager(), "User or password incorrect");
    }
}

