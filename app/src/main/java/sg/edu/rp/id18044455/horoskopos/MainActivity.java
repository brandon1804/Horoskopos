package sg.edu.rp.id18044455.horoskopos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggleDrawerBtn;
    NavigationView navigationView;


    FragmentManager fm;
    FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        toggleDrawerBtn = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        toggleDrawerBtn.setDrawerIndicatorEnabled(true);
        toggleDrawerBtn.syncState();
        drawerLayout.addDrawerListener(toggleDrawerBtn);




        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.containerFragment, new HomeFragment());
        fragmentTransaction.commit();



    }//end of onCreate


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.home){
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, new HomeFragment());
            fragmentTransaction.commit();
        }
        else if (item.getItemId() == R.id.settings){
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, new SettingsFragment());
            fragmentTransaction.commit();
        }
        else if (item.getItemId() == R.id.chineseZodiac){
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, new ChineseZodiacFragment());
            fragmentTransaction.commit();
        }
        else if (item.getItemId() == R.id.zodiacCompatibility){
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, new ZodiacCompatibilityFragment());
            fragmentTransaction.commit();
        }
        else if (item.getItemId() == R.id.horoscopeList){
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, new HoroscopeFragment());
            fragmentTransaction.commit();
        }
        else if (item.getItemId() == R.id.horoscopeCompatibility){
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.containerFragment, new CompatibilityFragment());
            fragmentTransaction.commit();
        }
        return false;
    }


}//end of class