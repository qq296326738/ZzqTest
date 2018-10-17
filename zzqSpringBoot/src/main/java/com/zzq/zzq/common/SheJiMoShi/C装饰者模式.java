package com.zzq.zzq.common.SheJiMoShi;

/**
 * 定义
 * 动态地给一个对象添加一些额外的职责。就增加功能来说，装饰模式相比生成子类更为灵活。
 * <p>
 * 2.介绍
 * 装饰者模式属于结构型模式。
 * 装饰者模式在生活中应用实际上也非常广泛，一如一间房，放上厨具，它就是厨房;放上床，就是卧室。
 * 通常我们扩展类的功能是通过继承的方式来实现，但是装饰者模式是通过组合的方式来实现，这是继承的替代方案之一。
 */
public class C装饰者模式 {
    public static void main(String[] args) {
        Room newRoom = new NewRoom();//有一间新房间
        RoomDecorator bedroom = new Bedroom(newRoom);
        bedroom.fitment();//装修成卧室
        RoomDecorator kitchen = new Kitchen(newRoom);
        kitchen.fitment();//装修成厨房
    }
}

/**
 * 角色说明：
 * Component（抽象组件）：接口或者抽象类，被装饰的最原始的对象。具体组件与抽象装饰角色的父类。
 * ConcreteComponent（具体组件）：实现抽象组件的接口。
 * Decorator（抽象装饰角色）：一般是抽象类，抽象组件的子类，同时持有一个被装饰者的引用，用来调用被装饰者的方法;同时可以给被装饰者增加新的职责。
 * ConcreteDecorator（具体装饰类）：抽象装饰角色的具体实现。
 */
/*就以装修房间为例子
创建抽象组件
这里是一个抽象房子类，定义一个装修的方法：*/
abstract class Room {
    public abstract void fitment();//装修方法
}

/*创建具体组件
现在有一间新房子，已经装上了电：*/
class NewRoom extends Room {//继承Room

    @Override
    public void fitment() {
        System.out.println("这是一间新房：装上电");
    }
}

/*创建抽象装饰角色
要为房子装修，定义抽象的房间装饰类：*/
abstract class RoomDecorator extends Room {//继承Room，拥有父类相同的方法
    private Room mRoom;//持有被装饰者的引用，这里是需要装修的房间

    public RoomDecorator(Room room) {
        this.mRoom = room;
    }

    @Override
    public void fitment() {
        mRoom.fitment();//调用被装饰者的方法
    }
}

/* 创建具体装饰类
我们要将房间装修成卧室和厨房，其具体实现是不同的：*/
class Bedroom extends RoomDecorator {//卧室类，继承自RoomDecorator

    public Bedroom(Room room) {
        super(room);
    }

    @Override
    public void fitment() {
        super.fitment();
        addBedding();
    }

    private void addBedding() {
        System.out.println("装修成卧室：添加卧具");
    }
}

class Kitchen extends RoomDecorator {//厨房类，继承自RoomDecorator

    public Kitchen(Room room) {
        super(room);
    }

    @Override
    public void fitment() {
        super.fitment();
        addKitchenware();
    }

    private void addKitchenware() {
        System.out.println("装修成厨房：添加厨具");
    }
}
/**
 * 应用场景
 * 需要扩展一个类的功能，或给一个类增加附加功能时
 * 需要动态的给一个对象增加功能，这些功能可以再动态的撤销
 * 当不能采用继承的方式对系统进行扩充或者采用继承不利于系统扩展和维护时。
 * 6. 优点
 * 采用组合的方式，可以动态的扩展功能，同时也可以在运行时选择不同的装饰器，来实现不同的功能。
 * 有效避免了使用继承的方式扩展对象功能而带来的灵活性差，子类无限制扩张的问题。
 * 被装饰者与装饰者解偶，被装饰者可以不知道装饰者的存在，同时新增功能时原有代码也无需改变，符合开放封闭原则。
 * 7. 缺点
 * 装饰层过多的话，维护起来比较困难。
 * 如果要修改抽象组件这个基类的话，后面的一些子类可能也需跟着修改，较容易出错
 */