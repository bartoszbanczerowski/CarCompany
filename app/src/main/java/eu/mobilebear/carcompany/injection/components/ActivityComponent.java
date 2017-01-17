package eu.mobilebear.carcompany.injection.components;

import dagger.Component;
import eu.mobilebear.carcompany.MainActivity;
import eu.mobilebear.carcompany.injection.annotations.PerActivity;
import eu.mobilebear.carcompany.injection.modules.ActivityModule;

/**
 * @author bartoszbanczerowski@gmail.com
 * Created on 16.01.2017.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

	void inject(MainActivity activity);
}
