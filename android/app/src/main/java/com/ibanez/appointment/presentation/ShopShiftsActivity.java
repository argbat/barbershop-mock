package com.ibanez.appointment.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ibanez.appointment.R;

import java.util.ArrayList;
import java.util.Collection;

public class ShopShiftsActivity extends AppCompatActivity {
    private String shortDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopservices);
        initInputBundle();
        initToolbar();
        initLocations();
    }

    private void initInputBundle() {
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            String selectedName = b.getString("selectedName");
            shortDescription = "Available shift for " + selectedName + ".";
        }
    }

    private void initLocations() {
        ShopShift item;

        ArrayList<ShopShift> items = new ArrayList<>();

        item = new ShopShift(1, "Monday 1 September 10:00", shortDescription);
        items.add(item);

        item = new ShopShift(2, "Monday 1 September 10:30", shortDescription);
        items.add(item);

        item = new ShopShift(3, "Monday 1 September 11:00", shortDescription);
        items.add(item);

        item = new ShopShift(4, "Monday 1 September 14:00", shortDescription);
        items.add(item);

        item = new ShopShift(5, "Tuesday 2 September 10:30", shortDescription);
        items.add(item);

        item = new ShopShift(6, "Tuesday 2 September 15:40", shortDescription);
        items.add(item);

        ShopTasksAdapter adapter = new ShopTasksAdapter(this);

        RecyclerView itemsView = findViewById(R.id.shopservices_items);
        itemsView.setAdapter(adapter);
        itemsView.setHasFixedSize(true);
        itemsView.setLayoutManager(new LinearLayoutManager(this));

        adapter.updateData(items);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.shopservices_toolbar);
        FrameLayout toolBarContent = toolbar.findViewById(R.id.shopservices_toolbar_content);
        toolBarContent.addView(getLayoutInflater().inflate(R.layout.shopservices_toolbar_items, null));

        ImageButton btn = toolBarContent.findViewById(R.id.shopservices_toolbar_items_back_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private class ShopTasksAdapter extends RecyclerView.Adapter {
        Context ctx;

        private class ViewHolder extends RecyclerView.ViewHolder {
            long id;
            TextView name;
            TextView shortDescription;

            private ViewHolder(View itemView) {
                super(itemView);
                this.name = itemView.findViewById(R.id.shopshift_item_name);
                this.shortDescription = itemView.findViewById(R.id.shopshift_item_short_desciption);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }

        private final ArrayList<ShopShift> data;

        private ShopTasksAdapter(Context ctx) {
            this.ctx = ctx;
            this.data = new ArrayList<>(0);
        }

        public void updateData(Collection<ShopShift> newData) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.shopshift_item, parent, false);
            return new ShopTasksAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ShopShift datum = data.get(position);
            ShopTasksAdapter.ViewHolder myHolder = (ShopTasksAdapter.ViewHolder)holder;
            myHolder.name.setText(datum.name);
            myHolder.shortDescription.setText(datum.shortDescription);
            myHolder.id = datum.id;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public long getItemId(int position) {
            ShopShift item = data.get(position);
            return item.id;
        }
    }

    private static class ShopShift {
        long id;
        String name;
        String shortDescription;

        ShopShift(long id, String name, String shortDescription) {
            this.id = id;
            this.name = name;
            this.shortDescription = shortDescription;
        }
    }
}
