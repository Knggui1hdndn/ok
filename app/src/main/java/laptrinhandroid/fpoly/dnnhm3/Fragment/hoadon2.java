package laptrinhandroid.fpoly.dnnhm3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import laptrinhandroid.fpoly.dnnhm3.Activity.hoadon11111;
import laptrinhandroid.fpoly.dnnhm3.R;


public class hoadon2 extends Fragment {

    FloatingActionButton faa;
    public hoadon2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hoadon2, container, false);
        faa=view.findViewById(R.id.floating2);
        faa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        return view;
    }
}