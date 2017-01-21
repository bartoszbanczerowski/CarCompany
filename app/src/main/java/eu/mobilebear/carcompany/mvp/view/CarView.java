package eu.mobilebear.carcompany.mvp.view;

import eu.mobilebear.carcompany.mvp.model.Car;
import java.util.List;

/**
 * @author bartoszbanczerowski@gmail.com Created on 21.01.2017.
 */

public interface CarView extends View {

  void showCars(List<Car> cars);

}
