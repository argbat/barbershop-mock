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

public class ShopServicesActivity extends AppCompatActivity {
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
            shortDescription = "Available service at " + selectedName + ".";
        }
    }

    private void initLocations() {
        ShopService item;

        ArrayList<ShopService> items = new ArrayList<>();

        item = new ShopService(1, "Service 1 - 30 minutes", shortDescription);
        items.add(item);

        item = new ShopService(2, "Service 2 - 40 minutes", shortDescription);
        items.add(item);

        item = new ShopService(3, "Service 3 - 60 minutes", shortDescription);
        items.add(item);

        ShopServicesAdapter adapter = new ShopServicesAdapter(this);

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

    private class ShopServicesAdapter extends RecyclerView.Adapter {
        Context ctx;

        private class ViewHolder extends RecyclerView.ViewHolder {
            long id;
            TextView name;
            TextView shortDescription;

            private ViewHolder(View itemView) {
                super(itemView);
                this.name = itemView.findViewById(R.id.shopservices_item_name);
                this.shortDescription = itemView.findViewById(R.id.shopservices_item_short_desciption);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("selectedId", getItemId());
                        intent.putExtra("selectedName", name.getText());
                        intent.setClass(ShopServicesActivity.this, ShopShiftsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

        private final ArrayList<ShopService> data;

        private ShopServicesAdapter(Context ctx) {
            this.ctx = ctx;
            this.data = new ArrayList<>(0);
        }

        public void updateData(Collection<ShopService> newData) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.shopservice_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ShopService datum = data.get(position);
            ViewHolder myHolder = (ViewHolder)holder;
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
            ShopService item = data.get(position);
            return item.id;
        }
    }

    private static class ShopService {
        long id;
        String name;
        String shortDescription;

        ShopService(long id, String name, String shortDescription) {
            this.id = id;
            this.name = name;
            this.shortDescription = shortDescription;
        }
    }
}
