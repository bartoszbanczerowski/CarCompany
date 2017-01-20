//package eu.mobilebear.carcompany.mvp.model;
//
//import android.support.annotation.Nullable;
//import io.realm.RealmList;
//import io.realm.RealmObject;
//import java.util.List;
//
///**
// * @author bartoszbanczerowski@gmail.com Created on 21.01.2017.
// */
//
//public class SearchCriteria extends RealmObject {
//
//  private RealmList<Manufacturer> manufacturers;
//  private RealmList<MainType> mainTypes;
//  private RealmList<BuiltDate> builtDates;
//
//  public SearchCriteria() {
//  }
//
//  public SearchCriteria(List<Manufacturer> manufacturers) {
//    this(manufacturers, null, null);
//  }
//
//  public SearchCriteria(List<Manufacturer> manufacturers, List<MainType> mainTypes) {
//    this(manufacturers, mainTypes, null);
//  }
//
//  public SearchCriteria(
//      List<Manufacturer> manufacturers,
//      @Nullable List<MainType> mainTypes,
//      @Nullable List<BuiltDate> builtDates) {
//    this.manufacturers = new RealmList<>;
//    this.mainTypes = mainTypes;
//    this.builtDates = builtDates;
//  }
//
//  public List<Manufacturer> getManufacturers() {
//    return manufacturers;
//  }
//
//  public void setManufacturers(
//      List<Manufacturer> manufacturers) {
//    this.manufacturers = manufacturers;
//  }
//
//  public List<MainType> getMainTypes() {
//    return mainTypes;
//  }
//
//  public void setMainTypes(List<MainType> mainTypes) {
//    this.mainTypes = mainTypes;
//  }
//
//  public List<BuiltDate> getBuiltDates() {
//    return builtDates;
//  }
//
//  public void setBuiltDates(List<BuiltDate> builtDates) {
//    this.builtDates = builtDates;
//  }
//
//  @Override
//  public String toString() {
//    return "Manufactuers: " + manufacturers.toString();
//  }
//}
