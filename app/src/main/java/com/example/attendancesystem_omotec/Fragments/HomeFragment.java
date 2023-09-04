package com.example.attendancesystem_omotec.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.attendancesystem_omotec.Adaptors.SliderAdapter;
import com.example.attendancesystem_omotec.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    private RecyclerView homePageRecyclerView;

    private List<Integer> sliderbannerlist=new ArrayList<>();
    private SliderAdapter sliderAdapter;
    private SliderView bannerSliderView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homePageRecyclerView=view.findViewById(R.id.categoryRecyclerView);
        bannerSliderView=view.findViewById(R.id.Banner_slider_slideview);

        sliderbannerlist.add(R.mipmap.slide1);
        sliderbannerlist.add(R.mipmap.slide2);
        sliderbannerlist.add(R.mipmap.slide3);
        sliderbannerlist.add(R.mipmap.slide4);
        sliderbannerlist.add(R.mipmap.slide5);
        sliderbannerlist.add(R.mipmap.slide6);
        sliderbannerlist.add(R.mipmap.slide7);

        sliderAdapter=new SliderAdapter(sliderbannerlist);
        bannerSliderView.setSliderAdapter(sliderAdapter);
        bannerSliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        bannerSliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        bannerSliderView.startAutoCycle();

        return view;
    }
}
