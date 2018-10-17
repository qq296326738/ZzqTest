package com.zzq.zzq.common.SheJiMoShi;

/**
 * 定义
 * 将一个请求封装成一个对象，从而让你使用不同的请求把客户端参数化，对请求排队或者记录日志，
 * 可以提供命令的撤销和恢复功能。
 * <p>
 * 2.介绍
 * 命令模式属于行为型模式。
 * 我们遇到最常见的命令模式就是关机操作了，我们只需点击一下关机按钮就可以了，
 * 至于计算机是如何关机的，我们不需要关心其实现细节。
 */
public class B命令模式 {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();//创建命令接收者
        Command command = new ShutdownCommand(receiver);//创建一个命令的具体实现对象，并指定命令接收者
        Invoker invoker = new Invoker(command);//创建一个命令调用者，并指定具体命令
        invoker.action();//发起调用命令请求
    }
}
/**
 * 角色说明：
 * Command（命令角色）：接口或者抽象类，定义要执行的命令。
 * ConcreteCommand（具体命令角色）：命令角色的具体实现，通常会持有接收者，并调用接收者来处理命令。
 * Invoker（调用者角色）：负责调用命令对象执行请求，通常会持有命令对象（可以持有多个命令对象）。Invoker是Client真正触发命令并要求命令执行相应操作的地方（使用命令对象的入口）。
 * Receiver（接收者角色）：是真正执行命令的对象。任何类都可能成为一个接收者，只要它能够实现命令要求实现的相应功能。
 * Client（客户端角色）：Client可以创建具体的命令对象，并且设置命令对象的接收者。
 */
//实现
//就以关机为例子来实现命令模式。

/**
 * 创建命令角色
 * 定义一个抽象的执行方法：
 */
interface Command {
    void execute();//执行命令
}

/**
 * 创建具体命令角色
 * 创建一个关机命令：
 */
class ShutdownCommand implements Command {//关机命令
    private Receiver receiver;//接受者

    public ShutdownCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        System.out.println("命令角色执行关机命令");
        receiver.action();//调用接受者
    }
}

/**
 * 创建调用者角色
 */
class Invoker {//调用者
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void action() {
        System.out.println("调用者执行命令");
        command.execute();
    }
}

/**
 * 创建接收者角色
 */
class Receiver {
    public void action() {//接收者执行具体的操作
        System.out.println("接收者执行具体的操作");
        System.out.println("开始执行关机操作：");
        System.out.println("退出所有程序进程");
        System.out.println("关机～");
    }
}

/**
 * 说明：
 * 命令模式同时也支持命令的撤销(Undo)操作和恢复(Redo)操作，比如我们平时关机时，也是可以撤销关机的。至于恢复操作，需要我们记下执行过的命令，在需要的时候重新执行一遍。
 *
 * 应用场景
 * 需要对行为进行记录，撤销，重做，事务处理时。
 * 对于大多数请求——响应模式的功能，比较适合使用命令模式。
 *
 * 优点
 * 调用者与接受者之间的解藕。
 * 易于扩展，扩展命令只需新增具体命令类即可，符合开放封闭原则。
 *
 * 缺点
 * 过多的命令会造成过多的类。
 */
