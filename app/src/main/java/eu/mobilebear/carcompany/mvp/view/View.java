package eu.mobilebear.carcompany.mvp.view;

/**
 * @author bartoszbanczerowski@gmail.com Created on 20.01.2017.
 */

public interface View {

  void showLoading();

  void dismissLoading();

  void showError(String message);

}
