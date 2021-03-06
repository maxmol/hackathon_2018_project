package i.maxmol.hackapp;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.expandingcollection.ECCardContentListItemAdapter;

import java.util.List;
import java.util.Random;

public class CardListItemAdapter extends ECCardContentListItemAdapter<String> {

    public CardListItemAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, R.layout.list_element, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.list_element, null);
            rowView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            viewHolder = new ViewHolder();
            viewHolder.itemText = (TextView) rowView.findViewById(R.id.list_item_text);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        final String item = getItem(position);
        if (item != null) {
            viewHolder.itemText.setText(item);
            viewHolder.itemText.setBackgroundColor(Color.parseColor("#00000000"));
        }


        Typeface typeface = ResourcesCompat.getFont(Countries.context, R.font.raleway_semibold);
        viewHolder.itemText.setBackgroundColor(Color.parseColor("#00000000"));
        viewHolder.itemText.setTypeface(typeface);
        // Example of changing/removing card list items
        /*viewHolder.itemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tapToRemoveText = "Tap again to remove!";
                TextView view = (TextView) v;
                if (view.getText().equals(tapToRemoveText)) {
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorPrimary));
                    CardListItemAdapter.this.remove(item);
                    CardListItemAdapter.this.notifyDataSetChanged();
                } else {
                    view.setText(tapToRemoveText);
                    v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorAccent));
                }
            }
        });*/
        rowView.setBackgroundColor(Color.parseColor("#00000000"));
        return rowView;
    }

    static class ViewHolder {
        TextView itemText;
    }

}