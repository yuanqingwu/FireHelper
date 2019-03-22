package com.wyq.firehelper.architecture.designpatterns.ObserverModel;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者模式：
 * 定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖于它的对象都会得到通知并被自动更新
 *
 * {@link Observable} 与{@link Observer} 是JDK中的内置类型
 *
 * @author Uni.W
 * @date 2019/3/22 22:17
 */
public class ObserverModel {

    public static void main(String[] args){
        //被观察的角色
        Website website = new Website();

        //观察者
        People people1 = new People("小明");
        People people2 = new People("小红");
        People people3 = new People("Tony");

        //将观察者注册到可观察对象的观察者列表中
        website.addObserver(people1);
        website.addObserver(people2);
        website.addObserver(people3);

        website.postNews("嫦娥登月成功");

    }

    public static class People implements Observer{

        public String name;
        public People(String name){
            this.name = name;
        }
        @Override
        public void update(Observable o, Object arg) {
            System.out.println("HI, "+name + "有新闻更新啦！ 内容："+arg);
        }
    }

    /**
     * 被观察者，当它有更新时，所有的观察者都会接收到相应的通知
     */
    public static class Website extends Observable{
        public void postNews(String news){
            //标志状态或者内容发生改变
            setChanged();
            //通知所有观察者
            notifyObservers(news);
        }
    }
}
