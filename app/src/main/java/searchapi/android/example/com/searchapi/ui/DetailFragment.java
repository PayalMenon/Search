package searchapi.android.example.com.searchapi.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import searchapi.android.example.com.searchapi.R;

public class DetailFragment extends Fragment {

    public static DetailFragment newInstance(String url, String transitionName) {

        Bundle args = new Bundle();
        args.putString("URL", url);
        args.putString("TRANSITION_NAME", transitionName);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postponeEnterTransition();

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).getSupportActionBar().hide();

        String url = getArguments().getString("URL");
        String transitionName = getArguments().getString("TRANSITION_NAME");

        ImageView image = (ImageView) getActivity().findViewById(R.id.image_detail_view);
        image.setTransitionName(transitionName);

        Picasso.with(getActivity()).load(url).placeholder(R.drawable.placeholder)
                .into(image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        startPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        startPostponedEnterTransition();
                    }
                });
    }
}
