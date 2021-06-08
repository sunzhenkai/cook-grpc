package governance.utils;

import java.lang.reflect.Field;

public class ReflectUtils {
  public static Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
    Class<?> cls = obj.getClass();
    Field declaredField = cls.getDeclaredField(fieldName);
    boolean accessible = declaredField.isAccessible();
    declaredField.setAccessible(true);
    Object value = declaredField.get(obj);
    declaredField.setAccessible(accessible);
    return value;
  }

  public static void setFieldValue(Object obj, String fieldName, Object fieldObj)
      throws NoSuchFieldException, IllegalAccessException {
    Class<?> cls = obj.getClass();
    Field declaredField = cls.getDeclaredField(fieldName);
    boolean accessible = declaredField.isAccessible();
    declaredField.setAccessible(true);
    declaredField.set(obj, fieldObj);
    declaredField.setAccessible(accessible);
  }
}
