package com.zzq.zzq.common.SheJiMoShi;

/**
 * 定义一个用于创建对象的接口，让子类决定实例化哪个类。
 * 简单工厂模式属于创建型模式。
 * 简单工厂模式又叫做静态工厂方法模式。
 */
public class A简单工厂模式 {
    public static void main(String[] args) {
        Factory1.create("A").show();//生产ProductA
        Factory1.create("B").show();//生产ProductB
        try {
            Factory1.create("C").show();//生产ProductC
        } catch (NullPointerException e) {
            System.out.println("没有ProductC");//没有ProductC,会报错
        }

        FactoryFanShe.create(ProductAA.class).show();//生产ProductA
        FactoryFanShe.create(ProductBB.class).show();//生产ProductB
    }
}

/**
 * Product（抽象产品类）：要创建的复杂对象，定义对象的公共接口。
 * ConcreteProduct（具体产品类）：实现Product接口。
 * Factory（工厂类）：返回ConcreteProduct实例
 */

/**
 * 创建抽象产品类，定义公共接口：
 */
abstract class Product1 {
    public abstract void show();
}

//具体产品类A
class ProductAA extends Product1 {
    @Override
    public void show() {
        System.out.println("product AA");
    }
}

//具体产品类B
class ProductBB extends Product1 {
    @Override
    public void show() {
        System.out.println("product BB");
    }
}

/**
 * 创建工厂类，创建具体的产品
 */
class Factory1 {

    public static Product1 create(String productName) {
        Product1 product = null;
        //通过switch语句控制生产哪种商品
        switch (productName) {
            case "A":
                product = new ProductAA();
                break;
            case "B":
                product = new ProductBB();
                break;
        }
        return product;
    }
}

/**
 * 反射实现工厂类
 */
class FactoryFanShe {

    public static <T extends Product1> T create(Class<T> clz) {
        Product1 product = null;
        try {
            String className = clz.getName();
            if (className != null) {
                product = clz.newInstance();
                return (T) product;
            }
            product = (Product1) Class.forName(className).newInstance();//反射出实例
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) product;
    }

}

/**
 * 应用场景
 * 生成复杂对象时，确定只有一个工厂类，可以使用简单工厂模式。否则有多个工厂类的话，使用工厂方法模式。
 * <p>
 * 优点
 * 代码解耦，创建实例的工作与使用实例的工作分开，使用者不必关心类对象如何创建。
 * <p>
 * 缺点
 * 违背开放封闭原则，若需添加新产品则必须修改工厂类逻辑，会造成工厂逻辑过于复杂。
 * 简单工厂模式使用了静态工厂方法，因此静态方法不能被继承和重写。
 * 工厂类包含了所有实例（产品）的创建逻辑，若工厂类出错，则会造成整个系统都会会受到影响。
 * <p>
 * 工厂方法模式与简单工厂模式比较
 * 工厂方法模式有抽象工厂类，简单工厂模式没有抽象工厂类且其工厂类的工厂方法是静态的。
 * 工厂方法模式新增产品时只需新建一个工厂类即可，符合开放封闭原则；
 * 而简单工厂模式需要直接修改工厂类，违反了开放封闭原则。
 * <p>
 * 简单工厂模式的优化
 * 由于简单工厂模式新增产品时需要直接修改工厂类，违反了开放封闭原则。因此可以使用反射来创建实例对象，确保能够遵循开放封闭原则。
 */

