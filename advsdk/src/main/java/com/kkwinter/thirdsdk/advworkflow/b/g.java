package com.kkwinter.thirdsdk.advworkflow.b;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

public final class g extends a {
    private static ArrayList<String> s = new ArrayList();
    private String k = "";
    private int l = 0;
    private Method m = null;
    private Class<?> n = null;
    private Object o = null;
    private Object p = null;
    private JSONObject q = null;
    private JSONObject r = null;

    public g(Context var1, String var2, String var3, String var4, String var5, long var6, JSONObject var8, String var9, HashMap<String, Object> var10, aa var11) {
        super(var1, var2, var3, var4, var5, var6, var8, var9, var10, var11);
    }


    //todo 莫名其妙
    private boolean a(String paramString1, String paramString2, String paramString3, String paramString4, StringBuilder paramStringBuilder) {

        StringBuilder localStringBuilder = paramStringBuilder;
        if (paramStringBuilder == null) {
            localStringBuilder = new StringBuilder();
        }

        for (; ; ) {
            boolean bool2;

            try {
                this.n = Class.forName(paramString2);
                if (this.n != null) {

                }
            } catch (Exception e) {
                try {
                    this.n = Class.forName(com.kkwinter.thirdsdk.advworkflow.b.d.a("KDDG+cV/l5ECHFEa9VKLk3V4D3IGErgLtZK9Zw=="));

                    if (this.n == null) {
                        localStringBuilder.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("CDTSzMB1ypE3AEQb/Q4oR7bf256QzGyBSF4="));
                        bool2 = false;
                        return bool2;
                    }
                } catch (Exception e1) {

                    localStringBuilder.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("FxTS7MlkzYsUARg=") + e1.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EXE="));
                    paramStringBuilder = null;
//                    continue;

                    try {
                        Constructor localConstructor = this.n.getConstructor(new Class[]{String.class, String.class, String.class, ClassLoader.class});
                        localConstructor.setAccessible(true);
                        String str = com.kkwinter.thirdsdk.advworkflow.util.i.a(paramString1);
                        if (Build.VERSION.SDK_INT < 21) {
                            str = this.a.getFilesDir().getAbsolutePath();
                        }
                        this.p = localConstructor.newInstance(new Object[]{paramString1, str, null, this.a.getClassLoader()});
                    } catch (Exception e2) {
                        localStringBuilder.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("FxTS7MlkzYsUARg=") + e2.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EXE="));
                        this.p = null;
                        continue;
                    }
                    if (this.p == null) {
                        localStringBuilder.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("CDTSzMB1ypE3AEQb/Q4qWJPf3ZiQ6W+Bb0w9se61DXg="));
                        return false;
                    }
                }
                try {
                    this.m = this.n.getMethod(com.kkwinter.thirdsdk.advworkflow.b.d.a("ID7L6+942JEI"), new Class[]{String.class});
                    this.m.setAccessible(true);
                    this.n = ((Class) this.m.invoke(this.p, new Object[]{paramString2}));
                    if (this.n == null) {
                        localStringBuilder.append(paramString2 + com.kkwinter.thirdsdk.advworkflow.b.d.a("q+ARajC8/acji53Sfs5uEJGyhZrLhHzU"));
                        return false;
                    }
                } catch (Exception e1) {
                    localStringBuilder.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("FxTS7MlkzYsUARg=") + e1.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EXE="));
                    this.n = null;
                    continue;
                }

                if ((!TextUtils.isEmpty(paramString3)) && (this.o == null)) {
                    try {
                        this.m = this.n.getDeclaredMethod(paramString3, new Class[0]);
                        this.m.setAccessible(true);
                        this.o = this.m.invoke(this.n, new Object[0]);
                        if (this.o == null) {
                            localStringBuilder.append(paramString2 + com.kkwinter.thirdsdk.advworkflow.b.d.a("q+ARaDaQXEzli5v0ffRUE7aBhrfEiUDB"));
                            return false;
                        }
                    } catch (Exception e1) {
                        localStringBuilder.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("FxTS7MlkzYsUARg=") + e1.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EXE="));
                        this.o = null;
                        continue;
                    }
                }
                if (this.m == null) {
                    if (!a(paramString4, localStringBuilder)) {
                        break;
                    }
                    this.l = 1;
                }
            }
            try {
                localStringBuilder.delete(0, localStringBuilder.length());
                for (; ; ) {
                    try {
                        if (TextUtils.isEmpty(paramString3)) {
                            continue;
                        }
                        if (this.l != 1) {
                            continue;
                        }
                        this.m.invoke(this.o, new Object[]{this.a});
                    } catch (Exception e1) {
                        label687:
                        localStringBuilder.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("FxTS7MlkzYsUARg=") + e1.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EXE="))
                        ;
                        boolean bool1 = false;
//                        continue;
                        if (this.l != 3) {
                            continue;
                        }
                        this.m.invoke(this.o, new Object[]{this.a, this.c});
//                        continue;
                        if (this.l != 4) {
                            continue;
                        }
                        this.m.invoke(this.o, new Object[]{this.a, this.p, this.c});
                        continue;
                    }

                    boolean bool1 = true;
                    bool2 = bool1;
                    if (bool1) {
                        break;
                    }
                    localStringBuilder.append(paramString2 + com.kkwinter.thirdsdk.advworkflow.b.d.a("q+ARaDaQXGfeiqrcfup2EL6uSw==") + paramString4 + com.kkwinter.thirdsdk.advworkflow.b.d.a("ZXFCPy/zLUqey5SXLNk="));
//                    return bool1;

                    if (b(paramString4, localStringBuilder)) {
                        this.l = 2;
                        break;
                    }
                    if (c(paramString4, localStringBuilder)) {
                        this.l = 3;
                        break;
                    }
                    if (d(paramString4, localStringBuilder)) {
                        this.l = 4;
                        break;
                    }
                    localStringBuilder.append(paramString2 + com.kkwinter.thirdsdk.advworkflow.b.d.a("bLYwC0mRHAf0zMPpIZp8YyU=") + paramString4 + com.kkwinter.thirdsdk.advworkflow.b.d.a("ZbcYLkqIMATy0cD3KA=="));
//                    return false;

//                    continue;
                    if (this.l != 2) {
                        continue;
                    }
                    this.m.invoke(this.n, new Object[]{this.a, this.p});
                }
            } catch (Exception e1) {
                for (; ; ) {
                }
            }
        }

        // TODO: 2019/4/22 莫名其妙
        return false;
    }


    private boolean a(String var1, StringBuilder var2) {
        try {
            this.m = this.n.getDeclaredMethod(var1, Context.class);
            this.m.setAccessible(true);
            return true;
        } catch (Exception var3) {
            var2.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("FxTS7MlkzYsUARg=") + var3.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EXE="));
            this.m = null;
            return false;
        }
    }

    private static boolean b(String var0, String var1, String var2, String var3, StringBuilder var4) {
        StringBuilder var5 = var4;
        if (var4 == null) {
            var5 = new StringBuilder();
        }

        try {
            if (com.kkwinter.thirdsdk.advworkflow.util.i.c(var3)) {
                com.kkwinter.thirdsdk.advworkflow.util.i.d(var3);
            }

            com.kkwinter.thirdsdk.advworkflow.util.i.a(var3);
            if (!com.kkwinter.thirdsdk.advworkflow.util.i.b(var3)) {
                var5.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("ITrO"));
                return false;
            } else if (!com.kkwinter.thirdsdk.advworkflow.util.h.a(var0, var1, var3, var2)) {
                var5.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("KCbE"));
                return false;
            } else {
                return true;
            }
        } catch (Exception var8) {
            var5.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("OT/B4cNj18JT") + var8.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("ZQ=="));
            return false;
        } finally {
            ;
        }
    }

    private boolean b(String var1, StringBuilder var2) {
        try {
            this.m = this.n.getDeclaredMethod(var1, Context.class, ClassLoader.class);
            this.m.setAccessible(true);
            return true;
        } catch (Exception var3) {
            var2.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("FxTS7MlkzYsUARg=") + var3.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EXE="));
            this.m = null;
            return false;
        }
    }


    private boolean c() {
        // $FF: Couldn't be decompiled
        try {
            JSONObject localJSONObject = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.i, com.kkwinter.thirdsdk.advworkflow.b.d.a("ODDZ5A=="), (JSONObject) null);
            if (localJSONObject == null) {
                b(0, com.kkwinter.thirdsdk.advworkflow.b.d.a("qdwlZwK6UHbih4rQ"));
                return false;
            }
            this.q = com.kkwinter.thirdsdk.advworkflow.util.k.a(localJSONObject, com.kkwinter.thirdsdk.advworkflow.b.d.a("PjTZ4Nlm2oc="), (JSONObject) null);
            this.r = com.kkwinter.thirdsdk.advworkflow.util.k.a(localJSONObject, com.kkwinter.thirdsdk.advworkflow.b.d.a("KT/e/dVd14QU"), (JSONObject) null);
            if ((this.q == null) || (this.r == null) || (com.kkwinter.thirdsdk.advworkflow.util.k.a(this.q, com.kkwinter.thirdsdk.advworkflow.b.d.a("ITWf"), (String) null) == null)) {
                b(0, com.kkwinter.thirdsdk.advworkflow.b.d.a("qdwlZwK6UHbih4rQ"));
                return false;
            }
        } catch (Exception localException) {
            b(0, com.kkwinter.thirdsdk.advworkflow.b.d.a("qdk3agufXG7thrHmcNNg1lY=") + localException.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EQ=="));
            return false;
        }
        String str = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.q, com.kkwinter.thirdsdk.advworkflow.b.d.a("ITWf"), "");
        if (Build.VERSION.SDK_INT < 21) {
            str = str.substring(0, 7);
            this.k = (this.a.getCacheDir() + com.kkwinter.thirdsdk.advworkflow.b.d.a("YyfP/ch7y6Y5QA==") + str + com.kkwinter.thirdsdk.advworkflow.b.d.a("YjXP9w=="));
        } else {
            this.k = (this.a.getCacheDir() + com.kkwinter.thirdsdk.advworkflow.b.d.a("YyfP/ch7y6Y5QA==") + str + com.kkwinter.thirdsdk.advworkflow.b.d.a("YjXI"));
        }
        return true;
    }

    private boolean c(String var1, StringBuilder var2) {
        try {
            this.m = this.n.getDeclaredMethod(var1, Context.class, String.class);
            this.m.setAccessible(true);
            return true;
        } catch (Exception var3) {
            var2.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("FxTS7MlkzYsUARg=") + var3.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EXE="));
            this.m = null;
            return false;
        }
    }

    private boolean d(String var1, StringBuilder var2) {
        try {
            this.m = this.n.getDeclaredMethod(var1, Context.class, ClassLoader.class, String.class);
            this.m.setAccessible(true);
            return true;
        } catch (Exception var3) {
            var2.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("FxTS7MlkzYsUARg=") + var3.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("EXE="));
            this.m = null;
            return false;
        }
    }

    public final void a() {
        if (this.c()) {
            String var1 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.q, com.kkwinter.thirdsdk.advworkflow.b.d.a("ITWf"), "");
            if (s.contains(var1)) {
                this.b(1, "");
            } else {
                (new Thread(new Runnable() {
                    public final void run() {

                        boolean bool1;
                        int i = 0;


                        StringBuilder localStringBuilder = new StringBuilder();
                        try {
                            boolean bool2 = g.this.a(localStringBuilder);
                            bool1 = bool2;
                            if (bool2 == true) {
                                // TODO: 2019/4/22 莫名其妙？？
//                                localObject = com.kkwinter.thirdsdk.advworkflow.util.k.a(g.a(g.this), com.kkwinter.thirdsdk.advworkflow.b.d.a("ITWf"), "");
//                                g.b().add(localObject);
                                bool1 = bool2;
                            }
                        } catch (Exception localException) {
                            for (; ; ) {
                                localStringBuilder.append(com.kkwinter.thirdsdk.advworkflow.b.d.a("OT/B4cNj18JT") + localException.getMessage() + com.kkwinter.thirdsdk.advworkflow.b.d.a("ZQ=="));
                                bool1 = false;
                            }
                        }

                        if (bool1) {
                            i = 1;
                        }
                        b(i, localStringBuilder.toString());
                    }
                })).start();
            }
        }
    }

    protected final boolean a(StringBuilder var1) {
        String var5 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.q, com.kkwinter.thirdsdk.advworkflow.b.d.a("ITWf"), "");
        String var6 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.q, com.kkwinter.thirdsdk.advworkflow.b.d.a("KD7d4cB72IY2DkwRzQ6j"), "");
        String var7 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.q, com.kkwinter.thirdsdk.advworkflow.b.d.a("KD7d4cB72IY2BlcN9w6ahGE="), "");
        String var8 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.r, com.kkwinter.thirdsdk.advworkflow.b.d.a("PDDJ5M1z3KwaAkA="), "");
        String var4 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.r, com.kkwinter.thirdsdk.advworkflow.b.d.a("Lz3L/N9a2I8e"), "");
        String var3 = var4;
        if (!var4.startsWith(var8)) {
            var3 = var8 + com.kkwinter.thirdsdk.advworkflow.b.d.a("Yg==") + var4;
        }

        var4 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.r, com.kkwinter.thirdsdk.advworkflow.b.d.a("JT/Z+8162oc2ClEX9xg="), "");
        var8 = com.kkwinter.thirdsdk.advworkflow.util.k.a(this.r, com.kkwinter.thirdsdk.advworkflow.b.d.a("KT/e/dVZ3JYTAEE="), "");
        if (!b(var6, var7, var5, this.k, var1)) {
            return false;
        } else {
            boolean var2 = this.a(this.k, var3, var4, var8, var1);
            com.kkwinter.thirdsdk.advworkflow.util.i.d(this.k);
            return var2;
        }
    }
}

