package com.jumpraw.mraid.gp;

public class MethodBuilderFactory {
  static MethodBuilderFactory instance = new MethodBuilderFactory();

  @Deprecated // for testing
  public static void setInstance(MethodBuilderFactory factory) {
    instance = factory;
  }

  public static Reflection.MethodBuilder create(Object object, String methodName) {
    return instance.internalCreate(object, methodName);
  }

  protected Reflection.MethodBuilder internalCreate(Object object, String methodName) {
    return new Reflection.MethodBuilder(object, methodName);
  }
}

