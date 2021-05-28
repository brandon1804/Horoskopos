package sg.edu.rp.id18044455.horoskopos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ZodiacSelectionScreen extends AppCompatActivity {


    GridView gridView;

    FirebaseAuth fAuth;
    FirebaseDatabase db;
    DatabaseReference root;
    String uZodiac;


    String [] zodiacs = {"Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig"};
    String [] zodiacsY = {"1960, 1972, 1984, 1996, 2008", "1961, 1973, 1985, 1997, 2009", "1962, 1974, 1986, 1998, 2010", "1963, 1975, 1987, 1999, 2011", "1964, 1976, 1988, 2000, 2012", "1965, 1977, 1989, 2001, 2013", "1966, 1978, 1990, 2002, 2014", "1967, 1979, 1991, 2003, 2015", "1968, 1980, 1992, 2004, 2016", "1969, 1981, 1993, 2005, 2017", "1970, 1982, 1994, 2006, 2018", "1971, 1983, 1995, 2007, 2019"};
    int [] zodiacImages = {R.drawable.rat, R.drawable.ox, R.drawable.tiger, R.drawable.rabbit, R.drawable.dragon, R.drawable.snake, R.drawable.horse, R.drawable.sheep, R.drawable.monkey, R.drawable.rooster, R.drawable.dog, R.drawable.pig};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodiac_selection_screen);
        getSupportActionBar().hide();



        gridView = findViewById(R.id.gridView);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        root = FirebaseDatabase.getInstance().getReference().child("Users").child(fAuth.getCurrentUser().getUid());


        ZodiacSelectionScreen.ZodiacAdapter zodiacAdapter = new ZodiacSelectionScreen.ZodiacAdapter();
        gridView.setAdapter(zodiacAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ZodiacSelectionScreen.this);
                uZodiac = zodiacs[position];
                myBuilder.setTitle("You have chosen " + uZodiac);
                myBuilder.setMessage(getString(R.string.tvCZInfo));
                myBuilder.setCancelable(false);


                myBuilder.setPositiveButton("Confirm Selection", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        root.child("Zodiac").setValue(uZodiac);
                        Intent intent = new Intent(ZodiacSelectionScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                myBuilder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });


    }//end of onCreate

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    private class ZodiacAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return zodiacs.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View viewH = getLayoutInflater().inflate(R.layout.grid_zodiac,null);
            TextView zodiac = viewH.findViewById(R.id.tvZ);
            TextView zodiacDate = viewH.findViewById(R.id.tvZD);
            ImageView zodiacImage = viewH.findViewById(R.id.zView);

            zodiac.setText(zodiacs[i]);
            zodiacDate.setText(zodiacsY[i]);
            zodiacImage.setImageResource(zodiacImages[i]);
            return viewH;
        }

    }//end of class


}//end of class