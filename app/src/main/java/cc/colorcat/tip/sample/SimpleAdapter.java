package cc.colorcat.tip.sample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Author: cxx
 * Date: 2018-07-05
 * GitHub: https://github.com/ccolorcat
 */
public final class SimpleAdapter extends BaseAdapter {
    private List<String> mItems;

    SimpleAdapter(List<String> data) {
        mItems = data;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new Holder();
            holder.textView = view.findViewById(android.R.id.text1);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.textView.setText(getItem(position));
        return view;
    }

    private static class Holder {
        private TextView textView;
    }
}
