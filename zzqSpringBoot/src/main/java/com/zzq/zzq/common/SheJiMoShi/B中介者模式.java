package com.zzq.zzq.common.SheJiMoShi;

/**
 * .定义
 * 用一个中介者对象来封装一系列的对象交互。
 * 中介者使得各对象不需要显式地相互引用，从而使其松散耦合，而且可以独立地改变它们之间的交互。
 * <p>
 * 介绍
 * 中介者模式属于行为型模式。
 * 中介者模式也称为调解者模式或者调停者模式。
 * 当程序存在大量的类时，多个对象之间存在着依赖的关系，呈现出网状结构，
 * 那么程序的可读性和可维护性就变差了，并且修改一个类需要牵涉到其他类，不符合开闭原则。
 */
public class B中介者模式 {
    public static void main(String[] args) {
        Lianjia houseMediator = new Lianjia();
        Purchaser purchaser = new Purchaser(houseMediator);
        Landlord landlord = new Landlord(houseMediator);
        houseMediator.setLandlord(landlord);
        houseMediator.setPurchaser(purchaser);

        landlord.send("出售一套别墅");
        System.out.println("------------------------");
        purchaser.send("求购一套学区房");
    }
}
/**
 * 角色说明：
 * Mediator(抽象中介者角色):抽象类或者接口,定义统一的接口，用于各同事角色之间的通信。
 * ConcreteMediator(具体中介者角色):继承或者实现了抽象中介者，
 * 实现了父类定义的方法,协调各个具体同事进行通信。
 * Colleague(抽象同事角色):抽象类或者接口,定义统一的接口，它只知道中介者而不知道其他同事对象。
 * ConcreteColleague(具体同事角色):继承或者实现了抽象同事角色，每个具体同事类都知道自己本身的行为，
 * 其他的行为只能通过中介者去进行。
 * 实现:
 * 说到中介者，肯定就想到了房屋中介，下面以房屋中介为例，房东通过中介发布出售信息，
 * 中介就会把房屋信息传递给有这需求的购房者，购房者再通过中介去看房买房等等。
 */

/**
 * 创建抽象同事角色
 * 无论是房东还是购房者，他们都能够发布信息和接受信息
 */
abstract class Person {//人物类
    protected HouseMediator houseMediator;

    public Person(HouseMediator houseMediator) {
        this.houseMediator = houseMediator;//获取中介
    }

    public abstract void send(String message);//发布信息

    public abstract void getNotice(String message);//接受信息
}

/**
 * 创建具体同事角色
 * 下面分别创建一个房东类和一个买房者类：
 */
class Purchaser extends Person {//买房者类，继承Person

    public Purchaser(HouseMediator houseMediator) {
        super(houseMediator);
    }

    @Override
    public void send(String message) {
        System.out.println("买房者发布信息：" + message);
        houseMediator.notice(this, message);
    }

    @Override
    public void getNotice(String message) {
        System.out.println("买房者收到消息：" + message);
    }
}

class Landlord extends Person {//房东者类，继承Person

    public Landlord(HouseMediator houseMediator) {
        super(houseMediator);
    }

    @Override
    public void send(String message) {
        System.out.println("房东发布信息：" + message);
        houseMediator.notice(this, message);
    }

    @Override
    public void getNotice(String message) {
        System.out.println("房东收到消息：" + message);
    }
}

/**
 * 创建抽象中介者角色
 * 这里就是房屋中介，定义一个通知的方法：
 */
interface HouseMediator {//房屋中介类

    void notice(Person person, String msg);//通知方法
}

/**
 * 创建具体中介者角色
 * 具体的房屋中介，以链家为例，他们能从房东和买房者获得信息，然后做出不同的行为：
 */
class Lianjia implements HouseMediator {//链家，实现HouseMediator
    Purchaser mPurchaser;
    Landlord mLandlord;

    public void setPurchaser(Purchaser purchaser) {//设置买房者
        mPurchaser = purchaser;
    }

    public void setLandlord(Landlord landlord) {//设置房东
        mLandlord = landlord;
    }


    @Override
    public void notice(Person person, String message) {//发送通知
        System.out.println("中介收到信息，并转发给相应的目标人群");
        if (person == mPurchaser) {
            mLandlord.getNotice(message);
        } else if (person == mLandlord) {
            mPurchaser.getNotice(message);
        }
    }
}
/**
 * 应用场景
 * 在程序中，如果类的依赖关系过于复杂，呈现网状的结构，可以使用中介者模式对其进行解耦。
 *
 * 优点
 * 降低类的关系复杂度，将多对多转化成一对多，实现解耦。
 * 符合迪米特原则，依赖的类最少。
 *
 * 缺点
 * 同事类越多，中介者的逻辑就越复杂，会变得越难维护。
 * 如果本来类的依赖关系不复杂，但是使用了中介者会使原来不复杂的逻辑变得复杂。
 * 因此需要根据实际情况去考虑，不要滥用中介者。
 */
