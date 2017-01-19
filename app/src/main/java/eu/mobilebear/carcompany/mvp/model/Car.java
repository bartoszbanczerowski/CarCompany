package eu.mobilebear.carcompany.mvp.model;

/**
 * @author bartoszbanczerowski@gmail.com Created on 19.01.2017.
 */
public class Car {

  private String manufacturer;
  private String mainType;
  private String builtDate;

  public Car(String manufacturer, String mainType, String builtDate) {
    this.manufacturer = manufacturer;
    this.mainType = mainType;
    this.builtDate = builtDate;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getMainType() {
    return mainType;
  }

  public void setMainType(String mainType) {
    this.mainType = mainType;
  }

  public String getBuiltDate() {
    return builtDate;
  }

  public void setBuiltDate(String builtDate) {
    this.builtDate = builtDate;
  }
}
