import java.lang.reflect.Array;

public class MyQueue<E> {
    private E elements[];
    private int front, rear;
    private int size, maxSize;

    public MyQueue(Class<E> element, int maxSize) {
        front = 0;
        rear = -1;
        this.maxSize = maxSize;
        elements = (E[]) Array.newInstance(element, maxSize);
    }

    public void enqueue(E e) {
        if (rear == maxSize - 1) {
            int j = 0;
            for (int i = front; i <= rear; i ++) {
                elements[j++] = elements[i];
            }
            front = 0;
            rear = size - 1;
        }
        elements[++ rear] = e;
        size++;
    }

    public E dequeue() {
        E saved = elements[front];
        elements[front] = null;
        front++;
        size--;
        return saved;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return size == maxSize;
    }

    public int size(){
        return size;
    }
}
