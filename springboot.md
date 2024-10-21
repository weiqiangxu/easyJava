# springboot


1. 编译器、类加载、运行期是什么意思( 一个是.java文件到.class文件、一个是.class文件加载到JVM内存、一个是运行程序的时候)
2. 编译期间AOP的例子（AspectJ 使用专门的编译器（ajc）对目标类进行处理、修改字节码(.class文件)）
3. 动态代理是什么意思. 具体的实现有哪些（就是实现Proxy类将对象注入代理类，实现的代理，常见的比如AOP）
4. JDK 动态代理（基于 Java 的反射机制，在运行时创建一个代理对象 - 实现InvocationHandler） 
5. CGLIB 代理 (继承目标类来创建一个代理子类)
6. 类加载期间 （用 Java 代理机制，在类被加载到 JVM 时动态地将切面逻辑织入到目标类中）
7. 使用 InvocationHandler 实现的动态代理是 CGLIB 还是JDK动态代理 （JDK动态代理）