package com.zzq.zzq.common.SheJiMoShi;

/**
 * 定义
 * 将抽象部分与实现部分分离，使它们都可以独立的变化。
 * <p>
 * 2.介绍
 * 桥接模式属于结构型模式。
 * 举个生活中的例子，一条数据线，一头USB接口的可以连接电脑、充电宝等等，另一头可以连接不同品牌的手机，
 * 通过这条数据线，两头不同的东西就可以连接起来，这就是桥接模式。
 */
public class C桥接模式 {
    public static void main(String[] args) {
        //创建各种衣服对象
        Clothes uniform = new Uniform();
        Clothes shirt = new Shirt();

        //不同职业的人穿衣服
        PersonY coder = new Coder();
        coder.setClothes(shirt);
        coder.dress();

        System.out.println("--------------------------------------");
        PersonY student = new Student();
        student.setClothes(uniform);
        student.dress();

        System.out.println("--------------------------------------");
        student.setClothes(shirt);
        student.dress();
    }
}

/**
 * 角色说明：
 * Abstraction(抽象化角色)：一般是抽象类，定义该角色的行为，同时保存一个对实现化角色的引用。
 * Implementor(实现化角色)：接口或者抽象类，定义角色必需的行为和属性。
 * ConcreteImplementorA、ConcreteImplementorB(具体实现化角色):实现角色的具体行为。
 */

/*实现:这里以穿衣服为例，不同职业的人可能要穿的衣服不一样。*/

/*创建实现化角色定义一个衣服接口：*/
interface Clothes {
    String getName();
}

/*创建具体实现化角色
创建两种衣服类：校服和衬衫。*/
class Uniform implements Clothes {

    @Override
    public String getName() {
        return "校服";
    }
}

class Shirt implements Clothes {

    @Override
    public String getName() {
        return "衬衫";
    }
}

/* 创建抽象化角色
定义一个人物类，有一个穿衣服的方法，并且持有衣服类的引用。
即抽象化角色持有实现化角色的引用，可以调用实现化角色的方法，达到桥接的作用。*/
abstract class PersonY {
    Clothes mClothes;//持有衣服类的引用

    public void setClothes(Clothes clothes) {
        mClothes = clothes;
    }

    protected abstract void dress();//穿衣服
}

/*创建具体抽象化角色
这里有两种角色穿衣服：学生和程序员。*/
class Student extends PersonY {

    @Override
    protected void dress() {
        System.out.println("学生穿上" + mClothes.getName());
    }
}

class Coder extends PersonY {

    @Override
    protected void dress() {
        System.out.println("程序员穿上" + mClothes.getName());
    }
}
/**
 * 应用场景
 * 一个类存在两个或以上的独立维度的变化，且这些维度都需要进行拓展。
 * 不希望使用继承或因为多层次继承导致类的个数急剧增加时。
 * 如果一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性，
 * 避免在两个层次之间建立静态的继承关系，可以通过桥接模式使他们在抽象层建立一个关联关系。
 *
 * 优点
 * 分离了抽象与实现。让抽象部分和实现部分独立开来，分别定义接口，这有助于对系统进行分层，从而产生更好的结构化的系统。
 * 良好的扩展性。抽象部分和实现部分都可以分别独立扩展，不会相互影响。
 *
 * 缺点
 * 增加了系统的复杂性。
 * 不容易设计，抽象与实现的分离要设计得好比较有难度。
 */

