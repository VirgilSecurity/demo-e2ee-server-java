package data.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that provides information about argument's name.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NamedArg {
    /**
     * The name of the annotated argument.
     * @return the name of the annotated argument
     */
    public String value();

    /**
     * The default value of the annotated argument.
     * @return the default value of the annotated argument
     */
    public String defaultValue() default "";
}