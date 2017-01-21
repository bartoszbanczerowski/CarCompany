package eu.mobilebear.carcompany.mvp.model;

import android.support.annotation.Nullable;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author bartoszbanczerowski@gmail.com Created on 21.01.2017.
 */
public class SearchCriteria extends RealmObject {

  @PrimaryKey
  private int id = 0;
  private RealmList<Search> manufacturers;
  private RealmList<Search> mainTypes;
  private RealmList<Search> builtDates;

  public SearchCriteria() {
  }

  public SearchCriteria(RealmList<Search> manufacturers) {
    this(manufacturers, null, null);
  }

  public SearchCriteria(RealmList<Search> manufacturers, RealmList<Search> mainTypes) {
    this(manufacturers, mainTypes, null);
  }

  public SearchCriteria(
      RealmList<Search> manufacturers,
      @Nullable RealmList<Search> mainTypes,
      @Nullable RealmList<Search> builtDates) {
    this.manufacturers = manufacturers;
    this.mainTypes = mainTypes;
    this.builtDates = builtDates;
  }

  public RealmList<Search> getManufacturers() {
    return manufacturers;
  }

  public void setManufacturers(RealmList<Search> manufacturers) {
    this.manufacturers = manufacturers;
  }

  public RealmList<Search> getMainTypes() {
    return mainTypes;
  }

  public void setMainTypes(RealmList<Search> mainTypes) {
    this.mainTypes = mainTypes;
  }

  public RealmList<Search> getBuiltDates() {
    return builtDates;
  }

  public void setBuiltDates(RealmList<Search> builtDates) {
    this.builtDates = builtDates;
  }

  @Override
  public String toString() {
    return "Manufactuers: " + manufacturers.size() + "\n" +
        "MainTypes: " + mainTypes.size() + "\n" +
        "BuiltDates: " + builtDates.size() + "\n";
  }
}
