package com.example.seuprofissional;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import activity.MenuInicial;
import helper.ConfiguracaoFirebase;

public class MainActivityProfissional extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profissional);
        Toolbar toolbarprofi= findViewById(R.id.toolbarProfi);
        setSupportActionBar(toolbarprofi);

        //CONFIGURAÇÃO DE OBJETOS
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        DrawerLayout drawer = findViewById(R.id.drawer_layout_profi);
        NavigationView navigationView = findViewById(R.id.nav_view_profi);

        //Adiciona as telas de navegação do menu mobile_navigation_user
        mAppBarConfiguration = new AppBarConfiguration.Builder( R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        //Configura área que irá carregar os fragmetnos
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_profi);
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
                deslogarUsuarioProfissional();
                startActivity(new Intent(getApplicationContext(), MenuInicial.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void deslogarUsuarioProfissional(){
        try{
            autenticacao.signOut();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_profi);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }



}