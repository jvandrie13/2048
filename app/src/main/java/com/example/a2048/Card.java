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

        if(count == 0){

        }
    }


}
