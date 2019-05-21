package com.example.mirutapp.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mirutapp.MiRutAppApplication;
import com.example.mirutapp.Model.Post;
import com.example.mirutapp.R;
import com.example.mirutapp.ViewModel.PostViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NewsFragment extends Fragment {
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private PostViewModel viewModel;
    RecyclerView recyclerNews;
    ArrayList<Post> listNews;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public NewsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NewsFragment newInstance(int columnCount) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        //dependency injection
        ((MiRutAppApplication) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        listNews = new ArrayList<>();
        recyclerNews = (RecyclerView) view.findViewById(R.id.list);
        recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));

        //OBTENER DATOS JSON
        /*super.onActivityCreated(savedInstanceState);
        //testing
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PostViewModel.class);
        viewModel.init();

        viewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                for(Post post: posts) {
                    //test: showing news titles
                    listNews.add(post);
                    //System.out.println(post.getTitle());
                }
            }
        });*/
        llenarLista();

        MyNewsRecyclerViewAdapter adapter = new MyNewsRecyclerViewAdapter(listNews);
        recyclerNews.setAdapter(adapter);
        //System.out.println(listNews); // ESTÁ VACÍA | SOLUCIONAR
        //System.out.println("ALACANCE A LLEGAR AQUI");
        return view;
    }

    private void llenarLista() {
        listNews.add(new Post("Concierto Masivo Trap","Hay un concierto masivo de trap, se hará en el estadio Nacional,habrán muchos invitados","Imagen 1"));
        listNews.add(new Post("Tocata Punk","Hay un concierto masivo de rock, se hará en el estadio Nacional,habrán muchos invitados","Imagen 1"));
        listNews.add(new Post("Suicidio masivo en Metro","Hay un suicidio masivo, se hará en Metro de Santiago,habrán muchos invitados","Imagen 1"));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Uri uri);

    }
}
