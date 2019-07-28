package com.example.cidebaymaxtv.instragramclone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment seletFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);



        bottomNavigationView = findViewById(R.id.buttom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_continer,
                new HomeFragment()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){


                        case R.id.nav_home:

                            seletFragment = new HomeFragment();

                            break;


                        case R.id.nav_seart:
                            seletFragment = new SearchFragment();

                            break;


                        case R.id.nav_add:
                            seletFragment = null;
                            startActivity(new Intent(Main2Activity.this, PostActivity.class));
                            break;

                        case R.id.nav_love:
                            seletFragment = new LoveFragment();

                            break;

                        case R.id.nav_profile:
                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("profiled", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            editor.apply();
                            seletFragment = new ProfileFragment();
                            break;


                    }

                    if (seletFragment != null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_continer,
                                seletFragment).commit();
                    }




                    return false;
                }
            };

}
