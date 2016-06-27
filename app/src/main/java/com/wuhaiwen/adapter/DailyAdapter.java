package com.wuhaiwen.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.util.DateUtil;
import com.util.ImageUtil;
import com.wuhaiwen.bean.Daily_forecast;
import com.wuhaiwen.weather.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wuhaiwen on 2016/6/19.
 */
public class DailyAdapter extends BaseAdapter {
    List<Daily_forecast> data;
    Context context;
    LayoutInflater inflater;

    public DailyAdapter(Context context, List<Daily_forecast> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
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
    public long getItemId(int position) {
        return 0;
    }

    /**
     * @param position    数据位置
     * @param convertView 创建视图
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.daily_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        viewHolder.BindData(data.get(position));
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.image_daily_cond)
        ImageView imageView_cond;
        @Bind(R.id.tv_daily_cond)
        TextView textView_cond;
        @Bind(R.id.tv_daily_temp)
        TextView textView_temp;
        @Bind(R.id.tv_daily)
        TextView textView_daily;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }

        //将数据绑定到控件上
        public void BindData(Daily_forecast daily_forecast) {
            imageView_cond.setImageResource(ImageUtil.Change_Cond_ImageId(daily_forecast.getCond().getCond_day()));
            textView_cond.setText(daily_forecast.getCond().getCond_day());
            textView_temp.setText(daily_forecast.getTmp().getMin() + "°" + "/" + daily_forecast.getTmp().getMax() + "°");
            String local_date = daily_forecast.getDate();
//            textView_daily.setText(DateUtil.FormatDateTime(local_date) +
//                    "(" + DateUtil.getWeek(local_date) + ")");
            textView_daily.setText(DateUtil.getWeek(local_date));
        }

    }
}
