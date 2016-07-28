package com.agna.setmaster.ui.screen.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.ui.screen.main.widget.ProfileListItemView;

import java.util.ArrayList;

/**
 *
 */
public class ProfilesAdapter extends RecyclerView.Adapter{

    private static final int ITEM_TYPE_PROFILE = 1;
    private static final int ITEM_TYPE_FOOTER = 2;


    private ArrayList<Profile> profiles = new ArrayList<>();
    private ProfileListItemView.OnProfileActionListener onProfileActionListener;


    public ProfilesAdapter(RecyclerView recyclerView) {
        initLayoutManager(recyclerView);
        recyclerView.setAdapter(this);
    }
    private void initLayoutManager(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public void showData(ArrayList<Profile> profiles) {
        this.profiles = profiles;
        notifyDataSetChanged();
    }

    public void setOnProfileActionListener(ProfileListItemView.OnProfileActionListener onProfileActionListener) {
        this.onProfileActionListener = onProfileActionListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_FOOTER) {
            return FooterHolder.newInstance(parent);
        } else {
            return ProfileHolder.newInstance(parent, onProfileActionListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProfileHolder) {
            Profile profile = profiles.get(position);
            ((ProfileHolder)holder).bind(profile);
        }
    }

    @Override
    public int getItemCount() {
        return profiles.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ITEM_TYPE_FOOTER;
        } else {
            return ITEM_TYPE_PROFILE;
        }
    }

    public static class ProfileHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ProfileListItemView profileListItemView;
        public ProfileHolder(View v,  ProfileListItemView.OnProfileActionListener onProfileActionListener) {
            super(v);
            profileListItemView = (ProfileListItemView) v.findViewById(R.id.profile_list_item_view);
            profileListItemView.setListener(onProfileActionListener);
        }

        public void bind(Profile profile) {
            profileListItemView.show(profile);
        }

        public static ProfileHolder newInstance(ViewGroup parent, ProfileListItemView.OnProfileActionListener onProfileActionListener) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_list_item_layout, parent, false);
            return new ProfileHolder(v, onProfileActionListener);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(View v) {
            super(v);
        }

        public static FooterHolder newInstance(ViewGroup parent) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_footer, parent, false);
            return new FooterHolder(v);
        }
    }
}
