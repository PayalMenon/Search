package searchapi.android.example.com.searchapi.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import searchapi.android.example.com.searchapi.Application;
import searchapi.android.example.com.searchapi.R;
import searchapi.android.example.com.searchapi.pojo.SearchInfo;
import searchapi.android.example.com.searchapi.pojo.SearchItemInfo;
import searchapi.android.example.com.searchapi.service.SearchService;

public class ListFragment extends Fragment implements MainActivity.FragmentListener {

    @Inject
    SearchService service;

    private RecyclerView imageList;
    private ListAdapter adapter;
    private List<SearchItemInfo> searchList = new ArrayList<>();
    private GridLayoutManager layoutManager;
    private String query;
    private ImageView detailsImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((Application) getActivity().getApplication()).getNetworkComponent().inject(this);

        imageList = (RecyclerView) getActivity().findViewById(R.id.image_list_view);

        adapter = new ListAdapter(getActivity(), searchList, this);
        imageList.setAdapter(adapter);

        layoutManager = new GridLayoutManager(getActivity(), 2);
        imageList.setLayoutManager(layoutManager);

        imageList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (currentPosition == (searchList.size() - 1)) {
                    getImageSearchList(searchList.size() + 1);
                }
            }
        });
        ((MainActivity)getActivity()).getSupportActionBar().show();
        ((MainActivity) getActivity()).setFragmentListener(this);
    }

    public void getImageSearchList(int startIndex) {

        Call<SearchInfo> call = service.getSearchImages(query, query, Integer.toString(startIndex));
        call.enqueue(new Callback<SearchInfo>() {

            @Override
            public void onResponse(Call<SearchInfo> call, Response<SearchInfo> response) {

                SearchInfo searchInfo = response.body();
                if (response.isSuccessful() && null != searchInfo) {

                    searchList.addAll(searchInfo.getItems());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.fetch_failed), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SearchInfo> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.fetch_failed), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void updateFragmentList(String query) {

        searchList.clear();
        adapter.notifyDataSetChanged();
        this.query = query;
        getImageSearchList(1);
    }

    @Override
    public void onItemClicked(int position, ImageView imageView) {
        SearchItemInfo info = searchList.get(position);
        String transitionName = ViewCompat.getTransitionName(imageView);

        Fragment detailsFragment = DetailFragment.newInstance(info.getLink(), transitionName);
        getFragmentManager().beginTransaction()
                .addSharedElement(imageView, transitionName)
                .addToBackStack("list_fragment")
                .replace(R.id.fragment_container, detailsFragment)
                .commit();
    }
}
