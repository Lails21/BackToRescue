package dsa.eetac.upc.edu.backtorescueapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjetoActivity extends AppCompatActivity {
    public String username;
    private APIRest myapirest;
    private Recycler recycler2;
    private RecyclerView recyclerView2;
    ProgressDialog progressDialog;

    TextView textname;
    TextView textimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto);
        //setupActionBar();

        recyclerView2 = (RecyclerView) findViewById(R.id.recycler2);
        recycler2 = new Recycler(this);
        recyclerView2.setAdapter(recycler2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        textname=findViewById(R.id.nomobjeto);
        textimage=findViewById(R.id.imageobjeto);

        Intent intent= getIntent();
        username = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        myapirest = APIRest.createAPIRest();

        getObjetoUser(username);


    }

    private void getObjetoUser(String username){
        Call<List<Objeto>> objetocall = myapirest.getObjetoUser(username);
        objetocall.enqueue(new Callback<List<Objeto>>() {
            @Override
            public void onResponse(Call<List<Objeto>> call, Response<List<Objeto>> response) {
                if(response.isSuccessful()){
                    List<Objeto> newobjlist = response.body();
                    recycler2.add
                }
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<List<Objeto>> call, Throwable t) {

            }
        }
    }


