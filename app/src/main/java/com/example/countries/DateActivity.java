package com.example.countries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DateActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;
    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.fact_layout);

        //setSupportActionBar(binding.toolbar);

        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/

        mTextViewResult = findViewById(R.id.txtResponse);
        TextView instructions = (TextView) findViewById(R.id.txtInstructions);
        TextView textYearMessage = (TextView) findViewById(R.id.txtNumMessage);

        DatePicker simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker); // initiate a date picker

        final Button btnFindFact = (Button) findViewById(R.id.btnFindFact);

        simpleDatePicker.setSpinnersShown(false); // set false value for the spinner shown function
        instructions.setText("Select a date to view a 'This Day in History' fun fact");

        String[] months = new String[]{"","January","February","March","April","May","June","July","August","September","October","November","December"};


        btnFindFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int month = simpleDatePicker.getMonth()+1;
                int day = simpleDatePicker.getDayOfMonth();
                String url = "https://numbersapi.p.rapidapi.com/" + month + "/" + day + "/date?fragment=true&json=true";

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .addHeader("X-RapidAPI-Key", "6565dd3472msh9303f28447aad73p14c79ajsncff8a2ea5e12")
                        .addHeader("X-RapidAPI-Host", "numbersapi.p.rapidapi.com")
                        .build();


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
                                final String year = obj.getString("year");

                                DateActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTextViewResult.setText(text);
                                        textYearMessage.setText("On " + months[month] + " " + day + ", " + year +":");
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        } else {
                            DateActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTextViewResult.setText("ERROR");
                                }
                            });
                        }
                    }
                });
            }
        });



    }
}
