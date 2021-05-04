package sg.edu.rp.id18044455.horoskopos;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class HomeFragment extends Fragment {

    FirebaseAuth fAuth;
    DatabaseReference root;
    FirebaseUser currUser;

    TextView tvGreetingH;
    Button btnYH, btnYCZ, btnCC, btnCZ, btnH;
    String greetingTime, uHoroscope, uZodiac;
    int horoscopeIndex, zodiacIndex, horoscopeDrawable, zodiacDrawable;

    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    String [] horoscopes = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    int [] horoscopeIcons = {R.drawable.aries, R.drawable.taurus, R.drawable.gemini, R.drawable.cancer, R.drawable.leo, R.drawable.virgo, R.drawable.libra, R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn, R.drawable.aquarius, R.drawable.pisces};
    String [] zodiacs = {"Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Sheep", "Monkey", "Rooster", "Dog", "Pig"};
    int [] zodiacIcons = {R.drawable.rat, R.drawable.ox, R.drawable.tiger, R.drawable.rabbit, R.drawable.dragon, R.drawable.snake, R.drawable.horse, R.drawable.sheep, R.drawable.monkey, R.drawable.rooster, R.drawable.dog, R.drawable.pig};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);

        getActivity().setTitle("Home");

        fAuth = FirebaseAuth.getInstance();
        currUser = fAuth.getCurrentUser();
        root = FirebaseDatabase.getInstance().getReference();


        tvGreetingH = homeView.findViewById(R.id.tvGreetingHome);
        btnYH = homeView.findViewById(R.id.btnYH);
        btnYCZ = homeView.findViewById(R.id.btnYCZ);
        btnCC = homeView.findViewById(R.id.btnCC);
        btnCZ = homeView.findViewById(R.id.btnCZ);
        btnH  = homeView.findViewById(R.id.btnH);



        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();

        updateUI();

        btnYH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new YourMonthlyFragment());
                fragmentTransaction.commit();
            }
        });

        btnYCZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new YourZodiacFragment());
                fragmentTransaction.commit();
            }
        });

        btnCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new CompatibilityFragment());
                fragmentTransaction.commit();
            }
        });

        btnCZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new ChineseZodiacFragment());
                fragmentTransaction.commit();
            }
        });

        btnH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new HoroscopeFragment());
                fragmentTransaction.commit();
            }
        });




        return homeView;
    }//end of onCreateView


    public void updateUI() {
        Calendar c = Calendar.getInstance();
        int currTime = c.get(Calendar.HOUR_OF_DAY);

        if(currTime >= 0 && currTime < 12){
            greetingTime = "Good Morning, ";
        }
        else if(currTime >= 12 && currTime < 16){
            greetingTime = "Good Afternoon, ";
        }
        else if(currTime >= 16 && currTime < 21){
            greetingTime = "Good Evening, ";
        }
        else if(currTime >= 21 && currTime < 24){
            greetingTime = "Good Night, ";
        }

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uHoroscope = snapshot.child("Users").child(currUser.getUid()).child("Horoscope").getValue().toString();
                uZodiac = snapshot.child("Users").child(currUser.getUid()).child("Zodiac").getValue().toString();
                tvGreetingH.setText(greetingTime + uHoroscope + ".");

                for(int i = 0; i < horoscopes.length; i++){
                    if(uHoroscope.equals(horoscopes[i])){
                        horoscopeIndex = i;
                    }
                }//end of for loop

                for(int i = 0; i < zodiacs.length; i++){
                    if(uZodiac.equals(zodiacs[i])){
                        zodiacIndex = i;
                    }
                }//end of for loop

                horoscopeDrawable = horoscopeIcons[horoscopeIndex];
                zodiacDrawable = zodiacIcons[zodiacIndex];


                btnYH.setCompoundDrawablesWithIntrinsicBounds(horoscopeDrawable, 0, 0, 0);
                btnYCZ.setCompoundDrawablesWithIntrinsicBounds(zodiacDrawable, 0, 0, 0);
                btnCC.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_people_24, 0, 0, 0);

            }//end of onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }//end of updateUI

}//end of class