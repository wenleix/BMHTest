package test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Random;

public class BMHTest
{
    public static String myMethod(String str, Generator generator)
    {
        if (str.equals("generate1")) {
            return generator.generate1();
        }
        else {
            return generator.generate2();
        }
    }

    public static void main(String args[])
            throws Throwable
    {
        Random rand = new Random();

        MethodHandle methodHandle = MethodHandles.lookup().unreflect(BMHTest.class.getMethod("myMethod", String.class, Generator.class));

        for (int i = 0; i < 10000; i++) {
            // bind to some randomly generated string
            String bindingString = getBindingString(rand, i);
            MethodHandle bindedMethodHandle = methodHandle.bindTo(bindingString);

            // repeatedly call invokeExact
            Generator state = new MyGenerator(rand.nextInt(), new Random());
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 1000; j++) {
                String str = (String) bindedMethodHandle.invokeExact(state);
                sb.append(str);
            }

            // make sure to print some to avoid compiler optimize the code
            int start = rand.nextInt(sb.length());
            int end = Math.min(start + rand.nextInt(100), sb.length());
            System.out.println(sb.substring(start, end));
        }

    }

    private static String getBindingString(Random rand, int i)
    {
        if (i % 3 == rand.nextInt(3)) {
            return "generate1";
        }
        else {
            return "generate2";
        }
    }

    public interface Generator
    {
        String generate1();
        String generate2();
    }

    public static class MyGenerator
            implements Generator
    {
        private int value;
        private Random random;

        public MyGenerator(int value, Random random) {
            this.value = value;
            this.random = random;
        }

        // just generate some random strings
        @Override
        public String generate1()
        {
            return String.valueOf(value * random.nextInt(123) % 456);
        }

        @Override
        public String generate2()
        {
            return String.valueOf((value + random.nextDouble()) / 12.345);
        }
    }
}
