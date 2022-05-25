package lambda;
import java.io.Serializable;

// 加上该注解，当你写的接口不符合函数式接口定义的时候，编译器就会报错
// 加不加@FunctionalInterface对接口是不是函数式接口是没有影响的
// 该注解只是提醒编译器去检查该接口是否包含一个抽象方法
@FunctionalInterface
public interface ISetter<T, U> extends Serializable {
    void set(T t, U u);
}
