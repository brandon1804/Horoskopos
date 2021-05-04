package sg.edu.rp.id18044455.horoskopos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ChineseZodiacFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View chineseZodiacView =  inflater.inflate(R.layout.fragment_chinese_zodiac, container, false);

        getActivity().setTitle("Chinese Zodiac");


        ListView listViewZodiac;
        ArrayList<Zodiac> zodiacList;
        ZodiacAdapter zodiacAdapter;
        FragmentManager fm;
        FragmentTransaction fragmentTransaction;

        String [] zodiacs = {"Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig"};
        String [] zodiacsY = {"1960, 1972, 1984, 1996, 2008", "1961, 1973, 1985, 1997, 2009", "1962, 1974, 1986, 1998, 2010", "1963, 1975, 1987, 1999, 2011", "1964, 1976, 1988, 2000, 2012", "1965, 1977, 1989, 2001, 2013", "1966, 1978, 1990, 2002, 2014", "1967, 1979, 1991, 2003, 2015", "1968, 1980, 1992, 2004, 2016", "1969, 1981, 1993, 2005, 2017", "1970, 1982, 1994, 2006, 2018", "1971, 1983, 1995, 2007, 2019"};
        int [] zodiacImages = {R.drawable.ratb, R.drawable.oxb, R.drawable.tigerb, R.drawable.rabbitb, R.drawable.dragonb, R.drawable.snakeb, R.drawable.horseb, R.drawable.sheepb, R.drawable.monkeyb, R.drawable.roosterb, R.drawable.dogb, R.drawable.pigb};

        listViewZodiac = chineseZodiacView.findViewById(R.id.listViewZodiac);
        zodiacList = new ArrayList<>();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();


        for(int i = 0; i < zodiacs.length; i++){
            Zodiac zodiac = new Zodiac(zodiacs[i],zodiacsY[i], zodiacImages[i]);
            zodiacList.add(zodiac);
        }//end of for loop


        zodiacAdapter = new ZodiacAdapter(getContext(), R.layout.zodiaclist_row, zodiacList);
        listViewZodiac.setAdapter(zodiacAdapter);


        listViewZodiac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Zodiac clickedZodiac = zodiacList.get(position);
                String zodiac = clickedZodiac.getZodiacName();

                Bundle bundle = new Bundle();
                bundle.putString("Zodiac", zodiac);

                ZodiacFragment zodiacFragment = new ZodiacFragment();
                zodiacFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.containerFragment, zodiacFragment);
                fragmentTransaction.commit();
            }
        });


        return chineseZodiacView;
    }//end of onCreateView





}//end of class