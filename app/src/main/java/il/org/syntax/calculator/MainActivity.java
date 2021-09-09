package il.org.syntax.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView startEdit;
    int lastNum;
    String Lastone;
    boolean actionSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnLAN=findViewById(R.id.lan);
        Button btnOne=findViewById(R.id.num_1);
        Button btnTwo=findViewById(R.id.num_2);
        Button btnThree=findViewById(R.id.num_3);
        Button btnFour=findViewById(R.id.num_4);
        Button btnFive=findViewById(R.id.num_5);
        Button btnSix=findViewById(R.id.num_6);
        Button btnSeven=findViewById(R.id.num_7);
        Button btnEight=findViewById(R.id.num_8);
        Button btnNine=findViewById(R.id.num_9);
        Button btnZero=findViewById(R.id.num_0);
        Button btnAC=findViewById(R.id.refresh);
        Button btnMinus=findViewById(R.id.minus);
        Button btnPlas=findViewById(R.id.plas1);
        Button btnDivision=findViewById(R.id.division);
        Button btnFra=findViewById(R.id.fraction);
        Button btnPoint=findViewById(R.id.point);
        Button btnDouble=findViewById(R.id.double2);
        Button btnEqual=findViewById(R.id.equal);
        startEdit=findViewById(R.id.txt_view);

        NumbersOfClickLisner numbersOfClickLisners= new NumbersOfClickLisner();
        btnOne.setOnClickListener(numbersOfClickLisners);
        btnTwo.setOnClickListener(numbersOfClickLisners);
        btnThree.setOnClickListener(numbersOfClickLisners);
        btnFour.setOnClickListener(numbersOfClickLisners);
        btnFive.setOnClickListener(numbersOfClickLisners);
        btnSix.setOnClickListener(numbersOfClickLisners);
        btnSeven.setOnClickListener(numbersOfClickLisners);
        btnEight.setOnClickListener(numbersOfClickLisners);
        btnNine.setOnClickListener(numbersOfClickLisners);
        btnZero.setOnClickListener(numbersOfClickLisners);


        ActionsOfClickLisner asctionoflisners= new ActionsOfClickLisner();
        btnDivision.setOnClickListener(asctionoflisners);
        btnMinus.setOnClickListener(asctionoflisners);
        btnFra.setOnClickListener(asctionoflisners);
        btnPlas.setOnClickListener(asctionoflisners);
        btnDouble.setOnClickListener(asctionoflisners);

        /*btnLAN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context;
                Resources resources;
                context = LocaleHelper.setLocale(MainActivity.this, "ar");
                resources = context.getResources();
            }
        });*/

        btnAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEdit.setText("");
            }
        });
        btnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEdit.setText(startEdit.getText()+".");
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Lastone==null) return;
              String numStr=startEdit.getText().toString();
              int newN=Integer.parseInt(numStr);
              switch (Lastone)
              {
                  case "+":
                      startEdit.setText(newN+lastNum+"");
                      break;
                  case "/":
                      if(newN==0) return;
                      startEdit.setText(newN/(lastNum*1.0)+"");
                      break;
                  case "-":
                      startEdit.setText(newN-lastNum+"");
                      break;
                  case "*":
                      startEdit.setText(newN*lastNum+"");
                      break;
                  case "^":
                      if(newN==0) return;
                      startEdit.setText(Math.pow(lastNum,newN)+"");
                      break;
              }
              actionSelect=true;
            }
        }
        );
    }
    private class NumbersOfClickLisner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String numTemp=((Button)v).getText().toString();
            if (actionSelect) {
                startEdit.setText(numTemp);
                actionSelect=false;
            }
            else {
                startEdit.setText(startEdit.getText() + numTemp);
            }
        }
    }

    private class ActionsOfClickLisner implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String numStr=startEdit.getText().toString();
            lastNum=Integer.parseInt(numStr);

            Lastone=((Button)v).getText().toString();
            actionSelect=true;
        }
    }
}
  class LocaleHelper {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static void onCreate(Context context) {

        String lang;
        if(getLanguage(context).isEmpty()){
            lang = getPersistedData(context, Locale.getDefault().getLanguage());
        }else {
            lang = getLanguage(context);
        }

        setLocale(context, lang);
    }

    public static void onCreate(Context context, String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        setLocale(context, lang);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static Context setLocale(Context context, String language) {
        persist(context, language);
        updateResources(context, language);
        return context;
    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    private static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}