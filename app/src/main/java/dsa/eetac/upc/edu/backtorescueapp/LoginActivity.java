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
    User user;
    public boolean registered;
    public EditText usertext3;
    public EditText usertext2;
    public EditText passwordtxt3;
    public EditText passwordtxt2;
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
                usertext3 = (EditText) findViewById(R.id.logintexto);
                Log.i("BackToRescue1", "Usertextsign: " + usertext3);
                //Guardamos el valor del editText una variable tipo String
                user3 = usertext3.getText().toString();
                Log.i("BackToRescue1", "User3sign: " + user3);
                passwordtxt3 = (EditText) findViewById(R.id.passwordtexto);
                Log.i("BackToRescue1", "Passwordtxtsign: " + passwordtxt3);
                password3 = passwordtxt3.getText().toString();
                Log.i("BackToRescue1", "Password3sign: " + password3);
                user = new User(user3, password3);
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
                usertext2 = (EditText) findViewById(R.id.logintexto);
                Log.i("BackToRescue1", "Usertext: " + usertext2);
                //Guardamos el valor del editText una variable tipo String
                user2 = usertext2.getText().toString();
                Log.i("BackToRescue1", "User2: " + user2);
                passwordtxt2 = (EditText) findViewById(R.id.passwordtexto);
                Log.i("BackToRescue1", "Passwordtxt: " + passwordtxt2);
                password2 = passwordtxt2.getText().toString();
                Log.i("BackToRescue1", "Password2: " + password2);
                user = new User(user2, password2);
                Log.i("BackToRescue1", "User: " + user);

                getUser(user);

            }
        });

    }


    private void getUser(User user) {

        Call<Player> characterCall = myapirest.getUser(user);
        characterCall.enqueue(new Callback<Player>() {

            public void onResponse(Call<Player> call, Response<Player> response) {
                Log.i("BackToRescueonresponse", user.username + response.message());
                Player player = response.body();

                try {
                    Log.i("BackToRescue1", "username: " + player.username);
                    //usertext = findViewById(R.id.email);
                    //user2= String.valueOf(usertext);
                    money = player.money;
                    Log.i("BackToRescue1", "Damage: " + player.money);
                    if (money != 0) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //Para pasar el string de un a otro activity se lo defines de esta manera
                        Log.i("BackToRescue1", "User2si: " + user2);
                        //intent.putExtra(EXTRA_MESSAGE, user2);
                        intent.putExtra("User", user2);
                        intent.putExtra("Password", password2);
                        Log.i("BackToRescue1", "SIIIIIIIII: " + player.username);
                        usertext2.setText(String.valueOf(""));
                        passwordtxt2.setText(String.valueOf(""));
                        startActivity(intent);
                        //progressDialog.hide();
                        //Log.i("BackToRescue1", "SIIIIIIIII: " + player.username);
                    }

                } catch (Exception e){
                    Log.i("BackToRescue1", "Noooo: ");
                    openDialog();
                }

            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.i("BackToRescueis", "onFailure" + t.getMessage());
                progressDialog.hide();
            }
        });

    }
    private void createNewUser(User user) {
        Call<Void> userCall=myapirest.createNewUser(user);
        userCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
               if(response.isSuccessful()) {
                   usertext3.setText(String.valueOf(""));
                   passwordtxt3.setText(String.valueOf(""));
               }
               else{
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
            public void onFailure(Call<Void> call, Throwable t) {
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

