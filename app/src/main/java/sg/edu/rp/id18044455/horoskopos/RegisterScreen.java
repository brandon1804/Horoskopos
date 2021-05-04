package sg.edu.rp.id18044455.horoskopos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class RegisterScreen extends AppCompatActivity {

    TextView tvAcc;
    EditText etEmailR, etPasswordR, etCPWR;
    Button registerBtn;
    FirebaseAuth fAuth;
    DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        getSupportActionBar().hide();


        fAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference().child("Users");


        tvAcc = findViewById(R.id.tvAcc);
        etEmailR = findViewById(R.id.editEmailR);
        etPasswordR = findViewById(R.id.editPasswordR);
        etCPWR = findViewById(R.id.editCPW);
        registerBtn = findViewById(R.id.btnRegister);




        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmailR.getText().toString();
                String password = etPasswordR.getText().toString();
                String confirmPassword = etCPWR.getText().toString();


                if(email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()){
                    etEmailR.setError("Email is required");
                    etPasswordR.setError("Password is required");
                    etCPWR.setError("Confirm Password is required");
                }

                else if(email.isEmpty()){
                    etEmailR.setError("Email is required");
                }

                else if(password.isEmpty()){
                    etPasswordR.setError("Password is required");
                }

                else if(confirmPassword.isEmpty()){
                    etCPWR.setError("Confirm Password is required");
                }

                else if(!password.equals(confirmPassword)){
                    etCPWR.setError("Passwords do not match");
                }

                else{
                    fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            String userID = fAuth.getCurrentUser().getUid();
                            root = root.child(userID);
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("userid", userID);
                            root.setValue(userMap);
                            Toast.makeText(RegisterScreen.this, "Registration Success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterScreen.this, HoroscopeSelectionScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterScreen.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }); //end of register


        tvAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });//end of tvAcc



    }//end of onCreate


}//end of class