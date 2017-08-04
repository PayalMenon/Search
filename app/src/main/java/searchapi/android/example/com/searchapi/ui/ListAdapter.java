package searchapi.android.example.com.searchapi.ui;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import searchapi.android.example.com.searchapi.R;
import searchapi.android.example.com.searchapi.pojo.SearchItemInfo;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {

    private Context context;
    private List<SearchItemInfo> searchList;
    private MainActivity.FragmentListener listener;

    public ListAdapter(Context context, List<SearchItemInfo> searchList, MainActivity.FragmentListener listener) {

        this.context = context;
        this.searchList = searchList;
        this.listener = listener;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View frameView = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ListAdapter.ListHolder(frameView);
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, final int position) {

        SearchItemInfo imageObject = this.searchList.get(position);

        if (imageObject != null) {

            Picasso.with(context).load(imageObject.getImage().getThumbnailLink())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageView);

        }

        ViewCompat.setTransitionName(holder.imageView, imageObject.getImage().getThumbnailLink());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(position, holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {

        return this.searchList.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ListHolder(final View itemView) {

            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.itemImage);
        }
    }
}
