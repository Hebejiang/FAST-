package sys.miaosha.controller;


   class A{
        public int x=0;
        public void fun(){
            System.out.println("A");
        }

    }

    class B extends A{
        public int x=1;
        public void fun(){
            System.out.println("B");
        }

    }
public class Test {
    public static void main(String [] args){
        A a = new B();
        System.out.println(a.x);
        a.fun();        ;

    }
}
