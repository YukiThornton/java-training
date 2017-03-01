package jpl.ch14.ex08;

/*
 * コード変更前：50回試して1度もデッドロックが起きなかった
 * コード変更後：50回試して1度もデッドロックが起きなかった
 */
public class Friendly {

    private Friendly partner;
    private String name;
    
    public Friendly(String name) {
        this.name = name;
    }
    
    public void hug() {
        synchronized (Friendly.class) {
            System.out.println(Thread.currentThread().getName() + " in " + name + 
                    ".hug() trying to invoke " + partner.name + ".hugback()");
            partner.hugback();
        }
    }
    
    public void hugback() {
        synchronized (Friendly.class) {
            System.out.println(Thread.currentThread().getName() + " in " + name + ".hugback()");
        }
    }
    
    public void becomeFriend(Friendly partner) {
        this.partner = partner;
    }

    public static void main(String[] args) {
        final Friendly jareth = new Friendly("jareth");
        final Friendly cory = new Friendly("cory");
        
        jareth.becomeFriend(cory);
        cory.becomeFriend(jareth);
        
        new Thread(new Runnable() {
            public void run() { jareth.hug();}
        }, "Thread1").start();

        new Thread(new Runnable() {
            public void run() { cory.hug();}
        }, "Thread2").start();

    }

}
