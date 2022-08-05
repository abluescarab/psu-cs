package edu.pdx.cs410J.agilston.phonebill.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.view.Menu;
import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.pdx.cs410J.agilston.phonebill.R;
import edu.pdx.cs410J.agilston.phonebill.databinding.ActivityMainBinding;
import edu.pdx.cs410J.agilston.phonebill.fragments.BillFragment;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, CallActivity.class);
            intent.putExtra(CallActivity.Extras.ACTION, CallActivity.Extras.ACTION_ADD_CALL);
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchButton = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionProvider().getActionView(menu.findItem(R.id.action_search));
        SearchView searchView = (SearchView)searchButton.getActionView();

        searchView.setQueryHint(getText(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // TODO: search customer name
                return false;
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
            BillFragment billFragment = (BillFragment)getSupportFragmentManager().findFragmentByTag(BillFragment.TAG);

            // search by customer name if current fragment shows all customers
            if(billFragment != null && billFragment.isVisible()) {
                // TODO
            }
            // open search window if current fragment shows calls in a bill
            else {
                Intent intent = new Intent(this, CallActivity.class);
                intent.putExtra(CallActivity.Extras.ACTION, CallActivity.Extras.ACTION_SEARCH_CALLS);
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
