package com.example.countries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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



public class RandomActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;
    private TextView mTextViewResult;
    private String category = "";

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
        EditText minNumberInput = (EditText) findViewById(R.id.number1);
        EditText maxNumberInput = (EditText) findViewById(R.id.number2);
        maxNumberInput.setVisibility(View.VISIBLE);

        minNumberInput.setHint("Minimum value");
        maxNumberInput.setHint("Maximum value");

        DatePicker simpleDatePicker = (DatePicker)findViewById(R.id.simpleDatePicker); // initiate a date picker
        simpleDatePicker.setVisibility(View.GONE);

        RadioGroup categorySelection = (RadioGroup) findViewById(R.id.radio_category);
        categorySelection.setVisibility(View.VISIBLE);

        final Button btnFindFact = (Button) findViewById(R.id.btnFindFact);

        instructions.setText("Select a category from which to generate a fun fact. Then enter a minimum and maximum value from which a number will be randomly generated");




        btnFindFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int minNumber;
                int maxNumber;

                try {
                    minNumber = Integer.parseInt(minNumberInput.getText().toString());

                } catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid number", Toast.LENGTH_SHORT);
                    toast.show();
                    minNumberInput.setText("");
                    return;
                }

                try {
                    maxNumber = Integer.parseInt(maxNumberInput.getText().toString());

                } catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid number", Toast.LENGTH_SHORT);
                    toast.show();
                    maxNumberInput.setText("");
                    return;
                }

                String url = "https://numbersapi.p.rapidapi.com/random/" + category + "/?min=" + minNumber + "&" + "max=" + maxNumber + "&fragment=true&json=true";


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
                            String year = "";

                            try {
                                obj = new JSONObject(myResponse);
                                final String text = obj.getString("text");
                                final String number = obj.getString("number");
                                if (category.equals("date")){
                                    year = obj.getString("year");
                                }

                                String finalYear = year;
                                RandomActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textYearMessage.setText(number);
                                        if (category.equals("date")){
                                            mTextViewResult.setText("In " + finalYear + ", " + text);
                                        } else {
                                            mTextViewResult.setText(text);
                                        }
                                    }
                                });

                            } catch (JSONException e) {
                                RandomActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTextViewResult.setText("No fun fact available for selected number :(");
                                    }
                                });
                                e.printStackTrace();
                            }


                        } else {
                            RandomActivity.this.runOnUiThread(new Runnable() {
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_trivia:
                if (checked)
                    category = "trivia";
                    break;
            case R.id.radio_math:
                if (checked)
                    category = "math";
                    break;
            case R.id.radio_date:
                if (checked)
                    category = "date";
                    break;
            case R.id.radio_year:
                if (checked)
                    category = "year";
                    break;
        }
    }
}


