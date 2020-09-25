package task12;

import javassist.CannotCompileException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Программа, демонстрирующая утечку памяти в Java.
 */
public class Main {

    static javassist.ClassPool classPool = javassist.ClassPool.getDefault();
    static int i = 0;

    public static void main(String[] args) throws CannotCompileException {
        heapOutOfMemoryError();
        metaspaceOutOfMemoryError();
    }

    /**
     * Функция, при вызове которой программа завершается с ошибкой OutOfMemoryError: Java Heap Space.
     */
    private static void heapOutOfMemoryError() {
        final List<String> list1 = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    list1.add("" + Integer.MAX_VALUE);
                }
            }
        }).start();

        final List<String> list2 = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    list2.add("" + new Random().nextDouble());
                }
            }
        }).start();
    }

    /**
     * Функция, при вызове которой программа завершается с ошибкой OutOfMemoryError: Metaspace.
     * Для тестирования целесообразно ограничить размер metaspace в конфигурации VM
     * при помощи команды -XX:MaxMetaspaceSize=512m.
     * @throws CannotCompileException
     */
    private static void metaspaceOutOfMemoryError() throws CannotCompileException {
        while (true) {
            Class sampleClass = classPool.makeClass("task12.Main" + i++).toClass();
        }
    }
}
