package com.zzq.zzq.common.SheJiMoShi;

/**
 * 定义
 * 定义一个操作中的算法框架，而将一些步骤延迟到子类中，
 * 使得子类可以不改变一个算法的结构即可重定义算法的某些特定步骤。
 * <p>
 * 介绍
 * 模板方法模式属于行为型模式。
 * 模板方法模式主要是用来定义一套流程下来的固定步骤，而具体的步骤实现则可以是不固定的。
 */
public class B模板方法模式 {

    public static void main(String[] args) {
        System.out.println("----派送A----");
        PostmanA postA = new PostA();
        postA.post();
        System.out.println("----派送B----");
        PostmanA postB = new PostB();
        postB.post();

    }
}
/**
 * 角色说明：
 * AbstractClass（抽象类）：，定义了一整套算法框架。
 * ConcreteClass（具体实现类）：具体实现类，根据需要去实现抽象类中的方法。
 * <p>
 * 实现
 * 继续以送快递为例，快递员送快递基本就是一套固定的流程：收到快递，准备派送->联系收货人->确定结果。
 */

/**
 * 创建抽象类
 * 定义算法框架，这里是快递员派送快递的步骤：
 */
abstract class PostmanA {//抽象快递员类

    //派送流程
    public final void post() {//这里申明为final，不希望子类覆盖这个方法，防止更改流程的执行顺序
        prepare();//准备派送
        call();//联系收货人
        if (isSign())//是否签收
            sign();//签收
        else refuse();//拒签
    }

    protected void prepare() {//准备操作，固定流程，父类实现
        System.out.println("快递已达到，准备派送");
    }

    protected abstract void call();//联系收货人，联系人不一样，所以为抽象方法，子类实现

    protected boolean isSign() {//是否签收,这个是钩子方法，用来控制流程的走向
        return true;
    }

    protected void sign() {//签收，这个是固定流程，父类实现
        System.out.println("客户已签收，上报系统");
    }

    protected void refuse() {//拒签，空实现，这个也是钩子方法，子类可以跟进实际来决定是否去实现这个方法
    }
}

/**
 * 需要注意的是上面的抽象类（Postman）包含了三种类型的方法：抽象方法、具体方法和钩子方法。
 * <p>
 * 抽象方法：需要子类去实现。如上面的call()。
 * 具体方法：抽象父类中直接实现。如上面的prepare()和sign()。
 * 钩子方法：有两种，第一种，它是一个空实现的方法，子类可以视情况来决定是否要覆盖它，如上面的refuse()；
 * 第二种，它的返回类型通常是boolean类型的，一般用于对某个条件进行判断，
 * 如果条件满足则执行某一步骤，否则将不执行，如上面的isSign()。
 */

class PostA extends PostmanA {//派送给A先生

    @Override
    protected void call() {//联系收货，实现父类的抽象方法
        System.out.println("联系A先生并送到门口");
    }
}

class PostB extends PostmanA {//派送给B先生

    @Override
    protected void call() {//联系收货，实现父类的抽象方法
        System.out.println("联系B先生并送到门口");
    }

    @Override
    protected boolean isSign() {//是否签收,覆盖父类的钩子方法，控制流程的走向
        return false;
    }

    @Override
    protected void refuse() {//拒签，覆盖父类的钩子方法
        System.out.println("拒绝签收：商品不符");
    }
}
/**
 * 应用场景
 * 一次性实现算法的执行顺序和固定不变部分，可变部分则交由子类来实现。
 * 多个子类中拥有相同的行为时，可以将其抽取出来放在父类中，避免重复的代码。
 * 使用钩子方法来让子类决定父类的某个步骤是否执行，实现子类对父类的反向控制。
 * 控制子类扩展。模板方法只在特定点调用钩子方法，这样就只允许在这些点进行扩展。
 * 6. 优点
 * 提高代码复用性，去除子类中的重复代码。
 * 提高扩展性，不同实现细节放到不同子类中，易于增加新行为。
 * 7. 缺点
 * 每个不同的实现都需要定义一个子类，这会导致类的个数的增加，设计更加抽象。
 */
