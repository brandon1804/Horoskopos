package sg.edu.rp.id18044455.horoskopos;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ZodiacCompatibilityResultFragment extends Fragment {


    FirebaseAuth fAuth;
    DatabaseReference root;

    ImageView fzView, szView;
    TextView tvFZ, tvFZD, tvZodiacCResult, tvSZ, tvSZD, tvOverviewCZ;
    Button btnCAZ;
    ImageView shareBtnZCR;

    ProgressBar zodiacCBar;

    String firstZ, firstZD, secondZ, secondZD, zodiacs;
    int fzodiacDrawable, szodiacDrawable, dF, sdF;


    FragmentManager fm;
    FragmentTransaction fragmentTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View zodiacCompatibilityResultView =  inflater.inflate(R.layout.fragment_zodiac_compatibility_result, container, false);
        getActivity().setTitle("Zodiac Compatibility Result");

        fAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();

        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();


        fzView = zodiacCompatibilityResultView.findViewById(R.id.fzView);
        szView = zodiacCompatibilityResultView.findViewById(R.id.szView);

        zodiacCBar = zodiacCompatibilityResultView.findViewById(R.id.zodiacCBar);

        tvFZ = zodiacCompatibilityResultView.findViewById(R.id.tvFZ);
        tvFZD = zodiacCompatibilityResultView.findViewById(R.id.tvFZD);
        tvZodiacCResult = zodiacCompatibilityResultView.findViewById(R.id.tvZodiacCResult);
        tvSZ = zodiacCompatibilityResultView.findViewById(R.id.tvSZ);
        tvSZD = zodiacCompatibilityResultView.findViewById(R.id.tvSZD);
        tvOverviewCZ = zodiacCompatibilityResultView.findViewById(R.id.tvOverviewCZ);

        updateUI();

        btnCAZ = zodiacCompatibilityResultView.findViewById(R.id.btnCAZ);
        shareBtnZCR = zodiacCompatibilityResultView.findViewById(R.id.shareBtnZCR);

        updateUI();

        shareBtnZCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Zodiac Compatibility Result", tvOverviewCZ.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), firstZ + " & " + secondZ + " Compatibility Result Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new ZodiacCompatibilityFragment());
                fragmentTransaction.commit();
            }
        });



        return zodiacCompatibilityResultView;
    }//end of onCreateView


    public void updateUI(){

        Bundle bundle = this.getArguments();

        if(bundle != null){

            firstZ = bundle.getString("firstZ");
            secondZ = bundle.getString("secondZ");
            firstZD = bundle.getString("firstZD");
            secondZD = bundle.getString("secondZD");
            fzodiacDrawable = bundle.getInt("fzodiacDrawable");
            szodiacDrawable = bundle.getInt("szodiacDrawable");

            fzView.setImageResource(fzodiacDrawable);
            szView.setImageResource(szodiacDrawable);

            tvFZ.setText(firstZ);
            tvFZD.setText(firstZD);
            tvSZ.setText(secondZ);
            tvSZD.setText(secondZD);


            zodiacs = firstZ + secondZ;
            zodiacs = zodiacValidation(zodiacs);


            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Integer score = Integer.parseInt(snapshot.child("HoroscopeData").child("ZodiacCompatibility").child(zodiacs).child("Score").getValue().toString());
                    zodiacCBar.setProgress(score);
                    tvZodiacCResult.setText(score.toString());
                    tvOverviewCZ.setText(snapshot.child("HoroscopeData").child("ZodiacCompatibility").child(zodiacs).child("Description").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }//end of bundle validation

    }//end of updateUI



    public static String zodiacValidation(String zodiacs){
        if (zodiacs.equals("PigDog")){
            zodiacs = "DogPig";
        }
        else if (zodiacs.equals("DragonDog")){
            zodiacs = "DogDragon";
        }
        else if (zodiacs.equals("HorseDragon")){
            zodiacs = "DragonHorse";
        }
        else if (zodiacs.equals("MonkeyDragon")){
            zodiacs = "DragonMonkey";
        }
        else if (zodiacs.equals("PigDragon")){
            zodiacs = "DragonPig";
        }
        else if (zodiacs.equals("RabbitDragon")){
            zodiacs = "DragonRabbit";
        }
        else if (zodiacs.equals("RoosterDragon")){
            zodiacs = "DragonRooster";
        }
        else if (zodiacs.equals("TigerDragon")){
            zodiacs = "DragonTiger";
        }
        else if (zodiacs.equals("DogGoat")){
            zodiacs = "GoatDog";
        }
        else if (zodiacs.equals("DragonGoat")){
            zodiacs = "GoatDragon";
        }
        else if (zodiacs.equals("HorseGoat")){
            zodiacs = "GoatHorse";
        }
        else if (zodiacs.equals("MonkeyGoat")){
            zodiacs = "GoatMonkey";
        }
        else if (zodiacs.equals("PigGoat")){
            zodiacs = "GoatPig";
        }
        else if (zodiacs.equals("RabbitGoat")){
            zodiacs = "GoatRabbit";
        }
        else if (zodiacs.equals("RatGoat")){
            zodiacs = "GoatRat";
        }
        else if (zodiacs.equals("RoosterGoat")){
            zodiacs = "GoatRooster";
        }
        else if (zodiacs.equals("SnakeGoat")){
            zodiacs = "GoatSnake";
        }
        else if (zodiacs.equals("TigerGoat")){
            zodiacs = "GoatTiger";
        }
        else if (zodiacs.equals("DogHorse")){
            zodiacs = "HorseDog";
        }
        else if (zodiacs.equals("MonkeyHorse")){
            zodiacs = "HorseMonkey";
        }
        else if (zodiacs.equals("PigHorse")){
            zodiacs = "HorsePig";
        }
        else if (zodiacs.equals("RoosterHorse")){
            zodiacs = "HorseRooster";
        }
        else if (zodiacs.equals("DogMonkey")){
            zodiacs = "MonkeyDog";
        }
        else if (zodiacs.equals("PigMonkey")){
            zodiacs = "MonkeyPig";
        }
        else if (zodiacs.equals("RoosterMonkey")){
            zodiacs = "MonkeyRooster";
        }
        else if (zodiacs.equals("DogOx")){
            zodiacs = "OxDog";
        }
        else if (zodiacs.equals("DragonOx")){
            zodiacs = "OxDragon";
        }
        else if (zodiacs.equals("GoatOx")){
            zodiacs = "OxGoat";
        }
        else if (zodiacs.equals("HorseOx")){
            zodiacs = "OxHorse";
        }
        else if (zodiacs.equals("MonkeyOx")){
            zodiacs = "OxMonkey";
        }
        else if (zodiacs.equals("PigOx")){
            zodiacs = "OxPig";
        }
        else if (zodiacs.equals("RabbitOx")){
            zodiacs = "OxRabbit";
        }
        else if (zodiacs.equals("RatOx")){
            zodiacs = "OxRat";
        }
        else if (zodiacs.equals("RoosterOx")){
            zodiacs = "OxRooster";
        }
        else if (zodiacs.equals("SnakeOx")){
            zodiacs = "OxSnake";
        }
        else if (zodiacs.equals("TigerOx")){
            zodiacs = "OxTiger";
        }
        else if (zodiacs.equals("DogRabbit")){
            zodiacs = "RabbitDog";
        }
        else if (zodiacs.equals("HorseRabbit")){
            zodiacs = "RabbitHorse";
        }
        else if (zodiacs.equals("MonkeyRabbit")){
            zodiacs = "RabbitMonkey";
        }
        else if (zodiacs.equals("PigRabbit")){
            zodiacs = "RabbitPig";
        }
        else if (zodiacs.equals("RoosterRabbit")){
            zodiacs = "RabbitRooster";
        }
        else if (zodiacs.equals("DogRat")){
            zodiacs = "RatDog";
        }
        else if (zodiacs.equals("DragonRat")){
            zodiacs = "RatDragon";
        }
        else if (zodiacs.equals("HorseRat")){
            zodiacs = "RatHorse";
        }
        else if (zodiacs.equals("MonkeyRat")){
            zodiacs = "RatMonkey";
        }
        else if (zodiacs.equals("PigRat")){
            zodiacs = "RatPig";
        }
        else if (zodiacs.equals("RabbitRat")){
            zodiacs = "RatRabbit";
        }
        else if (zodiacs.equals("RoosterRat")){
            zodiacs = "RatRooster";
        }
        else if (zodiacs.equals("SnakeRat")){
            zodiacs = "RatSnake";
        }
        else if (zodiacs.equals("TigerRat")){
            zodiacs = "RatTiger";
        }
        else if (zodiacs.equals("DogRooster")){
            zodiacs = "RoosterDog";
        }
        else if (zodiacs.equals("PigRooster")){
            zodiacs = "RoosterPig";
        }
        else if (zodiacs.equals("DogSnake")){
            zodiacs = "SnakeDog";
        }
        else if (zodiacs.equals("DragonSnake")){
            zodiacs = "SnakeDragon";
        }
        else if (zodiacs.equals("HorseSnake")){
            zodiacs = "SnakeHorse";
        }
        else if (zodiacs.equals("MonkeySnake")){
            zodiacs = "SnakeMonkey";
        }
        else if (zodiacs.equals("PigSnake")){
            zodiacs = "SnakePig";
        }
        else if (zodiacs.equals("RabbitSnake")){
            zodiacs = "SnakeRabbit";
        }
        else if (zodiacs.equals("RoosterSnake")){
            zodiacs = "SnakeRooster";
        }
        else if (zodiacs.equals("TigerSnake")){
            zodiacs = "SnakeTiger";
        }
        else if (zodiacs.equals("DogTiger")){
            zodiacs = "TigerDog";
        }
        else if (zodiacs.equals("HorseTiger")){
            zodiacs = "HorseTiger";
        }
        else if (zodiacs.equals("MonkeyTiger")){
            zodiacs = "TigerMonkey";
        }
        else if (zodiacs.equals("PigTiger")){
            zodiacs = "TigerPig";
        }
        else if (zodiacs.equals("RabbitTiger")){
            zodiacs = "TigerRabbit";
        }
        else if (zodiacs.equals("RoosterTiger")){
            zodiacs = "TigerRooster";
        }



        return zodiacs;
    }//end of horoscopeValidation


}//end of class