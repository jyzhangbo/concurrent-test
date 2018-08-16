package com.github.keyword.synchro;

/**
 * @Author:zhangbo
 * @Date:2018/8/15 16:57
 */
public class Bank extends Thread{

   private boolean add;
   private Integer drawMoney;

   private Money money;

   public Bank(boolean add,Integer drawMoney,Money money){
       this.add=add;
       this.drawMoney=drawMoney;
       this.money=money;
   }


    @Override
    public void run() {
       synchronized (money) {
           if (!add) {
               if (money.getMoney() < drawMoney) {
                   System.out.println("余额不足");
               } else {
                   System.out.println("取钱成功,取了" + drawMoney);
                   money.setMoney(money.getMoney() - drawMoney);
                   System.out.println("余额为" + money.getMoney());
               }
           } else {
               money.setMoney(money.getMoney() + drawMoney);
               System.out.println("加钱成功，余额为" + money.getMoney());
           }
       }
    }
}
