package test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class PrimitiveBMHTest
{
    public static int myMethod(int arg1, int arg2)
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

        MethodHandle methodHandle = MethodHandles.lookup().unreflect(PrimitiveBMHTest.class.getMethod("myMethod", int.class, int.class));
        for (int i = 0; i < 1000; i++) {
            // bind to something
            MethodHandle bindedMethodHandle = MethodHandles.insertArguments(methodHandle, 0, i);

            // repeatedly call invokeExact
            int sum = 0;
            for (int j = 0; j < numInvokeExactCall; j++) {
                int value = (int) bindedMethodHandle.invokeExact(j);
                sum += value;
            }

            // make sure to print some to avoid compiler optimize the code
            System.out.println("Sum = " + sum);
        }

    }
}
