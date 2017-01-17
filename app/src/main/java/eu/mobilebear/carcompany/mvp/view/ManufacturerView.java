package eu.mobilebear.carcompany.mvp.view;

import java.util.List;

import eu.mobilebear.carcompany.mvp.model.Manufacturer;

/**
 * @author bartoszbanczerowski@gmail.com
 * Created on 16.01.2017.
 */
public interface ManufacturerView {

	void showManufacturers(List<Manufacturer> manufacturers);

	void showLoading();

	void showError();
}
