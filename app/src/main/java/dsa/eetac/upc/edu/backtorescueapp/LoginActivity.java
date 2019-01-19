package dsa.eetac.upc.edu.backtorescueapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;

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
    public int damage;
    public String user2;
    //EditText passwordtxt;
    public String password2;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        myapirest = APIRest.createAPIRest();


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
                    damage = character.damage;
                    Log.i("BackToRescue1", "Damage: " + character.damage);
                    if (damage != 0) {
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
                Log.i("BackToRescue", "onFailure" + t.getMessage());
                progressDialog.hide();
            }
        });

    }

    public void openDialog (){
       ExampleDialog exampleDialog = new ExampleDialog();
       exampleDialog.show(getSupportFragmentManager(), "User or password incorrect");
    }
}

