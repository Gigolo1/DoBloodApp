package c.www.dobloodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;
import android.content.Intent;

public class verification extends AppCompatActivity {
    Button vbtn;
    String codeSent;
    FirebaseAuth Auth;
    EditText codevarification;
     String num="";
     String name;
     String dob;
     String gen;
     String bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Auth=FirebaseAuth.getInstance();
        vbtn =(findViewById(R.id.verifybtn));
        codevarification=findViewById(R.id.editotp);
         num=getIntent().getStringExtra("number");
        name=getIntent().getStringExtra("name");
         dob=getIntent().getStringExtra("dob");
         gen=getIntent().getStringExtra("gender");
        bg=getIntent().getStringExtra("blood");
        sendVerificationCode(num);
        vbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode(codevarification.getText().toString());
            }
        });
    }

    void registerData(String name,String dob,String blood,String gen) {
        final String n=name;
        final String db = dob;
        final String bg = blood;
        final String ge = gen;
        UserInformation u = new UserInformation(n,bg, ge, db);
        FirebaseDatabase.getInstance().getReference("Users").child((FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(u);
    }

    public void verifyCode(String codeentered)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent,codeentered );
        signInWithPhoneAuthCredential(credential);
    }

    private void sendVerificationCode(String number)
    {
        Log.d("after num","num sent for verification");
        Log.d("after num",num);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            Log.d("in callbacks","getting code");
            if(code!=null)
            {
                Log.d("in callbacks","verifying code");
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d("in callbacks","sent code");
            codeSent=s;
        }
    };
    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(getApplicationContext(),"Ride booked successfully",Toast.LENGTH_SHORT).show();
                            registerData(name,dob,bg,gen);
                            startActivity(new Intent(verification.this,UserProfileActivity.class));
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),"Invalid Code",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
