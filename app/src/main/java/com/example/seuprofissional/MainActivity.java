package com.example.seuprofissional;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import activity.MenuInicial;
import helper.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth autenticacao;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //CONFIGURAÇÃO DE OBJETOS
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //Adiciona as telas de navegação do menu mobile_navigation_user
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_pesqProfi, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        //Configura área que irá carregar os fragmetnos
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //configura menu superior de navegação
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //configura navegação para o NavigationView
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present.
     //  getMenuInflater().inflate(R.menu.main, menu);
      //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sair:
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(), MenuInicial.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void deslogarUsuario(){
        try{
            autenticacao.signOut();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }


}