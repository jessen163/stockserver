/**
 * upload#com.mc.m4s.common.FileUtilas.java 
 * 2011-4-7 下午02:58:10 created by Junqiang Jia
 */
package com.ryd.basecommon.util;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//import com.sun.imageio.plugins.bmp.BMPImageWriterSpi;
//import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
//import com.sun.imageio.plugins.jpeg.JPEGImageWriterSpi;
//import com.sun.imageio.plugins.png.PNGImageWriter;
//import com.sun.imageio.plugins.png.PNGImageWriterSpi;
//import com.sun.imageio.plugins.wbmp.WBMPImageWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 文件操作的工具类
 */
public class FileUtils {
    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    static {
        getAllFileType(); //初始化文件类型信息
    }

    /**
     * 常见文件头信息
     */
    private static void getAllFileType() {
        FILE_TYPE_MAP.put("jpg", "FFD8FF"); //JPEG (jpg)
        FILE_TYPE_MAP.put("png", "89504E47"); //PNG (png)
        FILE_TYPE_MAP.put("gif", "47494638"); //GIF (gif)
        FILE_TYPE_MAP.put("tif", "49492A00"); //TIFF (tif)
        FILE_TYPE_MAP.put("bmp", "424D"); //Windows Bitmap (bmp)
        FILE_TYPE_MAP.put("dwg", "41433130"); //CAD (dwg)
        FILE_TYPE_MAP.put("html", "68746D6C3E"); //HTML (html)
        FILE_TYPE_MAP.put("rtf", "7B5C727466"); //Rich Text Format (rtf)
        FILE_TYPE_MAP.put("xml", "3C3F786D6C");
        FILE_TYPE_MAP.put("zip", "504B0304");
        FILE_TYPE_MAP.put("rar", "52617221");
        FILE_TYPE_MAP.put("psd", "38425053"); //Photoshop (psd)
        FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A"); //Email [thorough only] (eml)
        FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F"); //Outlook Express (dbx)
        FILE_TYPE_MAP.put("pst", "2142444E"); //Outlook (pst)
        FILE_TYPE_MAP.put("xls", "D0CF11E0"); //MS Word
        FILE_TYPE_MAP.put("doc", "D0CF11E0"); //MS Excel 注意：word 和 excel的文件头一样
        FILE_TYPE_MAP.put("mdb", "5374616E64617264204A"); //MS Access (mdb)
        FILE_TYPE_MAP.put("wpd", "FF575043"); //WordPerfect (wpd)
        FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
        FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
        FILE_TYPE_MAP.put("pdf", "255044462D312E"); //Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("qdf", "AC9EBD8F"); //Quicken (qdf)
        FILE_TYPE_MAP.put("pwl", "E3828596"); //Windows Password (pwl)
        FILE_TYPE_MAP.put("wav", "57415645"); //Wave (wav)
        FILE_TYPE_MAP.put("avi", "41564920");
        FILE_TYPE_MAP.put("ram", "2E7261FD"); //Real Audio (ram)
        FILE_TYPE_MAP.put("rm", "2E524D46"); //Real Media (rm)
        FILE_TYPE_MAP.put("mpg", "000001BA"); //
        FILE_TYPE_MAP.put("mov", "6D6F6F76"); //Quicktime (mov)
        FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); //Windows Media (asf)
        FILE_TYPE_MAP.put("mid", "4D546864"); //MIDI (mid)
    }

    /**
     * 获取文件格式
     *
     * @return 具体的文件格式
     */
    public final static String getFileType(byte[] b) {
        byte[] b1 = new byte[50];
        if (b != null && b.length >= 50) {
            for (int i = 0; i < 50; i++) {
                b1[i] = b[i];
            }
        } else {
            b1 = b;
        }
        return getFileTypeByStream(b1);
    }

    /**
     * 获取文件格式
     *
     * @param is 输入流
     *
     * @return 具体的文件格式
     */
    public final static String getFileType(InputStream is) {
        String filetype = null;
        byte[] b = new byte[50];
        try {
            is.read(b);
            filetype = getFileTypeByStream(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return filetype;
    }

    /**
     * 获取文件类型
     *
     * @param b 文件的字节码
     *
     * @return 文件类型
     */
    public final static String getFileTypeByStream(byte[] b) {
        String filetypeHex = String.valueOf(getFileHexString(b));
        Iterator<Map.Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
        while (entryiterator.hasNext()) {
            Map.Entry<String, String> entry = entryiterator.next();
            String fileTypeHexValue = entry.getValue();
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static InputStream[] copyInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream[] ins = new InputStream[2];
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        ins[0]=new ByteArrayInputStream(baos.toByteArray());
        ins[1]=new ByteArrayInputStream(baos.toByteArray());
        return ins;
    }

    public static InputStream[] copyInputStreamForUeditor(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream[] ins = new InputStream[4];
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        ins[0]=new ByteArrayInputStream(baos.toByteArray());
        ins[1]=new ByteArrayInputStream(baos.toByteArray());
        ins[2]=new ByteArrayInputStream(baos.toByteArray());
        ins[3]=new ByteArrayInputStream(baos.toByteArray());
        return ins;
    }

    /**
     * 获取文件头信息
     *
     * @param b 文件头的字节数组
     *
     * @return 文件头类型
     */
    private final static String getFileHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= 0) {
            return null;
        }
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

//    /**
//     * 压缩图片
//     *
//     * @param image
//     * @param quality 质量最好为1
//     *
//     * @return
//     * @throws IOException
//     */
//    public static InputStream pressImage(BufferedImage image,
//                                         float quality,String type) throws IOException {
//
//        File temFile = null;
//        InputStream is2 = null;
//        ImageWriter imgWrier = null;
//        ImageWriteParam param = null;
//        ByteArrayOutputStream bos = null, bos2 = new ByteArrayOutputStream();
//        try {
//            bos = new ByteArrayOutputStream();
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
//            JPEGEncodeParam jpeg = JPEGCodec.getDefaultJPEGEncodeParam(image);
////            jpeg.setQuality(0.5f, true);
//            jpeg.setQuality(quality, true);
//            encoder.encode(image, jpeg);
//            bos.flush();
//            is2 = new ByteArrayInputStream(bos.toByteArray());
//            if (type.equals("png")) {
//                imgWrier = new PNGImageWriter(new PNGImageWriterSpi());
//            } else if (type.equals("bmp")) {
//                imgWrier = new WBMPImageWriter(new BMPImageWriterSpi());
//            } else if (type.equals("jpg")) {
//                imgWrier = new JPEGImageWriter(new JPEGImageWriterSpi());
//            }
//            if (null != imgWrier) {
//                temFile = File.createTempFile("tmppic", "." + type);
//                param = imgWrier.getDefaultWriteParam();
//                param.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
//                imgWrier.setOutput(ImageIO.createImageOutputStream(temFile));
//                imgWrier.write(null, new IIOImage(ImageIO.read(is2), null, null), param);
//                imgWrier.dispose();
//                InputStream is = new FileInputStream(temFile);
//                return is;
//            } else {
//                return is2;
//            }
//        } finally {
//            if (null != temFile) temFile.delete();
//            closeOutputStream(bos);
//            FileUtils.closeInputStream(is2);
//        }
//    }

    /**
     * 关闭输入流对象
     *
     * @param is
     */
    public final static void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    /**
     * 关闭输出流对象
     *
     * @param os
     */
    public final static void closeOutputStream(OutputStream os) {
        if (os != null) {
            try {
                os.flush();
                os.close();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    /**
     * 关闭Reader对象
     *
     * @param r
     */
    public final static void closeReader(Reader r) {
        if (r != null) {
            try {
                r.close();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    /**
     * 关闭Writer对象
     *
     * @param w
     */
    public final static void closeWriter(Writer w) {
        if (w != null) {
            try {
                w.flush();
                w.close();
            } catch (Exception e) {
                // Ignore
            }
        }
    }

    /**
     * 默认buffer的大小为1M
     */
    public static int BUFFER_SIZE = 1024 * 1024;

    /**
     * 默认的压缩文件的编码格式
     */
    public static String CHARSET_NAME = "GBK";

    protected final static Logger LOG = LoggerFactory
            .getLogger(FileUtils.class);

    /**
     * 字节转对象
     * @param bytes
     * @return
     */
    public static Object byteToObject(byte[] bytes) {
        Object obj = null;
        try {
            // bytearray to object
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 对象转字节
     * @param obj
     * @return
     */
    public static byte[] objectToByte(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }
}
