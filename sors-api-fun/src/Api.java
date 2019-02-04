import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface Api {

  String STATUS_A = "A";
  String STATUS_B = "B";
  String STATUS_C = "C";

  String value();
}
