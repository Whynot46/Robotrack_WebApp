package com.robotrack.web_app;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

@Override
public UserDetails loadUserByUsername(String phone_number) throws UsernameNotFoundException {
    User user = DataBase.get_user(phone_number); // Получаем пользователя по номеру телефона
    System.out.println(phone_number);
    
    if (user == null) {
        throw new UsernameNotFoundException("User with phone_number: " + phone_number + " not found");
    }
    System.out.println(user.toString());
    Role user_role = DataBase.get_role(user.get_role_id());
    return new org.springframework.security.core.userdetails.User(
            user.get_phone_number(),
            user.get_password_hash(),
            AuthorityUtils.createAuthorityList("ROLE_" + user_role.get_name())
    );
}
}