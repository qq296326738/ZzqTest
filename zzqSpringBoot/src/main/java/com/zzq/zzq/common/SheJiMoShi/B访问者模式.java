package com.zzq.zzq.common.SheJiMoShi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 定义
 * 封装某些作用于某种数据结构中各元素的操作，它可以在不改变数据结构的前提下定义作用于这些元素的新的操作。
 * <p>
 * 2.介绍
 * 访问者模式属于行为型模式。
 * 访问者模式是一种将数据结构和数据操作分离的设计模式。
 * 访问者模式比较复杂，而且实际使用的地方并不多。
 * 访问者模式适用于数据结构稳定的元素操作上，一旦数据结构易变，则不适用。
 */
public class B访问者模式 {
    public static void main(String[] args) {
        //创建不同的元素
        Music wangyiyue = new Music("网易云音乐");
        Music kugou = new Music("酷狗");
        Video youku = new Video("优酷");
        Video iqiyi = new Video("爱奇艺");

        //放入对象结构中
        Websites websites = new Websites();
        websites.addWeb(wangyiyue);
        websites.addWeb(kugou);
        websites.addWeb(youku);
        websites.addWeb(iqiyi);

        Visitor idler1 = new Idler("闲人1号");
        websites.accept(idler1);//集合接受idler1的访问

        System.out.println("-------------------------------------");
        Visitor busy1 = new Busy("忙人2号");
        websites.accept(busy1);////集合接受busy1的访问

    }
}

/**
 * 角色说明：
 * Visitor（抽象访问者）：接口或者抽象类，为每一个元素（Element）声明一个访问的方法。
 * ConcreteVisitor（具体访问者）：实现抽象访问者中的方法，即对每一个元素都有其具体的访问行为。
 * Element（抽象元素）：接口或者抽象类，定义一个accept方法，能够接受访问者（Visitor）的访问。
 * ConcreteElementA、ConcreteElementB（具体元素）：实现抽象元素中的accept方法，通常是调用访问者提供的访问该元素的方法。
 * Client（客户端类）：即要使用访问者模式的地方
 * <p>
 * <p>
 * 实现
 * 以我们平时听歌看视频为例，音乐视频网站都会提供在线播放和下载的功能，.
 * 当我们有空的时候往往就选择了在线播放，比较忙的时候就选择先下载下来，有空的时候再去观看。
 * 其中，音乐视频网站就是具体的要访问的元素，闲人和忙人就是具体的访问者，闲人和忙人的访问行为是不一样的。
 */

/**
 * 创建抽象访问者
 * 定义一个抽象的受访问方法以及其他公共的方法：
 */
abstract class Web {
    public String name;

    public Web(String name) {
        this.name = name;
    }

    //定义一个抽象的受访问方法
    public abstract void accept(Visitor visitor);

    //下载资源
    public abstract void download();

    public String getName() {
        return name;
    }
}

/**
 * 创建具体元素
 * 实现抽象元素中的accept()方法，通常是调用访问者提供的访问该元素的方法。
 * 下面创建音乐类以及视频类，他们都有一个download()方法，但是其具体下载的内容是不一样的，
 * 同时他们也存在各自独有的方法playMusic()和playVideo()：
 */
class Music extends Web {//音乐类，继承自Web类

    public Music(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {//接受访问者的访问
        visitor.visit(this);
    }

    @Override
    public void download() {//实现父类中的公共方法
        System.out.println("下载音乐~~");
    }

    public void playMusic() {//音乐类独有方法
        System.out.println("播放音乐ing");
    }
}

class Video extends Web {//视频类，继承自Web类

    public Video(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {//接受访问者的访问
        visitor.visit(this);
    }

    @Override
    public void download() {//实现父类中的公共方法
        System.out.println("下载视频~~");
    }

    public void playVideo() {//视频类独有方法
        System.out.println("播放视频ing");
    }
}

/**
 * 创建抽象访问者
 * 为每一个元素声明一个访问的方法：
 */
interface Visitor {
    void visit(Music music);//访问音乐类

    void visit(Video video);//访问视频类
}

/**
 * 创建具体访问者
 * 实现抽象访问者中的方法，即对每一个元素都有其具体的访问行为。下面以闲人和忙人为例：
 */
class Idler implements Visitor {//闲人

    private String name;

    public Idler(String name) {
        this.name = name;
    }

    @Override
    public void visit(Music music) {
        System.out.println(name + "浏览音乐网站：" + music.getName());
        music.playMusic();
    }

    @Override
    public void visit(Video video) {
        System.out.println(name + "浏览视频网站：" + video.getName());
        video.playVideo();
    }
}

class Busy implements Visitor {//忙人
    private String name;

    public Busy(String name) {
        this.name = name;
    }

    @Override
    public void visit(Music music) {
        System.out.println(name + "浏览音乐网站：" + music.getName());
        music.download();
    }

    @Override
    public void visit(Video video) {
        System.out.println(name + "浏览视频网站：" + video.getName());
        video.download();
    }
}

/**
 * 创建对象结构
 * 另外，为了方便访问多个元素，创建一个对象结构,在其内部管理元素集合,并且可以迭代这些元素供访问者访问：
 */
class Websites {
    List<Web> list = new ArrayList<>();//元素集合

    public void accept(Visitor visitor) {
        Iterator<Web> iterator = list.iterator();
        while (iterator.hasNext()) {//迭代遍历访问
            iterator.next().accept(visitor);
        }
    }

    public void addWeb(Web web) {
        list.add(web);
    }
}

/**
 * 应用场景
 * 对象结构比较稳定，很少改变，但是经常需要在此对象结构上定义新的操作行为时。
 * 需要对一个对象结构中的对象进行很多不同的并且不相关的操作,
 * 它可以在不改变这个数据结构的前提下定义作用于这些元素的新的操作。
 *
 * 优点
 * 各种角色各司其职，符合单一职责原则。
 * 原有的类上新增操作只需实现一个具体访问者就可以，不 必修改整个类层次，符合开闭原则。
 * 良好的扩展性，新增访问操作变得简单。
 * 数据操作和数据结构的解耦。
 *
 * 缺点
 * 具体元素对访问者公布了实现细节，破坏了类的封装性，违反了迪米特原则。
 * 违反了依赖倒置原则，为了达到区别对待依赖了具体而不是抽象。
 * 具体元素修改的成本太大。
 * 新增具体元素困难，需要在抽象访问者角色中增加一个新的抽象操作，违反了开闭原则。
 *
 * 其他
 * 访问者模式实际使用中比较少，但是真正需要用到时，还是很有用的。
 */
