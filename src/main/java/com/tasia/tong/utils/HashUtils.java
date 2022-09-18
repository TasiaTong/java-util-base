package com.tasia.tong.utils;

import com.tasia.tong.utils.exceptions.Md5UnexpectedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class HashUtils {

    public String md5(String rawString) {
        if (StringUtils.isBlank(rawString)) {
            throw new Md5UnexpectedException("md5 hash失败, rawString为空");
        }
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(rawString.getBytes());
            byte[] hash = md.digest();
            for (byte aHash : hash) {
                if ((0xff & aHash) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xff & aHash)));
                } else {
                    hexString.append(Integer.toHexString(0xff & aHash));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new Md5UnexpectedException("md5 hash失败", e);
        }
        return hexString.toString();
    }
}
