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


public class CompatibilityResultFragment extends Fragment {

    FirebaseAuth fAuth;
    DatabaseReference root;

    ImageView fhView, shView;
    TextView tvFH, tvFHD, tvCResult, tvSH, tvSHD, tvResult;
    Button btnCAH;
    ImageView shareBtnCR;

    ProgressBar cBar;


    String firstH, firstHD, secondH, secondHD, horoscopes;
    int fhoroscopeDrawable, shoroscopeDrawable;


    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View compatibilityResultView = inflater.inflate(R.layout.fragment_compatibility_result, container, false);
        getActivity().setTitle("Horoscope Compatibility Result");


        fAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference();

        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();


        fhView = compatibilityResultView.findViewById(R.id.fhView);
        shView = compatibilityResultView.findViewById(R.id.shView);

        cBar = compatibilityResultView.findViewById(R.id.cBar);

        tvFH = compatibilityResultView.findViewById(R.id.tvFH);
        tvFHD = compatibilityResultView.findViewById(R.id.tvFHD);
        tvCResult = compatibilityResultView.findViewById(R.id.tvCResult);
        tvSH = compatibilityResultView.findViewById(R.id.tvSH);
        tvSHD = compatibilityResultView.findViewById(R.id.tvSHD);
        tvResult = compatibilityResultView.findViewById(R.id.tvResult);

        btnCAH = compatibilityResultView.findViewById(R.id.btnCAH);
        shareBtnCR = compatibilityResultView.findViewById(R.id.shareBtnCR);

        updateUI();

        shareBtnCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Horoscope Compatibility Result", tvResult.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), firstH + " & " + secondH + " Compatibility Result Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCAH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.containerFragment, new CompatibilityFragment());
                fragmentTransaction.commit();
            }
        });

        return compatibilityResultView;
    }//end of onCreateView



    public void updateUI(){

        Bundle bundle = this.getArguments();

        if(bundle != null){

            firstH = bundle.getString("firstH");
            secondH = bundle.getString("secondH");
            firstHD = bundle.getString("firstHD");
            secondHD = bundle.getString("secondHD");
            fhoroscopeDrawable = bundle.getInt("fhoroscopeDrawable");
            shoroscopeDrawable = bundle.getInt("shoroscopeDrawable");

            fhView.setImageResource(fhoroscopeDrawable);
            shView.setImageResource(shoroscopeDrawable);

            tvFH.setText(firstH);
            tvFHD.setText(firstHD);
            tvSH.setText(secondH);
            tvSHD.setText(secondHD);


            horoscopes = firstH + secondH;
            horoscopes = horoscopeValidation(horoscopes);


            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Integer score = Integer.parseInt(snapshot.child("HoroscopeData").child("Compatibility").child(horoscopes).child("Score").getValue().toString());
                    cBar.setProgress(score);
                    tvCResult.setText(score.toString());
                    tvResult.setText(snapshot.child("HoroscopeData").child("Compatibility").child(horoscopes).child("Description").getValue().toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }//end of bundle validation

    }//end of updateUI



    public static String horoscopeValidation(String horoscopes){
        if (horoscopes.equals("PiscesAquarius")){
            horoscopes = "AquariusPisces";
        }
        else if (horoscopes.equals("AquariusAries")){
            horoscopes = "AriesAquarius";
        }
        else if (horoscopes.equals("CancerAries")){
            horoscopes = "AriesCancer";
        }
        else if (horoscopes.equals("CapricornAries")){
            horoscopes = "AriesCapricorn";
        }
        else if (horoscopes.equals("GeminiAries")){
            horoscopes = "AriesGemini";
        }
        else if (horoscopes.equals("LeoAries")){
            horoscopes = "AriesLeo";
        }
        else if (horoscopes.equals("LibraAries")){
            horoscopes = "AriesLibra";
        }
        else if (horoscopes.equals("PiscesAries")){
            horoscopes = "AriesPisces";
        }
        else if (horoscopes.equals("SagittariusAries")){
            horoscopes = "AriesSagittarius";
        }
        else if (horoscopes.equals("ScorpioAries")){
            horoscopes = "AriesScorpio";
        }
        else if (horoscopes.equals("TaurusAries")){
            horoscopes = "AriesTaurus";
        }
        else if (horoscopes.equals("VirgoAries")){
            horoscopes = "AriesVirgo";
        }
        else if (horoscopes.equals("AquariusCancer")){
            horoscopes = "CancerAquarius";
        }
        else if (horoscopes.equals("CapricornCancer")){
            horoscopes = "CancerCapricorn";
        }
        else if (horoscopes.equals("LeoCancer")){
            horoscopes = "CancerLeo";
        }
        else if (horoscopes.equals("LibraCancer")){
            horoscopes = "CancerLibra";
        }
        else if (horoscopes.equals("PiscesCancer")){
            horoscopes = "CancerPisces";
        }
        else if (horoscopes.equals("SagittariusCancer")){
            horoscopes = "CancerSagittarius";
        }
        else if (horoscopes.equals("ScorpioCancer")){
            horoscopes = "CancerScorpio";
        }
        else if (horoscopes.equals("VirgoCancer")){
            horoscopes = "CancerVirgo";
        }
        else if (horoscopes.equals("AquariusCapricorn")){
            horoscopes = "CapricornAquarius";
        }
        else if (horoscopes.equals("PiscesCapricorn")){
            horoscopes = "CapricornPisces";
        }
        else if (horoscopes.equals("AquariusGemini")){
            horoscopes = "GeminiAquarius";
        }
        else if (horoscopes.equals("CancerGemini")){
            horoscopes = "GeminiCancer";
        }
        else if (horoscopes.equals("CapricornGemini")){
            horoscopes = "GeminiCapricorn";
        }
        else if (horoscopes.equals("LeoGemini")){
            horoscopes = "GeminiLeo";
        }
        else if (horoscopes.equals("LibraGemini")){
            horoscopes = "GeminiLibra";
        }
        else if (horoscopes.equals("PiscesGemini")){
            horoscopes = "GeminiPisces";
        }
        else if (horoscopes.equals("SagittariusGemini")){
            horoscopes = "GeminiSagittarius";
        }
        else if (horoscopes.equals("ScorpioGemini")){
            horoscopes = "GeminiScorpio";
        }
        else if (horoscopes.equals("VirgoGemini")){
            horoscopes = "GeminiVirgo";
        }
        else if (horoscopes.equals("AquariusLeo")){
            horoscopes = "LeoAquarius";
        }
        else if (horoscopes.equals("CapricornLeo")){
            horoscopes = "LeoCapricorn";
        }
        else if (horoscopes.equals("LibraLeo")){
            horoscopes = "LeoLibra";
        }
        else if (horoscopes.equals("PiscesLeo")){
            horoscopes = "LeoPisces";
        }
        else if (horoscopes.equals("SagittariusLeo")){
            horoscopes = "LeoSagittarius";
        }
        else if (horoscopes.equals("ScorpioLeo")){
            horoscopes = "LeoScorpio";
        }
        else if (horoscopes.equals("VirgoLeo")){
            horoscopes = "LeoVirgo";
        }
        else if (horoscopes.equals("AquariusLibra")){
            horoscopes = "LibraAquarius";
        }
        else if (horoscopes.equals("CapricornLibra")){
            horoscopes = "LibraCapricorn";
        }
        else if (horoscopes.equals("PiscesLibra")){
            horoscopes = "LibraPisces";
        }
        else if (horoscopes.equals("SagittariusLibra")){
            horoscopes = "LibraSagittarius";
        }
        else if (horoscopes.equals("ScorpioLibra")){
            horoscopes = "LibraScorpio";
        }
        else if (horoscopes.equals("AquariusSagittarius")){
            horoscopes = "SagittariusAquarius";
        }
        else if (horoscopes.equals("CapricornSagittarius")){
            horoscopes = "SagittariusCapricorn";
        }
        else if (horoscopes.equals("PiscesSagittarius")){
            horoscopes = "SagittariusPisces";
        }
        else if (horoscopes.equals("AquariusScorpio")){
            horoscopes = "ScorpioAquarius";
        }
        else if (horoscopes.equals("CapricornScorpio")){
            horoscopes = "ScorpioCapricorn";
        }
        else if (horoscopes.equals("PiscesScorpio")){
            horoscopes = "ScorpioPisces";
        }
        else if (horoscopes.equals("SagittariusScorpio")){
            horoscopes = "ScorpioSagittarius";
        }
        else if (horoscopes.equals("AquariusTaurus")){
            horoscopes = "TaurusAquarius";
        }
        else if (horoscopes.equals("CancerTaurus")){
            horoscopes = "TaurusCancer";
        }
        else if (horoscopes.equals("CapricornTaurus")){
            horoscopes = "TaurusCapricorn";
        }
        else if (horoscopes.equals("GeminiTaurus")){
            horoscopes = "TaurusGemini";
        }
        else if (horoscopes.equals("LeoTaurus")){
            horoscopes = "TaurusLeo";
        }
        else if (horoscopes.equals("LibraTaurus")){
            horoscopes = "TaurusLibra";
        }
        else if (horoscopes.equals("PiscesTaurus")){
            horoscopes = "TaurusPisces";
        }
        else if (horoscopes.equals("SagittariusTaurus")){
            horoscopes = "TaurusSagittarius";
        }
        else if (horoscopes.equals("ScorpioTaurus")){
            horoscopes = "TaurusScorpio";
        }
        else if (horoscopes.equals("VirgoTaurus")){
            horoscopes = "TaurusVirgo";
        }
        else if (horoscopes.equals("AquariusVirgo")){
            horoscopes = "VirgoAquarius";
        }
        else if (horoscopes.equals("CapricornVirgo")){
            horoscopes = "VirgoCapricorn";
        }
        else if (horoscopes.equals("LibraVirgo")){
            horoscopes = "VirgoLibra";
        }
        else if (horoscopes.equals("PiscesVirgo")){
            horoscopes = "VirgoPisces";
        }
        else if (horoscopes.equals("SagittariusVirgo")){
            horoscopes = "VirgoSagittarius";
        }
        else if (horoscopes.equals("ScorpioVirgo")){
            horoscopes = "VirgoScorpio";
        }


        return horoscopes;
    }//end of horoscopeValidation


}//end of class