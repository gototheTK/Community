package com.community.communityback.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.community.communityback.config.auth.PrincipalDetails;
import com.community.communityback.model.User;
import com.community.communityback.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.server.ResponseStatusException;

// 시큐리티가 filter 가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는것 있음
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있음.
// 권한이나 인증이 필요한 특정 주소가 아니라면 이 필터를 안탄다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

    UserRepository userRepository;

    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String ACCESS_TOKEN = "access_token";
    private static final  String TOKEN_TYPE = "Bearer ";


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        //TODO Auto-generated constructor stub
    }


    // 인증이나 권한이 필요한 주소용청이 있을 때 해당 필터를 타게됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                System.out.println(request.getRequestURI());
        if(request.getRequestURI().equals("/join")){
            System.out.println("회원가입 요청");
            chain.doFilter(request, response);
            return;
        }

        String refresh_token = request.getHeader(REFRESH_TOKEN);
        String access_token = request.getHeader(ACCESS_TOKEN);
        
        
        System.out.println("요청을 받게됨");
        System.out.println("리프레시토큰"+refresh_token);
        System.out.println("액세스토큰"+access_token);

        // header가 있는지 확인
        if(refresh_token == null||access_token==null|| 
        !access_token.startsWith(TOKEN_TYPE)||
        !refresh_token.startsWith(TOKEN_TYPE)){
            chain.doFilter(request, response);
            return;
        }
        // TODO Auto-generated method stub
        System.out.println("인증이나 권한이 필요한 주소가 요청됨");

       try{
            // JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
        refresh_token = request.getHeader(REFRESH_TOKEN).replace(TOKEN_TYPE, "");
        System.out.println(refresh_token);
        access_token = request.getHeader(ACCESS_TOKEN).replace(TOKEN_TYPE, "");
        System.out.println(access_token);
        System.out.println("토큰 다듬기");

        String refresh_id = decodeToken(REFRESH_TOKEN, refresh_token, "id");

        String access_id = decodeToken(ACCESS_TOKEN, access_token, "id");

        System.out.println("토큰 아이디");

        String refresh_username = decodeToken(REFRESH_TOKEN, refresh_token, "username");
        System.out.println("토큰 요소");
        String access_role = decodeToken(ACCESS_TOKEN, access_token, "role");

        System.out.println("토큰 요소");

        // 서명이 정상적으로됨
        if(refresh_id.equals(access_id)){
            User userEntity = userRepository.findByUsername(refresh_username).orElseThrow(()->{
                return new IllegalArgumentException("아이디를 찾을수 없습니다.");
            });
            System.out.println(userEntity.getId().toString().equals(access_id));
            System.out.println(userEntity.getRole().toString().equals(access_role));
            System.out.println("마지막권한인증");
            if(userEntity.getId().toString().equals(access_id)&&
            userEntity.getRole().toString().equals(access_role)){
                System.out.println("마지막권한인증성공");
                PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

                // Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                 access_token = JWT.create()
                .withSubject("access_token")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*30)))
                .withClaim("id", principalDetails.getUser().getId().toString())
                .withClaim("role", principalDetails.getAuthority().toString())
                .sign(Algorithm.HMAC512(ACCESS_TOKEN));

                
                response.addHeader(ACCESS_TOKEN, "Bearer " + access_token);
                
            }

        }

        System.out.println("권한인증이 완료되었습니다.");

       }catch(Exception e){
           e.getStackTrace();
           System.out.println("권한인증을 실패하였습니다.");
       }
        
        

        chain.doFilter(request, response);

    }

    protected String decodeToken(String secret, String token, String claim){
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getClaim(claim).asString();
    }


}
