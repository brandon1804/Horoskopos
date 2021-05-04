package sg.edu.rp.id18044455.horoskopos;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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


public class YourYearlyFragment extends Fragment {

    FirebaseAuth fAuth;
    DatabaseReference root;
    FirebaseUser currUser;

    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    ImageView yryhIconView, yrybackBtn, yryShareBtn;
    TextView tvYourMonthly, tvYReadingY, tvYLColY, tvYLNumY, yrytvHTitle, yrytvHInfo;

    String uHoroscope;
    int horoscopeIndex;

    String [] horoscopes = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    String [] horoscopeDates = {"21 Mar - 19 Apr", "20 Apr - 20 May", "21 May - 20 Jun", "21 Jun - 22 Jul", "23 Jul - 22 Aug", "23 Aug - 22 Sep", "23 Sep - 22 Oct", "23 Oct - 21 Nov", "22 Nov - 21 Dec", "22 Dec - 19 Jan", "20 Jan - 18 Feb", "19 Feb - 20 Mar"};
    int [] horoscopeImages = {R.drawable.aries, R.drawable.taurus, R.drawable.gemini, R.drawable.cancer, R.drawable.leo, R.drawable.virgo, R.drawable.libra, R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn, R.drawable.aquarius, R.drawable.pisces};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View yourYearlyView = inflater.inflate(R.layout.fragment_your_yearly, container, false);

        getActivity().setTitle("Your 2021 Horoscope Prediction");
        fAuth = FirebaseAuth.getInstance();
        currUser = fAuth.getCurrentUser();
        root = FirebaseDatabase.getInstance().getReference();

        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();

        yryhIconView = yourYearlyView.findViewById(R.id.yryhIconView);
        yrybackBtn = yourYearlyView.findViewById(R.id.yrybackBtn);
        yryShareBtn = yourYearlyView.findViewById(R.id.yryShareBtn);

        tvYourMonthly = yourYearlyView.findViewById(R.id.tvYourMonthly);
        tvYReadingY = yourYearlyView.findViewById(R.id.tvYReadingY);
        tvYLColY = yourYearlyView.findViewById(R.id.tvYLColY);
        tvYLNumY = yourYearlyView.findViewById(R.id.tvYLNumY);

        yrytvHTitle = yourYearlyView.findViewById(R.id.yrytvHTitle);
        yrytvHInfo = yourYearlyView.findViewById(R.id.yrytvHInfo);

        updateUI();


        yrybackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        yryShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Yearly Horoscope Reading", tvYReadingY.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), uHoroscope + " Yearly Reading Copied!", Toast.LENGTH_SHORT).show();
            }
        });


        tvYourMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new YourMonthlyFragment());
                fragmentTransaction.commit();
            }
        });

        return yourYearlyView;
    }//end of onCreateView

    public void updateUI() {

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uHoroscope = snapshot.child("Users").child(currUser.getUid()).child("Horoscope").getValue().toString();

                for(int i = 0; i < horoscopes.length; i++){
                    if(uHoroscope.equals(horoscopes[i])){
                        horoscopeIndex = i;
                    }
                }//end of for loop

                yryhIconView.setImageResource(horoscopeImages[horoscopeIndex]);
                yrytvHTitle.setText(horoscopes[horoscopeIndex]);
                yrytvHInfo.setText(horoscopeDates[horoscopeIndex]);

                tvYReadingY.setText(snapshot.child("HoroscopeData").child("2021Readings").child("Yearly").child(uHoroscope).child("Description").getValue().toString());
                tvYLColY.setText("Lucky Colour - " + snapshot.child("HoroscopeData").child("2021Readings").child("Yearly").child(uHoroscope).child("Lucky Colour").getValue().toString());
                tvYLNumY.setText("Lucky Number - " + snapshot.child("HoroscopeData").child("2021Readings").child("Yearly").child(uHoroscope).child("Lucky Number").getValue().toString());
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