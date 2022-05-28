package com.example.tfg2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.tfg2.BD.BaseDatos;
import com.example.tfg2.fragments.CamisetasFragment;
import com.example.tfg2.fragments.CarritoFragment;
import com.example.tfg2.fragments.ConfiguracionFragment;
import com.example.tfg2.fragments.EventosFragment;
import com.example.tfg2.fragments.FalsoConfiguracionFragment;
import com.example.tfg2.fragments.FalsoFavoritosFragment;
import com.example.tfg2.fragments.FalsoNotificacionesFragment;
import com.example.tfg2.fragments.FavoritosFragment;
import com.example.tfg2.fragments.NotificacionesFragment;
import com.example.tfg2.fragments.SudaderasFragment;
import com.example.tfg2.fragments.TiendaFragment;
import com.example.tfg2.login.Registro;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    BaseDatos admin;
    SQLiteDatabase bd;
    Cursor fila;
    ContentValues contentValues = new ContentValues();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    TextView infoUser;

    boolean aaa = true;
    boolean tienda = true;
    boolean sudaderas;
    boolean camisetas;

    String nombreUser;
    String apellidosUser;
    String mailUser;
    int reg = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        comprobarIcinioSesion();
        View hView = navigationView.getHeaderView(0);
        infoUser = (TextView) hView.findViewById(R.id.infoUser);
        infoUser.setText(nombreUser);
        toolbar = findViewById(R.id.toolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.content, new TiendaFragment()).commit();
        setTitle("Tienda");
        setSupportActionBar(toolbar);
        toggle = setUpDrawerToggle();
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    //FirebaseAuth.getInstance().signOut();
    private void cerrarSesion() {

        FirebaseAuth.getInstance().signOut();
        showItemInicioSesion();
        hideItemCerrarSesion();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            hideItemInicioSesion();
            showItemCerrarSesion();
        } else {
            showItemInicioSesion();
            hideItemCerrarSesion();
        }
    }


    private void comprobarIcinioSesion() {
/*
        admin = new BaseDatos(this, "Almacen", null, 1);
        bd = admin.getWritableDatabase();

        fila = bd.rawQuery("select confRegistro from registro where confRegistro=" + 1, null);
*/
        if (mAuth.getCurrentUser() != null) {
            hideItemInicioSesion();
            showItemCerrarSesion();
        } else {
            showItemInicioSesion();
            hideItemCerrarSesion();
        }
        //bd.close();

    }


    //Modelo de usuadio Registrado
    public void hideItemInicioSesion() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_inicio_sesion).setVisible(false);
        nav_Menu.findItem(R.id.nav_falso_favoritos).setVisible(false);
        nav_Menu.findItem(R.id.nav_favoritos).setVisible(true);
        nav_Menu.findItem(R.id.nav_falso_configuracion).setVisible(false);
        nav_Menu.findItem(R.id.nav_configuracion).setVisible(true);
        nav_Menu.findItem(R.id.nav_falso_notificaciones).setVisible(false);
        nav_Menu.findItem(R.id.nav_notificaciones).setVisible(true);
    }

    //Cuando el usuario cierra sesion
    public void showItemInicioSesion() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_inicio_sesion).setVisible(true);
        nav_Menu.findItem(R.id.nav_falso_favoritos).setVisible(true);
        nav_Menu.findItem(R.id.nav_favoritos).setVisible(false);
        nav_Menu.findItem(R.id.nav_falso_configuracion).setVisible(true);
        nav_Menu.findItem(R.id.nav_configuracion).setVisible(false);
        nav_Menu.findItem(R.id.nav_falso_notificaciones).setVisible(true);
        nav_Menu.findItem(R.id.nav_notificaciones).setVisible(false);
    }

    public void showItemCerrarSesion() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_cerrar_sesion).setVisible(true);
        nav_Menu.findItem(R.id.nav_falso_favoritos).setVisible(false);
        nav_Menu.findItem(R.id.nav_favoritos).setVisible(true);
        nav_Menu.findItem(R.id.nav_falso_configuracion).setVisible(false);
        nav_Menu.findItem(R.id.nav_configuracion).setVisible(true);
        nav_Menu.findItem(R.id.nav_falso_notificaciones).setVisible(false);
        nav_Menu.findItem(R.id.nav_notificaciones).setVisible(true);
    }
    //Cuando el usuario a cerrado sesion
    public void hideItemCerrarSesion() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_cerrar_sesion).setVisible(false);
        nav_Menu.findItem(R.id.nav_falso_favoritos).setVisible(true);
        nav_Menu.findItem(R.id.nav_favoritos).setVisible(false);
        nav_Menu.findItem(R.id.nav_falso_configuracion).setVisible(true);
        nav_Menu.findItem(R.id.nav_configuracion).setVisible(false);
        nav_Menu.findItem(R.id.nav_falso_notificaciones).setVisible(true);
        nav_Menu.findItem(R.id.nav_notificaciones).setVisible(false);
    }

    private ActionBarDrawerToggle setUpDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItemNav(item);
        onOptionsItemSelected(item);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);

        if (!aaa) {
            menu.findItem(R.id.Tienda).setVisible(false);
            menu.findItem(R.id.Sudaderas).setVisible(false);
            menu.findItem(R.id.Camisetas).setVisible(false);
        } else {
            menu.findItem(R.id.Tienda).setVisible(true);
            menu.findItem(R.id.Sudaderas).setVisible(true);
            menu.findItem(R.id.Camisetas).setVisible(true);
            if (tienda) {
                menu.findItem(R.id.Tienda).setVisible(false);
                menu.findItem(R.id.Sudaderas).setVisible(true);
                menu.findItem(R.id.Camisetas).setVisible(true);
            } else if (sudaderas) {
                menu.findItem(R.id.Tienda).setVisible(true);
                menu.findItem(R.id.Sudaderas).setVisible(false);
                menu.findItem(R.id.Camisetas).setVisible(true);
            } else if (camisetas) {
                menu.findItem(R.id.Tienda).setVisible(true);
                menu.findItem(R.id.Sudaderas).setVisible(true);
                menu.findItem(R.id.Camisetas).setVisible(false);
            }
        }

        if (mAuth.getCurrentUser() != null) {
            menu.findItem(R.id.CarritodeCompra).setVisible(true);
            invalidateOptionsMenu();
        } else {
            menu.findItem(R.id.CarritodeCompra).setVisible(false);
            invalidateOptionsMenu();
        }


        return true;
    }

    private void selectItemNav(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        switch (item.getItemId()) {
            case R.id.nav_tienda:
                fragmentTransaction.replace(R.id.content, new TiendaFragment()).commit();
                aaa = true;
                Log.i("INFO", String.valueOf(aaa));
                invalidateOptionsMenu();
                setTitle(item.getTitle());
                break;
            case R.id.nav_notificaciones:
                fragmentTransaction.replace(R.id.content, new NotificacionesFragment()).commit();
                aaa = false;
                Log.i("INFO", String.valueOf(aaa));
                invalidateOptionsMenu();
                setTitle(item.getTitle());
                break;
            case R.id.nav_falso_notificaciones:
                fragmentTransaction.replace(R.id.content, new FalsoNotificacionesFragment()).commit();
                aaa = false;
                Log.i("INFO", String.valueOf(aaa));
                invalidateOptionsMenu();
                setTitle(item.getTitle());
                break;
            case R.id.nav_favoritos:
                fragmentTransaction.replace(R.id.content, new FavoritosFragment()).commit();
                aaa = false;
                Log.i("INFO", String.valueOf(aaa));
                invalidateOptionsMenu();
                setTitle(item.getTitle());
                break;
            case R.id.nav_falso_favoritos:
                fragmentTransaction.replace(R.id.content, new FalsoFavoritosFragment()).commit();
                aaa = false;
                Log.i("INFO", String.valueOf(aaa));
                invalidateOptionsMenu();
                setTitle(item.getTitle());
                break;
            case R.id.nav_eventos:
                fragmentTransaction.replace(R.id.content, new EventosFragment()).commit();
                aaa = false;
                Log.i("INFO", String.valueOf(aaa));
                invalidateOptionsMenu();
                setTitle(item.getTitle());
                break;
            case R.id.nav_configuracion:
                fragmentTransaction.replace(R.id.content, new ConfiguracionFragment()).commit();
                aaa = false;
                Log.i("INFO", String.valueOf(aaa));
                invalidateOptionsMenu();
                setTitle(item.getTitle());
                break;
            case R.id.nav_falso_configuracion:
                fragmentTransaction.replace(R.id.content, new FalsoConfiguracionFragment()).commit();
                aaa = false;
                Log.i("INFO", String.valueOf(aaa));
                invalidateOptionsMenu();
                setTitle(item.getTitle());
                break;
            case R.id.nav_inicio_sesion:
                Intent intent = new Intent(this, Registro.class);
                startActivity(intent);
                break;
            case R.id.nav_cerrar_sesion:
                cerrarSesion();
                fragmentTransaction.replace(R.id.content, new TiendaFragment()).commit();
                setTitle("Tienda");
                break;

        }

        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.Tienda:
                fragmentTransaction.replace(R.id.content, new TiendaFragment()).commit();
                tienda = true;
                sudaderas = false;
                camisetas = false;
                invalidateOptionsMenu();
                setTitle("Tienda");
                break;
            case R.id.Sudaderas:
                fragmentTransaction.replace(R.id.content, new SudaderasFragment()).commit();
                tienda = false;
                sudaderas = true;
                camisetas = false;
                invalidateOptionsMenu();
                setTitle("Sudaderas");
                break;
            case R.id.Camisetas:
                fragmentTransaction.replace(R.id.content, new CamisetasFragment()).commit();
                tienda = false;
                sudaderas = false;
                camisetas = true;
                invalidateOptionsMenu();
                setTitle("Camisetas");
                break;
            case R.id.CarritodeCompra:
                fragmentTransaction.replace(R.id.content, new CarritoFragment()).commit();
                invalidateOptionsMenu();
                setTitle("Carritis compri");
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}