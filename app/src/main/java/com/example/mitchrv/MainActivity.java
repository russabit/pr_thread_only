package com.example.mitchrv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.mitchrv.APIs.InterfaceMainActivity;
import com.example.mitchrv.fragments.InfoFragment;
import com.example.mitchrv.fragments.MessagesRecyclerFragment;
import com.example.mitchrv.fragments.RecyclerFragment;

public class MainActivity extends AppCompatActivity implements InterfaceMainActivity {

    private static final String TAG = "MainActivity";

    //vars

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        RecyclerFragment recyclerFragment = new RecyclerFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.main_container);
    if (fragmentById==null)
        ft.add(R.id.main_container, recyclerFragment).commit();
    }


    @Override
    public void inflateFragment(String imageUrl, String imageName, int name) {
        MessagesRecyclerFragment fragment = MessagesRecyclerFragment.newInstance(imageUrl, imageName, name);
        Log.d(TAG, "inflateFragment: called " + name);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_container, fragment).addToBackStack(null).commit();
    }
}
