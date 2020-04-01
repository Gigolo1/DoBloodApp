package c.www.dobloodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminProfileActivity extends AppCompatActivity /*implements View.OnClickListener*/{

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    Button buttonlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        buttonlogout =(Button) findViewById(R.id.buttonLogout);
        textViewUserEmail=(TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText("Welcome "+user.getEmail().toString());
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });
    }
}
