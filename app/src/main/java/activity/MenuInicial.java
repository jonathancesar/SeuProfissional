package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.seuprofissional.R;

public class MenuInicial extends AppCompatActivity {

    private ImageView imgUsuario;
    private ImageView imgProfissional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        imgUsuario = (ImageView) findViewById(R.id.imgUsuario);
        imgProfissional = (ImageView) findViewById(R.id.imgProfissional);

        imgUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginUser.class);
                startActivity(intent);
            }
        });

        imgProfissional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginProfissional.class);
                startActivity(intent);
            }
        });

    }




}

