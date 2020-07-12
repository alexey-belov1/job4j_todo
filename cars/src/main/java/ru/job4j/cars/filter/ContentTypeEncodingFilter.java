package ru.job4j.cars.filter;

import javax.servlet.*;
import java.io.IOException;

public class ContentTypeEncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sresp, FilterChain chain) throws IOException, ServletException {
        sresp.setContentType("text/plain");
        sresp.setCharacterEncoding("windows-1251");
        chain.doFilter(sreq, sresp);
    }
}
