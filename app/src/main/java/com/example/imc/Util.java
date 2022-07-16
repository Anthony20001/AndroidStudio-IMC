package com.example.imc;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Util extends AppCompatActivity {
    private static String state;

    public static String health_state(int age, boolean isWoman, double imc){
                        if(isWoman){
                            state = (age>0 && age<=24) ? body_build(imc, 19, 24):
                            (age>=25 && age<=34) ? body_build(imc, 20, 35):
                            (age>=35 && age<=44) ? body_build(imc, 21, 26):
                            (age>=45 && age<=54) ? body_build(imc, 22, 27):
                            (age>=55 && age<=64) ? body_build(imc, 23, 28):
                            (age>=65) ? body_build(imc, 25, 30) : "Ingrese una edad válidad";

                        }else{
                            state = (age>0 && age<16) ? body_build(imc, 19, 24):
                            (age>=16 && age<=18) ? body_build(imc, 20, 25):
                            (age>=19 && age<=24) ? body_build(imc, 21, 26):
                            (age>=25 && age<=34) ? body_build(imc, 22, 27):
                            (age>=35 && age<=44) ? body_build(imc, 23, 28):
                            (age>=45 && age<=54) ? body_build(imc, 23, 29):
                            (age>=55 && age<=64) ? body_build(imc, 24, 29):
                            (age>=65) ? body_build(imc, 25, 30) : "Ingrese una edad válidad";
                        }

                        return state;
    }


    public static String body_build(double imc, double min_normal, double max_normal){
        return (imc<min_normal) ? "Delgadez" : (imc>max_normal) ? "Sobrepeso" : "Normal";
    }


    public static boolean empty_input(EditText input){
        return input.getText().toString().trim().equals("");
    }


    public static double calculate_imc(int age, double height, double weight, String unit_lenght, String unit_weight){

        height = (unit_lenght.equals("PL")) ? height / 39.37 :
                (unit_lenght.equals("CM")) ? height / 100 : height;
        weight = (unit_weight.equals("LB")) ? weight / 2.2 : weight;

        return weight / Math.pow(height, 2);
    }


    public static void alertDialog(Activity activity, String message, String text_btn_accept, String text_btn_cancel){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton(text_btn_accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                })
                .setNegativeButton(text_btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
        builder.create().show();
    }

}
