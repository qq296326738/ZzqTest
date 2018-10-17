package com.zzq.zzq.common.SheJiMoShi;

/**
 * 定义
 * 要求一个子系统的外部与其内部的通信必须通过一个统一的对象进行。
 * 外观模式提供一个高层次的接口，使得子系统更易于使用。
 * <p>
 * 2.介绍
 * 外观模式属于结构型模式。
 * 外观模式也叫门面模式。
 * 通常我们对API进行封装，都会用到外观模式，只是我们可能不知道而已。
 * 外观模式通过一个外观类使得整个系统的结构只有一个统一的高层接口，这样能降低用户的使用成本。
 */
public class C外观模式 {
    public static void main(String[] args) {
        //这里是游戏研发，通过调用login()和pay()就能调起登录和支付，
        //无需关心支付是使用支付宝还是威信等，这是游戏SDK里面去做的事。
        GameSdk gameSdk = new GameSdk();
        gameSdk.login();
        gameSdk.pay(6);
    }
}

/**
 * 角色说明：
 * Facade(外观角色):对外的统一入口。
 * Complex System(复杂系统)：一般由多个子系统构成，负责具体功能的实现。
 * 4.实现
 * 我们玩游戏时一般都有登录和充值等操作，这些一般都是第三方SDK来完成。
 * 游戏研发一般只需进行简单的接入就可以使用登录充值功能了。我们来实现一个简单的游戏SDK：
 */
/*创建外观角色
这里只要是封装游戏SDK对外的接口，供游戏去研发去调用。这里只有两个接口：登录和充值。*/
class GameSdk {
    public void login() {//登录接口
        //调用登录子系统的接口
        LoginManager loginManager = new LoginManager();
        loginManager.login();
    }

    public void pay(int momey) {//支付接口
        //调用支付子系统的接口
        PayManager payManager = new PayManager();
        payManager.pay(momey);
    }
}

/* 子系统*/
//登录系统
class LoginManager {
    public void login() {
        System.out.println("打开登录界面");
        System.out.println("进行登录操作");
        System.out.println("登录成功");
    }
}

//支付系统
class PayManager {
    public void pay(int momey) {
        System.out.println("生成订单信息");
        System.out.println("选择支付方式");
        System.out.println("支付成功：" + momey + "元");
    }
}
/**
 * 应用场景
 * 为一个复杂的子系统提供一个简单接口，对外隐藏子系统的具体实现、隔离变化。
 * 使用外观模式可以将一个子系统和使用它的客户端以及其它的子系统分离开来，这就提高了子系统的独立性和可移植性。
 * 在构建一个层次化结构的时候，可以使用外观模式定义每一个层次对外交互的接口。这样，层与层之间只需要通过外观进行通信，从而简化层与层之间的依赖关系。
 * 6. 优点
 * 降低了客户端与子系统类的耦合度，实现了子系统与客户之间的松耦合关系。
 * 外观类对子系统的接口封装，使得系统更易于使用。
 * 提高灵活性，不管子系统如何变化，只要不影响门面对象，就可以自由修改。
 * 7. 缺点
 * 增加新的子系统可能需要修改外观类的源代码，违背了“开闭原则”。
 * 所有子系统的功能都通过一个接口来提供，这个接口可能会变得很复杂。
 */