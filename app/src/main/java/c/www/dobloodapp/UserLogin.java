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

public class UserLogin extends AppCompatActivity implements View.OnClickListener{

    private ProgressDialog progressdialog;

    private FirebaseAuth firebaseAuth;

    private Button buttonSignin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        progressdialog =new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
        }

        buttonSignin =(Button) findViewById(R.id.buttonSingin);
        editTextEmail=(EditText) findViewById(R.id.editTextMail);
        editTextPassword=(EditText) findViewById(R.id.editHid);
        textViewSignup=(TextView) findViewById(R.id.textViewSignup);

        buttonSignin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    public void userLogin(){
        String email= editTextEmail.getText().toString().trim();
        String password= editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please Enter Email!", Toast.LENGTH_SHORT).show();
            //stop the function execution
            return;
        }
        if(TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
            //stop the function execution
            return;
        }

        progressdialog.setMessage("Login User...");
        progressdialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressdialog.dismiss();

                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
            if(view==buttonSignin){
                userLogin();
            }
            if(view==textViewSignup){
                finish();
                startActivity(new Intent(this, UserRegisterationActivity.class));
            }
    }
}
