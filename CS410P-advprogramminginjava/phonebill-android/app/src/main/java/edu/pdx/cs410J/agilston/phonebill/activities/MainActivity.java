package edu.pdx.cs410J.agilston.phonebill.activities;

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
import androidx.recyclerview.widget.RecyclerView;

import edu.pdx.cs410J.agilston.phonebill.FileUtils;
import edu.pdx.cs410J.agilston.phonebill.PhoneBillList;
import edu.pdx.cs410J.agilston.phonebill.R;
import edu.pdx.cs410J.agilston.phonebill.adapters.CustomerAdapter;
import edu.pdx.cs410J.agilston.phonebill.databinding.ActivityMainBinding;
import edu.pdx.cs410J.agilston.phonebill.fragments.CallFragment;

public class MainActivity extends AppCompatActivity implements CustomerAdapter.OnItemClickListener {
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private SearchView searchView;
    private String currentCustomer;

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
            if(destination.getId() == R.id.CustomerFragment) {
                binding.fab.setImageResource(R.drawable.ic_add_person);
            }
            else {
                binding.fab.setImageResource(R.drawable.ic_add_call);
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

        MenuItem searchButton = menu.findItem(R.id.action_search);
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
        else if(item.getItemId() == R.id.action_search) {
            if(hasFragment(CallFragment.class)) {
                // open search window if current fragment shows calls in a bill
                Intent intent = new Intent(this, CallActivity.class);
                intent.putExtra(CallActivity.Extras.ACTION, CallActivity.Extras.ACTION_SEARCH_CALLS);
                // TODO: pass customer name to search activity
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private <T extends Fragment> boolean showingFragment(Class<T> clazz) {
        Fragment nav = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        if(nav != null) {
            Fragment fragment = nav.getChildFragmentManager().getPrimaryNavigationFragment();
            return fragment != null && fragment.getClass() == clazz;
        }

        return false;
    }

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

    public void flipCustomerView() {
        flipView(findViewById(R.id.customer_flipper), findViewById(R.id.customer_list),
                PhoneBillList.getCustomerCount(), PhoneBillList.getCustomerAdapter());
    }

    public void flipCallView(String customer) {
        flipView(findViewById(R.id.call_flipper), findViewById(R.id.call_list), PhoneBillList.getCallCount(customer),
                PhoneBillList.getCallAdapter());
    }

    public void resetSearch() {
        if(searchView != null) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
        }
    }

    private void flipView(ViewFlipper flipper, RecyclerView recyclerView, int count, RecyclerView.Adapter adapter) {
        if(flipper == null) {
            return;
        }

        if(count == 0 || (count == 1 && flipper.getDisplayedChild() == 1)) {
            flipper.showNext();
        }

        if(count > 0) {
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(String item) {
        currentCustomer = item;
    }
}
