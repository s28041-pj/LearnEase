package pl.pjatk.learnease.configure.security;


import static pl.pjatk.learnease.entity.user.Role.ADMIN;
import static pl.pjatk.learnease.entity.user.Role.USER;

public class AuthorityList {

    protected static String[] anyAuthority = {String.valueOf(ADMIN), String.valueOf(USER)};

    protected static String adminAuthority = String.valueOf(ADMIN);
}
