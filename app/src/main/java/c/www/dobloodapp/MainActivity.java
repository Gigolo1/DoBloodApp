package c.www.dobloodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button admin_button;
    private Button user_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin_button =(Button) findViewById(R.id.admin_button);
        user_button =(Button) findViewById(R.id.user_button);

        admin_button.setOnClickListener(this);
        user_button.setOnClickListener(this);
    }

    public void onClick(View view) {
        if(view==admin_button){
            startActivity(new Intent(this, AdminLogin.class));
        }
        if(view==user_button){
            finish();
            startActivity(new Intent(this, UserLogin.class));
        }
    }
}
