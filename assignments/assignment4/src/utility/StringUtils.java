package utility;

public class StringUtils {

  private StringUtils() {
  }

  public static boolean isNullOrEmpty(String... strings) {
    for (String string : strings) {
      if (isNullOrEmpty(string)) {
        return true;
      }
    }
    return false;
  }

  public static boolean isNullOrEmpty(String string) {
    return string == null || string.isEmpty();
  }

  public static boolean isNotNullOrEmpty(String... strings) {
    return !isNullOrEmpty(strings);
  }
}
