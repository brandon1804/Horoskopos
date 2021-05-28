package sg.edu.rp.id18044455.horoskopos;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;


public class SettingsFragment extends Fragment {

    FirebaseAuth fAuth;
    DatabaseReference root;
    FirebaseUser currUser;

    TextView tvSettingsH, tvSettingsZ, tvChangePW, tvChangeEmail, etDOM;
    Switch nSwitch;
    Button btnLogout;

    String uHoroscope, uZodiac, isNEnabled, newHoroscope, newZodiac, currMonthStr;
    int defaultHIndex, defaultZIndex, dayOfMonth;
    boolean isNChecked;

    String [] horoscopes = {"Aries", "Taurus", "Gemini", "Cancer", "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn", "Aquarius", "Pisces"};
    String [] zodiacs = {"Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View settingsView =  inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle("Settings");

        fAuth = FirebaseAuth.getInstance();
        currUser = fAuth.getCurrentUser();
        root = FirebaseDatabase.getInstance().getReference().child("Users").child(currUser.getUid());


        tvChangePW = settingsView.findViewById(R.id.tvChangePW);
        tvChangeEmail = settingsView.findViewById(R.id.tvChangeEmail);
        tvSettingsH = settingsView.findViewById(R.id.tvSettingsH);
        tvSettingsZ = settingsView.findViewById(R.id.tvSettingsZ);
        etDOM = settingsView.findViewById(R.id.etDOM);


        nSwitch = settingsView.findViewById(R.id.nSwitch);
        btnLogout = settingsView.findViewById(R.id.btnLogout);

        dayOfMonth = 0;
        isNEnabled = "false";

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

        updateSettings();


        tvChangePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.change_password, null);

                final EditText etCPW = viewDialog.findViewById(R.id.etCPW);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(getContext());
                myBuilder.setTitle("Change Password");
                myBuilder.setView(viewDialog);
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPassword = etCPW.getText().toString();
                        if (newPassword.isEmpty() == true){
                            etCPW.setError("Password is required");
                        }//end of if
                        else{
                            currUser.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Password Changed Successfully", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }//end of else
                    }
                });

                myBuilder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });//end of change password




        tvChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.change_email, null);

                final EditText etSEmail = viewDialog.findViewById(R.id.etSEmail);

                etSEmail.setText(currUser.getEmail());


                AlertDialog.Builder myBuilder = new AlertDialog.Builder(getContext());
                myBuilder.setTitle("Change Email");
                myBuilder.setView(viewDialog);
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Change Email", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newEmail = etSEmail.getText().toString();
                        if (newEmail.isEmpty() == true){
                            etSEmail.setError("Email is required");
                        }//end of if
                        else{
                            currUser.updateEmail(newEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "Email Changed Successfully", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }//end of else
                    }
                });

                myBuilder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });//end of change email



        tvSettingsH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select a horoscope");

                builder.setSingleChoiceItems(horoscopes, defaultHIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newHoroscope = horoscopes[which];
                    }
                });

                builder.setPositiveButton("Change Horoscope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (newHoroscope == null ){
                            newHoroscope = horoscopes[0];
                        }

                        root.child("Horoscope").setValue(newHoroscope);
                        updateSettings();
                        Toast.makeText(getContext(), "Horoscope Changed", Toast.LENGTH_LONG).show();



                    }
                });//end of positive

                builder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = builder.create();
                myDialog.show();

            }
        });//end of change horoscope


        tvSettingsZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select a zodiac");

                builder.setSingleChoiceItems(zodiacs, defaultZIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newZodiac = zodiacs[which];
                    }
                });

                builder.setPositiveButton("Change Zodiac", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (newZodiac == null ){
                            newZodiac = zodiacs[0];
                        }

                        root.child("Zodiac").setValue(newZodiac);
                        updateSettings();
                        Toast.makeText(getContext(), "Zodiac Changed", Toast.LENGTH_LONG).show();

                    }
                });//end of positive

                builder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = builder.create();
                myDialog.show();


            }
        });//end of change zodiac



        if (nSwitch.isChecked() == false){
            etDOM.setVisibility(View.INVISIBLE);
        }

        nSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    etDOM.setVisibility(View.VISIBLE);
                    root.child("NotificationsEnabled").setValue("true");
                }//end of if
                else{
                    etDOM.setVisibility(View.INVISIBLE);
                    root.child("NotificationsEnabled").setValue("false");
                }
            }
        });


        etDOM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        etDOM.setText(String.valueOf(dayOfMonth));
                        root.child("dayOfMonth").setValue(dayOfMonth);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);

                        if (calendar.getTime().compareTo(new Date()) < 0){
                            calendar.add(Calendar.MONTH, 1);
                        }

                        Intent intent = new Intent(getContext(), NotificationReceiver.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);

                        if (alarmManager != null) {
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                        }

                        Toast.makeText(getContext(), "Day of Month Set", Toast.LENGTH_SHORT).show();
                    }
                };

                Calendar cal = Calendar.getInstance();
                DatePickerDialog myDateDialog = new DatePickerDialog(getContext(), myDateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

                if(dayOfMonth != 0){
                    myDateDialog = new DatePickerDialog(getContext(), myDateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), dayOfMonth);
                }

                myDateDialog.getDatePicker().setMinDate(cal.getTimeInMillis() - 1000);
                myDateDialog.show();
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Log out?");
                builder.setMessage("This would log you out of Horoskopos.");

                builder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fAuth.signOut();
                        Intent intent = new Intent(getContext(), LoginScreen.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });//end of positive

                builder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = builder.create();
                myDialog.show();

            }
        });//end of btnLogout



        return settingsView;
    }//end of onCreateView



    private void updateSettings() {


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uHoroscope = snapshot.child("Horoscope").getValue().toString();
                uZodiac = snapshot.child("Zodiac").getValue().toString();

                DataSnapshot dsDOM = snapshot.child("dayOfMonth");
                DataSnapshot dsisNE = snapshot.child("NotificationsEnabled");


                if(dsDOM.exists()){
                    dayOfMonth = Integer.parseInt(dsDOM.getValue().toString());
                }

                if(dsisNE.exists()){
                    isNEnabled = snapshot.child("NotificationsEnabled").getValue().toString();
                }


                if (isNEnabled.equals("true")){
                    isNChecked = true;
                }
                else{
                    isNChecked = false;
                }

                nSwitch.setChecked(isNChecked);

                if (dayOfMonth != 0){
                    etDOM.setText(String.valueOf(dayOfMonth));
                }


                for(int i = 0; i < horoscopes.length; i++){
                    if(uHoroscope.equals(horoscopes[i])){
                        defaultHIndex = i;
                    }
                }//end of for loop

                for(int i = 0; i < zodiacs.length; i++){
                    if(uZodiac.equals(zodiacs[i])){
                        defaultZIndex = i;
                    }
                }//end of for loop

                tvSettingsH.setText(uHoroscope);
                tvSettingsZ.setText(uZodiac);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }//end of updateSettings




}//end of class