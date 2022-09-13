package edu.pdx.cs410J.agilston.phonebill.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.pdx.cs410J.agilston.phonebill.FileUtils;
import edu.pdx.cs410J.agilston.phonebill.PhoneBillList;
import edu.pdx.cs410J.agilston.phonebill.R;
import edu.pdx.cs410J.agilston.phonebill.adapters.CustomerAdapter;
import edu.pdx.cs410J.agilston.phonebill.databinding.ActivityMainBinding;
import edu.pdx.cs410J.agilston.phonebill.fragments.CallFragment;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private SearchView searchView;
    private MenuItem searchButton;
    private MenuItem filterButton;

    /**
     * Flips the view of a specified {@link ViewFlipper} depending on the contents of a {@link RecyclerView} adapter.
     *
     * @param flipper      flipper to flip
     * @param recyclerView RecyclerView to set adapter for
     * @param count        number of elements in the adapter
     * @param adapter      adapter to set to
     */
    public static void flipView(ViewFlipper flipper, RecyclerView recyclerView, int count,
                                RecyclerView.Adapter<?> adapter) {
        if(flipper == null) {
            return;
        }

        if(count == 0 || (count > 0 && flipper.getDisplayedChild() == 1)) {
            flipper.showNext();
        }

        recyclerView.setAdapter(adapter);
    }

    /**
     * Adds a dividing line between {@link RecyclerView} elements.
     *
     * @param recyclerView RecyclerView to add dividers to
     */
    public static void addItemDecoration(RecyclerView recyclerView) {
        // add item divider decoration
        Context context = recyclerView.getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        // set up nav controller for fragment navigation
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // change fab icon based on fragment
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            binding.fab.setImageResource(destination.getId() == R.id.CustomerFragment ? R.drawable.ic_add_person :
                    R.drawable.ic_add_call);

            if(searchButton != null) {
                searchButton.setVisible(destination.getId() == R.id.CustomerFragment);
            }

            if(filterButton != null) {
                filterButton.setVisible(destination.getId() == R.id.CallFragment);
            }
        });

        PhoneBillList.addBills(this, FileUtils.loadAll(this), false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchButton = menu.findItem(R.id.action_search);
        filterButton = menu.findItem(R.id.action_filter);
        searchView = (SearchView)searchButton.getActionView();

        searchView.setQueryHint(getText(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                CustomerAdapter adapter = PhoneBillList.getCustomerAdapter();

                if(adapter != null) {
                    adapter.getFilter().filter(s);
                }

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_readme) {
            Intent intent = new Intent(this, ReadmeActivity.class);
            startActivity(intent);
            return true;
        }
        else if(item.getItemId() == R.id.action_filter) {
            Fragment fragment = getFragment(CallFragment.class);

            if(fragment != null) {
                ((CallFragment)fragment).launchCallActivity(CallActivity.Extras.ACTION_SEARCH_CALLS);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets a fragment by type.
     *
     * @param clazz fragment type
     * @param <T>   fragment type
     */
    private <T extends Fragment> Fragment getFragment(Class<T> clazz) {
        Fragment nav = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        if(nav != null) {
            for(Fragment fragment : nav.getChildFragmentManager().getFragments()) {
                if(fragment.getClass() == clazz) {
                    return fragment;
                }
            }
        }

        return null;
    }

    /**
     * Resets the search view.
     */
    public void resetSearch() {
        if(searchView != null) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
        }
    }
}
