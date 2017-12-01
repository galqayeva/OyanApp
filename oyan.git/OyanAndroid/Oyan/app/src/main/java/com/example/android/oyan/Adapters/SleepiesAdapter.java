package com.example.android.oyan.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.oyan.Activities.MainActivity;
import com.example.android.oyan.Activities.Wakeup;
import com.example.android.oyan.BroadCastRecievers.NetworkChangeReceiver;
import com.example.android.oyan.Constants.Constant;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.R;
import com.example.android.oyan.Domain.Sip;
import com.example.android.oyan.Services.SipService;

import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.app.CallActivity;
import org.pjsip.pjsua2.app.MyCall;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Eldar on 11/12/2015.
 */
public class SleepiesAdapter extends SimpleAdapter {

    public List<? extends Map<String, String>> data;
    public Context context;
    public static MainActivity m;

    public SleepiesAdapter(Context context, List<? extends Map<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        final View v1 = v;
        final int position1 = position;
        try {
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item, null);
            }
            TextView text = (TextView) v.findViewById(R.id.text);
            text.setText(data.get(position).get("text"));
            final int f = position;
            TextView textPlace = (TextView) v.findViewById(R.id.textPlace);
            textPlace.setText(data.get(position).get("textPlace"));
            TextView textStatus = (TextView) v.findViewById(R.id.textViewStatus);
            textStatus.setText(data.get(position).get("textViewStatus"));
            TextView userTime = (TextView) v.findViewById(R.id.userTime);
            userTime.setText(data.get(position).get("userTime"));
            final ImageView img = (ImageView) v.findViewById(R.id.img);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Trebuchet MS.ttf");
            textPlace.setTypeface(font);
            text.setTypeface(font);
            textStatus.setTypeface(font);
            userTime.setTypeface(font);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemclick(position1);

                }
            });
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater l = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    AlertDialog.Builder b = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

                    View view4 = (View) l.inflate(R.layout.sleepiesphoto, null);
                    b.setView(view4);


                    ImageView imageView4 = (ImageView) view4.findViewById(R.id.sleepiesdialogPhoto);
                    TextView textViewName = (TextView) view4.findViewById(R.id.textViewdialogName);
                    TextView textViewSts = (TextView) view4.findViewById(R.id.textViewdialogStatus);

                    textViewName.setText(data.get(position1).get("text"));
                    textViewSts.setText(data.get(position1).get("textViewStatus"));
                    b.show();
                    if (data.get(position1).get("img") != null)
                        Glide.with(context).load(Constant.SERVER_URL_PHOTO +
                                data.get(position1).get("img"))
                                //.memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
                                .placeholder(R.drawable.userph).into(imageView4);
                }
            });
            ImageView imageButton = (ImageView) v.findViewById(R.id.makeCall);
            imageButton.setOnClickListener(new View.OnClickListener() {
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
                        String buddy_uri = "sip:" + data.get(f).get("textSipUsername") + "@" + data.get(f).get("textSipRegistrar").substring(4);
                        CallOpParam prm = new CallOpParam(true);
                        try {
                            call.makeCall(buddy_uri, prm);
                        } catch (Exception e) {
                            call.delete();
                            return;
                        }
                        Intent intent = new Intent(m, CallActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("img", Constant.SERVER_URL_PHOTO + data.get(f).get("img"));
                        intent.putExtra("text", data.get(f).get("text"));
                        intent.putExtra("isFriend", data.get(f).get("isFriend"));
                        intent.putExtra("Id", data.get(f).get("textId"));
                        m.startActivity(intent);
                    }
                }
            });
            if (data.get(position1).get("img") != null)
                Glide.with(context).load(Constant.SERVER_URL_PHOTO + data.get(position).get("img")).placeholder(R.drawable.userph).into(img);
        } catch (Exception e) {
            Log.v("Error  - ", e.getMessage());
        }
        return v;
    }

    public void itemclick(int position) {

        UserDomain userDomain5 = new UserDomain();
        userDomain5.setId(Integer.valueOf(data.get(position).get("textId")));
        userDomain5.setPhotoName(data.get(position).get("img"));
        userDomain5.setFullName(data.get(position).get("text"));
        Intent intent = new Intent(m, Wakeup.class);
        intent.putExtra("wakeupDomain", (Serializable) userDomain5);
        m.startActivity(intent);
    }


}