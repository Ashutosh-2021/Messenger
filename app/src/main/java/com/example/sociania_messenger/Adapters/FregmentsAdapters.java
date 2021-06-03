package com.example.sociania_messenger.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sociania_messenger.Fregments.Chats;
import com.example.sociania_messenger.Fregments.Status;

public class FregmentsAdapters extends FragmentPagerAdapter {
    public FregmentsAdapters(@NonNull FragmentManager fm) {
        super(fm);
    }

    // Get the Option
    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Call the Fragment
        switch (position) {
            case 0:
                return new Chats();
            case 1:
                return new Status();
            default:
                return new Chats();
        }

    }

    // Count the Option
    @Override
    public int getCount() {
        return 2;
    }

    // Give the Page Title

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = null;
        // Give the Fragment title
        if (position == 0) {
            title = "Chats";
        }
        if (position == 1) {
            title = "Status";
        }
        return title;
    }
}
