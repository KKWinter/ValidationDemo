package com.kkwinter.thirdsdk.advsdk.b;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;


/**
 *  根据url下载文件，保存到file到工具类
 */
public final class o {

    //downloadMainUrl    downloadMirrorUrl  var2> path.db   md5

    /**
     *
     * @param var0 downloadMainUrl
     * @param var1 downloadMirrorUrl
     * @param var2 path
     * @param var3 md5
     * @return
     */
    public static boolean a(String var0, String var1, String var2, String var3) {
        int var4;
        if (!TextUtils.isEmpty(var0)) {
            for(var4 = 0; var4 < 2; ++var4) {
                if (b(var0, var2, var3)) {
                    return true;
                }
            }
        }

        if (!TextUtils.isEmpty(var1)) {
            for(var4 = 0; var4 < 2; ++var4) {
                if (b(var1, var2, var3)) {
                    return true;
                }
            }
        }

        return false;
    }

    //http 和 file 分流
    private static boolean b(String var0, String var1, String var2) {
        return !var0.startsWith("file://") && !var0.startsWith("FILE://") ? c(var0, var1, var2) : a(var0, var1, var2);
    }


    // file路径  写文件
    private static boolean a(String var0, String var1, String var2) {
        if (!com.kkwinter.thirdsdk.advsdk.b.p.d(var0 = var0.replace("file://", ""))) {
            return false;
        } else {
            try {
                if (!p.a(var0, var1)) {
                    return false;
                } else {
                    return !TextUtils.isEmpty(var2) ? a(new File(var1)).equalsIgnoreCase(var2) : true;
                }
            } catch (Exception var3) {
                return false;
            }
        }
    }



    //下载http   url path md5
    private static boolean c(String var0, String var1, String var2) {
        try {
            if (!TextUtils.isEmpty(var2) && (new File(var1)).exists() && a(new File(var1)).equalsIgnoreCase(var2)) {
                return true;
            }
        } catch (Exception var23) {
            ;
        }

        HttpURLConnection var3 = null;
        RandomAccessFile var4 = null;
        InputStream var5 = null;
        boolean var16 = false;

        label292: {
            boolean var27;
            label293: {
                label294: {
                    try {
                        var16 = true;
                        if ((new File(var1)).exists()) {
                            (new File(var1)).delete();
                        }

                        (var3 = (HttpURLConnection)(new URL(var0)).openConnection()).setConnectTimeout(30000);
                        var3.setReadTimeout(30000);
                        var3.setRequestMethod("GET");
                        var3.setInstanceFollowRedirects(true);
                        if (var3.getResponseCode() == 200) {
                            (var4 = new RandomAccessFile(new File(var1), "rwd")).seek(0L);
                            var5 = var3.getInputStream();
                            byte[] var26 = new byte[4096];

                            int var6;
                            while((var6 = var5.read(var26)) != -1) {
                                var4.write(var26, 0, var6);
                            }

                            try {
                                if (var5 != null) {
                                    var5.close();
                                }

                                var4.close();
                                var5 = null;
                                var4 = null;
                            } catch (Exception var22) {
                                ;
                            }

                            if (!TextUtils.isEmpty(var2)) {
                                var27 = a(new File(var1)).equalsIgnoreCase(var2);
                                var16 = false;
                                break label293;
                            }

                            var16 = false;
                            break label294;
                        }

                        var16 = false;
                    } catch (Exception var24) {
                        if ((new File(var1)).exists()) {
                            (new File(var1)).delete();
                            var16 = false;
                            break label292;
                        }

                        var16 = false;
                        break label292;
                    } finally {
                        if (var16) {
                            try {
                                if (var5 != null) {
                                    var5.close();
                                }

                                if (var4 != null) {
                                    var4.close();
                                }

                                if (var3 != null) {
                                    var3.disconnect();
                                }
                            } catch (IOException var18) {
                                ;
                            }

                        }
                    }

                    try {
                        if (var3 != null) {
                            var3.disconnect();
                        }
                    } catch (Exception var19) {
                        var19.printStackTrace();
                    }

                    return false;
                }

                try {
                    if (var5 != null) {
                        var5.close();
                    }

                    if (var4 != null) {
                        var4.close();
                    }

                    if (var3 != null) {
                        var3.disconnect();
                    }
                } catch (IOException var21) {
                    ;
                }

                return true;
            }

            try {
                if (var5 != null) {
                    var5.close();
                }

                if (var4 != null) {
                    var4.close();
                }

                if (var3 != null) {
                    var3.disconnect();
                }
            } catch (IOException var20) {
                ;
            }

            return var27;
        }

        try {
            if (var5 != null) {
                var5.close();
            }

            if (var4 != null) {
                var4.close();
            }

            if (var3 != null) {
                var3.disconnect();
            }
        } catch (IOException var17) {
            ;
        }

        return false;
    }



    private static String a(File var0) {
        if (var0.isFile() && var0.exists()) {
            FileInputStream var2 = null;
            byte[] var3 = new byte[1024];
            boolean var9 = false;

            String var16;
            label133: {
                try {
                    var9 = true;
                    MessageDigest var1 = MessageDigest.getInstance("MD5");
                    var2 = new FileInputStream(var0);

                    int var15;
                    while((var15 = var2.read(var3, 0, 1024)) != -1) {
                        var1.update(var3, 0, var15);
                    }

                    for(var16 = (new BigInteger(1, var1.digest())).toString(16); var16.length() < 32; var16 = "0" + var16) {
                        ;
                    }

                    var9 = false;
                    break label133;
                } catch (Exception var13) {
                    var9 = false;
                } finally {
                    if (var9) {
                        try {
                            if (var2 != null) {
                                var2.close();
                            }
                        } catch (IOException var10) {
                            ;
                        }

                    }
                }

                var16 = "";

                try {
                    if (var2 != null) {
                        var2.close();
                    }
                } catch (IOException var11) {
                    ;
                }

                return var16;
            }

            try {
                var2.close();
            } catch (IOException var12) {
                ;
            }

            return var16;
        } else {
            return "";
        }
    }

}

