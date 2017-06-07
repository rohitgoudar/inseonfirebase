package com.appdeveloperrohitgmail.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.SmileRating;

public class MainActivity extends AppCompatActivity {

    TextView q2;
    LinearLayout v2;
    String[] high,low;
    CheckBox checkBox[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        q2= (TextView)findViewById(R.id.q2) ;
        v2= (LinearLayout)findViewById(R.id.v2);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("2");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // String value = dataSnapshot.getValue(String.class);
                Log.d("ds1", "Value is: " +dataSnapshot.toString());
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(dataSnapshot1.getKey().equals("high")){
                        high=new String[(int) dataSnapshot1.getChildrenCount()];
                        int i=0;
                        for(DataSnapshot snapshot:dataSnapshot1.getChildren()){
                            high[i++]=snapshot.getValue().toString();
                        }
                    }
                    if(dataSnapshot1.getKey().equals("low")){
                        low=new String[(int) dataSnapshot1.getChildrenCount()];
                        int i=0;
                        for(DataSnapshot snapshot:dataSnapshot1.getChildren()){
                            low[i++]=snapshot.getValue().toString();
                        }
                    }
                    q2.setText(dataSnapshot1.getValue().toString());
                    Log.d("DS","KEY: "+dataSnapshot1.getKey()+" \nvalue"+dataSnapshot1.getValue());
                }
                Log.i("HIGH VALUES: ",high[1]);
                Log.i("LOW VALUES : ",low.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ds2", "Failed to read value.", databaseError.toException());

            }
        });


        checkBox = new CheckBox[10];

        final SmileRating smileRating=(SmileRating)findViewById(R.id.smiley_rating2);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                int i=0;
                switch (smiley)
                {
                    case SmileRating.BAD:
                    case SmileRating.GOOD:
                    case SmileRating.OKAY:
                    case SmileRating.TERRIBLE:
                        v2.removeAllViews();
                        for(String value:low){
                            checkBox[i] = new CheckBox(MainActivity.this);
                            checkBox[i].setText(value);
                            checkBox[i].setId(i);
                            v2.addView(checkBox[i]);
                            i++;
                        }
                        break;
                    case SmileRating.GREAT:
                        v2.removeAllViews();
                        for(String value:high){
                            checkBox[i]=new CheckBox(MainActivity.this);
                            checkBox[i].setText(value);
                            checkBox[i].setId(i);
                            v2.addView(checkBox[i]);
                            i++;
                        }
                        break;
                }


            }
        });





    }
}


    /*Can you let us know the reason for your rating?
        Room was not well equipped with furniture, lighting
        Room and bathroom were not very clean
        Bed Linen and towels were not compfortable or clean */