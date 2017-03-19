package org.jdhp.android.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

// TODO: data percistance of "rate"
// TODO: add a button to get the "rate" from internet; it replace the default rate
// TODO: chose currencies from a list + percistance of each currency couple (or rename the app CurrencyConverterYenEuro)

public class MainActivity extends AppCompatActivity {

    public static final double DEFAULT_RATE = 0.0082;
    double rate;

    EditText editTextYen;
    EditText editTextEuro;
    EditText editTextRate;

//    Button buttonConvert;

    EditText lastEditTextFocuced;

    void updateYen() {
        String strYen = editTextYen.getText().toString();
        if(strYen.isEmpty()) {
            editTextEuro.setText("");
        } else {
            double yen = Double.parseDouble(strYen);
            double euro = yen * rate;

            editTextEuro.setText(String.format("%.2f", euro));
            //editTextEuro.setText(Double.toString(euro));
        }
    }

    void updateEuro() {
        String strEuro = editTextEuro.getText().toString();
        if(strEuro.isEmpty()) {
            editTextYen.setText("");
        } else {
            double euro = Double.parseDouble(strEuro);
            double yen = euro / rate;

            editTextYen.setText(String.format("%.2f", yen));
            //editTextYen.setText(Double.toString(yen));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rate = DEFAULT_RATE;

        editTextYen = (EditText) findViewById(R.id.et_yen);
        editTextEuro = (EditText) findViewById(R.id.et_euro);
        editTextRate = (EditText) findViewById(R.id.et_rate);

        lastEditTextFocuced = null;

        editTextRate.setText(Double.toString(DEFAULT_RATE));
        editTextRate.setHint("Default = " + Double.toString(DEFAULT_RATE));

        ////////////////////////////////////////////////////////////////////////////////////////////
        // editTextYen Listeners ///////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////

        editTextYen.addTextChangedListener(new TextWatcher() {
            // http://stackoverflow.com/questions/4310525/counting-chars-in-edittext-changed-listener
            // http://stackoverflow.com/questions/11134144/android-edittext-onchange-listener
            public void afterTextChanged(Editable s) {
                if(editTextYen.hasFocus()) {
                    updateYen();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editTextYen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    lastEditTextFocuced = editTextYen;
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        // editTextEuro Listeners //////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////

        editTextEuro.addTextChangedListener(new TextWatcher() {
            // http://stackoverflow.com/questions/4310525/counting-chars-in-edittext-changed-listener
            // http://stackoverflow.com/questions/11134144/android-edittext-onchange-listener
            public void afterTextChanged(Editable s) {
                if(editTextEuro.hasFocus()) {
                    updateEuro();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editTextEuro.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus) {
                    lastEditTextFocuced = editTextEuro;
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////
        // editTextRate Listeners //////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////

        editTextRate.addTextChangedListener(new TextWatcher() {
            // http://stackoverflow.com/questions/4310525/counting-chars-in-edittext-changed-listener
            // http://stackoverflow.com/questions/11134144/android-edittext-onchange-listener
            public void afterTextChanged(Editable s) {
                if(editTextRate.hasFocus()) {
                    String strRate = editTextRate.getText().toString();

                    if (strRate.isEmpty()) {
                        rate = DEFAULT_RATE;
                    } else {
                        rate = Double.parseDouble(strRate);
                        if (rate <= 0) {
                            rate = DEFAULT_RATE;
                        }
                    }

                    if(lastEditTextFocuced == editTextYen) {
                        updateYen();
                    } else if(lastEditTextFocuced == editTextEuro) {
                        updateEuro();
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        editTextRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus) {
                    editTextRate.setText(Double.toString(rate));
                }
            }
        });

//        buttonConvert = (Button) findViewById(R.id.btn_convert);
//        buttonConvert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ...
//            }
//        });
    }
}
