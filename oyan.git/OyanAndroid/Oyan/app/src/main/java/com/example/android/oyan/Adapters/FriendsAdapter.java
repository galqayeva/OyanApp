package com.example.android.oyan.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.oyan.BroadCastRecievers.NetworkChangeReceiver;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.Sip;
import com.example.android.oyan.Services.SipService;

import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.app.CallActivity;
import org.pjsip.pjsua2.app.MyCall;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Eldar on 11/12/2015.
 */
public class FriendsAdapter extends BaseAdapter {


    public Context context;
    public ArrayList<UserDomain> userDomains;

    public FriendsAdapter(Context context, ArrayList<UserDomain> userDomains) {

        this.userDomains = userDomains;
        this.context = context;
       }

    @Override
    public int getCount() {
        if (userDomains == null) return 0;
        return userDomains.size();
    }

    @Override
    public UserDomain getItem(int position) {
        return userDomains.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String addTime(long ft) {

        String minute1 = "";
        try {
            int hour = (int) (ft / 60);
            int minute = (int) (ft % 60);

            Calendar myTime = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            myTime.set(Calendar.HOUR_OF_DAY, hour);
            myTime.set(Calendar.MINUTE, minute);

            Calendar localTime = Calendar.getInstance(Locale.getDefault());

            if (localTime.getTimeZone().getID().equals("Asia/Baku")) {
                localTime = Calendar.getInstance(TimeZone.getTimeZone("Asia/Dubai"));
            }

            localTime.setTimeInMillis(myTime.getTimeInMillis());
            hour = localTime.get(Calendar.HOUR_OF_DAY);
            minute = localTime.get(Calendar.MINUTE);

            String hour1 = hour < 10 ? "0" + hour : hour + "";
            minute1 = hour1 + " : " + (minute < 10 ? "0" + minute : minute + "");
        } catch (Exception e) {
        }
        return minute1;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        FriendHolder holder = null;
        final int position1 = position;

        try {
            if (convertView == null) {

                convertView = View.inflate(context, R.layout.friendlist_item, null);
                holder = new FriendHolder();
                holder.textId1 = (TextView) convertView.findViewById(R.id.textId1);
                holder.isActive1 = (TextView) convertView.findViewById(R.id.isActive1);
                holder.textDiffer1 = (TextView) convertView.findViewById(R.id.text2);
                holder.text = (TextView) convertView.findViewById(R.id.text1);
                holder.textPlace = (TextView) convertView.findViewById(R.id.textPlace1);
                holder.userTime = (TextView) convertView.findViewById(R.id.userTime1);
                holder.img = (ImageView) convertView.findViewById(R.id.img1);
                holder.textViewStatus = (TextView) convertView.findViewById(R.id.textViewStatus);
                holder.imageButton = (ImageView) convertView.findViewById(R.id.makeCall1);
                convertView.setTag(holder);
            } else {

                holder = (FriendHolder) convertView.getTag();
                holder.userTime.setText("");
                holder.textId1.setText("");
                holder.isActive1.setText("");
                holder.textDiffer1.setText("");
                holder.text.setText("");
                holder.textPlace.setText("");
                holder.textViewStatus.setText("");
                holder.imageButton.setVisibility(View.INVISIBLE);
            }
            final int f = position;
            String tt = "";

            holder.textId1.setText(userDomains.get(position).getId() + "");
            holder.isActive1.setText(userDomains.get(position).getIsActive() + "");
            holder.textDiffer1.setText(userDomains.get(position).getTimeDifference() + "");
            holder.text.setText(userDomains.get(position).getFullName());
            holder.textViewStatus.setText(userDomains.get(position).getStatus());

            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Trebuchet MS.ttf");
            holder.textPlace.setTypeface(font);
            holder.text.setTypeface(font);
            holder.textViewStatus.setTypeface(font);
            holder.userTime.setTypeface(font);
            if (userDomains.get(position).getIsActive() == 1) {

                holder.userTime.setText(nameofweek(userDomains.get(position).getWeekDay())
                        +" " + addTime(userDomains.get(position).getFirstTime()));
                holder.textPlace.setText(userDomains.get(position).getLocation());

                if (userDomains.get(position).getTimeDifference() < 20)
                    holder.imageButton.setVisibility(View.VISIBLE);
                else
                    holder.imageButton.setVisibility(View.INVISIBLE);
            } else {
                holder.imageButton.setVisibility(View.INVISIBLE);

            }

            Glide.with(context).
                    load(Constant.SERVER_URL_PHOTO + userDomains.get(position1).getPhotoName()).
                    placeholder(R.drawable.userph).into(holder.img);

            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater l = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    AlertDialog.Builder b = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

                    View view4 = (View) l.inflate(R.layout.sleepiesphoto, null);
                    b.setView(view4);


                    ImageView imageView4 = (ImageView) view4.findViewById(R.id.sleepiesdialogPhoto);
                    TextView textViewName = (TextView) view4.findViewById(R.id.textViewdialogName);
                    TextView textViewSts = (TextView) view4.findViewById(R.id.textViewdialogStatus);
                    textViewName.setText(userDomains.get(position).getFullName());
                    textViewSts.setText(userDomains.get(position).getStatus());
                    b.show();
                    if (userDomains.get(position1).getPhotoName() != null)
                        Glide.with(context).
                                load(Constant.SERVER_URL_PHOTO + userDomains.get(position1).getPhotoName())
                                 .placeholder(R.drawable.userph).into(imageView4);
                }
            });
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!Sip.networkConnected) {
                        AlertDialog.Builder b = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        final AlertDialog ad = b.create();
                        ad.setTitle("Call Connection");
                        ad.setMessage("Call Connection is not successfull. Please connect again!");
                        ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Connect", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final Intent i1 = new Intent(context, SipService.class);
                                context.stopService(i1);
//                                Sip.networkConnected = false;
                                context.startService(i1);
                                ad.dismiss();
                            }
                        });
                        ad.show();

                    } else {

                        if (Sip.currentCall != null) {
                            return;
                        }

                        if (Sip.account == null || !Sip.account.isValid()) {
                            NetworkChangeReceiver n = new NetworkChangeReceiver();
                            n.onReceive(context, new Intent());
                            return;
                        }

                        MyCall call = new MyCall(Sip.account, -1);
                        Sip.currentCall = call;
                        String buddy_uri = "sip:" + userDomains.get(position1).getSipUsername() + "@" + userDomains.get(position1).getSipRegistrar().substring(4);
                        CallOpParam prm = new CallOpParam(true);
                        try {
                            call.makeCall(buddy_uri, prm);
                        } catch (Exception e) {
                            call.delete();
                            return;
                        }
                        Intent intent = new Intent(context, CallActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("img", Constant.SERVER_URL_PHOTO + userDomains.get(position).getPhotoName());
                        intent.putExtra("text", userDomains.get(position1).getFullName());
                        context.startActivity(intent);
                    }
                }
            });

        } catch (Exception e) {

        }
        return convertView;
    }


    private static class FriendHolder {

        public TextView isActive1;
        public TextView textDiffer1;
        public TextView text;
        public TextView textPlace;
        public TextView userTime;
        public ImageView imageButton;
        public ImageView img;
        public TextView textId1;
        public TextView textViewStatus;

    }

    String nameofweek(long w){
        if(w == 1) return "Sunday";
        else if (w == 2 )return "Monday";
        else if (w == 3 )return "Tuesday";
        else if (w == 4 )return "Wednesday";
        else if (w == 5 )return "Thursday";
        else if (w == 6 )return "Friday";
        else if (w == 7 )return "Saturday";
return "";
    }
}