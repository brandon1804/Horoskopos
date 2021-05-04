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


public class MonthlyFragment extends Fragment {


    FirebaseAuth fAuth;
    DatabaseReference root;

    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    ImageView hIconView, backBtn, shareBtn;
    TextView tvYearly, tvReading, tvGD, tvBD, tvHTitle, tvHInfo;
    String currMonthStr;
    String uHoroscope;
    int horoscopeIndex;

    String [] horoscopes = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    String [] horoscopeDates = {"21 Mar - 19 Apr", "20 Apr - 20 May", "21 May - 20 Jun", "21 Jun - 22 Jul", "23 Jul - 22 Aug", "23 Aug - 22 Sep", "23 Sep - 22 Oct", "23 Oct - 21 Nov", "22 Nov - 21 Dec", "22 Dec - 19 Jan", "20 Jan - 18 Feb", "19 Feb - 20 Mar"};
    int [] horoscopeImages = {R.drawable.aries, R.drawable.taurus, R.drawable.gemini, R.drawable.cancer, R.drawable.leo, R.drawable.virgo, R.drawable.libra, R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn, R.drawable.aquarius, R.drawable.pisces};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View monthlyView = inflater.inflate(R.layout.fragment_monthly, container, false);

        fAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();

        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();

        backBtn = monthlyView.findViewById(R.id.backBtn);
        hIconView = monthlyView.findViewById(R.id.hIconView);
        shareBtn = monthlyView.findViewById(R.id.shareBtn);

        tvHTitle = monthlyView.findViewById(R.id.tvHTitle);
        tvHInfo = monthlyView.findViewById(R.id.tvHInfo);
        tvYearly = monthlyView.findViewById(R.id.tvYearly);
        tvReading = monthlyView.findViewById(R.id.tvReading);
        tvGD = monthlyView.findViewById(R.id.tvGD);
        tvBD = monthlyView.findViewById(R.id.tvBD);

        updateUI();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new HoroscopeFragment());
                fragmentTransaction.commit();
            }
        });


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Monthly Horoscope Reading", tvReading.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), uHoroscope + " Monthly Reading Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        tvYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("YHoroscope", uHoroscope);

                YearlyFragment yearlyFragment = new YearlyFragment();
                yearlyFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.containerFragment, yearlyFragment);
                fragmentTransaction.commit();
            }
        });



        return monthlyView;
    }//end of onCreateView

    public void updateUI() {

        Bundle bundle = this.getArguments();

        if(bundle != null){
            uHoroscope = bundle.getString("Horoscope");
        }

        Calendar c = Calendar.getInstance();
        int currMonth = c.get(Calendar.MONTH);


        for(int i = 0; i < horoscopes.length; i++){
            if(uHoroscope.equals(horoscopes[i])){
                horoscopeIndex = i;
            }
        }//end of for loop

        hIconView.setImageResource(horoscopeImages[horoscopeIndex]);
        tvHTitle.setText(horoscopes[horoscopeIndex]);
        tvHInfo.setText(horoscopeDates[horoscopeIndex]);


        if(currMonth == Calendar.JANUARY){
            currMonthStr = "January";
        }
        else if(currMonth == Calendar.FEBRUARY){
            currMonthStr = "February";
        }
        else if(currMonth == Calendar.MARCH){
            currMonthStr = "March";
        }
        else if(currMonth == Calendar.APRIL){
            currMonthStr = "April";
        }
        else if(currMonth == Calendar.MAY){
            currMonthStr = "May";
        }
        else if(currMonth == Calendar.JUNE){
            currMonthStr = "June";
        }
        else if(currMonth == Calendar.JULY){
            currMonthStr = "July";
        }
        else if(currMonth == Calendar.AUGUST){
            currMonthStr = "August";
        }
        else if(currMonth == Calendar.SEPTEMBER){
            currMonthStr = "September";
        }
        else if(currMonth == Calendar.OCTOBER){
            currMonthStr = "October";
        }
        else if(currMonth == Calendar.NOVEMBER){
            currMonthStr = "November";
        }
        else if(currMonth == Calendar.DECEMBER){
            currMonthStr = "December";
        }

        getActivity().setTitle(currMonthStr + " Horoscope Prediction");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvReading.setText(snapshot.child("HoroscopeData").child("2021Readings").child("Monthly").child(currMonthStr).child(uHoroscope).child("Description").getValue().toString());
                tvGD.setText("Good Days - " + snapshot.child("HoroscopeData").child("2021Readings").child("Monthly").child(currMonthStr).child(uHoroscope).child("GoodDays").getValue().toString());
                tvBD.setText("Bad Days -  " + snapshot.child("HoroscopeData").child("2021Readings").child("Monthly").child(currMonthStr).child(uHoroscope).child("BadDays").getValue().toString());
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

