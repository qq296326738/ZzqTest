package com.zzq.zzq.common.SheJiMoShi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 为其他对象提供一种代理以控制这个对象的访问。
 * <p>
 * 介绍
 * 代理模式属于结构型模式。
 * 代理模式也叫委托模式。
 * 生活中，比如代购、打官司等等，实际上都是一种代理模式。
 */
public class C代理模式 {
    public static void main(String[] args) {
        People domestic = new Domestic();        //创建国内购买人
        People oversea = new Oversea(domestic);  //创建海外代购类并将domestic作为构造函数传递
        oversea.buy();                           //调用海外代购的buy()
        /////////////////动态代理///////////////////////////
        People people = new Domestic();                                 //创建国内购买人
        DynamicProxy proxy = new DynamicProxy(people);                  //创建动态代理
        ClassLoader classLoader = people.getClass().getClassLoader();   //获取ClassLoader
        People people1 = (People) Proxy.newProxyInstance(classLoader, new Class[]{People.class}, proxy); //通过 Proxy 创建海外代购实例 ，实际上通过反射来实现的。
        people1.buy();//调用海外代购的buy()


    }
}

/**
 * Subject（抽象主题类）：接口或者抽象类，声明真实主题与代理的共同接口方法。
 * RealSubject（真实主题类）：也叫做被代理类或被委托类，定义了代理所表示的真实对象，负责具体业务逻辑的执行，客户端可以通过代理类间接的调用真实主题类的方法。
 * Proxy（代理类）：也叫委托类，持有对真实主题类的引用，在其所实现的接口方法中调用真实主题类中相应的接口方法执行。
 * Client（客户端类）：使用代理模式的地方。
 * <p>
 * 实现
 * 以海外代购为例，在国内的人想买国外的东西只能去找国外的人去进行代购。
 * 创建抽象主题类
 * 人都是有购买这个方法的：
 */
interface People {

    void buy();//购买
}

/**
 * 创建真实主题类
 * 国内的人想购买某些产品，定义具体的购买过程：
 */
class Domestic implements People {
    @Override
    public void buy() {//具体实现
        System.out.println("国内要买一个包");
    }
}

/**
 * 创建代理类
 * 海外的代购党需要知道是谁（持有真实主题类的引用）想购买啥产品：
 */
class Oversea implements People {
    People mPeople;//持有People类的引用

    public Oversea(People people) {
        mPeople = people;
    }

    @Override
    public void buy() {
        System.out.println("我是海外代购：");
        mPeople.buy();//调用了被代理者的buy()方法,
    }
}

/**
 * 静态代理与动态代理
 *   从代码的角度来分，代理可以分为两种：一种是静态代理，另一种是动态代理。
 * <p>
 *   静态代理就是在程序运行前就已经存在代理类的字节码文件，代理类和委托类的关系在运行前就确定了。上面的例子实现就是静态代理。
 * <p>
 *   动态代理类的源码是在程序运行期间根据反射等机制动态的生成，所以不存在代理类的字节码文件。代理类和委托类的关系是在程序运行时确定。
 * <p>
 *   下面我们实现动态代理，Java提供了动态的代理接口InvocationHandler，实现该接口需要重写invoke()方法：
 */

//创建动态代理类
class DynamicProxy implements InvocationHandler {//实现InvocationHandler接口
    private Object obj;//被代理的对象

    public DynamicProxy(Object obj) {
        this.obj = obj;
    }

    //重写invoke()方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("海外动态代理调用方法： " + method.getName());
        Object result = method.invoke(obj, args);//调用被代理的对象的方法
        return result;
    }
}

/**
 * 静态代理的缺点：
 * <p>
 * 静态代理如果接口新增一个方法，除了所有实现类（真实主题类）需要实现这个方法外，所有代理类也需要实现此方法。增加了代码维护的复杂度。
 * 代理对象只服务于一种类型的对象，如果要服务多类型的对象。必须要为每一种对象都进行代理，静态代理在程序规模稍大时就无法胜任了。
 * <p>
 * 动态代理的优点：
 * <p>
 * 可以通过一个代理类完成全部的代理功能，接口中声明的所有方法都被转移到调用处理器一个集中的方法中处理（InvocationHandler.invoke）。
 * 当接口方法数量较多时，我们可以进行灵活处理，而不需要像静态代理那样每一个方法进行中转。
 * 动态代理的应用使我们的类职责更加单一，复用性更强。
 * <p>
 * 动态代理的缺点：
 * <p>
 * 不能对类进行代理，只能对接口进行代理，如果我们的类没有实现任何接口，
 * 那么就不能使用这种方式进行动态代理（因为$Proxy()这个类集成了Proxy,Java的集成不允许出现多个父类）。
 */


/*
优点
代理作为调用者和真实主题的中间层,降低了模块间和系统的耦合性。
可以以一个小对象代理一个大对象,达到优化系统提高运行速度的目的。
代理对象能够控制调用者的访问权限，起到了保护真实主题的作用。

缺点
由于在调用者和真实主题之间增加了代理对象，因此可能会造成请求的处理速度变慢。
实现代理模式需要额外的工作（有些代理模式的实现非常复杂），从而增加了系统实现的复杂度。

*/



