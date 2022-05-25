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

    public static void main(String[] args) throws Exception {
        // 保存jvm运行过程中生成的lambda字节码文件到指定路径
        //System.getProperties().put("jdk.internal.lambda.dumpProxyClasses", "F:/lambda");
        String getName = LambdaUtils.convertToFieldName(User::getId);
        String setName = LambdaUtils.convertToFieldName(User::setName);
        String setUserName = LambdaUtils.convertToFieldName(User::setUserName);
        System.out.println(getName);
        System.out.println(setName);
        System.out.println(setUserName);
    }
}
