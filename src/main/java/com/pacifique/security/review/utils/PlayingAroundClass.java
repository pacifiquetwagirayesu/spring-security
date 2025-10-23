package com.pacifique.security.review.utils;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@RequiredArgsConstructor
public class PlayingAroundClass {
    public static void main(String[] args) {
//        Runnable task  = () ->{
//            System.out.println("args");
//        };
//
//        ExecutorService exs = Executors.newFixedThreadPool(1);
//        try {
//            exs.submit(task);
//        }catch (RejectedExecutionException e) {
//            System.out.println("e = " + e);
//        }finally {
//            exs.shutdown();
//        }

//        Role[] values = Role.values();
//        System.out.println("Arrays.asList(values) = " + Arrays.asList(values));
//        for (Role role : values) {
//            boolean delete = role.hasPermission("delete");
//            System.out.println("role = " + delete);
//        }

//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                "admin", "admin",
//                Arrays.asList(
//                        new SimpleGrantedAuthority(PREFIX + MANAGER)));
//        SecurityContextHolder.getContext().setAuthentication(token);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("authentication = " + authentication);
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        for (GrantedAuthority grantedAuthority : authorities) {
//            String replaced = grantedAuthority.getAuthority().replace(PREFIX, "");
//            boolean permission = Role.valueOf(replaced).hasPermission("read");
//            System.out.println("permission = " + permission);
//        }

//        byte[] encoded = Base64.getEncoder().encode("hello world security review".getBytes());
//        String s = new String(encoded);
//        System.out.println("s = " + s);

//        String superAdmin = "superAdminpermissionsuperAdminpermissionsuperAdminpermission";
//        byte[] bytes = Base64.encodeBase64(superAdmin.getBytes(), true);
//        System.out.println("new String(bytes) = " + new String(bytes));
//
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        String encode = passwordEncoder.encode("password");
//
//        System.out.println("encode = " + encode);

        Date date = new Date(System.currentTimeMillis() + 60000);
        System.out.println("date = " + date);


    }



}
