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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HoroscopeSelectionScreen extends AppCompatActivity {

    GridView gridView;

    FirebaseAuth fAuth;
    FirebaseDatabase db;
    DatabaseReference root;
    String uHoroscope;


    String [] horoscopes = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    String [] horoscopeDates = {"21 Mar - 19 Apr", "20 Apr - 20 May", "21 May - 20 Jun", "21 Jun - 22 Jul", "23 Jul - 22 Aug", "23 Aug - 22 Sep", "23 Sep - 22 Oct", "23 Oct - 21 Nov", "22 Nov - 21 Dec", "22 Dec - 19 Jan", "20 Jan - 18 Feb", "19 Feb - 20 Mar"};
    int [] horoscopeImages = {R.drawable.aries, R.drawable.taurus, R.drawable.gemini, R.drawable.cancer, R.drawable.leo, R.drawable.virgo, R.drawable.libra, R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn, R.drawable.aquarius, R.drawable.pisces};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horoscope_selection_screen);
        getSupportActionBar().hide();



        gridView = findViewById(R.id.gridView);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        root = FirebaseDatabase.getInstance().getReference().child("Users").child(fAuth.getCurrentUser().getUid());


        HoroscopesAdapter horoscopesAdapter = new HoroscopesAdapter();
        gridView.setAdapter(horoscopesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(HoroscopeSelectionScreen.this);
                uHoroscope = horoscopes[position];
                myBuilder.setTitle("You have chosen " + uHoroscope);
                myBuilder.setMessage(getString(R.string.tvCHInfo));
                myBuilder.setCancelable(false);


                myBuilder.setPositiveButton("Confirm Selection", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        root.child("Horoscope").setValue(uHoroscope);
                        Intent intent = new Intent(HoroscopeSelectionScreen.this, ZodiacSelectionScreen.class);
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

    private class HoroscopesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return horoscopes.length;
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

            View viewH = getLayoutInflater().inflate(R.layout.grid_row,null);
            TextView horoscope = viewH.findViewById(R.id.tvH);
            TextView horoscopeDate = viewH.findViewById(R.id.tvHD);
            ImageView horoscopeImage = viewH.findViewById(R.id.hView);

            horoscope.setText(horoscopes[i]);
            horoscopeDate.setText(horoscopeDates[i]);
            horoscopeImage.setImageResource(horoscopeImages[i]);
            return viewH;
        }

    }//end of class


}//end of class