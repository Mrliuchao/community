package com.imooc.community.advice;

import com.alibaba.fastjson.JSON;
import com.imooc.community.dto.ResultDTO;
import com.imooc.community.exception.CustomizeErrorCode;
import com.imooc.community.exception.CustomizeException;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle (HttpServletRequest request, Throwable e, Model model, HttpServletResponse response){
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            ResultDTO resultDTO ;
            //返回 JSON
            if (e instanceof CustomizeException){
              resultDTO =   ResultDTO.errorOf((CustomizeException)e);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ioe){

            }

            return null;
            }else {
            //错误页面跳转
            if (e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return  new ModelAndView("error");
        }

    }


}