package example.com;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Logic of pagination scrolling recyclerview
 */
public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener{

    LinearLayoutManager linearLayoutManager;

    public PaginationScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        // avoid duplicate load
        if (isLoading())
            return;

        int visibleItemCount            = linearLayoutManager.getChildCount();
        int totalItemCount              = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition    = linearLayoutManager.findFirstVisibleItemPosition();

        if (firstVisibleItemPosition >= 0 &&
                (visibleItemCount + firstVisibleItemPosition) >= totalItemCount)
            loadMoreItems();
    }

    public abstract void loadMoreItems();
    public abstract boolean isLoading();
}
