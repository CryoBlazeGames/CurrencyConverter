package com.cryoblaze.currencyconverter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText primaryCurrencyText;
    private EditText secondaryCurrencyText;

    private EditText inAmount;
    private EditText outAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        primaryCurrencyText = findViewById(R.id.Currency1);
        secondaryCurrencyText = findViewById(R.id.Currency2);

        final Button switchCurrencies = findViewById(R.id.switchCurrency);
        final Button convertCurrencies = findViewById(R.id.convertCurrency);

        inAmount = findViewById(R.id.amountFrom);
        outAmount = findViewById(R.id.amountTo);

        final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Error");
        dlgAlert.setMessage("One of the currency input field is empty!");
        dlgAlert.setCancelable(true);

        final AlertDialog.Builder dlgsAlert = new AlertDialog.Builder(this);
        dlgsAlert.setTitle("Error");
        dlgsAlert.setMessage("Both Currency types cannot be same!");
        dlgsAlert.setCancelable(true);

        final AlertDialog.Builder dialogAlert = new AlertDialog.Builder(this);
        dialogAlert.setTitle("Error");
        dialogAlert.setMessage("The currencies entered are not supported by the currency converter!");
        dialogAlert.setCancelable(true);

        dlgAlert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Discuss The Dialog
                    }
                });

        dlgsAlert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Discuss The Dialog
                    }
                });

        dialogAlert.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Discuss The Dialog
                    }
                });

        switchCurrencies.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                if ((!primaryCurrencyText.getText().toString().isEmpty() && !secondaryCurrencyText.getText().toString().isEmpty()) && !primaryCurrencyText.getText().toString().equals(secondaryCurrencyText.getText().toString()))
                {
                    switchText(false);
                }
                else if ((primaryCurrencyText.getText().toString().equals("") && !secondaryCurrencyText.getText().toString().equals("")) || (!primaryCurrencyText.getText().toString().equals("") && secondaryCurrencyText.getText().toString().equals("")))
                {
                    dlgAlert.create().show();
                }
                else if (primaryCurrencyText.getText().toString().isEmpty()
                        && secondaryCurrencyText.getText().toString().isEmpty()
                        && primaryCurrencyText.getText().toString().equals(secondaryCurrencyText.getText().toString()))
                {
                    switchText(true);
                }
                else if (!primaryCurrencyText.getText().toString().isEmpty()
                        && !secondaryCurrencyText.getText().toString().isEmpty()
                        && primaryCurrencyText.getText().toString().equals(secondaryCurrencyText.getText().toString()))
                {
                    dlgsAlert.create().show();
                }
                else
                {
                    switchText(true);
                }
            }
        });

        convertCurrencies.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                final float exchangeRate;
                final float exchangedValue;
                final String supportedCurrencies[] = {"USD", "INR", "NZD", "AUD", "CAD"};

                // Makes sure that there is a value in amount field
                if (!inAmount.getText().toString().isEmpty())
                {
                    if ((containsValue(supportedCurrencies, primaryCurrencyText.getText().toString()) && containsValue(supportedCurrencies, secondaryCurrencyText.getText().toString())))
                    {
                        exchangeRate = GetExchangeRates(primaryCurrencyText.getText().toString(), secondaryCurrencyText.getText().toString());
                        exchangedValue = exchangeRate * Float.parseFloat(inAmount.getText().toString());
                        outAmount.setText(String.valueOf(exchangedValue));
                    }
                    else if ((!containsValue(supportedCurrencies, primaryCurrencyText.getText().toString()) && containsValue(supportedCurrencies, secondaryCurrencyText.getText().toString()))
                            || (containsValue(supportedCurrencies, primaryCurrencyText.getText().toString()) && !containsValue(supportedCurrencies, secondaryCurrencyText.getText().toString())))
                    {
                        if (primaryCurrencyText.getText().toString().isEmpty()
                                && secondaryCurrencyText.getText().toString().isEmpty())
                        {
                        }
                        else
                        {
                            dialogAlert.create().show();
                        }
                    }
                    else
                    {
                        dialogAlert.create().show();
                    }
                }
                else
                {
                    if ((containsValue(supportedCurrencies, primaryCurrencyText.getText().toString()) && containsValue(supportedCurrencies, secondaryCurrencyText.getText().toString())))
                    {
                        exchangeRate = GetExchangeRates(primaryCurrencyText.getText().toString(), secondaryCurrencyText.getText().toString());
                        exchangedValue = exchangeRate * 0;
                        outAmount.setText(String.valueOf(exchangedValue));
                    }
                }
            }
        });
    }

    /* Switch the texts of currency inputs */
    private void switchText(boolean isEmpty)
    {
        final String newPrimaryText;
        final String newSecondaryText;

        if (!isEmpty)
        {
            newPrimaryText = secondaryCurrencyText.getText().toString();
            newSecondaryText = primaryCurrencyText.getText().toString();

            // Switch the text and hints of both fields
            primaryCurrencyText.setText(newPrimaryText);
            primaryCurrencyText.setHint(newPrimaryText);
            secondaryCurrencyText.setText(newSecondaryText);
            secondaryCurrencyText.setHint(newSecondaryText);
        }
        else
        {
            newPrimaryText = secondaryCurrencyText.getHint().toString();
            newSecondaryText = primaryCurrencyText.getHint().toString();

            // Switch the text and hints of both fields
            primaryCurrencyText.setHint(newPrimaryText);
            secondaryCurrencyText.setHint(newSecondaryText);
        }
    }

    /* Get's the exchange rates for the currency you are looking for */
    private float GetExchangeRates(String fromCurrency, String toCurrency)
    {
        /* Currency Exchange Rates for each currency type! */
        /*
        * More currencies can be added down here,
        * NOTE: make sure all currencies have exchange rate for all other currencies
        * for example: Map<String, String> gbp = new HashMap<>();
        * now make sure you add this "GBP" in usd, cad, nzd, inr and aud as well
        * and all the other these currencies in gbp.
        * */
        Map<String, String> usd = new HashMap<>();
        usd.put("INR", "71.50");
        usd.put("AUD", "1.49");
        usd.put("NZD", "1.55");
        usd.put("CAD", "1.32");

        Map<String, String> cad = new HashMap<>();
        cad.put("INR", "54.01");
        cad.put("AUD", "1.13");
        cad.put("NZD", "1.17");
        cad.put("USD", "0.76");

        Map<String, String> aud = new HashMap<>();
        aud.put("INR", "47.85");
        aud.put("USD", "0.67");
        aud.put("NZD", "1.04");
        aud.put("CAD", "0.89");

        Map<String, String> nzd = new HashMap<>();
        nzd.put("INR", "46.21");
        nzd.put("CAD", "0.86");
        nzd.put("AUD", "0.97");
        nzd.put("USD", "0.65");

        Map<String, String> inr = new HashMap<>();
        inr.put("USD", "0.014");
        inr.put("AUD", "0.021");
        inr.put("NZD", "0.022");
        inr.put("CAD", "0.019");
        /*------------------------------------------------------------------*/

        /* Add all the currency's and their exchanges rates */
        Map<String, Map<String, String>> mapArray = new HashMap<>();
        mapArray.put("USD", usd);
        mapArray.put("CAD", cad);
        mapArray.put("AUD", aud);
        mapArray.put("NZD", nzd);
        mapArray.put("INR", inr);

        String exchangeValue = "";

        /* Finds the key based on currency you want to convert from */
        for (Map.Entry<String, Map<String, String>> inCurrency : mapArray.entrySet())
        {
            /* Checks if key and the currency you want to convert from matches */
            if (inCurrency.getKey().equals(fromCurrency))
            {
                /* If the key matches, find the exchange rate based on the currency you want to convert to */
                for (Map.Entry<String, String> maps : inCurrency.getValue().entrySet())
                {
                    if (maps.getKey().equals(toCurrency))
                    {
                        exchangeValue = maps.getValue();
                        break;
                    }
                }
                break;
            }
        }

        /* Returns the exchange rates */
        return Float.parseFloat(exchangeValue);
    }

    /* This function checks if array contains the value you are looking for! */
    private boolean containsValue(String[] arr, String val)
    {
        boolean containValue = false;
        for (String element : arr)
        {
            if (element.equals(val)){
                containValue = true;
                break;
            }
            containValue = false;
        }
        return containValue;
    }
}
