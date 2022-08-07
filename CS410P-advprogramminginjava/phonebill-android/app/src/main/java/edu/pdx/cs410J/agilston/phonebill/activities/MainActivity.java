package edu.pdx.cs410J.agilston.phonebill.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import edu.pdx.cs410J.agilston.phonebill.PhoneBill;
import edu.pdx.cs410J.agilston.phonebill.PhoneBillList;
import edu.pdx.cs410J.agilston.phonebill.PhoneCall;
import edu.pdx.cs410J.agilston.phonebill.R;
import edu.pdx.cs410J.agilston.phonebill.adapters.CustomerAdapter;
import edu.pdx.cs410J.agilston.phonebill.databinding.ActivityMainBinding;
import edu.pdx.cs410J.agilston.phonebill.fragments.CustomerFragment;
import edu.pdx.cs410J.agilston.phonebill.fragments.CallFragment;

public class MainActivity extends AppCompatActivity {
    private CustomerAdapter customerAdapter;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private CustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        loadBills();

        // set up navcontroller for fragment navigation
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // set adapter for customer list
        RecyclerView recyclerView = findViewById(R.id.customer_list);
        CustomerAdapter adapter = new CustomerAdapter(PhoneBillList.getCustomers(), item -> {
            // TODO: open call view after clicking on customer
            Bundle bundle = new Bundle();
            bundle.putString(CallFragment.Extras.ITEM_NAME, item);
            navController.navigate(R.id.action_bills_to_calls, bundle);
        });
        recyclerView.setAdapter(adapter);

        // bind floating action button
        binding.fab.setOnClickListener(view -> {
            if(hasFragment(CustomerFragment.class)) {
                AlertDialog.Builder dialog = createDialog(R.layout.dialog_add_customer, R.string.title_add_customer,
                        R.string.hint_customer_name);

                dialog.setPositiveButton("Add", (dialogInterface, i) -> {
                    // TODO: add customer
                    dialogInterface.dismiss();
                });

                dialog.show();
            }
            else {
                Intent intent = new Intent(this, CallActivity.class);
                intent.putExtra(CallActivity.Extras.ACTION, CallActivity.Extras.ACTION_ADD_CALL);
                startActivity(intent);
            }
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
        SearchView searchView = (SearchView)searchButton.getActionView();

        searchView.setQueryHint(getText(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
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
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private AlertDialog.Builder createDialog(@LayoutRes int layoutId, @StringRes int titleId, @StringRes int hintId) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle(titleId);
        View dialogView = LayoutInflater.from(dialog.getContext()).inflate(layoutId,
                findViewById(android.R.id.content), false);
        EditText editText = dialogView.findViewById(R.id.dialog_input);

        editText.setHint(hintId);
        dialog.setView(dialogView);
        dialog.setNegativeButton("Cancel", ((dialogInterface, i) -> dialogInterface.cancel()));

        return dialog;
    }

    private <T extends Fragment> boolean hasFragment(Class<T> clazz) {
        Fragment nav = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);

        if(nav != null) {
            Fragment fragment = nav.getChildFragmentManager().getPrimaryNavigationFragment();
            return fragment != null && fragment.getClass() == clazz;
        }

        return false;
    }

    private void loadBills() {
        // TODO: replace test bills
        String numberFormat = "%1$s%1$s%1$s-%1$s%1$s%1$s-%1$s%1$s%1$s%1$s";
        String dateFormat = "01/01/2022 0%1$s:00 PM";

        for(int i = 1; i <= 5; i++) {
            PhoneBill bill = new PhoneBill("Customer " + i);

            for(int j = 1; j <= 5; j++) {
                PhoneCall call = new PhoneCall(String.format(numberFormat, i), String.format(numberFormat, i + 1),
                        String.format(dateFormat, j), String.format(dateFormat, j + 1));
                bill.addPhoneCall(call);
            }

            PhoneBillList.addBill(bill);
        }
    }
}
