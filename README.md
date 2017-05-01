Compiling
========= 

Compile with Java 1.8.0_131

    javac test/BMHTest.java


Running
=======

Run with Java 1.8.0_131 to reproduce the problem:

    java -Djava.lang.invoke.MethodHandle.DUMP_CLASS_FILES=true test.BMHTest


Results
=======

We can see it generates excessive number of BMH classes. Here is the result for a sample run:

    ls DUMP_CLASS_FILES/java/lang/invoke/ | wc -l

    10026

Here is a sample disassemble code:

    javap -c DUMP_CLASS_FILES/java/lang/invoke/LambdaForm\$BMH4321.class

    Compiled from "LambdaForm$BMH4321"
    final class java.lang.invoke.LambdaForm$BMH4321 {
      static java.lang.Object reinvoke_4321(java.lang.Object, java.lang.Object);
        Code:
           0: ldc           #12                 // String CONSTANT_PLACEHOLDER_0 <<(Generator)String : BMH.reinvoke_001=Lambda(a0:L/SpeciesData<LL>,a1:L)=>{\n    t2:L=BoundMethodHandle$Species_LL.argL1(a0:L);\n    t3:L=BoundMethodHandle$Species_LL.argL0(a0:L);\n    t4:L=MethodHandle.invokeBasic(t3:L,t2:L,a1:L);t4:L}\n& BMH=[MethodHandle(String,Generator)String, generate2]>>
           2: checkcast     #14                 // class java/lang/invoke/MethodHandle
           5: astore_0
           6: aload_0
           7: checkcast     #16                 // class java/lang/invoke/BoundMethodHandle$Species_LL
          10: dup
          11: astore_0
          12: getfield      #20                 // Field java/lang/invoke/BoundMethodHandle$Species_LL.argL1:Ljava/lang/Object;
          15: astore_2
          16: aload_0
          17: getfield      #23                 // Field java/lang/invoke/BoundMethodHandle$Species_LL.argL0:Ljava/lang/Object;
          20: astore_3
          21: aload_3
          22: checkcast     #14                 // class java/lang/invoke/MethodHandle
          25: aload_2
          26: aload_1
          27: invokevirtual #26                 // Method java/lang/invoke/MethodHandle.invokeBasic:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
          30: areturn
    
      static void dummy();
        Code:
           0: ldc           #30                 // String BMH.reinvoke_4321=Lambda(a0:L/SpeciesData<LL>,a1:L)=>{\n    t2:L=BoundMethodHandle$Species_LL.argL1(a0:L);\n    t3:L=BoundMethodHandle$Species_LL.argL0(a0:L);\n    t4:L=MethodHandle.invokeBasic(t3:L,t2:L,a1:L);t4:L}
           2: pop
           3: return
    }





