package eu.mobilebear.carcompany;

import static eu.mobilebear.carcompany.utils.FragmentUtils.*;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.mobilebear.carcompany.fragments.MainTypeFragment;
import eu.mobilebear.carcompany.fragments.ManufacturersFragment;
import eu.mobilebear.carcompany.injection.Injector;
import eu.mobilebear.carcompany.injection.components.CarComponent;
import eu.mobilebear.carcompany.injection.components.DaggerCarComponent;
import eu.mobilebear.carcompany.injection.modules.ActivityModule;
import eu.mobilebear.carcompany.utils.FragmentUtils.TagFragment;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

  @Inject
  FragmentManager fragmentManager;

  @BindView(R.id.container)
  FrameLayout container;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  private Unbinder unbinder;
  private CarComponent carComponent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initializeCarComponent(this);
    carComponent.inject(this);
    unbinder = ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    fragmentManager
        .beginTransaction().add(R.id.container, ManufacturersFragment.newInstance(),
        BUILT_DATES_FRAGMENT)
        .commit();

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  private void initializeCarComponent(Activity activity) {
    carComponent = DaggerCarComponent.builder()
        .applicationComponent(Injector.getApplicationComponent())
        .activityModule(new ActivityModule(activity))
        .build();
  }

  public CarComponent getCarComponent() {
    return carComponent;
  }

  public void replaceFragment(@TagFragment String tag, String manufacturerId) {
    Fragment fragment = fragmentManager.findFragmentByTag(tag);
    switch (tag) {
      case MANUFACTURER_FRAGMENT:
        fragment = ManufacturersFragment.newInstance();
        break;
      case MAIN_TYPES_FRAGMENT:
        fragment = MainTypeFragment.newInstance(manufacturerId);
        break;
      case BUILT_DATES_FRAGMENT:
        break;
    }
    if (fragment != null) {
      fragmentManager.beginTransaction()
          .replace(R.id.container, fragment)
          .addToBackStack(tag)
          .commit();
    }
  }
}
