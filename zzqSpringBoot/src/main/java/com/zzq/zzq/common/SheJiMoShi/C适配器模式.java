package com.zzq.zzq.common.SheJiMoShi;

/**
 * 定义
 * 将一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作。
 * <p>
 * 2.介绍
 * 适配器模式属于结构型模式。
 * 适配器模式有类适配器模式和对象适配器模式这两种。
 * 生活中的手机充电器就是一个适配器的例子，手机一般都是在5V的电压下进行充电，但是外部的电压都是220V，那怎么办，这就需要充电器去适配了，将220V的电压转换为5V。
 * 实际开发中，我们常遇到两个类之间的接口不兼容，但是又不想去改动接口，这可以通过适配器模式来解决这个问题。
 */
public class C适配器模式 {
    public static void main(String[] args) {
        /*对象适配器*/
        Electric electric = new Electric();
        System.out.println("默认电压：" + electric.output_220v());

        Adapter phoneAdapter = new PhoneAdapter(electric);//传递一个对象给适配器
        System.out.println("适配转换后的电压：" + phoneAdapter.convert_5v());

        /*类适配器*/
        Adapter phoneAdapter1 = new PhoneAdapter1();
        System.out.println("适配转换后的电压：" + phoneAdapter1.convert_5v());

        /**
         * 对象适配器模式与类适配器模式比较
         类适配器采用了继承的方式来实现;而对象适配器是通过传递对象来实现，这是一种组合的方式。
         类适配器由于采用了继承，可以重写父类的方法;对象适配器则不能修改对象本身的方法等。
         适配器通过继承都获得了父类的方法，客户端使用时都会把这些方法暴露出去，增加了一定的使用成本;对象适配器则不会。
         类适配器只能适配他的父类，这个父类的其他子类都不能适配到;而对象适配器可以适配不同的对象，只要这个对象的类型是同样的。
         类适配器不需要额外的引用;对象适配器需要额外的引用来保存对象。
         总的来说，使用对象适配器比较好。当然具体问题具体分析。
         */
    }
}

/**
 * 角色说明：
 * Adapter(适配器接口):即目标角色，定义把其他类转换为何种接口，也就是我们期望的接口。
 * Adaptee(被适配角色):即源角色，一般是已存在的类，需要适配新的接口。
 * ConcreteAdapter(具体适配器):实现适配器接口，把源角色接口转换为目标角色期望的接口。
 */
/*创建适配器接口
* 现在我们需要定义一个220V转换成5V的接口：*/
interface Adapter {//适配器类

    int convert_5v();//装换成5V
}

/*创建被适配角色
被适配角色，一般是已存在的类，需要适配新的接口。生活中的220V电源无处不在：*/
class Electric {// 电源

    public int output_220v() {//输出220V
        return 220;
    }
}

/* 创建具体适配器
我们需要一个具体适配器，这个适配器就是变压器，能够将220V转为5V输出*/
class PhoneAdapter implements Adapter {//手机适配器类
    private Electric mElectric;//适配器持有源目标对象

    public PhoneAdapter(Electric electric) {//通过构造方法传入对象
        mElectric = electric;
    }

    @Override
    public int convert_5v() {
        System.out.println("适配器开始工作：");
        System.out.println("输入电压：" + mElectric.output_220v());
        System.out.println("输出电压：" + 5);
        return 5;
    }
}

class PhoneAdapter1 extends Electric implements Adapter {//通过继承源目标类的方式，不持有源目标对象

    @Override
    public int convert_5v() {
        System.out.println("适配器开始工作：");
        System.out.println("输入电压：" + output_220v());
        System.out.println("输出电压：" + 5);
        return 5;
    }
}
/**
 * 应用场景
 * 当想使用一个已经存在的类，但它的接口不符合需求时。
 * 当想创建一个可以复用的类，该类可以与其他不相关的类或不可预见的类协同工作。
 *
 * 优点
 * 提高了类的复用性，适配器能让一个类有更广泛的用途。
 * 提高了灵活性，更换适配器就能达到不同的效果。不用时也可以随时删掉适配器，对原系统没影响。
 * 符合开放封闭原则，不用修改原有代码。没有任何关系的类通过增加适配器就能联系在一起。
 *
 * 缺点
 * 过多的使用适配器，会让系统非常零乱，不易整体进行把握。明明调用A接口，却被适配成B接口。
 */