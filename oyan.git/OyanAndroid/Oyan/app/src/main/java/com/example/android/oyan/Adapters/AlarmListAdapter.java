package com.example.android.oyan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.android.oyan.Services.AlarmManagerService;
import com.example.android.oyan.Controllers.EditAlarmController;
import com.example.android.oyan.Domain.AlarmDomain;
import com.example.android.oyan.Domain.UserDomain;
import com.example.android.oyan.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Eldar on 10/28/2015.
 */
public class AlarmListAdapter extends BaseAdapter {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    private ArrayList<AlarmDomain> alarmDomains;
    private LayoutInflater layoutInflater;
    private Context context;
    private UserDomain userDomain;
    private AlarmListAdapter alarmListAdapter = this;
    public boolean[] firstChecked;

    public AlarmListAdapter(Context context, ArrayList<AlarmDomain> alarmDomains, UserDomain userDomain, boolean[] firstCheck) {

        this.alarmDomains = alarmDomains;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.userDomain = userDomain;
        this.firstChecked = firstCheck;


    }

    @Override
    public int getCount() {
        return alarmDomains.size();
    }

    @Override
    public AlarmDomain getItem(int position) {
        return alarmDomains.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        // current menu type
        return position % 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.alarm_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView4);
            holder.aSwitch = (Switch) convertView.findViewById(R.id.switch1);
            holder.Mo = (TextView) convertView.findViewById(R.id.Mo);
            holder.Tu = (TextView) convertView.findViewById(R.id.Tu);
            holder.We = (TextView) convertView.findViewById(R.id.We);
            holder.Th = (TextView) convertView.findViewById(R.id.Th);
            holder.Fr = (TextView) convertView.findViewById(R.id.Fr);
            holder.Sa = (TextView) convertView.findViewById(R.id.Sa);
            holder.Su = (TextView) convertView.findViewById(R.id.Su);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.Mo.setTextColor(Color.parseColor("#A8A8A8"));
            holder.Tu.setTextColor(Color.parseColor("#A8A8A8"));
            holder.We.setTextColor(Color.parseColor("#A8A8A8"));
            holder.Th.setTextColor(Color.parseColor("#A8A8A8"));
            holder.Fr.setTextColor(Color.parseColor("#A8A8A8"));
            holder.Sa.setTextColor(Color.parseColor("#A8A8A8"));
            holder.Su.setTextColor(Color.parseColor("#A8A8A8"));
            holder.aSwitch.setChecked(false);
            holder.aSwitch.setText("");
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Trebuchet MS.ttf");
        holder.textView.setTypeface(font);
        holder.Mo.setTypeface(font);
        holder.Tu.setTypeface(font);
        holder.We.setTypeface(font);
        holder.Th.setTypeface(font);
        holder.Fr.setTypeface(font);
        holder.Sa.setTypeface(font);
        holder.Su.setTypeface(font);

        try {
            final Switch aSwitch1 = holder.aSwitch;
            Long time = alarmDomains.get(position).getTime();
            long h = time / 60;
            long m = time % 60;
            String hour = String.valueOf(h);
            if (h < 10) hour = "0" + hour;
            else {
                hour = String.valueOf(h);
            }
            String minute = String.valueOf(m);
            if (m < 10) minute = "0" + minute;
            else {
                minute = String.valueOf(m);
            }
            holder.textView.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
            long[] weekdays1 = alarmDomains.get(position).getWeekDays();
            for (long x : weekdays1) {
                switch ((int) x) {

                    case 2:
                        holder.Mo.setTextColor(Color.parseColor("#FFFFFF"));
                        break;
                    case 3:
                        holder.Tu.setTextColor(Color.parseColor("#FFFFFF"));
                        break;
                    case 4:
                        holder.We.setTextColor(Color.parseColor("#FFFFFF"));
                        break;
                    case 5:
                        holder.Th.setTextColor(Color.parseColor("#FFFFFF"));
                        break;
                    case 6:
                        holder.Fr.setTextColor(Color.parseColor("#FFFFFF"));
                        break;
                    case 7:
                        holder.Sa.setTextColor(Color.parseColor("#FFFFFF"));
                        break;
                    case 1:
                        holder.Su.setTextColor(Color.parseColor("#FFFFFF"));
                        break;
                    default:
                        break;
                }
            }

            if (alarmDomains.get(position).getIsActive() == 1)
                holder.aSwitch.setChecked(true);
            else {
                holder.aSwitch.setChecked(false);
            }
            holder.aSwitch.setText(String.valueOf(position));

            holder.aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (aSwitch1.isChecked())
                        editIsActive(aSwitch1.getText().toString(), 1);
                    else {
                        editIsActive(aSwitch1.getText().toString(), -1);
                    }
                }
            });


        } catch (Exception e) {
        }
        return convertView;
    }

    public void editIsActive(String idx, int isActive) {

        AlarmDomain alarmDomain = alarmDomains.get(Integer.valueOf(idx));
        alarmDomain.setIsActive(isActive);
        AlarmManagerService.alarmDomainList = alarmDomains;
        Thread t = new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent(context, AlarmManagerService.class);
                context.startService(intent);
            }
        };
        t.start();

        EditAlarmController editAlarmController = new EditAlarmController();
        editAlarmController.isActive(context, alarmDomain, alarmListAdapter);
    }

    public void onResultIsActive(Object object) {

    }

    static class ViewHolder {

        TextView textView;
        Switch aSwitch;
        TextView Mo;
        TextView Tu;
        TextView We;
        TextView Th;
        TextView Fr;
        TextView Sa;
        TextView Su;
    }

}