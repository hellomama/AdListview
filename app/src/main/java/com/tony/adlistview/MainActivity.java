package com.tony.adlistview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.listView);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        AdAdapter adAdapter = new AdAdapter(this);
        recyclerView.setAdapter(adAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = manager.findFirstVisibleItemPosition();
                int lastPosition = manager.findLastVisibleItemPosition();
                for (int i = firstPosition; i< lastPosition+1 ; i++)
                {
                    View view = manager.findViewByPosition(i);
                    if (manager.getItemViewType(view)==1)
                    {
                        RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                        if (holder instanceof AdAdapter.BHolder)
                        {
                            AdAdapter.BHolder bHolder = (AdAdapter.BHolder)holder;
                            bHolder.imageView.setDx(view.getTop());
                        }
                    }
                }
            }
        });
    }

    private static class AdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {

        LayoutInflater inflater;
        public AdAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View view = inflater.inflate(R.layout.base_item,parent,false);
                return new AHolder(view);
            }
            View view = inflater.inflate(R.layout.ad_item,parent,false);
            return new BHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof AHolder)
            {
                AHolder aHolder = (AHolder)holder;
                aHolder.textView.setText("position:"+position);
            }else if (holder instanceof BHolder)
            {
                BHolder bHolder = (BHolder)holder;
                Glide.with(bHolder.imageView.getContext()).
                        load("http://www.fzlu.net/uploads/allimg/150918/3-15091Q00R2a2.jpg")
                        .into(bHolder.imageView);
            }
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 8)
            {
                return 1;
            }
            return 0;
        }

        class AHolder extends RecyclerView.ViewHolder
        {
            TextView textView;
            public AHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text);
            }
        }

        class BHolder extends RecyclerView.ViewHolder
        {
            ScrollImageView imageView;
            public BHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.image);
            }
        }
    }
}
