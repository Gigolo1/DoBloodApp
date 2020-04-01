package c.www.dobloodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AdminRegisterationActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
     EditText editTextEmail;
     EditText editTextPassword;
     TextView textViewSignin;
     EditText hname;
     EditText hid;
     EditText hadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registeration);

        Button buttonRegister;

        ProgressDialog progressdialog;
        progressdialog=new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), AdminProfileActivity.class));
        }

        buttonRegister =(Button) findViewById(R.id.buttonRegister);
        editTextEmail=(EditText) findViewById(R.id.editTextMail);
        editTextPassword=(EditText) findViewById(R.id.editpass);
        textViewSignin=(TextView) findViewById(R.id.textViewSignin);
        hname=findViewById(R.id.editHname);
        hid=findViewById(R.id.editHid);
        hadd=findViewById(R.id.editHadd);
        final String email= editTextEmail.getText().toString().trim();
        final String password= editTextPassword.getText().toString().trim();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextEmail.getText().toString())){
                    //email is empty
                    Toast.makeText(AdminRegisterationActivity.this, "Please Enter Email!", Toast.LENGTH_SHORT).show();
                    //stop the function execution
                    return;
                }
                if(TextUtils.isEmpty(editTextPassword.getText().toString())){
                    //password is empty
                    Toast.makeText(AdminRegisterationActivity.this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
                    //stop the function execution
                    return;
                }
                System.out.println(email);
                System.out.println(password);
                firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),editTextPassword.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    registerData();
                                    Intent in=new Intent(AdminRegisterationActivity.this, UserProfileActivity.class);
                                    startActivity(in);
//                                    startActivity(new Intent(AdminRegisterationActivity.this, UserProfileActivity.class));
                                }
                                else{
                                    Toast.makeText(AdminRegisterationActivity.this, "Could not Register, Please try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }
        });

        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminRegisterationActivity.this, UserLogin.class));
            }
        });



    }

    private void registerData() {
        final String name = hname.getText().toString();
        final String id = hid.getText().toString();
        final String add = hadd.getText().toString();
        admins admin = new admins(name, id,add);
        FirebaseDatabase.getInstance().getReference("Admin").child((Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())).getUid()).setValue(admin);
    }

//    private void registerUser( String email,String password){
//
//        Log.d("before method","log");
//        progressdialog.setMessage("Registering User...");
//        progressdialog.show();
//
//        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                Log.d("inside method","log");
//              if(task.isSuccessful()){
//                  progressdialog.dismiss();
//                  startActivity(new Intent(UserRegisterationActivity.this,UserProfileActivity.class));
//              }
//              else {
//                  progressdialog.dismiss();
//                  Toast.makeText(UserRegisterationActivity.this, "Could not Registere, Please try again!", Toast.LENGTH_SHORT).show();
//              }
//            }
//        });
//    }
}
