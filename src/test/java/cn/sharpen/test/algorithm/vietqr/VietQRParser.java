package cn.sharpen.test.algorithm.vietqr;

import java.util.*;

public class VietQRParser {

    // 通用 TLV 解析
    public static Map<String, String> parseTLV(String input) {
        Map<String, String> result = new LinkedHashMap<>();
        int index = 0;
        while (index + 4 <= input.length()) {
            String tag = input.substring(index, index + 2);
            int len = Integer.parseInt(input.substring(index + 2, index + 4));
            int valueStart = index + 4;
            int valueEnd = valueStart + len;
            if (valueEnd > input.length()) break;

            String value = input.substring(valueStart, valueEnd);
            result.put(tag, value);
            index = valueEnd;

            if (tag.equals("63")) break; // CRC 是结束字段
        }
        return result;
    }

    // 解析主 VietQR 内容
    public static void parseVietQR(String qrData) {
        Map<String, String> mainTLV = parseTLV(qrData);

        // 解析 Tag 38 或 62（可能是 Merchant Account Info）
        if (mainTLV.containsKey("38") || mainTLV.containsKey("62")) {
            String merchantData = mainTLV.containsKey("38") ? mainTLV.get("38") : mainTLV.get("62");
            Map<String, String> merchantTLV = parseTLV(merchantData);

            String bin = merchantTLV.get("00"); // 有些格式用 00 是 BIN
            String cardOrAcct = merchantTLV.get("01"); // 有些格式用 01 放账号

            System.out.println("🏦 银行 BIN: " + bin);
            System.out.println("💳 卡号/账户: " + cardOrAcct);
        }

        // 提取收款人姓名（Tag 59）
        if (mainTLV.containsKey("59")) {
            System.out.println("👤 收款人姓名: " + mainTLV.get("59"));
        }

        // 国家代码
        if (mainTLV.containsKey("58")) {
            System.out.println("🌍 国家码: " + mainTLV.get("58"));
        }

        // CRC
        if (mainTLV.containsKey("63")) {
            System.out.println("✅ CRC 校验: " + mainTLV.get("63"));
        }
    }

    public static void main(String[] args) {
        // 示例 VietQR 数据
        String qrData = "00020101021138620010A000000727013200069704260118NLP6884099234567890208QRIBFTTA53037045802VN6304EC6A";
        // String qrData = "00020101021138630010A000000727013300069704260119NLP68840362589658960208QRIBFTTA53037045802VN6304990D";
        parseVietQR(qrData);
    }
}
