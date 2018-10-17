package com.zzq.zzq.common.SheJiMoShi;

import java.util.ArrayList;
import java.util.List;

/**
 * 定义对象间的一种一个对多的依赖关系，当一个对象的状态发送改变时，所以依赖于它的对象都得到通知并被自动更新。
 * 介绍
 * 观察者属于行为型模式。
 * 观察者模式又被称作发布/订阅模式。
 * 观察者模式主要用来解耦，将被观察者和观察者解耦，让他们之间没有没有依赖或者依赖关系很小。
 */
public class B观察者模式 {
    public static void main(String[] args) {
        Observable postman = new Postman1();

        Observer boy1 = new Boy("路飞");
        Observer boy2 = new Boy("乔巴");
        Observer girl1 = new Girl("娜美");

        postman.add(boy1);
        postman.add(boy2);
        postman.add(girl1);

        postman.notify("快递到了,请下楼领取.");

    }
}

/**
 * 角色说明：
 * Subject（抽象主题）：又叫抽象被观察者，把所有观察者对象的引用保存到一个集合里，每个主题都可以有任何数量的观察者。抽象主题提供一个接口，可以增加和删除观察者对象。
 * ConcreteSubject（具体主题）：又叫具体被观察者，将有关状态存入具体观察者对象；在具体主题内部状态改变时，给所有登记过的观察者发出通知。
 * Observer (抽象观察者):为所有的具体观察者定义一个接口，在得到主题通知时更新自己。
 * ConcrereObserver（具体观察者）：实现抽象观察者定义的更新接口，当得到主题更改通知时更新自身的状态。
 * <p>
 * 继续以送快递为例，快递员有时只是把快递拉到楼下，然后就通知收件人下楼去取快递。
 */

/**
 * 创建抽象观察者
 * 定义一个接到通知的更新方法，即收件人收到通知后的反应：
 */
interface Observer {//抽象观察者

    public void update(String message);//更新方法
}

/**
 * 创建具体观察者
 * 实现抽象观察者中的方法，这里创建两个类，一个男孩类和一个女孩类，定义他们收到通知后的反应：
 */
class Boy implements Observer {
    private String name;//名字

    public Boy(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {//男孩的具体反应
        System.out.println(name + ",收到了信息:" + message + "屁颠颠的去取快递.");
    }
}

class Girl implements Observer {
    private String name;//名字

    public Girl(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {//女孩的具体反应
        System.out.println(name + ",收到了信息:" + message + "让男朋友去取快递~");
    }
}

/**
 * 创建抽象主题
 * 即抽象被观察者，定义添加，删除，通知等方法：
 */
interface Observable {//抽象被观察者

    void add(Observer observer);//添加观察者

    void remove(Observer observer);//删除观察者

    void notify(String message);//通知观察者
}

/**
 * 创建具体主题
 * 即具体被观察者，也就是快递员，派送快递时根据快递信息来通知收件人让其来取件：
 */
class Postman1 implements Observable {//快递员

    private List<Observer> personList = new ArrayList<Observer>();//保存收件人（观察者）的信息

    @Override
    public void add(Observer observer) {//添加收件人
        personList.add(observer);
    }

    @Override
    public void remove(Observer observer) {//移除收件人
        personList.remove(observer);

    }

    @Override
    public void notify(String message) {//逐一通知收件人（观察者）
        for (Observer observer : personList) {
            observer.update(message);
        }
    }
}
/**
 * 应用场景
 * 当一个对象的改变需要通知其它对象改变时，而且它不知道具体有多少个对象有待改变时。
 * 当一个对象必须通知其它对象，而它又不能假定其它对象是谁
 * 跨系统的消息交换场景，如消息队列、事件总线的处理机制。
 *
 * 优点
 * 解除观察者与主题之间的耦合。让耦合的双方都依赖于抽象，而不是依赖具体。从而使得各自的变化都不会影响另一边的变化。
 * 易于扩展，对同一主题新增观察者时无需修改原有代码。
 *
 * 缺点
 * 依赖关系并未完全解除，抽象主题仍然依赖抽象观察者。
 * 使用观察者模式时需要考虑一下开发效率和运行效率的问题，程序中包括一个被观察者、多个观察者，开发、调试等内容会比较复杂，而且在Java中消息的通知一般是顺序执行，那么一个观察者卡顿，会影响整体的执行效率，在这种情况下，一般会采用异步实现。
 * 可能会引起多余的数据通知。
 */

