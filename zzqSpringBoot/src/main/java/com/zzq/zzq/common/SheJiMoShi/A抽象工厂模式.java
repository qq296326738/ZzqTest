package com.zzq.zzq.common.SheJiMoShi;

/**
 * 为创建一组相关或者相互依赖的对象提供一个接口，而无需指定它们的具体类。
 * 抽象工厂模式属于创建型模式。
 * 工厂方法模式每个工厂只能创建一种类型的产品，而抽象工厂模式则能够创建多种类型的产品。
 * 例如：硬盘工厂只生产硬盘这种产品，而电脑工厂则组合不同的硬盘、内存、CPU等生产出电脑来。
 */
public class A抽象工厂模式 {
    public static void main(String[] args) {
        System.out.println("--------------------生产联想电脑-----------------------");
        ComputerFactory lenovoComputerFactory = new LenovoComputerFactory();
        lenovoComputerFactory.createCPU().showCPU();
        lenovoComputerFactory.createMemory().showMemory();
        lenovoComputerFactory.createHD().showHD();

        System.out.println("--------------------生产华硕电脑-----------------------");
        ComputerFactory asusComputerFactory = new AsusComputerFactory();
        asusComputerFactory.createCPU().showCPU();
        asusComputerFactory.createMemory().showMemory();
        asusComputerFactory.createHD().showHD();

        System.out.println("--------------------生产惠普电脑-----------------------");
        ComputerFactory hpComputerFactory = new HpComputerFactory();
        hpComputerFactory.createCPU().showCPU();
        hpComputerFactory.createMemory().showMemory();
        hpComputerFactory.createHD().showHD();
    }
}

/**
 * AbstractProduct（抽象产品类）：定义产品的公共接口。
 * ConcreteProduct（具体产品类）：定义产品的具体对象，实现抽象产品类中的接口。
 * AbstractFactory（抽象工厂类）：定义工厂中用来创建不同产品的方法。
 * ConcreteFactory（具体工厂类）：实现抽象工厂中定义的创建产品的方。
 */

/**
 * 创建抽象产品类
 */
//抽象产品类-- CPU
abstract class CPU {
    public abstract void showCPU();
}

//抽象产品类-- 内存
abstract class Memory {
    public abstract void showMemory();
}

//抽象产品类-- 硬盘
abstract class HD {
    public abstract void showHD();
}

/**
 * 创建具体产品类
 * 继承Product类
 */
//具体产品类-- Intet CPU
class IntelCPU extends CPU {

    @Override
    public void showCPU() {
        System.out.println("Intet CPU");
    }
}

//具体产品类-- AMD CPU
class AmdCPU extends CPU {

    @Override
    public void showCPU() {
        System.out.println("AMD CPU");
    }
}

//具体产品类-- 三星 内存
class SamsungMemory extends Memory {

    @Override
    public void showMemory() {
        System.out.println("三星 内存");
    }
}

//具体产品类-- 金士顿 内存
class KingstonMemory extends Memory {

    @Override
    public void showMemory() {
        System.out.println("金士顿 内存");
    }
}

//具体产品类-- 希捷 硬盘
class SeagateHD extends HD {

    @Override
    public void showHD() {
        System.out.println("希捷 硬盘");
    }
}

//具体产品类-- 西部数据 硬盘
class WdHD extends HD {

    @Override
    public void showHD() {
        System.out.println("西部数据 硬盘");
    }
}

/**
 * 创建抽象工厂类
 */
//抽象工厂类，电脑工厂类
abstract class ComputerFactory {
    public abstract CPU createCPU();

    public abstract Memory createMemory();

    public abstract HD createHD();
}

/**
 * 创建具体工厂类
 */
//具体工厂类--联想电脑
class LenovoComputerFactory extends ComputerFactory {

    @Override
    public CPU createCPU() {
        return new IntelCPU();
    }

    @Override
    public Memory createMemory() {
        return new SamsungMemory();
    }

    @Override
    public HD createHD() {
        return new SeagateHD();
    }
}

//具体工厂类--华硕电脑
class AsusComputerFactory extends ComputerFactory {

    @Override
    public CPU createCPU() {
        return new AmdCPU();
    }

    @Override
    public Memory createMemory() {
        return new KingstonMemory();
    }

    @Override
    public HD createHD() {
        return new WdHD();
    }
}

//具体工厂类--惠普电脑
class HpComputerFactory extends ComputerFactory {

    @Override
    public CPU createCPU() {
        return new IntelCPU();
    }

    @Override
    public Memory createMemory() {
        return new KingstonMemory();
    }

    @Override
    public HD createHD() {
        return new WdHD();
    }
}

/**
 * 应用场景
 * 生产多个产品组合的对象时。
 *
 * 优点
 * 代码解耦，创建实例的工作与使用实例的工作分开，使用者不必关心类对象如何创建。
 *
 * 缺点
 * 如果增加新的产品,则修改抽象工厂和所有的具体工厂,违反了开放封闭原则
 *
 * 工厂方法模式与抽象工厂模式比较
 * 在工厂方法模式中具体工厂负责生产具体的产品，每一个具体工厂对应一种具体产品，工厂方法具有唯一性。
 * 抽象工厂模式则可以提供多个产品对象，而不是单一的产品对象。
 */
