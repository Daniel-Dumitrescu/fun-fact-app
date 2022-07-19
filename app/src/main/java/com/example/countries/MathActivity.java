package com.example.countries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MathActivity extends AppCompatActivity {

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
        EditText yearInput = (EditText) findViewById(R.id.number1);

        DatePicker simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker); // initiate a date picker
        simpleDatePicker.setVisibility(View.GONE);

        instructions.setText("Enter a number to view a fun mathematical fact about that number");


        final Button btnFindFact = (Button) findViewById(R.id.btnFindFact);


        btnFindFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int number = 0;

                try {
                    number = Integer.parseInt(yearInput.getText().toString());
                } catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid number", Toast.LENGTH_SHORT);
                    toast.show();
                    yearInput.setText("");
                    return;
                }

                String url = "https://numbersapi.p.rapidapi.com/" + number + "/math?fragment=true&json=true";

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
                            String date = "";

                            try {
                                obj = new JSONObject(myResponse);
                                final String text = obj.getString("text");

                                final String number = obj.getString("number");

                                String finalDate = date;
                                MathActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textYearMessage.setText(number);
                                        mTextViewResult.setText(text);
                                    }
                                });

                            } catch (JSONException e) {
                                MathActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTextViewResult.setText("No fun fact available for selected number :(");
                                    }
                                });
                                e.printStackTrace();
                            }


                        } else {
                            MathActivity.this.runOnUiThread(new Runnable() {
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
