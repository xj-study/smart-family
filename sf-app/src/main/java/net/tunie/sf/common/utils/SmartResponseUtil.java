package net.tunie.sf.common.utils;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.http.HttpServletResponse;
import net.tunie.sf.common.domain.ResponseDTO;

import java.io.IOException;

public class SmartResponseUtil {
    public static void write(HttpServletResponse response, ResponseDTO<?> responseDTO) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(JSON.toJSONString(responseDTO));
            response.flushBuffer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
