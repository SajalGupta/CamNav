package com.example.sajal.camnav;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sajal on 05-04-2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    List<RecylerViewData> data ;
    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RecyclerViewAdapter(List<RecylerViewData> data) {

        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       // View view = inflater.inflate(R.layout.custom_row,viewGroup,false);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row,viewGroup,false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        RecylerViewData current = data.get(i);
        myViewHolder.title.setText(current.title);
        myViewHolder.icon.setImageResource(current.iconId);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {

            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            title=(TextView)itemView.findViewById(R.id.listText);
            icon=(ImageView)itemView.findViewById(R.id.listIcon);

        }

        @Override
        public void onClick(View v) {
            v.setSelected(true);
            Toast.makeText(v.getContext(),"Item Clicked"+title.getText(),Toast.LENGTH_SHORT).show();
            if(clickListener!=null){
                clickListener.itemClicked(v,getPosition(),data);
            }
        }
    }
    public interface ClickListener{
        public void itemClicked(View view,int position,List<RecylerViewData> data);
    }
}
