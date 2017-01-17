package eu.mobilebear.carcompany;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.mobilebear.carcompany.mvp.model.Manufacturer;
import eu.mobilebear.carcompany.injection.Injector;
import eu.mobilebear.carcompany.rest.RestClient;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

	@Inject
	RestClient restClient;

	@Inject
	FragmentManager fragmentManager;

	@BindView(R.id.manufacturersTextView)
	TextView textView;

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.fab)
	FloatingActionButton fab;

	private Unbinder unbinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Injector.inject(this);
		setContentView(R.layout.activity_main);
		unbinder = ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show());

		Observable<Manufacturer> manufacturer = restClient.getCarService()
				.getManufacturers(restClient.getToken(), 0, 10);


		Subscription subscription = manufacturer
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doOnNext(manufacturer1 -> {
					textView.setText(manufacturer1.toString());
					getManufacturers(manufacturer1);
				})
				.doOnError(throwable -> Timber.e("Response: " + throwable.getMessage()))
				.doOnCompleted(() -> Timber.d("siema"))
				.subscribe();

	}

	private Map<Integer, String> manufacturers;

	private void getManufacturers(Manufacturer manufacturer) {
		manufacturers = manufacturer.getManufacturers();
		for(String value : manufacturers.values()){
			Timber.d("Manufacturer: " + value);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
