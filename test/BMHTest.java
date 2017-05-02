package test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class BMHTest
{
    public static String myMethod(String arg1, String arg2)
    {
        return arg1 + arg2;
    }

    public static void main(String args[])
            throws Throwable
    {
        if (args.length != 1) {
            System.out.println("Usage: java BMHTest [# InvokeExact Calls]");
            return;
        }
        int numInvokeExactCall = Integer.parseInt(args[0]);

        MethodHandle methodHandle = MethodHandles.lookup().unreflect(BMHTest.class.getMethod("myMethod", String.class, String.class));
        for (int i = 0; i < 1000; i++) {
            // bind to something
            MethodHandle bindedMethodHandle = methodHandle.bindTo("foo" + i);

            // repeatedly call invokeExact
            int totalLength = 0;
            for (int j = 0; j < numInvokeExactCall; j++) {
                String value = (String) bindedMethodHandle.invokeExact("bar" + j);
                totalLength += value.length();
            }

            // make sure to print some to avoid compiler optimize the code
            System.out.println("Total Length = " + totalLength);
        }

    }
}
