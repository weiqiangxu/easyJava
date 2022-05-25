package lambda;


import po.User;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public class LambdaUtils {

    /**
     * 通过getter的方法引用获取字段名
     */
    public static <T> String convertToFieldName(IGetter<T> fn) throws Exception {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        String prefix = null;
        if (methodName.startsWith("get")) {
            prefix = "get";
        } else if (methodName.startsWith("is")) {
            prefix = "is";
        }
        if (prefix == null) {
            System.out.println("无效的getter方法: " + methodName);
        }
        return toLowerCaseFirstOne(methodName.replace(prefix, ""));
    }

    /**
     * 通过setter的方法引用获取字段名
     */
    public static <T, U> String convertToFieldName(ISetter<T, U> fn) throws Exception {
        SerializedLambda lambda = getSerializedLambda(fn);
        String methodName = lambda.getImplMethodName();
        if (!methodName.startsWith("set")) {
            System.out.println("无效的setter方法：" + methodName);
        }
        return toLowerCaseFirstOne(methodName.replace("set", ""));
    }

    /**
     * 关键在于这个方法
     */
    public static SerializedLambda getSerializedLambda(Serializable fn) throws Exception {
        // 函数式接口继承Serializable时，编译器在编译Lambda表达式时
        // 生成了一个writeReplace方法，这个方法会返回SerializedLambda，可以反射调用这个方法
        Method method = fn.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(Boolean.TRUE);
        SerializedLambda lambda = (SerializedLambda) method.invoke(fn);
        return lambda;
    }

    /**
     * 字符串首字母转小写
     */
    public static String toLowerCaseFirstOne(String field) {
        if (Character.isLowerCase(field.charAt(0)))
            return field;
        else {
            char firstOne = Character.toLowerCase(field.charAt(0));
            String other = field.substring(1);
            return new StringBuilder().append(firstOne).append(other).toString();
        }
    }

    // 仅仅使用与小驼峰
    public static void main(String[] args) throws Exception {
        // 保存jvm运行过程中生成的lambda字节码文件到指定路径
        //System.getProperties().put("jdk.internal.lambda.dumpProxyClasses", "F:/lambda");
        String getId = LambdaUtils.convertToFieldName(User::getId);
        System.out.println(getId);
        String getName = LambdaUtils.convertToFieldName(User::getName);
        System.out.println(getName);
        String setName = LambdaUtils.convertToFieldName(User::setName);
        System.out.println(setName);
        String getUserName = LambdaUtils.convertToFieldName(User::getUserName);
        System.out.println(getUserName);
        String getSchoolName = LambdaUtils.convertToFieldName(User::getSchoolName);
        System.out.println(getSchoolName);
    }
}
