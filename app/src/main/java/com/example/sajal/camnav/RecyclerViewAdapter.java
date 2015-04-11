package com.example.sajal.camnav;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    List<RecylerViewData> data ;
    Context context;

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RecyclerViewAdapter(Context context,List<RecylerViewData> data) {

        this.data=data;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       // View view = inflater.inflate(R.layout.custom_row,viewGroup,false);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row,viewGroup,false);


        return new MyViewHolder(view);
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
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
           // int height = size.y;
            ObjectAnimator moveAnim = ObjectAnimator.ofFloat(itemView,"x",width,0);
            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(itemView, "alpha", 0, 1);
            AnimatorSet movFadeAnim = new AnimatorSet();
            movFadeAnim.playTogether(moveAnim,fadeAnim);
            movFadeAnim.setDuration(1000);
            moveAnim.setInterpolator(new LinearInterpolator());
            movFadeAnim.start();


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
