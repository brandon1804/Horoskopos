package sg.edu.rp.id18044455.horoskopos;

import android.media.Rating;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ZodiacFragment extends Fragment {

    FirebaseAuth fAuth;
    DatabaseReference root;
    FirebaseUser currUser;


    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    String uZodiac;
    int zodiacIndex;

    ImageView zbackBtn, zIconView;
    TextView  tvZTitle, tvZInfo, tvOverall, tvWealth, tvCareer, tvHealth, tvLove;
    RatingBar rtO, rtC, rtW, rtH, rtL;


    String [] zodiacs = {"Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig"};
    String [] zodiacsY = {"1960, 1972, 1984, 1996, 2008", "1961, 1973, 1985, 1997, 2009", "1962, 1974, 1986, 1998, 2010", "1963, 1975, 1987, 1999, 2011", "1964, 1976, 1988, 2000, 2012", "1965, 1977, 1989, 2001, 2013", "1966, 1978, 1990, 2002, 2014", "1967, 1979, 1991, 2003, 2015", "1968, 1980, 1992, 2004, 2016", "1969, 1981, 1993, 2005, 2017", "1970, 1982, 1994, 2006, 2018", "1971, 1983, 1995, 2007, 2019"};
    int [] zodiacImages = {R.drawable.rat, R.drawable.ox, R.drawable.tiger, R.drawable.rabbit, R.drawable.dragon, R.drawable.snake, R.drawable.horse, R.drawable.sheep, R.drawable.monkey, R.drawable.rooster, R.drawable.dog, R.drawable.pig};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View zodiacView = inflater.inflate(R.layout.fragment_zodiac, container, false);

        getActivity().setTitle("Zodiac Forecast");
        fAuth = FirebaseAuth.getInstance();
        currUser = fAuth.getCurrentUser();
        root = FirebaseDatabase.getInstance().getReference();

        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();

        zbackBtn = zodiacView.findViewById(R.id.zbackBtn);
        zIconView = zodiacView.findViewById(R.id.zIconView);

        tvZTitle = zodiacView.findViewById(R.id.tvZTitle);
        tvZInfo = zodiacView.findViewById(R.id.tvZInfo);



        tvOverall = zodiacView.findViewById(R.id.tvOverall);
        tvCareer = zodiacView.findViewById(R.id.tvCareer);
        tvWealth = zodiacView.findViewById(R.id.tvWealth);
        tvHealth = zodiacView.findViewById(R.id.tvHealth);
        tvLove = zodiacView.findViewById(R.id.tvLove);

        rtO = zodiacView.findViewById(R.id.ratingBarO);
        rtC = zodiacView.findViewById(R.id.ratingBarC);
        rtW = zodiacView.findViewById(R.id.ratingBarW);
        rtH = zodiacView.findViewById(R.id.ratingBarH);
        rtL = zodiacView.findViewById(R.id.ratingBarL);

        updateUI();

        zbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new ChineseZodiacFragment());
                fragmentTransaction.commit();
            }
        });


        return zodiacView;
    }//end of onCreateView


    public void updateUI(){
        Bundle bundle = this.getArguments();

        if(bundle != null){
            uZodiac = bundle.getString("Zodiac");
        }


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(int i = 0; i < zodiacs.length; i++){
                    if(uZodiac.equals(zodiacs[i])){
                        zodiacIndex = i;
                    }
                }//end of for loop

                zIconView.setImageResource(zodiacImages[zodiacIndex]);
                tvZTitle.setText(zodiacs[zodiacIndex]);
                tvZInfo.setText(zodiacsY[zodiacIndex]);

                tvOverall.setText(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("Overall").getValue().toString());
                rtO.setRating(Float.parseFloat(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("OverallScore").getValue().toString()));

                tvCareer.setText(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("Career").getValue().toString());
                rtC.setRating(Float.parseFloat(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("CareerScore").getValue().toString()));

                tvWealth.setText(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("Wealth").getValue().toString());
                rtW.setRating(Float.parseFloat(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("WealthScore").getValue().toString()));

                tvHealth.setText(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("Health").getValue().toString());
                rtH.setRating(Float.parseFloat(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("HealthScore").getValue().toString()));

                tvLove.setText(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("Love").getValue().toString());
                rtL.setRating(Float.parseFloat(snapshot.child("HoroscopeData").child("2021Readings").child("ChineseZodiac").child(uZodiac).child("LoveScore").getValue().toString()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }//end of updateUI


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }


}//end of class