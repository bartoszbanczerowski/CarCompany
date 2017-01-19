package eu.mobilebear.carcompany.mvp.model;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

/**
 * @author bartoszbanczerowski@gmail.com Created on 16.01.2017.
 */
public class APIResponse {

  @SerializedName("page")
  private int page;

  @SerializedName("pageSize")
  private int pageSize;

  @SerializedName("totalPageCount")
  private int totalPageCount;

  @SerializedName("wkda")
  private HashMap<String, String> items;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalPageCount() {
    return totalPageCount;
  }

  public void setTotalPageCount(int totalPageCount) {
    this.totalPageCount = totalPageCount;
  }

  public HashMap<String, String> getItems() {
    return items;
  }

  public void setItems(HashMap<String, String> items) {
    this.items = items;
  }

  @Override
  public String toString() {
    return "page:" + page + "\n" +
        "pageSize:" + pageSize + "\n" +
        "totalPageCount:" + totalPageCount + "\n" +
        "items:" + items;
  }

}
