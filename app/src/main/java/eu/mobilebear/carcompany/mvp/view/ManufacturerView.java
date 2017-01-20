package eu.mobilebear.carcompany.mvp.view;

import eu.mobilebear.carcompany.mvp.model.Manufacturer;
import java.util.List;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public interface ManufacturerView extends View {

  void showManufacturers(List<Manufacturer> manufacturerResponses);

}
