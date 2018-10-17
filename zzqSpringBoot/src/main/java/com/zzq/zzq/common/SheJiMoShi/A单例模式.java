package com.zzq.zzq.common.SheJiMoShi;

/**
 * 1. 单例类只能有一个实例。
 * 2. 单例类必须自己创建自己的唯一实例。
 * 3. 单例类必须给所有其他对象提供这一实例。
 */
public class A单例模式 {
    public static void main(String[] args) {

    }
}

/**
 * 饿汉式
 * 优点：写法简单，线程安全。
 * 缺点：没有懒加载的效果，如果没有使用过的话会造成内存浪费。
 */
class Singleton {

    private Singleton() {//构造方法为private,防止外部代码直接通过new来构造多个对象
    }

    private static final Singleton single = new Singleton();  //在类初始化时，已经自行实例化,所以是线程安全的。

    public static Singleton getInstance() {  //通过getInstance()方法获取实例对象
        return single;
    }
}

/**
 * 懒汉式（线程不安全）
 * 优点：实现了懒加载的效果。
 * 缺点：线程不安全。
 */
class Singleton1 {
    private Singleton1() {
    }

    private static Singleton1 single = null;

    public static Singleton1 getInstance() {
        if (single == null) {
            single = new Singleton1();  //在第一次调用getInstance()时才实例化，实现懒加载,所以叫懒汉式
        }
        return single;
    }
}

/**
 * 懒汉式（线程安全）
 * 优点：实现了懒加载的效果，线程安全。
 * 缺点：使用synchronized会造成不必要的同步开销，而且大部分时候我们是用不到同步的。
 */
class Singleton2 {
    private Singleton2() {
    }

    private static Singleton2 single = null;

    public static synchronized Singleton2 getInstance() { //加上synchronized同步
        if (single == null) {
            single = new Singleton2();
        }
        return single;
    }
}

/**
 * 双重检查锁定（DCL）
 * 优点：懒加载，线程安全，效率较高
 * 缺点：volatile影响一点性能，高并发下有一定的缺陷，某些情况下DCL会失效，虽然概率较小。
 */
class Singleton3 {
    private volatile static Singleton3 singleton; //volatile 能够防止代码的重排序，保证得到的对象是初始化过

    private Singleton3() {
    }

    public static Singleton3 getSingleton() {
        if (singleton == null) {  //第一次检查，避免不必要的同步
            synchronized (Singleton.class) {  //同步
                if (singleton == null) {   //第二次检查，为null时才创建实例
                    singleton = new Singleton3();
                }
            }
        }
        return singleton;
    }
}

/**
 * 静态内部类
 * 优点：懒加载，线程安全，推荐使用
 */
class Singleton4 {
    private Singleton4() {
    }

    public static Singleton4 getInstance() {
        //第一次调用getInstance方法时才加载SingletonHolder并初始化sInstance
        return SingletonHolder.sInstance;
    }

    //静态内部类
    private static class SingletonHolder {
        private static final Singleton4 sInstance = new Singleton4();
    }
}

/**
 * 枚举单例
 * 优点：线程安全，写法简单，能防止反序列化重新创建新的对象。
 * 缺点：可读性不高，枚举会比静态常量多那么一丁点的内存。
 */
/*public enum Singleton {

    INSTANCE;   //定义一个枚举的元素，它就是Singleton的一个实例

    public void doSomething() {
    }
}*/
