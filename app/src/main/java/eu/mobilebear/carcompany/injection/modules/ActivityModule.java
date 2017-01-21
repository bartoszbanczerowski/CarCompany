package eu.mobilebear.carcompany.injection.modules;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import dagger.Module;
import dagger.Provides;
import eu.mobilebear.carcompany.injection.annotations.ActivityContext;
import eu.mobilebear.carcompany.injection.annotations.PerActivity;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
@Module
public class ActivityModule {

  private final Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides
  @PerActivity
  @ActivityContext
  Context context() {
    return activity;
  }

  @Provides
  @PerActivity
  FragmentManager provideSupportFragmentManager() {
    return ((FragmentActivity) activity).getSupportFragmentManager();
  }
}
