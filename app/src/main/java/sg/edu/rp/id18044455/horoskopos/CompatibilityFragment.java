package sg.edu.rp.id18044455.horoskopos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class CompatibilityFragment extends Fragment {


    Button btnFH, btnSH, btnCalculate;
    String firstH, firstHD, secondH, secondHD;
    int fhoroscopeDrawable, shoroscopeDrawable, dF, sdF;
    int fNum, sNum;

    FragmentManager fm;
    FragmentTransaction fragmentTransaction;


    String [] horoscopes = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    String [] horoscopeDates = {"21 Mar - 19 Apr", "20 Apr - 20 May", "21 May - 20 Jun", "21 Jun - 22 Jul", "23 Jul - 22 Aug", "23 Aug - 22 Sep", "23 Sep - 22 Oct", "23 Oct - 21 Nov", "22 Nov - 21 Dec", "22 Dec - 19 Jan", "20 Jan - 18 Feb", "19 Feb - 20 Mar"};
    int [] horoscopeImages = {R.drawable.aries, R.drawable.taurus, R.drawable.gemini, R.drawable.cancer, R.drawable.leo, R.drawable.virgo, R.drawable.libra, R.drawable.scorpio, R.drawable.sagittarius, R.drawable.capricorn, R.drawable.aquarius, R.drawable.pisces};
    int [] horoscopeBImages = {R.drawable.ariesb, R.drawable.taurusb, R.drawable.geminib, R.drawable.cancerb, R.drawable.leob, R.drawable.virgob, R.drawable.librab, R.drawable.scorpiob, R.drawable.sagittariusb, R.drawable.capricornb, R.drawable.aquariusb, R.drawable.piscesb};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View compatibilityView =  inflater.inflate(R.layout.fragment_compatibility, container, false);
        getActivity().setTitle("Horoscope Compatibility");


        btnFH = compatibilityView.findViewById(R.id.btnFH);
        btnSH = compatibilityView.findViewById(R.id.btnSH);
        btnCalculate = compatibilityView.findViewById(R.id.btnCalculate);



        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();


        dF = 0;
        sdF = 0;



        btnFH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select a horoscope");

                builder.setSingleChoiceItems(horoscopes, dF, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firstH = horoscopes[which];
                        firstHD = horoscopeDates[which];
                        fhoroscopeDrawable = horoscopeImages[which];
                        dF = which;
                        fNum = which;
                    }
                });

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (firstH == null && firstHD == null){
                            firstH = horoscopes[0];
                            firstHD = horoscopeDates[0];
                            fhoroscopeDrawable = horoscopeImages[0];
                            fNum = 0;
                        }
                        btnFH.setCompoundDrawablesWithIntrinsicBounds(fhoroscopeDrawable, 0, 0, 0);
                        btnFH.setText(firstH + "\n" + firstHD);
                        btnFH.setBackgroundColor(Color.parseColor("#303841"));
                    }
                });//end of positive

                builder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = builder.create();
                myDialog.show();

            }
        });//end of btnFH


        btnSH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select a horoscope");
                builder.setSingleChoiceItems(horoscopes, sdF, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        secondH = horoscopes[which];
                        secondHD = horoscopeDates[which];
                        shoroscopeDrawable = horoscopeImages[which];
                        sdF = which;
                        sNum = which;
                    }
                });

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (secondH == null && secondHD == null){
                            secondH = horoscopes[0];
                            secondHD = horoscopeDates[0];
                            shoroscopeDrawable = horoscopeImages[0];
                            sNum = 0;
                        }

                        btnSH.setCompoundDrawablesWithIntrinsicBounds(shoroscopeDrawable, 0, 0, 0);
                        btnSH.setText(secondH + "\n" + secondHD);
                        btnSH.setBackgroundColor(Color.parseColor("#303841"));
                    }
                });//end of positive

                builder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = builder.create();
                myDialog.show();

            }
        });//end of btnFH


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstH == null || secondH == null){
                   Toast.makeText(getContext(), "Please select a horoscope", Toast.LENGTH_LONG).show();
                }//end of if

                else{
                    Bundle bundle = new Bundle();
                    bundle.putString("firstH", firstH);
                    bundle.putString("secondH", secondH);
                    bundle.putString("firstHD", firstHD);
                    bundle.putString("secondHD", secondHD);
                    bundle.putInt("fhoroscopeDrawable", horoscopeBImages[fNum]);
                    bundle.putInt("shoroscopeDrawable", horoscopeBImages[sNum]);


                    CompatibilityResultFragment compatibilityResultFragment = new CompatibilityResultFragment();
                    compatibilityResultFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.containerFragment, compatibilityResultFragment);
                    fragmentTransaction.commit();
                }


            }
        });



        return compatibilityView;
    }//end of onCreateView



}//end of class