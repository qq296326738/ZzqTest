package com.zzq.zzq.common.SheJiMoShi;

/**
 * 定义一个用于创建对象的接口，让子类决定实例化哪个类。
 * 工厂方法模式属于创建型模式。
 * 工厂方法模式主要用来创建复杂的对象，简单对象能够使用new来创建就不用工厂方法模式来创建了。
 */
public class A工厂方法模式 {
    public static void main(String[] args) {
        //产品A
        Factory factoryA = new FactoryA();
        Product productA = factoryA.create();
        productA.show();
        //产品B
        Factory factoryB = new FactoryB();
        Product productB = factoryB.create();
        productB.show();

    }
}
/**
 * 角色说明：
 * Product（抽象产品类）：要创建的复杂对象，定义对象的公共接口。
 * ConcreteProduct（具体产品类）：实现Product接口。
 * Factory（抽象工厂类）：该方法返回一个Product类型的对象。
 * ConcreteFactory（具体工厂类）：返回ConcreteProduct实例。
 */

/**
 * 创建抽象产品类，定义公共接口：
 */
abstract class Product {
    public abstract void show();
}

//具体产品类A
class ProductA extends Product {
    @Override
    public void show() {
        System.out.println("product A");
    }
}

//具体产品类B
class ProductB extends Product {
    @Override
    public void show() {
        System.out.println("product B");
    }
}

/**
 * 创建抽象工厂类，定义公共接口：
 */
abstract class Factory {
    public abstract Product create();
}

//具体工厂类A
class FactoryA extends Factory {
    @Override
    public Product create() {
        return new ProductA();//创建ProductA
    }
}

//具体工厂类B
class FactoryB extends Factory {
    @Override
    public Product create() {
        return new ProductB();//创建ProductB
    }
}
/**
 * 应用场景
 * 生成复杂对象时，无需知道具体类名，只需知道相应的工厂方法即可。
 *
 * 优点
 * 符合开放封闭原则。新增产品时，只需增加相应的具体产品类和相应的工厂子类即可。
 * 符合单一职责原则。每个具体工厂类只负责创建对应的产品。
 *
 * 缺点
 * 一个具体工厂只能创建一种具体产品。
 * 增加新产品时，还需增加相应的工厂类，系统类的个数将成对增加，增加了系统的复杂度和性能开销。
 * 引入的抽象类也会导致类结构的复杂化。
 */
