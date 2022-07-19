package com.example.countries;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.countries.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.*;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

         */

        mTextViewResult = findViewById(R.id.text_view_result);

        OkHttpClient client = new OkHttpClient();

        //String url = "https://reqres.in/api/users?page=2";

        /*
        Request request = new Request.Builder()
                .url(url)
                .build();
         */

        int min = 10;
        int max = 20;
        String url = "https://numbersapi.p.rapidapi.com/random/trivia?min=" + min + "&max=" + max + "&fragment=true&json=true";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", "6565dd3472msh9303f28447aad73p14c79ajsncff8a2ea5e12")
                .addHeader("X-RapidAPI-Host", "numbersapi.p.rapidapi.com")
                .build();

        /*
        Response response = null;
        try {
            response = client.newCall(request).execute();
            final String myResponse = response.body().string();
            mTextViewResult.setText(myResponse);
        } catch (IOException e) {
            mTextViewResult.setText("ERROR");
            e.printStackTrace();
        }
        */

        /*
        client.newCall(request).enqueue(new Callback() {

                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                e.printStackTrace();
                                                mTextViewResult.setText("ERROR");
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                if (response.isSuccessful()) {

                                                    final String myResponse = response.body().string();
                                                    JSONObject obj = null;
                                                    try {
                                                        obj = new JSONObject(myResponse);
                                                        final String text = obj.getString("text");
                                                        MainActivity.this.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                mTextViewResult.setText(text);
                                                            }
                                                        });

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }



                                                } else {
                                                    MainActivity.this.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            mTextViewResult.setText("ERROR");
                                                        }
                                                    });
                                                }
                                            }
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


         */

        final Button btnLaunchDate = (Button) findViewById(R.id.btnLaunchDate);
        final Button btnLaunchYear = (Button) findViewById(R.id.btnLaunchYear);
        final Button btnLaunchMath = (Button) findViewById(R.id.btnLaunchMath);
        final Button btnLaunchRandom = (Button) findViewById(R.id.btnLaunchRandom);
        final Button btnLaunchTrivia = (Button) findViewById(R.id.btnLaunchTrivia);

        btnLaunchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getDateFact = new Intent(MainActivity.this, DateActivity.class);
                startActivity(getDateFact);
            }
        });

        btnLaunchYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getYearFact = new Intent(MainActivity.this, YearActivity.class);
                startActivity(getYearFact);
            }
        });

        btnLaunchMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getMathFact = new Intent(MainActivity.this, MathActivity.class);
                startActivity(getMathFact);
            }
        });

        btnLaunchTrivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getTriviaFact = new Intent(MainActivity.this, TriviaActivity.class);
                startActivity(getTriviaFact);
            }
        });

        btnLaunchRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getRandomFact = new Intent(MainActivity.this, RandomActivity.class);
                startActivity(getRandomFact);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}