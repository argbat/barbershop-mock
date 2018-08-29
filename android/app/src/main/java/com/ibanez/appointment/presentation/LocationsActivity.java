package com.ibanez.appointment.presentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ibanez.appointment.R;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationsActivity extends AppCompatActivity {
    private ArrayList<BarbershopLocation> locations = new ArrayList<>();
    private ArrayList<BarbershopLocation> filteredLocations = new ArrayList<>();
    private LocationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);
        initToolbar();
        initLocations();
        initSearchViews();
    }

    private void initLocations() {
        BarbershopLocation location;

        location = new BarbershopLocation(1, "Barber Shop 1", "North Street 101 - <1 mile", "5000CA");
        locations.add(location);

        location = new BarbershopLocation(2, "Barber Shop 2", "South Street 107 - <1 mile", "5000CA");
        locations.add(location);

        location = new BarbershopLocation(3, "Barber Shop 3", "Franklin Av 2007 - 2 miles", "5001CA");
        locations.add(location);

        location = new BarbershopLocation(4, "Barber Shop 4", "Roosevelt Street 7 - n/a", "5001CA");
        locations.add(location);

        adapter = new LocationsAdapter(this);

        RecyclerView locationsView = findViewById(R.id.locations_items);
        locationsView.setAdapter(adapter);
        locationsView.setHasFixedSize(true);
        locationsView.setLayoutManager(new LinearLayoutManager(this));

        adapter.updateData(locations);
    }

    private void initSearchViews() {
        EditText editText = findViewById(R.id.locations_search_input);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideVirtualKeyboard(LocationsActivity.this, view);
                    filterLocations(view);
                    return true;
                }

                return false;
            }
        });

        View searchActionIcon = findViewById(R.id.locations_search_action_icon);
        searchActionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = view.getRootView().findViewById(R.id.locations_search_input);
                hideVirtualKeyboard(LocationsActivity.this, editText);
                filterLocations(editText);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.locations_toolbar);
        FrameLayout toolBarContent = toolbar.findViewById(R.id.locations_toolbar_content);
        toolBarContent.addView(getLayoutInflater().inflate(R.layout.locations_toolbar_items, null));

        ImageButton btn = toolBarContent.findViewById(R.id.locations_toolbar_items_back_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void filterLocations(TextView editText) {
        filteredLocations.clear();

        String value = editText.getText().toString().toLowerCase();
        if (value.isEmpty()) {
            adapter.updateData(locations);
        } else {
            for (BarbershopLocation location : locations) {
                if (!filteredLocations.contains(location)) {
                    if (location.name.toLowerCase().contains(value)
                            || location.address.toLowerCase().contains(value)
                            || location.zipCode.toLowerCase().contains(value)) {
                        filteredLocations.add(location);
                    }
                }
            }
            adapter.updateData(filteredLocations);
        }
    }

    private static void hideVirtualKeyboard(Context context, View focusedView) {
        InputMethodManager imm = (InputMethodManager)context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    private class LocationsAdapter extends RecyclerView.Adapter {
        Context ctx;

        private class ViewHolder extends RecyclerView.ViewHolder {
            long id;
            TextView name;
            TextView address;

            private ViewHolder(View itemView) {
                super(itemView);
                this.name = itemView.findViewById(R.id.location_item_name);
                this.address = itemView.findViewById(R.id.location_item_address);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("selectedId", getItemId());
                        intent.putExtra("selectedName", name.getText());
                        intent.setClass(LocationsActivity.this, ShopServicesActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

        private final ArrayList<BarbershopLocation> data;

        private LocationsAdapter(Context ctx) {
            this.ctx = ctx;
            this.data = new ArrayList<>(0);
        }

        public void updateData(Collection<BarbershopLocation> newData) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.location_item, parent, false);
            return new LocationsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            BarbershopLocation datum = data.get(position);
            LocationsAdapter.ViewHolder myHolder = (LocationsAdapter.ViewHolder)holder;
            myHolder.id = datum.id;
            myHolder.name.setText(datum.name);
            myHolder.address.setText(datum.address);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public long getItemId(int position) {
            BarbershopLocation item = data.get(position);
            return item.id;
        }
    }

    private static class BarbershopLocation {
        long id;
        String name;
        String address;
        String zipCode;

        BarbershopLocation(long id, String name, String address, String zipCode) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.zipCode = zipCode;
        }
    }
}

