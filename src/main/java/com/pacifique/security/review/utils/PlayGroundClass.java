package com.pacifique.security.review.utils;

import com.pacifique.security.review.model.User;
import com.pacifique.security.review.proxies.NewAccountEmailServiceProxy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

public class PlayGroundClass {
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

//        var context = new AnnotationConfigApplicationContext(SpringSecurityReviewApplication.class);
//        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
//        String encode = passwordEncoder.encode("password");
//        System.out.println("passwordEncoder = " + encode);
//        var jwtObj = new AnnotationConfigApplicationContext(JwtService.class);
//        JwtService jwtService = jwtObj.getBean(JwtService.class);
//        AuthUser authUser = AuthUser.getUser(User.builder().email("email")
//                    .id(1L)
//                        .role(Role.ADMIN.name())
//                        .firstName("admin")
//                        .lastName("user")
//                .password(passwordEncoder.encode("pass"))
//                .permissions(Role.ADMIN.getPermissions())
//                .build());
//        String generatedToken = jwtService.generateToken(authUser);
//        System.out.println("generatedToken = " + generatedToken);

        var context = new AnnotationConfigApplicationContext(NewAccountEmailServiceProxy.class);
        NewAccountEmailServiceProxy serviceProxy = context.getBean(NewAccountEmailServiceProxy.class);

        serviceProxy.accountActivityNotification(User.builder()
                .lastName("last").firstName("first").email("emailtwo")
                .build());


    }


}
