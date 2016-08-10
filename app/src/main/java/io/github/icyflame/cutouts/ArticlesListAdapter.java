package io.github.icyflame.cutouts;

/**
 * Created by siddharth on 10/8/16.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by siddharth on 22/7/16.
 */

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesListAdapter.SimpleViewHolder> {
    public static final String TAG = "adapter-main";

    private final Context mContext;
    private JsonArray mItems;

    /**
     * Parent MUST provide this. The function will be called with the JsonObject as argument.
     */
    public interface itemOnClickListener {
        View.OnClickListener setItemOnClickListener(JsonObject item);
    }

    itemOnClickListener mParentCallback;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title, authors;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_title);
            authors = (TextView) view.findViewById(R.id.item_authors);
        }
    }

    public ArticlesListAdapter(Context context, JsonArray items, Activity parent) {
        mContext = context;
        mItems = items;

        try {
            mParentCallback = ((itemOnClickListener) parent);
        } catch (Exception err) {
            Log.e(TAG, "ArticlesListAdapter: " + parent.toString() +
                    " must implement the itemOnClickListener interface", err);
        }
    }

    public ArticlesListAdapter(Context context, JsonArray items, android.support.v4.app.Fragment parent) {
        mContext = context;
        mItems = items;

        try {
            mParentCallback = ((itemOnClickListener) parent);
        } catch (Exception err) {
            Log.e(TAG, "ArticlesListAdapter: " + parent.toString() +
                    " must implement the itemOnClickListener interface", err);
        }
    }

    public void replaceDataset(JsonArray items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.view_preview_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.title.setText(mItems.get(position)
                .getAsJsonObject()
                .get("link")
                .getAsString());
        holder.authors.setText(mItems.get(position)
                .getAsJsonObject()
                .get("author")
                .getAsString());
    }

    /*
    public void addItem(int position) {
        final int id = mCurrentItemId++;
        mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }
    */

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
