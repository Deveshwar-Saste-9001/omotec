package com.example.attendancesystem_omotec.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.attendancesystem_omotec.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.ViewHolder> {
    private List<Integer> sliderbannerList;

    public SliderAdapter(List<Integer> sliderbannerList) {
        this.sliderbannerList = sliderbannerList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.setBanner(sliderbannerList.get(position));


    }

    @Override
    public int getCount() {
        return sliderbannerList.size();
    }
    static class ViewHolder extends SliderViewAdapter.ViewHolder {
        private ImageView sliderimage;
        public ViewHolder(View itemView) {
            super(itemView);
            sliderimage=itemView.findViewById(R.id.Slide_banner1);
        }
        public void setBanner(int id){
            sliderimage.setImageResource(id);
        }
    }
}
