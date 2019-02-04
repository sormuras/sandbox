import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Javadoc for Test should show Api annotation with status.
 */
@Api("B")
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
}
