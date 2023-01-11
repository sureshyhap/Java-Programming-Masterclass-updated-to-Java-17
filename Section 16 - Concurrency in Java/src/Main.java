
public class Main {
   public static void main(String[] args) {
      System.out.println(ThreadColor.ANSI_PURPLE + "Hello from main thread.");
      
      final Thread anotherThread = new AnotherThread();
      anotherThread.setName("ANOTHER THREAD");
      anotherThread.start();
      
      System.out.println(ThreadColor.ANSI_PURPLE + "Hello again from the main thread.");

      new Thread() {
         public void run() {
            System.out.println(ThreadColor.ANSI_GREEN + "Hello from anonymous thread.");
         }
      }.start();
      Thread myRunnableThread = new Thread(new MyRunnable() {
         @Override
         public void run() {
            System.out.println(ThreadColor.ANSI_RED + "Hello from the anonymous class's "
                  + "implementation of run()");
            try {
               anotherThread.join(2000);
               System.out.println(ThreadColor.ANSI_RED + "Another thread terminated or timed out "
                     + "so I'm running again.");
            }
            catch (InterruptedException e) {
               System.err.println("I couldn't wait after all. I was interrupted.");
            }
         }
      });
      myRunnableThread.start();
      //anotherThread.interrupt();
   }
}

class AnotherThread extends Thread {
   @Override
   public void run() {
      System.out.println(ThreadColor.ANSI_BLUE + "Hello from " + Thread.currentThread().getName());
      try {
         Thread.sleep(5000);
      }
      catch (InterruptedException e) {
         System.err.println(ThreadColor.ANSI_BLUE + "A different thread woke me up.");
         return;
      }
      System.out.println("5 seconds have passed and I'm awake.");
   }
}

class ThreadColor {
   public static final String ANSI_RESET = "\u001B[0m";
   public static final String ANSI_BLACK = "\u001B[30m";
   public static final String ANSI_RED = "\u001B[31m";
   public static final String ANSI_GREEN = "\u001B[32m";
   public static final String ANSI_BLUE = "\u001B[34m";
   public static final String ANSI_PURPLE = "\u001B[35m";
   public static final String ANSI_CYAN = "\u001B[36m";
}

class MyRunnable implements Runnable {
   @Override
   public void run() {
      System.out.println(ThreadColor.ANSI_RED + "Hello from MyRunnable's implementation of run()");
   }
}