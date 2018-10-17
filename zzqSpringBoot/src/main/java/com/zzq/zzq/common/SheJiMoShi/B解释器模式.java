package com.zzq.zzq.common.SheJiMoShi;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义
 * 给定一门语言，定义它的文法的一种表示，并定义一个解释器，该解释器使用该表示来解释语言中的句子。
 * <p>
 * 介绍
 * 解释器模式属于行为型模式。
 * 解释器模式提供了一种解释语言的语法或表达式的方式。
 * 解释器模式实际开发中很少用到。
 */
public class B解释器模式 {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.read("a = 1024");//读取表达式
        calculator.read("b = 512");
        System.out.println("a = 1024");
        System.out.println("b = 512");

        calculator.read("a + b");
        System.out.println("a + b = " + calculator.calculate());//计算结果
        calculator.read("a - b");
        System.out.println("a - b = " + calculator.calculate());
    }

}
/**
 * 角色说明：
 * AbstractExpression（抽象表达式）：定义一个抽象的解释方法，其具体的实现在各个具体的子类解释器中完成。
 * TerminalExpression（终结符表达式）：实现对文法中与终结符有关的解释操作。
 * NonterminalExpression（非终结符表达式）：实现对文法中的非终结符有关的解释操作。
 * ContextAA（环境角色）：包含解释器之外的全部信息。
 * Client（客户端角色）：解析表达式，构建抽象语法树，执行具体的解释操作等。
 */
//实现
//以加减法的实现为例，我们实现下面表达式的解释并输出结果，为了方便解释，在表达式中介加了空格方便处理。

/**
 * 创建抽象表达式
 */
abstract class ArithmeticExpression {//抽象算术表达式

    public abstract Object interpret(ContextBB Context);//抽象解释方法
}

/**
 * 终结符表达式
 * 从上面的表达式可以看出，终结符有两种，一种是数字，另外一种是变量
 */
//数字表达式，用来解释数字
class NumExpression extends ArithmeticExpression {
    private String strNum;

    public NumExpression(String strNum) {
        this.strNum = strNum;
    }

    @Override
    public Integer interpret(ContextBB Context) {//解释数字
        return Integer.parseInt(strNum);
    }
}

//变量表达式，用来解释变量
class VarExpression extends ArithmeticExpression {
    private String var;

    public VarExpression(String var) {
        this.var = var;
    }

    @Override
    public String interpret(ContextBB Context) {//解释变量
        return var;
    }
}

/**
 * 创建非终结符表达式
 * 上面的表达式有三种非终结符，分别是+号、-号和=号。
 */
//加法表达式，用来解释加法,如a+b
class AddExpression extends ArithmeticExpression {
    private ArithmeticExpression left, right;//加号左右两边的内容

    public AddExpression(ArithmeticExpression left, ArithmeticExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer interpret(ContextBB Context) {//解释加法表达式的结果，即算出left+right的结果
        return Context.get((String) left.interpret(Context)) + Context.get((String) right.interpret(Context));
    }
}

//减法表达式，用来解释减法,如a-b
class SubExpression extends ArithmeticExpression {
    private ArithmeticExpression left, right;//减号左右两边的内容

    public SubExpression(ArithmeticExpression left, ArithmeticExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer interpret(ContextBB Context) {//解释减法表达式的结果，即算出left-right的结果
        return Context.get((String) left.interpret(Context)) - Context.get((String) right.interpret(Context));
    }
}

//等号表达式，用来解释变量赋值，如a=1024
class EqualExpression extends ArithmeticExpression {
    private ArithmeticExpression left, right;//等号左右两边的内容

    public EqualExpression(ArithmeticExpression left, ArithmeticExpression right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public Object interpret(ContextBB Context) {//解释等号表达式的结果，并将结果保存到ContextAA，变量名为key,值为value
        Context.put((String) left.interpret(Context), (int) right.interpret(Context));
        return null;
    }
}

/**
 * 创建环境角色
 * 创建环境主要包含解释器之外的全部信息，这里用来保存变量以及其值
 */
class ContextBB {
    Map<String, Object> mMap = new HashMap<>();//使用HashMap来保存结果

    public void put(String key, int value) {
        mMap.put(key, value);
    }

    public int get(String key) {
        return (int) mMap.get(key);
    }
}

/**
 * 创建客户端角色：
 * 客户端角色主要负责解析表达式，构建抽象语法树，执行具体的解释操作等。
 */
class Calculator {//计算器类
    ContextBB mContext = new ContextBB();
    private ArithmeticExpression mExpression;

    public void read(String expression) {//读取表达式
        String[] split = expression.split(" ");//表达式以空格隔开，方便拆分
        switch (split[1]) {//根据不同符号去执行具体的解析操作
            case "=":
                new EqualExpression(new VarExpression(split[0]), new NumExpression(split[2])).interpret(mContext);
                break;
            case "+":
                mExpression = new AddExpression(new VarExpression(split[0]), new VarExpression(split[2]));
                break;
            case "-":
                mExpression = new SubExpression(new VarExpression(split[0]), new VarExpression(split[2]));
                break;
        }

    }

    public int calculate() {//计算结果
        return (int) mExpression.interpret(mContext);
    }
}
/**
 * 应用场景
 * 简单的语法需要解释时，如解释一个sql语句。
 * 一些重复发生的问题，比如加减乘除四则运算，但是公式每次都不同，有时是a+b-cd，有时是ab+c-d等，公式千变万化，但是都是由加减乘除四个非终结符来连接的，这时我们就可以使用解释器模式。
 *
 * 优点
 * 灵活的扩展性，想扩展语法规则时只需新增新的解释器就可以了。如上面的例子中，想增加乘除法，只想增加相应的解释类，并增加相应的表达式解释操作即可。

 * 缺点
 * 每一个文法都至少对应一个解释器，会产生大量的类，难于维护。
 * 解释器模式由于大量使用循环和递归，需要考虑效率的问题，而且调试也不方便。
 * 对于复杂的文法，构建其抽象语法树会显得异常繁琐。
 * 所以不推荐在重要的模块中使用解释器模式，维护困难。
 */
