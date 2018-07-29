package com.hackthon.srahulkumar.remotebus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {
    private  int[] img={R.drawable.image01,R.drawable.image2,R.drawable.image3};
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_one, container, false);
       /* Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        v.setSupportActionBar(toolbar);*/
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        ViewFlipper flipper=(ViewFlipper) v.findViewById(R.id.vf);
        for (int i=0;i<img.length;i++){
            ImageView imageView=new ImageView(getContext());
            imageView.setImageResource(img[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            flipper.addView(imageView);
        }
        Animation in= AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation out= AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);
        flipper.setInAnimation(in);
        flipper.setOutAnimation(out);
        flipper.setFlipInterval(2000);
        flipper.setAutoStart(true);
        return  v;
    }

}
