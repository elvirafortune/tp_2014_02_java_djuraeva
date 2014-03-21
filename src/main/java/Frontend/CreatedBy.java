package Frontend; /**
 * Created by Elvira on 11.03.14.
 */
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CreatedBy {
    String name() default "elvira_djuraeva";
    String date() default "11.03.14";
}