package com.example.imc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity{
    DecimalFormat decimal = new DecimalFormat("#.00");
    TextView num_imc, lbl_imc, title_imc;
    EditText input_age, input_height, input_weight;
    ImageView img_body;
    ImageButton btn_woman, btn_man;
    Button btn_calculate;
    String unit_lenght, unit_weight;
    boolean empty_input = false;
    boolean isWoman;
    int flag_select_gender = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num_imc = findViewById(R.id.imc_result);
        lbl_imc = findViewById(R.id.body_build_result);
        title_imc = findViewById(R.id.title_imc);

        btn_calculate = findViewById(R.id.btn_calculate);
        btn_woman = findViewById(R.id.btn_woman);
        btn_man = findViewById(R.id.btn_man);

        input_age = findViewById(R.id.input_age);
        input_height = findViewById(R.id.input_height);
        input_weight = findViewById(R.id.input_weight);

        img_body = findViewById(R.id.img_body);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner_weight = findViewById(R.id.spinner_weight);
        ArrayAdapter<CharSequence> adapter_weight = ArrayAdapter.createFromResource(this, R.array.weight_measures, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner_weight.setAdapter(adapter_weight);

        Spinner spinner_lenght = findViewById(R.id.spinner_lenght);
        ArrayAdapter<CharSequence> adapter_lenght = ArrayAdapter.createFromResource(this, R.array.lenght_measures, android.R.layout.simple_spinner_dropdown_item);
        spinner_lenght.setAdapter(adapter_lenght);

        title_imc.setVisibility(View.GONE);
        num_imc.setVisibility(View.GONE);
        lbl_imc.setVisibility(View.GONE);


        spinner_lenght.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unit_lenght = adapterView.getItemAtPosition(i).toString();
            }
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        spinner_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                unit_weight = adapterView.getItemAtPosition(i).toString();
            }
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });



        btn_woman.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onClick(View view) {
                btn_woman.setImageDrawable(getResources().getDrawable(R.drawable.icon_woman_color_all));
                btn_man.setImageDrawable(getResources().getDrawable(R.drawable.icon_man_color_any));
                flag_select_gender = 1;
                isWoman = true;
            }
        });


        btn_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onClick(View view) {
                btn_woman.setImageDrawable(getResources().getDrawable(R.drawable.icon_woman_color_any));
                btn_man.setImageDrawable(getResources().getDrawable(R.drawable.icon_man_color_all));
                flag_select_gender = 1;
                isWoman = false;
            }
        });


        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            public void onClick(View view) {
                try{
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(btn_calculate.getWindowToken(), 0);

                    if (Util.empty_input(input_age)) {
                        input_age.setError(getResources().getString(R.string.empty_input));
                        empty_input = true;

                    } else if (Util.empty_input(input_height)) {
                        input_height.setError(getResources().getString(R.string.empty_input));
                        empty_input = true;

                    } else if (Util.empty_input(input_weight)) {
                        input_weight.setError(getResources().getString(R.string.empty_input));
                        empty_input = true;

                    }


                    if(!empty_input){
                        if (flag_select_gender == 1) {

                            double height, weight, imc;
                            int age;

                            height = Double.parseDouble(input_height.getText().toString().trim());
                            weight = Double.parseDouble(input_weight.getText().toString().trim());
                            age = Integer.parseInt(input_age.getText().toString().trim());

                            imc = Util.calculate_imc(age, height, weight, unit_lenght, unit_weight);
                            num_imc.setText(String.valueOf((decimal.format(imc))));

                            double value_height = Double.parseDouble(input_height.getText().toString());
                            double value_weight = Double.parseDouble(input_weight.getText().toString());
                            int hedad = Integer.parseInt(input_age.getText().toString());

                            num_imc.setText(String.valueOf((decimal.format(imc))));
                            lbl_imc.setText(Util.health_state(age, isWoman, imc));

                            Toast.makeText(MainActivity.this, String.valueOf(isWoman), Toast.LENGTH_SHORT).show();


                            if(isWoman) {
                                if (lbl_imc.getText().equals(getResources().getString(R.string.normal))) {
                                    num_imc.setTextColor(getResources().getColor(R.color.green));
                                    lbl_imc.setTextColor(getResources().getColor(R.color.green));
                                    img_body.setImageDrawable(getResources().getDrawable(R.drawable.woman_color_all_second));
                                } else if (lbl_imc.getText().equals(getResources().getString(R.string.thin))) {
                                    num_imc.setTextColor(getResources().getColor(R.color.orange));
                                    lbl_imc.setTextColor(getResources().getColor(R.color.orange));
                                    img_body.setImageDrawable(getResources().getDrawable(R.drawable.woman_color_all_third));
                                } else if (lbl_imc.getText().equals(getResources().getString(R.string.overweight))) {
                                    num_imc.setTextColor(getResources().getColor(R.color.red));
                                    lbl_imc.setTextColor(getResources().getColor(R.color.red));
                                    img_body.setImageDrawable(getResources().getDrawable(R.drawable.woman_color_all_first));
                                }
                            }else{
                                if (lbl_imc.getText().equals(getResources().getString(R.string.normal))){
                                    num_imc.setTextColor(getResources().getColor(R.color.green));
                                    lbl_imc.setTextColor(getResources().getColor(R.color.green));
                                    img_body.setImageDrawable(getResources().getDrawable(R.drawable.man_color_second));
                                }else if (lbl_imc.getText().equals(getResources().getString(R.string.thin))){
                                    num_imc.setTextColor(getResources().getColor(R.color.orange));
                                    lbl_imc.setTextColor(getResources().getColor(R.color.orange));
                                    img_body.setImageDrawable(getResources().getDrawable(R.drawable.man_color_third));
                                }else if (lbl_imc.getText().equals(getResources().getString(R.string.overweight))){
                                    num_imc.setTextColor(getResources().getColor(R.color.red));
                                    lbl_imc.setTextColor(getResources().getColor(R.color.red));
                                    img_body.setImageDrawable(getResources().getDrawable(R.drawable.man_color_first));
                                }
                            }

                            title_imc.setVisibility(View.VISIBLE);
                            num_imc.setVisibility(View.VISIBLE);
                            lbl_imc.setVisibility(View.VISIBLE);
                            img_body.setVisibility(View.VISIBLE);

                            //else of (flag_select_gender == 1)
                        }else{
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.select_gender), Toast.LENGTH_SHORT).show();
                        }

                        //else of (!empty_input)
                    }else{
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.empty_input), Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "ERR_GLOBAL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }// ====================== End class MainActivity ======================//


   @SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_restore:
                input_age.setText("");
                input_height.setText("");
                input_weight.setText("");
                title_imc.setVisibility(View.GONE);
                num_imc.setVisibility(View.GONE);
                lbl_imc.setVisibility(View.GONE);
                img_body.setVisibility(View.GONE);
                btn_woman.setImageDrawable(getResources().getDrawable(R.drawable.icon_woman_color_any));
                btn_man.setImageDrawable(getResources().getDrawable(R.drawable.icon_man_color_any));
                flag_select_gender = 0;
                return true;

            case R.id.btn_exit:
                Util.alertDialog(MainActivity.this,
                        getResources().getString(R.string.msg_exit_app),
                        getResources().getString(R.string.accept),
                        getResources().getString(R.string.cancel));

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu){
        //Print two option menu in toolbar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}