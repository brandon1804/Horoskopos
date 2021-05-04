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


public class YourMonthlyFragment extends Fragment {

    FirebaseAuth fAuth;
    DatabaseReference root;
    FirebaseUser currUser;

    FragmentManager fm;
    FragmentTransaction fragmentTransaction;


    ImageView yrmhIconView, yrmbackBtn, yrmShareBtn;

    TextView tvYYearly, tvyrmReading, yrmtvGD, yrmtvBD, yrmtvHTitle, yrmtvHInfo;
    String currMonthStr;
    String uHoroscope;
    int horoscopeIndex;


    String [] horoscopes = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    String [] horoscopeDates = {"21 Mar - 19 Apr", "20 Apr - 20 May", "21 May - 20 Jun", "21 Jun - 22 Jul", "23 Jul - 22 Aug", "23 Aug - 22 Sep", "23 Sep - 22 Oct", "23 Oct - 21 Nov", "22 Nov - 21 Dec", "22 Dec - 19 Jan", "20 Jan - 18 Feb", "19 Feb - 20 Mar"};
    int [] horoscopeImages = {R.drawable.aries, R.drawable.taurus, R.drawable.gemini, R.drawable.cancer, R.drawable.leo, R.drawable.virgo, R.drawable.libra, R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn, R.drawable.aquarius, R.drawable.pisces};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View yourMonthlyView = inflater.inflate(R.layout.fragment_your_monthly, container, false);


        fAuth = FirebaseAuth.getInstance();
        currUser = fAuth.getCurrentUser();
        root = FirebaseDatabase.getInstance().getReference();

        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();


        yrmhIconView = yourMonthlyView.findViewById(R.id.yrmhIconView);
        yrmbackBtn = yourMonthlyView.findViewById(R.id.yrmbackBtn);
        yrmShareBtn = yourMonthlyView.findViewById(R.id.yrmShareBtn);

        yrmtvHTitle = yourMonthlyView.findViewById(R.id.yrmtvHTitle);
        yrmtvHInfo = yourMonthlyView.findViewById(R.id.yrmtvHInfo);

        tvYYearly = yourMonthlyView.findViewById(R.id.tvYYearly);

        tvyrmReading = yourMonthlyView.findViewById(R.id.tvyrmReading);
        yrmtvGD = yourMonthlyView.findViewById(R.id.yrmtvGD);
        yrmtvBD = yourMonthlyView.findViewById(R.id.yrmtvBD);


        updateUI();



        yrmbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        yrmShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Monthly Horoscope Reading", tvyrmReading.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), uHoroscope + " Monthly Reading Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        tvYYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new YourYearlyFragment());
                fragmentTransaction.commit();
            }
        });

        return yourMonthlyView;
    }//end of onCreateView

    public void updateUI() {




        Calendar c = Calendar.getInstance();
        int currMonth = c.get(Calendar.MONTH);


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

        getActivity().setTitle("Your " + currMonthStr + " Horoscope Prediction");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uHoroscope = snapshot.child("Users").child(currUser.getUid()).child("Horoscope").getValue().toString();

                for(int i = 0; i < horoscopes.length; i++){
                    if(uHoroscope.equals(horoscopes[i])){
                        horoscopeIndex = i;
                    }
                }//end of for loop

                yrmhIconView.setImageResource(horoscopeImages[horoscopeIndex]);
                yrmtvHTitle.setText(horoscopes[horoscopeIndex]);
                yrmtvHInfo.setText(horoscopeDates[horoscopeIndex]);

                tvyrmReading.setText(snapshot.child("HoroscopeData").child("2021Readings").child("Monthly").child(currMonthStr).child(uHoroscope).child("Description").getValue().toString());
                yrmtvGD.setText("Good Days - " + snapshot.child("HoroscopeData").child("2021Readings").child("Monthly").child(currMonthStr).child(uHoroscope).child("GoodDays").getValue().toString());
                yrmtvBD.setText("Bad Days - " + snapshot.child("HoroscopeData").child("2021Readings").child("Monthly").child(currMonthStr).child(uHoroscope).child("BadDays").getValue().toString());
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