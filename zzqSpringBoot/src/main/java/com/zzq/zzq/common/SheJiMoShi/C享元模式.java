package com.zzq.zzq.common.SheJiMoShi;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义
 * 使用共享对象可有效地支持大量的细粒度的对象.
 * <p>
 * 2.介绍
 * 享元模式属于结构型模式。
 * 享元模式是池技术的重要实现方式，它可以减少重复对象的创建，使用缓存来共享对象，从而降低内存的使用。
 * 细粒度的对象其状态可以分为两种：内部状态和外部状态。
 * 内部状态:对象可共享出来的信息，存储在享元对象内部并且不会随环境的改变而改变。
 * 外部状态:对象依赖的一个标记是随环境改变而改变的,并且不可共享。
 */
public class C享元模式 {
    public static void main(String[] args) {
        BikeFactory factory = new BikeFactory();
        IBike ofo = factory.getBike("ofo");
        ofo.billing(2);
        IBike mobike = factory.getBike("Mobike");
        mobike.billing(1);
        IBike ofo1 = factory.getBike("ofo");
        ofo1.billing(3);
    }
}

/**
 * 角色说明：
 * Flyweight(抽象享元角色)：接口或抽象类，可以同时定义出对象的外部状态和内部状态的接口或实现。
 * ConcreteFlyweight（具体享元角色）：实现抽象享元角色中定义的业务。
 * UnsharedConcreteFlyweight（不可共享的享元角色）：并不是所有的抽象享元类的子类都需要被共享，不能被共享的子类可设计为非共享具体享元类；当需要一个非共享具体享元类的对象时可以直接通过实例化创建。该对象一般不会出现在享元工厂中。
 * FlyweightFactory（享元工厂）：管理对象池和创建享元对象。
 */
/*实现
最近共享单车非常火，就以共享单车为例。如果我们之前没用过共享单车的话，
需要先交一份押金才能用;交过押金之后，第二次使用的话就可以直接用了，
不用再次交押金。这就跟对象池非常像，如果池中没有这个对象的话，
需要先创建一个对象;如果存在的话，则可以直接使用这个对象了。*/

/*创建抽象享元角色
定义一个单车接口，里面有个计费方法：*/
interface IBike {
    void billing(int time);
}

/*创建具体享元角色
创建共享单车类，其中单价是它的内部状态，不随环境而改变;总价是它的外部状态，随着环境改变而改变。*/
class ShareBike implements IBike {//共享单车类
    private int price = 1;//单价
    private int total;//总价

    @Override
    public void billing(int time) {
        total = price * time;
        System.out.println("骑车花费了" + total + "元");
    }
}

/* 创建享元工厂
负责管理对象池和创建享元对象：*/
class BikeFactory {
    private static Map<String, IBike> pool = new HashMap<>();//使用HashMap来保存IBike对象

    public IBike getBike(String name) {
        IBike bike = null;
        if (pool.containsKey(name)) {//如果存在对象的话，直接使用
            System.out.println("押金已交，直接用车：" + name);
            bike = pool.get(name);
        } else {//对象不存在的话，先创建对象
            bike = new ShareBike();
            pool.put(name, bike);
            System.out.println(name + "交100押金，可以用车了。");
        }
        return bike;
    }
}
/**
 * 应用场景
 * 系统存在大量相似或相同的对象。
 * 外部状态相同类似情况下。
 * 需要缓冲池时。
 * 6. 优点
 * 大大减少了系统创建的对象，降低了程序内存的使用。
 * 7. 缺点
 * 将对象分为内部状态和外部状态两部分，导致系统变复杂，逻辑也更复杂。
 * 将享元对象的状态外部化，而读取外部状态使得运行时间稍微变长。
 */
