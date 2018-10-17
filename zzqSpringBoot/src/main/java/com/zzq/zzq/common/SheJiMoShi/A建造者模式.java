package com.zzq.zzq.common.SheJiMoShi;

/**
 * 将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
 * 建造者模式属于创建型模式。
 * 建造者模式主要用来创建复杂的对象，用户可以不用关心其建造过程和细节。
 * 例如：当要组装一台电脑时，我们选择好CPU、内存、硬盘等等，
 * 然后交给装机师傅，装机师傅就把电脑给组装起来，我们不需要关心是怎么拼装起来的。
 */
public class A建造者模式 {
    public static void main(String[] args) {
        Builder builder = new ConcreteBuilder();//创建建造者实例，（装机人员）
//        Computer computer1 = builder.buildCPU("CPU").buildHD("HD").buildMemory("MEMORY").create();
        Director direcror = new Director(builder);//创建指挥者实例，并分配相应的建造者，（老板分配任务）
        direcror.Construct("i7-6700", "三星DDR4", "希捷1T");//组装电脑
        Computer computer = builder.create();
        System.out.println(computer);
    }
}


/**
 * 角色说明：
 * Product（产品类）：
 * 要创建的复杂对象。在本类图中，产品类是一个具体的类，而非抽象类。
 * 实际编程中，产品类可以是由一个抽象类与它的不同实现组成，
 * 也可以是由多个抽象类与他们的实现组成。
 * Builder（抽象建造者）：
 * 创建产品的抽象接口，一般至少有一个创建产品的抽象方法和一个返回产品的抽象方法。
 * 引入抽象类，是为了更容易扩展。
 * ConcreteBuilder（实际的建造者）：
 * 继承Builder类，实现抽象类的所有抽象方法。
 * 实现具体的建造过程和细节。
 * Director（指挥者类）：
 * 分配不同的建造者来创建产品，统一组装流-程。
 */

/**
 * 定义具体的产品类（Product）：电脑
 */
class Computer {
    private String mCPU;
    private String mMemory;
    private String mHD;

    public void setCPU(String CPU) {
        this.mCPU = CPU;
    }

    public void setMemory(String memory) {
        this.mMemory = memory;
    }

    public void setHD(String HD) {
        this.mHD = HD;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "mCPU='" + mCPU + '\'' +
                ", mMemory='" + mMemory + '\'' +
                ", mHD='" + mHD + '\'' +
                '}';
    }
}

/**
 * 定义抽象建造者（Builder）：组装电脑的过程
 */
abstract class Builder {
    public abstract Builder buildCPU(String cpu);//组装CPU

    public abstract Builder buildMemory(String memory);//组装内存

    public abstract Builder buildHD(String hd);//组装硬盘

    public abstract Computer create();//返回组装好的电脑
}

/**
 * 创建具体的建造者（ConcreteBuilder）:装机人员
 */
class ConcreteBuilder extends Builder {
    //创建产品实例
    private Computer mComputer = new Computer();

    @Override
    public Builder buildCPU(String cpu) {//组装CPU
        mComputer.setCPU(cpu);
        return this;
    }

    @Override
    public Builder buildMemory(String memory) {//组装内存
        mComputer.setMemory(memory);
        return this;
    }

    @Override
    public Builder buildHD(String hd) {//组装硬盘
        mComputer.setHD(hd);
        return this;
    }

    @Override
    public Computer create() {//返回组装好的电脑
        return mComputer;
    }
}

/**
 * 定义指挥者类（Director）：老板委派任务给装机人员
 */
class Director {
    private Builder mBuild = null;

    public Director(Builder build) {
        this.mBuild = build;
    }

    //指挥装机人员组装电脑
    public void Construct(String cpu, String memory, String hd) {
        this.mBuild.buildCPU(cpu);
        this.mBuild.buildMemory(memory);
        this.mBuild.buildHD(hd);
    }
}

/**
 * 应用场景
 * 创建一些复杂的对象时,对象内部的构建过程存在复杂变化。
 * 相同的构建过程，不同的执行顺序，产生不同结果时。
 * 不同配置的构建对象，产生不同结果时。
 *
 * 优点
 * 封装性良好，隐藏内部构建细节。
 * 易于解耦，将产品本身与产品创建过程进行解耦，可以使用相同的创建过程来得到不同的产品。也就说细节依赖抽象。
 * 易于扩展，具体的建造者类之间相互独立，增加新的具体建造者无需修改原有类库的代码。
 * 易于精确控制对象的创建，由于具体的建造者是独立的，因此可以对建造过程逐步细化，而不对其他的模块产生任何影响。
 *
 * 缺点
 * 产生多余的Build对象以及Dirextor类。
 * 建造者模式所创建的产品一般具有较多的共同点，其组成部分相似；如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。
 * 如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。
 */

