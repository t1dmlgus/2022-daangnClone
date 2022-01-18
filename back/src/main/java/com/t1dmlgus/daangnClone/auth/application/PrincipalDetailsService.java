package com.t1dmlgus.daangnClone.auth.application;

import com.t1dmlgus.daangnClone.auth.domain.PrincipalDetails;
import com.t1dmlgus.daangnClone.user.domain.User;
import com.t1dmlgus.daangnClone.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User byEmail = userRepository.findByEmail(username);

        if (byEmail != null) {
            return new PrincipalDetails(byEmail);
        }
        return null;
    }
}
