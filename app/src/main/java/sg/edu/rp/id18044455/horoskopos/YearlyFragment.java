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


public class YearlyFragment extends Fragment {

    FirebaseAuth fAuth;
    DatabaseReference root;
    FirebaseUser currUser;

    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    ImageView backBtnY, hIconViewY, shareBtnY;
    TextView  tvMonthlyY, tvReadingY, tvLColY, tvLNumY, tvHTitleY, tvHInfoY;
    String uHoroscope;
    int horoscopeIndex;


    String [] horoscopes = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    String [] horoscopeDates = {"21 Mar - 19 Apr", "20 Apr - 20 May", "21 May - 20 Jun", "21 Jun - 22 Jul", "23 Jul - 22 Aug", "23 Aug - 22 Sep", "23 Sep - 22 Oct", "23 Oct - 21 Nov", "22 Nov - 21 Dec", "22 Dec - 19 Jan", "20 Jan - 18 Feb", "19 Feb - 20 Mar"};
    int [] horoscopeImages = {R.drawable.aries, R.drawable.taurus, R.drawable.gemini, R.drawable.cancer, R.drawable.leo, R.drawable.virgo, R.drawable.libra, R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn, R.drawable.aquarius, R.drawable.pisces};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View yearlyView = inflater.inflate(R.layout.fragment_yearly, container, false);

        getActivity().setTitle("2021 Horoscope Prediction");
        fAuth = FirebaseAuth.getInstance();
        currUser = fAuth.getCurrentUser();
        root = FirebaseDatabase.getInstance().getReference();

        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();

        backBtnY = yearlyView.findViewById(R.id.backBtnY);
        hIconViewY = yearlyView.findViewById(R.id.hIconViewY);
        shareBtnY = yearlyView.findViewById(R.id.shareBtnY);

        tvHTitleY = yearlyView.findViewById(R.id.tvHTitleY);
        tvHInfoY = yearlyView.findViewById(R.id.tvHInfoY);

        tvMonthlyY = yearlyView.findViewById(R.id.tvMonthlyY);
        tvReadingY = yearlyView.findViewById(R.id.tvReadingY);
        tvLColY = yearlyView.findViewById(R.id.tvLColY);
        tvLNumY = yearlyView.findViewById(R.id.tvLNumY);


        updateUI();

        backBtnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new HoroscopeFragment());
                fragmentTransaction.commit();
            }
        });

        shareBtnY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Yearly Horoscope Reading", tvReadingY.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), uHoroscope + " Yearly Reading Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        tvMonthlyY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Horoscope", uHoroscope);

                MonthlyFragment monthlyFragment = new MonthlyFragment();
                monthlyFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.containerFragment, monthlyFragment);
                fragmentTransaction.commit();
            }
        });

        return yearlyView;
    }//end of onCreateView

    public void updateUI() {

        Bundle bundle = this.getArguments();

        if(bundle != null){
            uHoroscope = bundle.getString("YHoroscope");
        }

        for(int i = 0; i < horoscopes.length; i++){
            if(uHoroscope.equals(horoscopes[i])){
                horoscopeIndex = i;
            }
        }//end of for loop

        hIconViewY.setImageResource(horoscopeImages[horoscopeIndex]);
        tvHTitleY.setText(horoscopes[horoscopeIndex]);
        tvHInfoY.setText(horoscopeDates[horoscopeIndex]);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvReadingY.setText(snapshot.child("HoroscopeData").child("2021Readings").child("Yearly").child(uHoroscope).child("Description").getValue().toString());
                tvLColY.setText("Lucky Colour - " + snapshot.child("HoroscopeData").child("2021Readings").child("Yearly").child(uHoroscope).child("Lucky Colour").getValue().toString());
                tvLNumY.setText("Lucky Number - " + snapshot.child("HoroscopeData").child("2021Readings").child("Yearly").child(uHoroscope).child("Lucky Number").getValue().toString());
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