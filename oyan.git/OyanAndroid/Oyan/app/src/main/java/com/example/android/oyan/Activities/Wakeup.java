package com.example.android.oyan.Activities;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.oyan.Callbacks.WakeUpCallback;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Controllers.WakeUpController;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.Domain.WakeUpDomain;
import com.example.android.oyan.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;

public class Wakeup extends AppCompatActivity implements WakeUpCallback {

    public TextView fullname;
    public TextView status;
    public UserDomain userDomain;
    ImageView profilPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup);

        userDomain = (UserDomain) getIntent().getSerializableExtra("wakeupDomain");
        final Wakeup wp = this;
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Trebuchet MS.ttf");
        ((TextView) this.findViewById(R.id.fragmentNameW)).setTypeface(font);
        profilPhoto = (ImageView) this.findViewById(R.id.profilPhotoW);
        status = ((TextView) this.findViewById(R.id.statusIDW));

        ((TextView) this.findViewById(R.id.fragmentNameW)).setText(userDomain.getFullName());
        status.setText(userDomain.getStatus());

        ImageView backImage = (ImageView) this.findViewById(R.id.backW);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wp.finish();
            }
        });

        WakeUpController controller = new WakeUpController();
        controller.getCallData(this, this, userDomain);
        int t = R.drawable.userph;
        Bitmap myBitmap = null;
        if (myBitmap == null) {
            Picasso.with(this).load(Constant.SERVER_URL_PHOTO +
                    userDomain.getPhotoName())
                    .placeholder(t).into(profilPhoto);
        } else {
            profilPhoto.setImageBitmap(myBitmap);
        }

        LinearLayout rl = (LinearLayout) this.findViewById(R.id.frament_count);
        Calendar localTime = Calendar.getInstance(Locale.getDefault());
        int hour = localTime.getTime().getHours();
        if (hour > 6 && hour < 18) {
            rl.setBackgroundResource(R.drawable.bg1);

        } else if (hour > 18 && hour < 20) {
            rl.setBackgroundResource(R.drawable.bg2);
        } else {
            rl.setBackgroundResource(R.drawable.bg3);
        }

    }

    @Override
    public void onGetCallDataResult(Object object) {
        if (object instanceof WakeUpDomain) {
            WakeUpDomain resultDomain = (WakeUpDomain) object;
            ((TextView) findViewById(R.id.textq1)).setText(String.valueOf(resultDomain.getIncomingCallAmount()));
            ((TextView) findViewById(R.id.textq2)).setText(String.valueOf(resultDomain.getAcceptCallAmount()));
            ((TextView) findViewById(R.id.textq3)).setText(String.valueOf(resultDomain.getOutgoingCallAmount()));
        }
    }
}
