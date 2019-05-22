package com.example.mirutapp.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mirutapp.Fragment.NewsFragment.OnListFragmentInteractionListener;
import com.example.mirutapp.Model.Post;
import com.example.mirutapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNewsRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsRecyclerViewAdapter.ViewHolder> {

    //private final List<DummyItem> mValues;
    private List<Post> listNews = new ArrayList<>();
    //private final OnListFragmentInteractionListener mListener;
    int mExpandedPosition = -1;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Post currentNews = listNews.get(position);
        holder.txtTitle.setText(currentNews.getTitle());
        holder.txtDescription.setText(currentNews.getDescription());
        String urlImage = listNews.get(position).getImage();
        // Si no hay imagen (url) se carga una por defecto
        if (urlImage.isEmpty()) { //url.isEmpty()
            Picasso.get()
                    .load(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(holder.image);

        }else{
            Picasso.get()
                    .load(urlImage)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(holder.image);
        }

        // Esto es para que al hacer click se colapse o se expanda la descripcion
        final boolean isExpanded = position==mExpandedPosition;
        holder.txtDescription.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                //TransitionManager.beginDelayedTransition();
                notifyDataSetChanged();
            }
        });

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
       TextView txtTitle, txtDescription;
       ImageView image;

        public ViewHolder(View view) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.idTitle);
            txtDescription = (TextView) view.findViewById(R.id.idDescription);
            image = (ImageView) view.findViewById(R.id.idImage);
        }
    }
}
