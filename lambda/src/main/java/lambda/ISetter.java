package lambda;
import java.io.Serializable;

@FunctionalInterface
public interface ISetter<T, U> extends Serializable {
    void set(T t, U u);
}
