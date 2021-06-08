package governance.model;


import io.grpc.ClientInterceptor;
import lombok.Data;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Data
public class StubWrapper {
  private Class<?> cls;
  private String name;
  private List<Class<? extends ClientInterceptor>> interceptors;

  public void setInterceptors(Class<? extends ClientInterceptor>[] interceptors) {
    this.interceptors = Arrays.asList(interceptors);
    this.interceptors.sort(Comparator.comparing(Class::getName));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("cls:").append(this.cls.getName());
    sb.append(";name:").append(this.name);
    if (interceptors != null && this.interceptors.size() > 0) {
      sb.append(";interceptors:[");
      for (int i = 0; i < interceptors.size(); ++i) {
        if (i > 0) {
          sb.append(",");
        }
        sb.append(interceptors.get(i).getName());
      }
      sb.append("]");
    }
    return sb.toString();
  }

  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof StubWrapper)) {
      return false;
    } else {
      StubWrapper ot = (StubWrapper) obj;
      return this.toString().equals(ot.toString());
    }
  }
}
