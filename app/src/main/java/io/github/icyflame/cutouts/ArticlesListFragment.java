package io.github.icyflame.cutouts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ArticlesListFragment extends Fragment implements ArticlesListAdapter.itemOnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private JsonArray mArticlesList;
    private RecyclerView mRecyclerView;
    private ArticlesListAdapter mAdapter;

    public ArticlesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 JsonArray consisting all the articles
     * @return A new instance of fragment ArticlesListFragment.
     */
    public static ArticlesListFragment newInstance(JsonArray param1) {
        ArticlesListFragment fragment = new ArticlesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticlesList = new Gson().fromJson(getArguments().getString(ARG_PARAM1), JsonArray.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View main = inflater.inflate(R.layout.fragment_articles_list, container, false);

        mRecyclerView = ((RecyclerView) main.findViewById(R.id.list_articles_main_rv));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new ArticlesListAdapter(getActivity(), mArticlesList, ArticlesListFragment.this);

        mRecyclerView.setAdapter(mAdapter);

        return main;
    }

    @Override
    public View.OnClickListener setItemOnClickListener(final JsonObject item) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "You clicked on the object with ID: " + item.get("id").getAsInt(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
