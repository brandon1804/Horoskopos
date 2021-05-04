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


public class HoroscopeFragment extends Fragment {

    ListView listViewHoroscopes;
    ArrayList<Horoscope>horoscopeList;
    HoroscopeAdapter horoscopeAdapter;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View horoscopeView = inflater.inflate(R.layout.fragment_horoscope, container, false);
        getActivity().setTitle("Horoscopes");

        String [] horoscopes = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
        String [] horoscopeDates = {"21 Mar - 19 Apr", "20 Apr - 20 May", "21 May - 20 Jun", "21 Jun - 22 Jul", "23 Jul - 22 Aug", "23 Aug - 22 Sep", "23 Sep - 22 Oct", "23 Oct - 21 Nov", "22 Nov - 21 Dec", "22 Dec - 19 Jan", "20 Jan - 18 Feb", "19 Feb - 20 Mar"};
        int [] horoscopeImages = {R.drawable.ariesb, R.drawable.taurusb, R.drawable.geminib, R.drawable.cancerb, R.drawable.leob, R.drawable.virgob, R.drawable.librab, R.drawable.scorpiob, R.drawable.sagittariusb, R.drawable.capricornb, R.drawable.aquariusb, R.drawable.piscesb};


        listViewHoroscopes = horoscopeView.findViewById(R.id.listViewHoroscopes);
        horoscopeList = new ArrayList<>();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();


        for(int i = 0; i < horoscopes.length; i++){
            Horoscope horoscope = new Horoscope(horoscopes[i],horoscopeDates[i], horoscopeImages[i]);
            horoscopeList.add(horoscope);
        }//end of for loop


        horoscopeAdapter = new HoroscopeAdapter(getContext(), R.layout.horoscopelist_row, horoscopeList);
        listViewHoroscopes.setAdapter(horoscopeAdapter);


        listViewHoroscopes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Horoscope clickedHoroscope = horoscopeList.get(position);
                String horoscope = clickedHoroscope.getHoroscopeName();

                Bundle bundle = new Bundle();
                bundle.putString("Horoscope", horoscope);

                MonthlyFragment monthlyFragment = new MonthlyFragment();
                monthlyFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.containerFragment, monthlyFragment);
                fragmentTransaction.commit();
            }
        });


        return horoscopeView;
    }//end of onCreateView

}//end of class