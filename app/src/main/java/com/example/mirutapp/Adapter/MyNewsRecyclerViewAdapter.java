package com.example.mirutapp.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mirutapp.Fragment.NewsFragment.OnListFragmentInteractionListener;
import com.example.mirutapp.Fragment.dummy.DummyContent.DummyItem;
import com.example.mirutapp.Model.Post;
import com.example.mirutapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNewsRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    private List<Post> listNews = new ArrayList<>();
    //private final OnListFragmentInteractionListener mListener;

    public MyNewsRecyclerViewAdapter(List<Post> news) {
        //mValues = items;
        this.listNews = news;
    }

    public MyNewsRecyclerViewAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Post currentNews = listNews.get(position);
        holder.txtTitle.setText(currentNews.getTitle());
        holder.txtDescription.setText(currentNews.getDescription());
        holder.image.setText(currentNews.getImage());
    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public void setList(List<Post> news) {
        this.listNews=news;
        notifyDataSetChanged();
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

        /*@Override
        public String toString() {
            return super.toString() + " '" + txtTitle.getText() + "'";
        }*/
    }
}
