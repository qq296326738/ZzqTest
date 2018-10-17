package com.zzq.zzq.common.SheJiMoShi;

/**
 * 定义
 * 在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态，
 * 这样以后就可以将该对象恢复到先前保存的状态。
 * 介绍
 * 备忘录模式属于行为型模式。
 * 备忘录模式比较适合用于功能复杂，但是需要维护和纪录历史的地方，
 * 或者是需要保存一个或者多个属性的地方；在未来某个时刻需要时，将其还原到原来纪录的状态。
 */
public class B备忘录模式 {
    public static void main(String[] args) {
        System.out.println("首次进入游戏");
        Game game = new Game();
        game.play();//玩游戏
        Memento memento = game.createMemento();//创建存档
        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(memento);//保存存档
        game.exit();//退出游戏

        System.out.println("-------------");
        System.out.println("二次进入游戏");
        Game secondGame = new Game();
        secondGame.setMemento(caretaker.getMemento());//读取存档
        secondGame.play();//继续玩游戏
        secondGame.exit();//不存档,嘿嘿嘿
    }
}
/**
 * 角色说明：
 * Originator（发起人角色）：负责创建一个备忘录（Memoto），能够记录内部状态，以及恢复原来记录的状态。
 * 并且能够决定哪些状态是需要备忘的。
 * Memoto（备忘录角色）：    将发起人（Originator）对象的内部状态存储起来；
 * 并且可以防止发起人（Originator）之外的对象访问备忘录（Memoto）。
 * Caretaker（负责人角色）： 负责保存备忘录（Memoto），不能对备忘录（Memoto）的内容进行操作和访问，
 * 只能将备忘录传递给其他对象。
 * <p>
 * 以游戏存档为例子：
 */

/**
 * 创建发起人角色
 * 这里则是游戏类，游戏类提供存档和读档的功能：
 */
class Game {//游戏类
    private int mLevel = 1;//等级
    private int mCoin = 0;//金币数量

    @Override
    public String toString() {
        return "game{" +
                "mLevel=" + mLevel +
                ", mCoin=" + mCoin +
                '}';
    }

    public void play() {
        System.out.println("升级了");
        mLevel++;
        System.out.println("当前等级为:" + mLevel);
        System.out.println("获得金币:32");
        mCoin += 32;
        System.out.println("当前金币数量为:" + mCoin);
    }

    public void exit() {
        System.out.println("退出游戏");
        System.out.println("退出游戏时的属性 : " + toString());
    }

    public Memento createMemento() {//创建备忘录,即游戏存档
        Memento memento = new Memento();
        memento.setLevel(mLevel);
        memento.setCoin(mCoin);
        return memento;
    }

    public void setMemento(Memento memento) {
        mLevel = memento.getLevel();
        mCoin = memento.getCoin();
        System.out.println("读取存档信息:" + toString());
    }
}

/**
 * 创建备忘录角色
 * 负责将游戏类的内部状态存储起来：
 */
class Memento {//备忘录类
    public int level;//等级
    public int coin;//金币数量

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getLevel() {
        return level;
    }

    public int getCoin() {
        return coin;
    }
}

/**
 * 创建负责人角色
 * 负责保存备忘录，不能对备忘录的内容进行操作和访问，只能将备忘录传递给其他对象。
 */
class Caretaker {//备忘录管理类
    private Memento mMemento;

    public void setMemento(Memento memento) {
        mMemento = memento;
    }

    public Memento getMemento() {
        return mMemento;
    }
}
/**
 * 应用场景
 * 需要保存对象的某一时刻的状态时
 *
 * 优点
 * 能够让状态回滚到某一时刻的状态
 * 实现了状态保存对象的封装，用户无需关心其实现细节。
 *
 * 缺点
 * 要保存的对象如果成员变量过多的话，资源消耗也会相应增多。
 */


