package com.zzq.zzq.common.SheJiMoShi;

/**
 * 用原型实例指定创建对象的种类，并通过拷贝这些原型创建新的对象。
 * 原型模式属于创建型模式。
 * 一个已存在的对象（即原型），通过复制原型的方式来创建一个内部属性跟原型都一样的新的对象，这就是原型模式。
 * 原型模式的核心是clone方法，通过clone方法来实现对象的拷贝。
 */
public class A原型模式 {
    public static void main(String[] args) throws CloneNotSupportedException {
        Card card1 = new Card();
        card1.setNum(9527);
        card1.setSpec(10, 20);
        System.out.println(card1.toString());
        System.out.println("----------------------");

        Card card2 = card1.clone();
        System.out.println(card2.toString());
        System.out.println("----------------------");
    }
}

/**
 * Prototype（抽象原型类）：抽象类或者接口，用来声明clone方法。
 * ConcretePrototype1、ConcretePrototype2（具体原型类）：即要被复制的对象。
 * Client（客户端类）：即要使用原型模式的地方。
 * <p>
 * Prototype（抽象原型类）
 * 通常情况下，Prototype是不需要我们去定义的。因为拷贝这个操作十分常用，Java中提供了Cloneable接口来支持拷贝操作，它就是原型模式中的Prototype。
 * 当然，原型模式也未必非得去实现Cloneable接口，也有其他的实现方式。
 */

//实现Cloneable接口：
class Card implements Cloneable {//实现Cloneable接口，Cloneable只是标识接口
    private int num;//卡号

    private Spec spec = new Spec();//卡规格

    public Card() {
        System.out.println("Card 执行构造函数");
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setSpec(int length, int width) {
        spec.setLength(length);
        spec.setWidth(width);
    }

    @Override
    public String toString() {
        return "Card{" +
                "num=" + num +
                ", spec=" + spec +
                '}';
    }

    @Override
    protected Card clone() throws CloneNotSupportedException {//重写clone()方法，clone()方法不是Cloneable接口里面的，而是Object里面的
        System.out.println("clone时不执行构造函数");
        Card clone = (Card) super.clone();
        clone.spec = (Spec) spec.clone();//对spec对象也调用clone，实现深拷贝
        return clone;
    }
}

//规格类，有长和宽这两个属性
class Spec implements Cloneable {
    private int width;
    private int length;

    public void setLength(int length) {
        this.length = length;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "width=" + width +
                ", length=" + length +
                '}';
    }

    @Override
    protected Spec clone() throws CloneNotSupportedException {
        return (Spec) super.clone();
    }
}

/**
 * 应用场景
 *
 * 如果初始化一个类时需要耗费较多的资源，比如数据、硬件等等，可以使用原型拷贝来避免这些消耗。
 * 通过new创建一个新对象时如果需要非常繁琐的数据准备或者访问权限，那么也可以使用原型模式。
 * 一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时，可以拷贝多个对象供调用者使用，即保护性拷贝。
 *
 * 优点
 * 可以解决复杂对象创建时消耗过多的问题，在某些场景下提升创建对象的效率。
 * 保护性拷贝，可以防止外部调用者对对象的修改，保证这个对象是只读的。
 *
 * 缺点
 * 拷贝对象时不会执行构造函数。
 * 有时需要考虑深拷贝和浅拷贝的问题。
 */
