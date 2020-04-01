package c.www.dobloodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserRegisterationActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    public Button buttonRegister;
    private Spinner bloodgroup;
    private Spinner gender;
    public EditText editTextdob;
    public EditText mobilenumber;
    public EditText name;
    final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registeration);

        FirebaseDatabase database= FirebaseDatabase.getInstance();
         Button buttonRegister;

         final EditText editTextEmail;
         final EditText editTextPassword;
        final TextView textViewSignin;
         ProgressDialog progressdialog;
        progressdialog=new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
        }

        buttonRegister =(Button) findViewById(R.id.buttonRegister);
        editTextEmail=(EditText) findViewById(R.id.editTextMail);
        editTextPassword=(EditText) findViewById(R.id.editHid);
        textViewSignin=(TextView) findViewById(R.id.textViewSignin);
        bloodgroup=(Spinner)findViewById(R.id.bloodgroup);
        gender=(Spinner)findViewById(R.id.gender);
        editTextdob=(EditText)findViewById(R.id.editTextdob);
        mobilenumber=(EditText)findViewById(R.id.mobilenumber);
        name=(EditText)findViewById(R.id.editTextname);

        final String email= editTextEmail.getText().toString().trim();
        final String password= editTextPassword.getText().toString().trim();
        final DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        editTextdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UserRegisterationActivity.this, d, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText().toString())){
                    //name is empty
                    Toast.makeText(UserRegisterationActivity.this, "Please Enter Name!", Toast.LENGTH_SHORT).show();
                    //stop the function execution
                    return;
                }
                if(TextUtils.isEmpty(editTextEmail.getText().toString())){
                    //email is empty
                    Toast.makeText(UserRegisterationActivity.this, "Please Enter Email!", Toast.LENGTH_SHORT).show();
                    //stop the function execution
                    return;
                }
                if(TextUtils.isEmpty(bloodgroup.getSelectedItem().toString())){
                    //blood group is empty
                    Toast.makeText(UserRegisterationActivity.this, "Please Enter Blood Group!", Toast.LENGTH_SHORT).show();
                    //stop the function execution
                    return;
                }
                if(TextUtils.isEmpty(gender.getSelectedItem().toString())){
                    //gender is empty
                    Toast.makeText(UserRegisterationActivity.this, "Please Enter Gender!", Toast.LENGTH_SHORT).show();
                    //stop the function execution
                    return;
                }
                if(TextUtils.isEmpty(editTextdob.getText().toString())){
                    //dob is empty
                    Toast.makeText(UserRegisterationActivity.this, "Please Enter Date of Birth!", Toast.LENGTH_SHORT).show();
                    //stop the function execution
                    return;
                }
                if(TextUtils.isEmpty(editTextPassword.getText().toString())){
                    //password is empty
                    Toast.makeText(UserRegisterationActivity.this, "Please Enter Password!", Toast.LENGTH_SHORT).show();
                    //stop the function execution
                    return;
                }
//                if (mobilenumber.getText().toString().length() != 13) {
//                    mobilenumber.setError("Please enter 10 digit number preceding with +91");
//                    return;
//                }

                FirebaseUser user= firebaseAuth.getCurrentUser();

                System.out.println(email);
                System.out.println(password);
                firebaseAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(),editTextPassword.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    //registerData();
                                    Intent ain=new Intent(UserRegisterationActivity.this,verification.class);
                                    ain.putExtra("name",name.getText().toString());
                                    ain.putExtra("number",mobilenumber.getText().toString());
                                    ain.putExtra("dob",editTextdob.getText().toString());
                                    ain.putExtra("gender",gender.getSelectedItem().toString());
                                    ain.putExtra("blood",bloodgroup.getSelectedItem().toString());
                                    startActivity(ain);
                                }
                                else{
                                    Toast.makeText(UserRegisterationActivity.this, "Could not Registere, Please try again!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }
        });


        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserRegisterationActivity.this, UserLogin.class));
            }
        });
    }
    void registerData() {
        final String n=name.getText().toString();
        final String db = editTextdob.getText().toString();
        final String bg = bloodgroup.getSelectedItem().toString();
        final String ge = gender.getSelectedItem().toString();
        UserInformation u = new UserInformation(n,bg, ge, db);
        FirebaseDatabase.getInstance().getReference("Users").child((FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(u);
    }

    private void updateLabel() {
        String format = "dd/MM/yy";
        SimpleDateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
        editTextdob.setText(df.format(c.getTime()));
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
