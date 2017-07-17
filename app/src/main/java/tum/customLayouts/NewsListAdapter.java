package tum.customLayouts;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tum.hitchmeup.R;

/**
 * Created by Philipp on 7/16/2017.
 */

public class NewsListAdapter extends ArrayAdapter<String>{

    private Context context = null;
    private List<String> values = null;// can be changed to arbitrary Objects so everthing can be stored

    public NewsListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);

        values = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.news_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.newsIcon);
        imageView.setImageResource(R.drawable.lightbulb_96);
        textView.setText("New Match found");
        textView2.setText(values.get(position));
        // change the icon for Windows and iPhone

        return rowView;
    }

    @Override
    public void add(@Nullable String object) {
        super.add(object);

    }
}
