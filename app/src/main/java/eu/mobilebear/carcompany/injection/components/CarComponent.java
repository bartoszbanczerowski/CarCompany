package eu.mobilebear.carcompany.injection.components;

import android.content.Context;
import dagger.Component;
import eu.mobilebear.carcompany.MainActivity;
import eu.mobilebear.carcompany.fragments.MainTypeFragment;
import eu.mobilebear.carcompany.fragments.ManufacturersFragment;
import eu.mobilebear.carcompany.injection.annotations.ActivityContext;
import eu.mobilebear.carcompany.injection.annotations.PerActivity;
import eu.mobilebear.carcompany.injection.modules.ActivityModule;
import eu.mobilebear.carcompany.injection.modules.CarModule;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,
    modules = {ActivityModule.class, CarModule.class})
public interface CarComponent {

  void inject(MainActivity activity);

  void inject(ManufacturersFragment fragment);

  void inject(MainTypeFragment fragment);

  @ActivityContext
  Context getActivityContext();
}
