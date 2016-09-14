package org.owasp.orizon;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;

/**
 * http://stackoverflow.com/questions/26760153/parse-jar-file-and-find-relationships-between-classes
 */
public class JarFinder
{

    protected static void findReferences(String jarName, JarFile jarFile) 
        throws ClassFormatException, IOException, ClassNotFoundException
    {
        Map<String, JavaClass> javaClasses = 
            collectJavaClasses(jarName, jarFile);

        for (JavaClass javaClass : javaClasses.values())
        {
            System.out.println("Class "+javaClass.getClassName());
            Map<JavaClass, Set<Method>> references = 
                computeReferences(javaClass, javaClasses);
            for (Entry<JavaClass, Set<Method>> entry : references.entrySet())
            {
                JavaClass referencedJavaClass = entry.getKey();
                Set<Method> methods = entry.getValue();
                System.out.println(
                    "    is referencing class "+
                    referencedJavaClass.getClassName()+" by calling");
                for (Method method : methods)
                {
                    System.out.println(
                        "        "+method.getName()+" with arguments "+
                        Arrays.toString(method.getArgumentTypes()));
                }
            }
        }
    }

    private static Map<String, JavaClass> collectJavaClasses(
        String jarName, JarFile jarFile) 
            throws ClassFormatException, IOException
    {
        Map<String, JavaClass> javaClasses =
            new LinkedHashMap<String, JavaClass>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements())
        {
            JarEntry entry = entries.nextElement();
            if (!entry.getName().endsWith(".class"))
            {
                continue;
            }

            ClassParser parser = 
                new ClassParser(jarName, entry.getName());
            JavaClass javaClass = parser.parse();
            javaClasses.put(javaClass.getClassName(), javaClass);
        }
        return javaClasses;
    }

    public static Map<JavaClass, Set<Method>> computeReferences(
        JavaClass javaClass, Map<String, JavaClass> knownJavaClasses) 
            throws ClassNotFoundException
    {
        Map<JavaClass, Set<Method>> references = 
            new LinkedHashMap<JavaClass, Set<Method>>();
        ConstantPool cp = javaClass.getConstantPool();
        ConstantPoolGen cpg = new ConstantPoolGen(cp);
        for (Method m : javaClass.getMethods())
        {
            String fullClassName = javaClass.getClassName();
            String className = 
                fullClassName.substring(0, fullClassName.length()-6);
            MethodGen mg = new MethodGen(m, className, cpg);
            InstructionList il = mg.getInstructionList();
            if (il == null)
            {
                continue;
            }
            InstructionHandle[] ihs = il.getInstructionHandles();
            for(int i=0; i < ihs.length; i++) 
            {
                InstructionHandle ih = ihs[i];
                Instruction instruction = ih.getInstruction();
                if (!(instruction instanceof InvokeInstruction))
                {
                    continue;
                }
                InvokeInstruction ii = (InvokeInstruction)instruction;
                ReferenceType referenceType = ii.getReferenceType(cpg);
                if (!(referenceType instanceof ObjectType))
                {
                    continue;
                }

                ObjectType objectType = (ObjectType)referenceType;
                String referencedClassName = objectType.getClassName();
                JavaClass referencedJavaClass = 
                    knownJavaClasses.get(referencedClassName);
                if (referencedJavaClass == null)
                {
                    continue;
                }

                String methodName = ii.getMethodName(cpg);
                Type[] argumentTypes = ii.getArgumentTypes(cpg);
                Method method = 
                    findMethod(referencedJavaClass, methodName, argumentTypes);
                Set<Method> methods = references.get(referencedJavaClass);
                if (methods == null)
                {
                    methods = new LinkedHashSet<Method>();
                    references.put(referencedJavaClass, methods);
                }
                methods.add(method);
            }
        }
        return references;
    }

    private static Method findMethod(
        JavaClass javaClass, String methodName, Type argumentTypes[])
            throws ClassNotFoundException
    {
        for (Method method : javaClass.getMethods())
        {
            if (method.getName().equals(methodName))
            {
                if (Arrays.equals(argumentTypes, method.getArgumentTypes()))
                {
                    return method;
                }
            }
        }
        for (JavaClass superClass : javaClass.getSuperClasses())
        {
            Method method = findMethod(superClass, methodName, argumentTypes);
            if (method != null)
            {
                return method;
            }
        }
        return null;
    }
}
