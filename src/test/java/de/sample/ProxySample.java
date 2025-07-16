package de.sample;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxySample {

  interface HelloWorld {
    String sayHello();
  }

  public static void main(String[] args) {
    final var sh = new HelloWorld() {
      @Override
      public String sayHello() {
        return "hello";
      }
    };
    System.out.println(sh.sayHello());

    var myProxy = (HelloWorld) Proxy.newProxyInstance(
      ProxySample.class.getClassLoader(),
      new Class[]{HelloWorld.class},
      new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
          if (method.getName().equals("sayHello")) {
            return "hello proxy! (" + method.invoke(sh, args) + ")";
          } else {
            return method.invoke(sh, args);
          }
        }
      }
    );
    System.out.println(myProxy.sayHello());
    System.out.println(myProxy);

  }

}
