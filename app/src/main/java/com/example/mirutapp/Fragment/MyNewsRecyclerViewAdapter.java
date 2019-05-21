package com.example.mirutapp.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mirutapp.Fragment.NewsFragment.OnListFragmentInteractionListener;
import com.example.mirutapp.Fragment.dummy.DummyContent.DummyItem;
import com.example.mirutapp.Model.Post;
import com.example.mirutapp.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNewsRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    List<Post> listNews;
    //private final OnListFragmentInteractionListener mListener;

    public MyNewsRecyclerViewAdapter(List<Post> news) {
        //mValues = items;
        this.listNews = news;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /*holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);*/
        holder.txtTitle.setText(listNews.get(position).getTitle());
        holder.txtDescription.setText(listNews.get(position).getDescription());
        holder.image.setText(listNews.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       /* public final View mView;
        //public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;*/
       TextView txtTitle, txtDescription, image;

        public ViewHolder(View view) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.idTitle);
            txtDescription = (TextView) view.findViewById(R.id.idDescription);
            image = (TextView) view.findViewById(R.id.idImage);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtTitle.getText() + "'";
        }
    }
}
