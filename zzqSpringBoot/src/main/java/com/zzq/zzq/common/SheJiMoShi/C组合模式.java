package com.zzq.zzq.common.SheJiMoShi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 定义
 * 将对象组合成树形结构以表示“部分-整体”的层次结构，使得用户对单个对象和组合对象的使用具有一致性。
 * <p>
 * 2.介绍
 * 组合模式属于结构型模式。
 * 组合模式有时叫做部分—整体模式，主要是描述部分与整体的关系。
 * 组合模式实际上就是个树形结构，一棵树的节点如果没有分支，就是叶子节点;如果存在分支，则是树枝节点。
 * 我们平时遇到的最典型的组合结构就是文件和文件夹了，具体的文件就是叶子节点，
 * 而文件夹下还可以存在文件和文件夹，所以文件夹一般是树枝节点。
 * <p>
 */
public class C组合模式 {
    public static void main(String[] args) {
//        //创建网站根页面 root
//        PageElement root = new Column("网站页面");
//        //网站页面添加两个栏目：音乐,视屏;以及一个广告内容。
//        PageElement music = new Column("音乐");
//        PageElement video = new Column("视屏");
//        PageElement ad = new Content("广告");
//        root.addPageElement(music);
//        root.addPageElement(video);
//        root.addPageElement(ad);
//
//        //音乐栏目添加两个子栏目：国语,粤语
//        PageElement chineseMusic = new Column("国语");
//        PageElement cantoneseMusic = new Column("粤语");
//        music.addPageElement(chineseMusic);
//        music.addPageElement(cantoneseMusic);
//
//        //国语,粤语栏目添加具体内容
//        chineseMusic.addPageElement(new Content("十年.mp3"));
//        cantoneseMusic.addPageElement(new Content("明年今日.mp3"));
//
//        //视频栏目添加具体内容
//        video.addPageElement(new Content("唐伯虎点秋香.avi"));
//
//        //打印整个页面的内容
//        root.print("");

        //客户端测试方法
        //依赖具体的实现类Column
        Column root = new Column("网站页面");

        Column music = new Column("音乐");
        Column video = new Column("视屏");
        PageElement ad = new Content("广告");
        root.addPageElement(music);
        root.addPageElement(video);
        root.addPageElement(ad);

        Column chineseMusic = new Column("国语");
        Column cantoneseMusic = new Column("粤语");
        music.addPageElement(chineseMusic);
        music.addPageElement(cantoneseMusic);

        chineseMusic.addPageElement(new Content("十年.mp3"));
        cantoneseMusic.addPageElement(new Content("明年今日.mp3"));

        video.addPageElement(new Content("唐伯虎点秋香.avi"));

        root.print("");

    }
}

/**
 * 角色说明：
 * Component（抽象组件角色）：定义参加组合对象的共有方法和属性，可以定义一些默认的函数或属性。
 * Leaf（叶子节点）：叶子没有子节点，因此是组合树的最小构建单元。
 * Composite（树枝节点）：定义所有枝干节点的行为，存储子节点，实现相关操作。
 */

/* 创建抽象组件角色
这里就是一个网站的抽象页面元素：*/
abstract class PageElement {//页面

    protected List<PageElement> mPageElements = new ArrayList<>();//用来保存页面元素

    private String name;

    public PageElement(String name) {
        this.name = name;
    }

    public abstract void addPageElement(PageElement pageElement);//添加栏目或者具体内容

    public abstract void rmPageElement(PageElement pageElement);//删除栏目或者具体内容

    public abstract void clear();//清空所有元素

    public abstract void print(String placeholder);//打印页面结构

    public String getName() {
        return name;
    }
}

/**
 * 创建叶子节点
 * 叶子节点继承了抽象组件角色，但是由于没有分支，所以一些添加删除操作是实现不了的。
 * 叶子节点都是一些具体的内容，比如具体的音乐内容、视屏内容等等。
 */
class Content extends PageElement {//具体内容

    public Content(String name) {
        super(name);
    }

    @Override
    public void addPageElement(PageElement pageElement) {
        throw new UnsupportedOperationException("不支持此操作");
    }

    @Override
    public void rmPageElement(PageElement pageElement) {
        throw new UnsupportedOperationException("不支持此操作");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("不支持此操作");
    }

    @Override
    public void print(String placeholder) {
        System.out.println(placeholder + "──" + getName());
    }

}

/**
 * 创建树枝节点
 * 树枝节点能够删除添加叶子或树枝。
 */
class Column extends PageElement {//栏目

    public Column(String name) {
        super(name);
    }

    @Override
    public void addPageElement(PageElement pageElement) {
        mPageElements.add(pageElement);
    }

    @Override
    public void rmPageElement(PageElement pageElement) {
        mPageElements.remove(pageElement);
    }

    @Override
    public void clear() {
        mPageElements.clear();
    }

    /**
     * @param placeholder 占位符
     */
    @Override
    public void print(String placeholder) {
        //利用递归来打印文件夹结构
        System.out.println(placeholder + "└──" + getName());
        Iterator<PageElement> i = mPageElements.iterator();
        while (i.hasNext()) {
            PageElement pageElement = i.next();
            pageElement.print(placeholder + "   ");
        }
    }

}

/**
 * 其他说明：
 * 上面的例子可以看到叶子节点其实并不需要添加删除等方法，但由于叶子节点实际上是依赖了抽象组件角色。
 * 一方面，这遵循了依赖倒置原则——依赖抽象，而不依赖具体实现;
 * 同时，也保证了叶子节点跟树枝节点具体相同的结构，即他们具有同样的方法接口，
 * 能够让客户端以一致的方式去处理单个对象和组合对象。
 * 但另一方，这违反了单一职责原则与接口隔离原则，让 叶子节点继承了它本不应该有的方法，
 * 并且不太优雅的抛出了 UnsupportedOperationException 。这实际叫透明的组合模式。
 */
abstract class PageElement1 {//页面
    private String name;

    public PageElement1(String name) {
        this.name = name;
    }

    //抽象组件角色去掉增删等接口

    public abstract void print(String placeholder);

    public String getName() {
        return name;
    }
}

class Content1 extends PageElement1 {//具体内容，只专注自己的职责

    public Content1(String name) {
        super(name);
    }

    @Override
    public void print(String placeholder) {
        System.out.println(placeholder + "──" + getName());
    }
}

class Column1 extends PageElement1 {//栏目
    private List<PageElement> mPageElements = new ArrayList<>();//用来保存页面元素

    public Column1(String name) {
        super(name);
    }

    public void addPageElement(PageElement pageElement) {
        mPageElements.add(pageElement);
    }

    public void rmPageElement(PageElement pageElement) {
        mPageElements.remove(pageElement);
    }

    public void clear() {
        mPageElements.clear();
    }

    @Override
    public void print(String placeholder) {
        System.out.println(placeholder + "└──" + getName());
        Iterator<PageElement> i = mPageElements.iterator();
        while (i.hasNext()) {
            PageElement pageElement = i.next();
            pageElement.print(placeholder + "   ");
        }
    }


}