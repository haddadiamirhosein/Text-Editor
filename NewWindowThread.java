public class NewWindowThread implements Runnable{
    @Override
    public void run() {
        new Editor();
    }
}
