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


public class ZodiacCompatibilityFragment extends Fragment {



    Button btnFZ, btnSZ, btnCalculateZC;
    String firstZ, firstZD, secondZ, secondZD;
    int fzodiacDrawable, szodiacDrawable, dF, sdF;
    int fNum, sNum;

    FragmentManager fm;
    FragmentTransaction fragmentTransaction;


    String [] zodiacs = {"Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig"};
    String [] zodiacsY = {"1960, 1972, 1984, 1996, 2008", "1961, 1973, 1985, 1997, 2009", "1962, 1974, 1986, 1998, 2010", "1963, 1975, 1987, 1999, 2011", "1964, 1976, 1988, 2000, 2012", "1965, 1977, 1989, 2001, 2013", "1966, 1978, 1990, 2002, 2014", "1967, 1979, 1991, 2003, 2015", "1968, 1980, 1992, 2004, 2016", "1969, 1981, 1993, 2005, 2017", "1970, 1982, 1994, 2006, 2018", "1971, 1983, 1995, 2007, 2019"};
    int [] zodiacImages = {R.drawable.rat, R.drawable.ox, R.drawable.tiger, R.drawable.rabbit, R.drawable.dragon, R.drawable.snake, R.drawable.horse, R.drawable.sheep, R.drawable.monkey, R.drawable.rooster, R.drawable.dog, R.drawable.pig};
    int [] zodiacBImages = {R.drawable.ratb, R.drawable.oxb, R.drawable.tigerb, R.drawable.rabbitb, R.drawable.dragonb, R.drawable.snakeb, R.drawable.horseb, R.drawable.sheepb, R.drawable.monkeyb, R.drawable.roosterb, R.drawable.dogb, R.drawable.pigb};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View zodiacCompatibilityView =  inflater.inflate(R.layout.fragment_zodiac_compatibility, container, false);
        getActivity().setTitle("Zodiac Compatibility");



        btnFZ = zodiacCompatibilityView.findViewById(R.id.btnFZ);
        btnSZ = zodiacCompatibilityView.findViewById(R.id.btnSZ);
        btnCalculateZC = zodiacCompatibilityView.findViewById(R.id.btnCalculateZC);


        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();


        dF = 0;
        sdF = 0;



        btnFZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select your zodiac");

                builder.setSingleChoiceItems(zodiacs, dF, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firstZ = zodiacs[which];
                        firstZD = zodiacsY[which];
                        fzodiacDrawable = zodiacImages[which];
                        dF = which;
                        fNum = which;
                    }
                });

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (firstZ == null && firstZD == null){
                            firstZ = zodiacs[0];
                            firstZD = zodiacsY[0];
                            fzodiacDrawable = zodiacImages[0];
                            fNum = 0;
                        }
                        btnFZ.setCompoundDrawablesWithIntrinsicBounds(fzodiacDrawable, 0, 0, 0);
                        btnFZ.setText(firstZ + "\n" + firstZD);
                        btnFZ.setBackgroundColor(Color.parseColor("#303841"));
                    }
                });//end of positive

                builder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = builder.create();
                myDialog.show();

            }
        });//end of btnFZ


        btnSZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select a zodiac");
                builder.setSingleChoiceItems(zodiacs, sdF, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        secondZ = zodiacs[which];
                        secondZD = zodiacsY[which];
                        szodiacDrawable = zodiacImages[which];
                        sdF = which;
                        sNum = which;
                    }
                });

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (secondZ == null && secondZD == null){
                            secondZ = zodiacs[0];
                            secondZD = zodiacsY[0];
                            szodiacDrawable = zodiacImages[0];
                            sNum = 0;
                        }

                        btnSZ.setCompoundDrawablesWithIntrinsicBounds(szodiacDrawable, 0, 0, 0);
                        btnSZ.setText(secondZ + "\n" + secondZD);
                        btnSZ.setBackgroundColor(Color.parseColor("#303841"));
                    }
                });//end of positive

                builder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = builder.create();
                myDialog.show();

            }
        });//end of btnSZ


        btnCalculateZC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstZ == null || secondZ == null){
                    Toast.makeText(getContext(), "Please select a zodiac", Toast.LENGTH_LONG).show();
                }//end of if

                else{
                    Bundle bundle = new Bundle();
                    bundle.putString("firstZ", firstZ);
                    bundle.putString("secondZ", secondZ);
                    bundle.putString("firstZD", firstZD);
                    bundle.putString("secondZD", secondZD);
                    bundle.putInt("fzodiacDrawable", zodiacBImages[fNum]);
                    bundle.putInt("szodiacDrawable", zodiacBImages[sNum]);


                    ZodiacCompatibilityResultFragment zodiacCompatibilityResultFragment = new ZodiacCompatibilityResultFragment();
                    zodiacCompatibilityResultFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.containerFragment, zodiacCompatibilityResultFragment);
                    fragmentTransaction.commit();
                }


            }
        });



        return zodiacCompatibilityView;
    }//end of onCreateView


}//end of class