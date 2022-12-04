package com.example.a2048;
import android.content.Context;
import android.text.Layout;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout{
    private int count = 0;
    private TextView name;

    public Card(Context context){
        super(context);
        name = new TextView(getContext());
        name.setTextSize(32);
        name.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1,-1);
        lp.setMargins(10,10,0,0);
        addView(name, lp);
        setCount(0);
    }

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
        if(count>0){
            name.setText(count +"");
        }
        else{
            name.setText("");
        }
// these colors can be changed to anything,just using these for now
        //we can see what they look like altogether later with more implemented
        if(count == 0){
            name.setBackgroundColor(0xffccc0b2);
        }
        if(count == 2){
            name.setBackgroundColor(0xffeee4da);
        }
        if(count == 4){
            name.setBackgroundColor(0xffede0c8);
        }
        if(count == 8){
            name.setBackgroundColor(0xfff2b179);
        }
        if(count == 16){
            name.setBackgroundColor(0xfff59563);
        }
        if(count == 32){
            name.setBackgroundColor(0xfff67c5f);
        }
        if(count == 64){
            name.setBackgroundColor(0xfff65e3b);
        }
        if(count == 128){
            name.setBackgroundColor(0xffedcf72);
        }
        if(count == 256){
            name.setBackgroundColor(0xffedc750);
        }
        if(count == 512){
            name.setBackgroundColor(0xffedc850);
        }
        if(count == 1024){
            name.setBackgroundColor(0xffecc640);
        }
        if(count == 2048){
            name.setBackgroundColor(0xffedc22d);
        }
    }

    public boolean equals(Card card){
        return getCount()==card.getCount();
    }

}

