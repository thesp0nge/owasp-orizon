package org.owasp.orizon;

import java.io.IOException;
import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;
import org.apache.bcel.*;

/**
 * TODO: Please write DisassemblerTest jUnit test case
 * TODO: Add log4j calls
 */
public class Disassembler {

  public static boolean disassemble(String name) {
    JavaClass mod = null;
    try {
      mod = Repository.lookupClass(name);
    }
    catch (Exception e) {
      System.err.println("Could not get class " + name);
      return false;
    }

    ClassGen modClass = new ClassGen(mod);
    ConstantPoolGen cp = modClass.getConstantPool();

    Method[] methods = mod.getMethods();
    for (int i = 0; i < methods.length; i++) {
      System.out.println("* "+methods[i].getName());
      MethodGen mg = new MethodGen(methods[i], mod.getClassName(), cp);
      InstructionList il = mg.getInstructionList();
      System.out.println(il.toString());
    }

    return true;
  }
}
